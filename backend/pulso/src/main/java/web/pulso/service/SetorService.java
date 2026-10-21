package web.pulso.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.pulso.dto.request.SetorRequestDTO;
import web.pulso.dto.response.setor.SetorResponseDTO;
import web.pulso.exception.business.BadRequestException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.service.mappers.SetorMapper;
import web.pulso.entity.Setor;
import web.pulso.entity.enums.Especialidade;
import web.pulso.entity.Instituicao;
import web.pulso.repository.SetorRepository;
import web.pulso.repository.InstituicaoRepository;
import web.pulso.repository.specification.SetorSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;
    private final InstituicaoRepository instituicaoRepository;



    @Transactional
    public SetorResponseDTO criar(SetorRequestDTO setorRequestDTO, Long idUsuario) {

        if (idUsuario == null)
            throw new BadRequestException("O campo idUsuario é obrigatório para criar um setor");

        Instituicao instituicao = instituicaoRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new NotFoundException(
                        "Instituição não encontrada com ID: " + idUsuario));

        Setor setor = setorMapper.toEntity(setorRequestDTO);
        setor.setInstituicao(instituicao);
        
        setor.validar();

        Setor setorSalvo = setorRepository.save(setor);
        return setorMapper.toResponseDTO(setorSalvo);
    }

    @Transactional
    public SetorResponseDTO atualizar(Long id, SetorRequestDTO setorRequestDTO) {
        Setor setorExistente = setorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Setor não encontrado com ID: " + id));

        if (setorRequestDTO.getNome() != null && !setorRequestDTO.getNome().isBlank())
            setorExistente.setNome(setorRequestDTO.getNome());

        if (setorRequestDTO.getEspecialidade() != null)
            setorExistente.setEspecialidade(setorRequestDTO.getEspecialidade());

        setorExistente.validar();

        Setor setorAtualizado = setorRepository.save(setorExistente);
        return setorMapper.toResponseDTO(setorAtualizado);
    }

    public List<SetorResponseDTO> listar(
            String nome,
            Especialidade especialidade,
            Long idUsuario
    ) {

        Long instituicaoId = null;

        if (idUsuario != null) {

            instituicaoId = instituicaoRepository.findByUsuarioId(idUsuario)
                    .orElseThrow(() -> new NotFoundException(
                            "Instituição não encontrada para o usuário ID " + idUsuario))
                    .getId();
        }

        Specification<Setor> spec = Specification.allOf(

                SetorSpecification.nome(nome),

                SetorSpecification.especialidade(especialidade),

                SetorSpecification.daInstituicao(instituicaoId)
        );

        List<Setor> setores = setorRepository.findAll(spec);

        return setores.stream()
                .map(setorMapper::toResponseDTO)
                .toList();
    }

    public SetorResponseDTO buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Setor não encontrado com ID: " + id));
        return setorMapper.toResponseDTO(setor);
    }

    public void deletar(Long id) {
        if (!setorRepository.existsById(id)) {
            throw new NotFoundException("Setor não encontrado com ID: " + id);
        }
        setorRepository.deleteById(id);
    }
}