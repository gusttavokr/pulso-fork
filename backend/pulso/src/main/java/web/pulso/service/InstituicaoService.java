package web.pulso.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import web.pulso.dto.request.InstituicaoRequestDTO;
import web.pulso.dto.request.VinculoLiderEscalaRequestDTO;
import web.pulso.dto.request.VinculoRequestDTO;
import web.pulso.dto.response.instituicao.InstituicaoResponseDTO;
import web.pulso.dto.response.instituicao.status.InstituicaoProfissionaisStatusResponseDTO;
import web.pulso.exception.business.ConflictException;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.service.mappers.InstituicaoMapper;
import web.pulso.entity.Instituicao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.entity.Usuario;
import web.pulso.entity.enums.Especialidade;
import web.pulso.repository.InstituicaoRepository;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.UsuarioRepository;
import web.pulso.repository.specification.InstituicaoSpecification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoMapper instituicaoMapper;
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Instituicao criarComUsuario(
            InstituicaoRequestDTO dto,
            Usuario usuario) {

        if (usuario.getInstituicao() != null || usuario.getProfissional() != null)
            throw new ConflictException(
                    "Usuário já possui instituição vinculada ou profissional vinculado");

        Instituicao instituicao = instituicaoMapper.toEntity(dto);
        instituicao.setUsuario(usuario);

        return instituicaoRepository.save(instituicao);
    }

    @Transactional
    public InstituicaoResponseDTO criar(
            InstituicaoRequestDTO dto,
            Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException(
                        "Usuário não encontrado"));

        Instituicao instituicao = criarComUsuario(dto, usuario);

        return instituicaoMapper.toResponseDTO(instituicao);
    }

    /*
     * =====================================================
     * 🔹 ATUALIZAÇÃO
     * =====================================================
     */

    @Transactional
    public InstituicaoResponseDTO atualizar(
            Long id,
            InstituicaoRequestDTO dto) {

        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Instituição não encontrada com ID: " + id));

        if (dto.getCnpj() != null && !dto.getCnpj().isBlank())
            instituicao.setCnpj(dto.getCnpj());

        if (dto.getNome() != null && !dto.getNome().isBlank())
            instituicao.setNome(dto.getNome());

        if (dto.getEmails() != null && !dto.getEmails().isEmpty())
            instituicao.setEmails(new ArrayList<>(dto.getEmails()));

        if (dto.getTelefone() != null && !dto.getTelefone().isBlank())
            instituicao.setTelefone(dto.getTelefone());

        if (dto.getFotoPerfil() != null && !dto.getFotoPerfil().isBlank())
            instituicao.setFotoPerfil(dto.getFotoPerfil());

        if (dto.getEndereco() != null && !dto.getEndereco().isBlank())
            instituicao.setEndereco(dto.getEndereco());

        if (dto.getExigeVinculo() != null)
            instituicao.setExigeVinculo(dto.getExigeVinculo());

        return instituicaoMapper.toResponseDTO(
                instituicaoRepository.save(instituicao));
    }

    /*
     * =====================================================
     * 🔹 BUSCAS
     * =====================================================
     */

    public InstituicaoResponseDTO buscarPorId(Long id) {
        return instituicaoRepository.findById(id)
                .map(instituicaoMapper::toResponseDTO)
                .orElseThrow(() -> new NotFoundException(
                        "Instituição não encontrada com ID: " + id));
    }

    public List<InstituicaoResponseDTO> listar(
            Long idUsuario,
            String nome,
            String email,
            String telefone,
            String endereco,
            String cnpj,
            Boolean exigeVinculo) {

        Profissional lider = null;

        if (idUsuario != null) {
            lider = profissionalRepository.findByUsuarioId(idUsuario)
                    .orElseThrow(() -> new NotFoundException(
                            "Profissional não encontrado"));

            if (lider.getInstituicoesLiderEscala() == null ||
                    lider.getInstituicoesLiderEscala().isEmpty()) {

                throw new ForbiddenException("O profissional não é líder de escala");
            }
        }

        Specification<Instituicao> spec = Specification.allOf(
                InstituicaoSpecification.nomeContem(nome),
                InstituicaoSpecification.emailContem(email),
                InstituicaoSpecification.telefoneContem(telefone),
                InstituicaoSpecification.enderecoContem(endereco),
                InstituicaoSpecification.cnpjIgual(cnpj),
                InstituicaoSpecification.exigeVinculo(exigeVinculo),
                InstituicaoSpecification.liderVinculado(lider));

        List<Instituicao> instituicoes = instituicaoRepository.findAll(spec);

        if (lider != null) {
            Set<Especialidade> especialidadesLider = lider.getEspecialidades();

            instituicoes.forEach(inst -> {
                List<Setor> setoresFiltrados = inst.getSetores().stream()
                        .filter(setor -> especialidadesLider.contains(setor.getEspecialidade()))
                        .toList();
                inst.setSetores(setoresFiltrados);
            });
        }

        return instituicoes.stream()
                .map(instituicaoMapper::toResponseDTO)
                .toList();
    }

    /*
     * =====================================================
     * 🔹 REMOÇÃO
     * =====================================================
     */

    @Transactional
    public void deletar(Long id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new NotFoundException(
                    "Instituição não encontrada com ID: " + id);
        }
        instituicaoRepository.deleteById(id);
    }

    /*
     * =====================================================
     * 🔹 VÍNCULO COM PROFISSIONAL
     * =====================================================
     */

    @Transactional
    public void vincularProfissional(VinculoRequestDTO dto, Long idUsuario) {

        Instituicao instituicao = instituicaoRepository
                .findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        Profissional profissional = profissionalRepository
                .findById(dto.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));

        if (Boolean.TRUE.equals(dto.getIsPj())) {

            if (!instituicao.getProfisionais_pj().contains(profissional)) {
                instituicao.getProfisionais_pj().add(profissional);
                profissional.getInstituicoesPj().add(instituicao);
            }

        } else {

            if (!instituicao.getProfissionais_clt().contains(profissional)) {
                instituicao.getProfissionais_clt().add(profissional);
                profissional.getInstituicoesClt().add(instituicao);
            }
        }
    }

    @Transactional
    public void setarLiderEscala(
            VinculoLiderEscalaRequestDTO dto,
            Long idUsuario) {

        Instituicao instituicao = instituicaoRepository
                .findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Instituição não encontrada"));

        Profissional lider = profissionalRepository
                .findById(dto.getLiderEscalaId())
                .orElseThrow(() -> new NotFoundException("Líder de escala não encontrado"));

        if (!instituicao.getProfisionais_pj().contains(lider) &&
                !instituicao.getProfissionais_clt().contains(lider)) {
            throw new ConflictException(
                    "O profissional deve estar vinculado à instituição como PJ ou CLT para ser líder de escala");
        }

        if (!instituicao.getProfissionais_lider_escala().contains(lider)) {
            instituicao.getProfissionais_lider_escala().add(lider);
            lider.getInstituicoesLiderEscala().add(instituicao);
        }
    }

    @Transactional
    public InstituicaoProfissionaisStatusResponseDTO obterStatusInstituicao(Long idUsuario) {
        Instituicao instituicao = instituicaoRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new NotFoundException("instituição não encontrada"));

        var profissionaisStatus = new InstituicaoProfissionaisStatusResponseDTO();
        var mesAtual = LocalDate.now().withDayOfMonth(1);

        Long alocados = profissionalRepository
                .countMedicosAlocadosNoMes(instituicao.getId(), mesAtual);
        Long total = (long) instituicao.getTodosMedicos().size();
        profissionaisStatus.setTotal(total);
        profissionaisStatus.setAlocados(alocados);
        profissionaisStatus.setNaoAlocados(total - alocados);
        return profissionaisStatus;
    }
}
