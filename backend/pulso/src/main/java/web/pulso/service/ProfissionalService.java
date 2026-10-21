package web.pulso.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.dto.response.profissional.ProfissionalEstatisticaResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.dto.response.profissional.estatistica.EstatisticaPlantoesAno;
import web.pulso.dto.response.profissional.estatistica.Mes;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.service.mappers.ProfissionalMapper;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.Especialidade;
import web.pulso.repository.PlantaoRepository;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.UsuarioRepository;
import web.pulso.repository.specification.ProfissionalSpecification;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ProfissionalMapper profissionalMapper;
    private final UsuarioRepository usuarioRepository;
    private final PlantaoRepository plantaoRepository;


    // Criar um novo profissional
    @Transactional
    public ProfissionalResponseDTO criar(ProfissionalRequestDTO dto, Long idUsuario) {

        if (idUsuario == null)
            throw new BadRequestException("O campo usuarioId é obrigatório para criar um profissional");

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + idUsuario));

        if (usuario.getInstituicao() != null || usuario.getProfissional() != null)
            throw new ConflictException(
                    "Usuário já possui instituição vinculada ou profissional vinculado");

        Profissional profissional = profissionalMapper.toEntity(dto);
        profissional.setUsuario(usuario);

        profissionalRepository.save(profissional);

        return profissionalMapper.toResponseDTO(profissional);
    }

    @Transactional
    public Profissional criarComUsuario(ProfissionalRequestDTO dto, Usuario usuario) {

        if (usuario == null)
            throw new BadRequestException("Usuário não pode ser nulo");

        if (usuario.getProfissional() != null)
            throw new ConflictException("Usuário já possui profissional vinculado");

        Profissional profissional = profissionalMapper.toEntity(dto);
        profissional.setUsuario(usuario);

        return profissionalRepository.save(profissional);
    }

    // Atualizar profissional existente
    @Transactional
    public ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO dto) {

        Profissional profissionalExistente = profissionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID " + id));

        if (dto.getNome() != null && !dto.getNome().isBlank())
            profissionalExistente.setNome(dto.getNome());

        if (dto.getEmails() != null && !dto.getEmails().isEmpty())
            profissionalExistente.setEmails(new ArrayList<>(dto.getEmails()));

        if (dto.getTelefone() != null && !dto.getTelefone().isBlank())
            profissionalExistente.setTelefone(dto.getTelefone());

        if (dto.getFotoPerfil() != null && !dto.getFotoPerfil().isBlank())
            profissionalExistente.setFotoPerfil(dto.getFotoPerfil());

        if (dto.getAnoFormacao() != null)
            profissionalExistente.setAnoFormacao(dto.getAnoFormacao());

        if (dto.getPix() != null && !dto.getPix().isBlank())
            profissionalExistente.setPix(dto.getPix());

        if (dto.getTipoPix() != null)
            profissionalExistente.setTipoPix(dto.getTipoPix());

        if (dto.getCrmNumero() != null && !dto.getCrmNumero().isBlank())
            profissionalExistente.setCrmNumero(dto.getCrmNumero());

        if (dto.getCrms() != null && !dto.getCrms().isEmpty())
            profissionalExistente.setCrms(new HashSet<>(dto.getCrms()));

        if (dto.getDataNasc() != null)
            profissionalExistente.setDataNasc(dto.getDataNasc());

        profissionalRepository.save(profissionalExistente);
        return profissionalMapper.toResponseDTO(profissionalExistente);
    }

    public List<ProfissionalEstatisticaResponseDTO> listar(Long idPlantao) {

        if (idPlantao == null)
            throw new BadRequestException("ID do plantão é obrigatório");

        Plantao plantao = plantaoRepository.findById(idPlantao)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado"));

        Especialidade especialidadeSetor =
                plantao.getEscala().getSetor().getEspecialidade();

        Long instituicaoId =
                plantao.getEscala().getSetor().getInstituicao().getId();

        Specification<Profissional> spec = Specification.allOf(
                ProfissionalSpecification.daEspecialidade(especialidadeSetor),
                ProfissionalSpecification.naInstituicao(instituicaoId),
                ProfissionalSpecification.naoConfirmadoNoPlantao(idPlantao)
        );

        List<Profissional> profissionais = profissionalRepository.findAll(spec);

        return profissionais.stream()
                .map(profissional -> {

                    ProfissionalEstatisticaResponseDTO dto =
                            profissionalMapper.toEstatisticaResponseDTO(profissional);

                    dto.setEstatisticasPlantoes(
                            gerarEstatisticaAnual(profissional.getId())
                    );

                    return dto;

                })
                .toList();
    }

    private EstatisticaPlantoesAno gerarEstatisticaAnual(Long profissionalId) {

        int anoAtual = LocalDate.now().getYear();

        Map<Integer, Integer> quantidadePorMes = new HashMap<>();

        // Inicializa os 12 meses com 0
        for (int mes = 1; mes <= 12; mes++) {
            quantidadePorMes.put(mes, 0);
        }

        // Combinados
        profissionalRepository.contarPlantoesCombinadosPorMes(profissionalId, anoAtual)
                .forEach(obj -> {
                    Integer mes = (Integer) obj[0];
                    Long qtd = (Long) obj[1];
                    quantidadePorMes.merge(mes, qtd.intValue(), Integer::sum);
                });

        // Confirmados
        profissionalRepository.contarPlantoesConfirmadosPorMes(profissionalId, anoAtual)
                .forEach(obj -> {
                    Integer mes = (Integer) obj[0];
                    Long qtd = (Long) obj[1];
                    quantidadePorMes.merge(mes, qtd.intValue(), Integer::sum);
                });

        List<Mes> meses = quantidadePorMes.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new Mes(e.getKey(), e.getValue()))
                .toList();

        return new EstatisticaPlantoesAno(anoAtual, meses);
    }

    // Buscar por ID
    public ProfissionalResponseDTO buscarPorId(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID " + id));

        return profissionalMapper.toResponseDTO(profissional);
    }

    // Deletar
    @Transactional
    public void deletar(Long id) {
        if (!profissionalRepository.existsById(id))
            throw new NotFoundException("Profissional não encontrado com ID " + id);

        profissionalRepository.deleteById(id);
    }
}
