package web.pulso.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.pulso.dto.request.EscalaRequestDTO;
import web.pulso.dto.response.escala.EscalaResponseDTO;
import web.pulso.dto.response.escala.EscalaSimpleResponseDTO;
import web.pulso.exception.business.*;
import web.pulso.service.mappers.EscalaMapper;
import web.pulso.entity.Escala;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.enums.StatusEscala;
import web.pulso.repository.EscalaRepository;
import web.pulso.repository.specification.EscalaSpecification;
import web.pulso.service.patterns.builder.EscalaBuilder;
import web.pulso.service.patterns.factory.generatePlantaoStrategy.GeracaoPlantoesStrategy;
import web.pulso.service.patterns.factory.generatePlantaoStrategy.GeradorPlantoesFactory;
import web.pulso.service.resolver.escala.EscalaContexto;
import web.pulso.service.resolver.escala.EscalaContextoResolver;
import web.pulso.utils.DataUtils;

@Service
@RequiredArgsConstructor
public class EscalaService {
        private final EscalaMapper escalaMapper;
        private final EscalaRepository escalaRepository;
        private final EscalaContextoResolver contextoResolver;
        private final PlantaoService plantaoService;

        @Transactional
        public EscalaSimpleResponseDTO criar(EscalaRequestDTO dto, Long idUsuario) {

                EscalaContexto contexto = contextoResolver.resolver(idUsuario, dto.getSetorId());

                Profissional lider = contexto.lider();
                Setor setor = contexto.setor();

                if (escalaRepository.existsEscalaNoSetorNoMes(setor.getId(), dto.getMes())) {
                        throw new ConflictException(
                                        "Já existe uma escala cadastrada para este setor no mês de referência informado");
                }

                List<Plantao> plantoes = gerarPlantoes(
                                dto.getCopia(),
                                dto.getMes(),
                                setor.getId(),
                                idUsuario);

                Escala escala = EscalaBuilder.builder()
                                .nome(dto.getNome())
                                .mesReferencia(dto.getMes())
                                .lider(lider)
                                .setor(setor)
                                .plantoes(plantoes)
                                .build();

                escalaRepository.save(escala);
                return escalaMapper.toSimpleResponseDTO(escala);
        }

        private List<Plantao> gerarPlantoes(Boolean copia, LocalDate mesReferencia, Long setorId, Long usuarioId) {
                GeracaoPlantoesStrategy strategy = GeradorPlantoesFactory.escolherStrategy(copia);

                Map<LocalDateTime, Plantao> contexto = copia
                                ? plantaoService.criarContextoParaCopia(mesReferencia, setorId, usuarioId)
                                : Collections.emptyMap();

                return strategy.gerar(mesReferencia, contexto);

        }

        @Transactional
        public EscalaSimpleResponseDTO atualizar(Long id, EscalaRequestDTO dto) {

                Escala escalaExistente = escalaRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Escala não encontrada com ID " + id));

                escalaExistente.validarAlteracoesPermitidas(dto.getNome(), dto.getSetorId(), dto.getMes());

                escalaExistente.setNome(dto.getNome());

                Escala escalaAtualizada = escalaRepository.save(escalaExistente);

                return escalaMapper.toSimpleResponseDTO(escalaAtualizada);

        }

        public EscalaResponseDTO buscarPorId(Long id) {
                Escala escalaBuscada = escalaRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Nenhuma escala encontrada com o id: " + id));

                return escalaMapper.toResponseDTO(escalaBuscada);
        }

        public List<EscalaSimpleResponseDTO> listar(
                        Long usuarioId,
                        String mesReferencia,
                        StatusEscala status,
                        Especialidade especialidade,
                        Boolean liderEscala,
                        Boolean instituicao) {

                if (!Boolean.TRUE.equals(liderEscala) && !Boolean.TRUE.equals(instituicao)) {
                        throw new BadRequestException(
                                        "Pelo menos um dos parâmetros: 'liderEscala' ou 'instituicao' deve ser true.");
                }

                LocalDate dataReferencia = DataUtils.converterMesReferenciaParaData(mesReferencia);
                Specification<Escala> spec = Specification.allOf(
                                EscalaSpecification.mesReferencia(dataReferencia),
                                EscalaSpecification.status(status),
                                EscalaSpecification.especialidade(especialidade),

                                Boolean.TRUE.equals(liderEscala)
                                                ? EscalaSpecification.doLiderEscala(usuarioId)
                                                : null,
                                Boolean.TRUE.equals(instituicao)
                                                ? EscalaSpecification.daInstituicao(usuarioId)
                                                : null);

                return escalaRepository.findAll(spec)
                                .stream()
                                .map(escalaMapper::toSimpleResponseDTO)
                                .toList();
        }

        @Transactional
        public void excluir(Long id) {
                Escala escala = escalaRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Nenhuma escala encontrada com o id: " + id));

                if (!escala.podeSerExcluida())
                        throw new UnprocessableEntityException(
                                        "A escala não pode ser excluída porque já está publicada.");

                escalaRepository.delete(escala);
        }

        @Transactional
        public EscalaSimpleResponseDTO publicar(Long id, Boolean publicada) {
                Escala escala = escalaRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Escala não encontrada com ID " + id));

                escala.publicar(publicada);
                escalaRepository.save(escala);

                return escalaMapper.toSimpleResponseDTO(escala);
        }
}
