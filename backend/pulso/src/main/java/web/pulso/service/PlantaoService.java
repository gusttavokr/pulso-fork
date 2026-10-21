package web.pulso.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.pulso.dto.filtros.PlantaoFilter;
import web.pulso.dto.request.PlantaoRequestDTO;
import web.pulso.dto.response.plantao.PlantaoResponseDTO;
import web.pulso.dto.response.plantao.PlantaoSimpleResponseDTO;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.integration.calendar.PlantaoConfirmadoEvent;
import web.pulso.service.mappers.PlantaoMapper;
import web.pulso.entity.Escala;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;

import web.pulso.entity.enums.StatusTroca;
import web.pulso.repository.EscalaRepository;
import web.pulso.repository.PlantaoRepository;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.TrocaRepository;
import web.pulso.repository.specification.PlantaoSpecification;
import web.pulso.utils.EventDispatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.YearMonth;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantaoService {
    private final PlantaoRepository plantaoRepository;
    private final EscalaRepository escalaRepository;
    private final PlantaoMapper plantaoMapper;
    private final ProfissionalRepository profissionalRepository;
    private final TrocaRepository trocaRepository;

    @Transactional
    @CacheEvict(value = "plantoesCache", allEntries = true)
    public PlantaoResponseDTO criar(PlantaoRequestDTO dto) {

        if (dto.getEscalaId() == null)
            throw new BadRequestException("O campo escalaId é obrigatório para criar um plantão");

        Escala escala = escalaRepository.findById(dto.getEscalaId())
                .orElseThrow(() -> new NotFoundException("Escala não encontrada com ID " + dto.getEscalaId()));

        LocalDateTime agora = LocalDateTime.now();
        if (dto.getHoraEntrada().isBefore(agora.plusDays(7)))
            throw new ConflictException("Não é permitido criar plantões com menos de 7 dias de antecedência.");

        LocalDate mesReferencia = escala.getMesReferencia();
        YearMonth mes = YearMonth.from(mesReferencia);

        LocalDateTime inicioMes = mes.atDay(1).atStartOfDay();
        LocalDateTime fimMes = mes.plusMonths(1).atDay(1).atStartOfDay();

        LocalDateTime entrada = dto.getHoraEntrada();
        LocalDateTime saida = dto.getHoraSaida();

        boolean entradaValida = !entrada.isBefore(inicioMes) && entrada.isBefore(fimMes);
        boolean saidaValida = !saida.isBefore(inicioMes) && !saida.isAfter(fimMes.plusDays(1));

        if (!entradaValida || !saidaValida)
            throw new BadRequestException(
                    "O horário de entrada e saída deve estar dentro do mês de referência da escala.");

        Plantao plantao = plantaoMapper.toEntity(dto);
        plantao.setEscala(escala);

        Plantao salvo = plantaoRepository.save(plantao);
        return plantaoMapper.toResponseDTO(salvo, null);
    }

    @Transactional
    @CacheEvict(value = "plantoesCache", allEntries = true)
    public PlantaoResponseDTO atualizar(Long id, PlantaoRequestDTO dto) {
        Plantao plantao = plantaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado com ID " + id));

        if (plantao.getEscala().isPublicada())
            throw new ConflictException("Não é possível alterar plantões de uma escala publicada.");

        if (!plantao.mesmaEscalaId(dto.getEscalaId()))
            throw new ConflictException("Não é permitido mover o plantão para outra escala.");

        plantaoMapper.atualizarEntityComDTO(plantao, dto);

        plantaoRepository.save(plantao);

        Troca trocaMaisRecente = trocaRepository
                .findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao)
                .orElse(null);

        return plantaoMapper.toResponseDTO(plantao, trocaMaisRecente);
    }

    @Cacheable(value = "plantoesCache", key = "#filter.hashCode()")
    public List<PlantaoSimpleResponseDTO> listar(PlantaoFilter filter) {

        Specification<Plantao> contextoSpec;

        if (Boolean.TRUE.equals(filter.getAbertos())) {

            Profissional profissionalObj = profissionalRepository
                    .findByUsuarioId(filter.getIdUsuario())
                    .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));

            contextoSpec = PlantaoSpecification.plantoesAbertos(profissionalObj);

        } else if (Boolean.TRUE.equals(filter.getProfissional())) {
            contextoSpec = PlantaoSpecification.doProfissional(filter.getIdUsuario());
        } else if (Boolean.TRUE.equals(filter.getLiderEscala())) {
            contextoSpec = PlantaoSpecification.doLiderEscala(filter.getIdUsuario());
        } else if (Boolean.TRUE.equals(filter.getInstituicao())) {
            contextoSpec = PlantaoSpecification.daInstituicao(filter.getIdUsuario());
        } else {
            throw new BadRequestException(
                    "Pelo menos um dos parâmetros 'profissional', 'liderEscala' ou 'instituicao' deve ser true.");
        }

        Specification<Plantao> filtros = Specification.allOf(
                PlantaoSpecification.data(filter.getData()),
                PlantaoSpecification.especialidade(filter.getEspecialidade()),
                PlantaoSpecification.precoMin(filter.getPrecoMin()),
                PlantaoSpecification.precoMax(filter.getPrecoMax()),
                PlantaoSpecification.horarios(filter.getHorariosInicio(), filter.getHorariosFim()),
                PlantaoSpecification.ehNovoPlantao(filter.getEhNovoPlantao()),
                PlantaoSpecification.ehRepasse(filter.getEhRepasse()),
                PlantaoSpecification.ehTroca(filter.getEhTroca()));

        Specification<Plantao> spec = Specification.allOf(contextoSpec, filtros);

        List<Plantao> plantoes = plantaoRepository.findAll(spec);

        return plantoes.stream()
                .map(plantaoMapper::toSimpleResponseDTO)
                .toList();
    }

    public PlantaoResponseDTO buscarPorId(Long id) {
        Plantao plantao = plantaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado com ID " + id));

        Troca trocaMaisRecente = trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao)
                .orElse(null);
        ;

        return plantaoMapper.toResponseDTO(plantao, trocaMaisRecente);
    }

    @Transactional
    @CacheEvict(value = "plantoesCache", allEntries = true)
    public void deletar(Long id) {
        Plantao plantao = plantaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado com ID " + id));

        if (plantao.getEscala().isPublicada())
            throw new ConflictException("Não é possível deletar plantões de uma escala publicada.");

        if (plantao.getHoraEntrada().isBefore(LocalDateTime.now()))
            throw new ConflictException("Não é possível deletar plantões com hora de entrada já passada.");

        plantaoRepository.delete(plantao);
    }

    @Transactional
    @CacheEvict(value = "plantoesCache", allEntries = true)
    public PlantaoResponseDTO pegarPlantao(Long plantaoId, Long usuarioId) {

        Profissional profissional = profissionalRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NotFoundException(
                        "Profissional não encontrado para o usuário ID " + usuarioId));

        Plantao plantao = plantaoRepository.findById(plantaoId)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado com ID " + plantaoId));

        Escala escala = plantao.getEscala();

        if (escala.isPublicada())
            throw new ConflictException("Não é possível pegar um plantão de uma escala que não está publicada.");

        if (!profissional.possuiEspecialidadeParaSetor(escala.getSetor()))
            throw new ConflictException("O profissional não possui a especialidade exigida pelo setor da escala.");

        plantao.verificarProfissionalJaVinculado(profissional);
        plantao.adicionarProfissionalConfirmado(profissional);

        Optional<Troca> trocaSimples = trocaRepository
                .findByPlantaoOrigemAndStatusAndProfissionalDestinoIsNull(plantao, StatusTroca.PENDENTE);

        trocaSimples.ifPresent(t -> {
            t.confirmarParaProfissional(profissional);
            trocaRepository.save(t);
        });

        profissionalRepository.save(profissional);
        plantaoRepository.save(plantao);

        Troca trocaMaisRecente = trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao)
                .orElse(null);

        EventDispatcher.publish(new PlantaoConfirmadoEvent(plantao, profissional.getUsuario().getEmail()));
        return plantaoMapper.toResponseDTO(plantao, trocaMaisRecente);
    }

    @Transactional
    @CacheEvict(value = "plantoesCache", allEntries = true)
    public PlantaoResponseDTO atualizarPlantaoCombinado(
            Long plantaoId,
            Long profissionalUsuarioId,
            Long liderUsuarioId,
            boolean adicionar) {

        Plantao plantao = validarPermissaoLider(plantaoId, liderUsuarioId);

        Profissional profissional = profissionalRepository.findByUsuarioId(profissionalUsuarioId)
                .orElseThrow(() -> new NotFoundException(
                        "Profissional não encontrado para o usuário ID " + profissionalUsuarioId));

        if (adicionar) {
            plantao.adicionarProfissionalCombinado(profissional);
        } else {
            plantao.removerProfissionalCombinado(profissional);
        }

        profissionalRepository.save(profissional);
        plantaoRepository.save(plantao);

        Troca trocaMaisRecente = trocaRepository.findFirstByPlantaoOrigemOrderByDataSolicitacaoDesc(plantao)
                .orElse(null);
        return plantaoMapper.toResponseDTO(plantao, trocaMaisRecente);
    }

    private Plantao validarPermissaoLider(Long plantaoId, Long liderUsuarioId) {

        // Profissional lider =
        // profissionalRepository.findByUsuarioIdWithInstituicoes(liderUsuarioId)
        // .orElseThrow(() -> new NotFoundException(
        // "Líder não encontrado para o usuário ID " + liderUsuarioId));

        Plantao plantao = plantaoRepository.findByIdAndLiderValido(plantaoId, liderUsuarioId)
                .orElseThrow(() -> new ForbiddenException(
                        "O profissional não é líder da instituição do plantão ou plantão não existe"));

        Escala escala = plantao.getEscala();

        if (escala.isPublicada()) {
            throw new ConflictException("Não é possível alterar o profissional de uma escala já publicada.");
        }

        return plantao;
    }

    public Map<LocalDateTime, Plantao> criarContextoParaCopia(LocalDate mes, Long setorId, Long liderId) {

        List<Plantao> anteriores = plantaoRepository.buscarPlantoesUltimaEscalaAnterior(
                setorId,
                liderId,
                mes);
        if (anteriores.isEmpty()) {
            throw new NotFoundException("Não existe escala anterior para cópia");
        }
        return anteriores.stream()
                .collect(Collectors.toMap(
                        Plantao::getHoraEntrada,
                        p -> p));
    }

    @Transactional
    @CacheEvict(value = "plantoesCache", allEntries = true)
    public PlantaoResponseDTO atualizarComentario(
            Long idPlantao,
            String comentario,
            Long idUsuario) {

        Plantao plantao = plantaoRepository.findById(idPlantao)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado"));

        Profissional profissional = profissionalRepository.findByUsuarioIdWithInstituicoes(idUsuario)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));

        plantao.validarPermissaoEditarComentario(profissional);

        plantao.setComentario((comentario == null || comentario.isBlank()) ? null : comentario.trim());

        plantaoRepository.save(plantao);

        return plantaoMapper.toResponseDTO(plantao, null);
    }

}
