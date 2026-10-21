package web.pulso.service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import web.pulso.dto.request.RespostaTrocaRequestDTO;
import web.pulso.exception.business.*;
import web.pulso.repository.PlantaoRepository;
import jakarta.transaction.Transactional;
import web.pulso.dto.request.TrocaRequestDTO;
import web.pulso.dto.response.troca.TrocaResponseDTO;
import web.pulso.service.mappers.TrocaMapper;
import web.pulso.entity.Plantao;
import web.pulso.entity.Profissional;
import web.pulso.entity.Troca;
import web.pulso.entity.enums.StatusTroca;
import web.pulso.entity.enums.TipoTroca;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.TrocaRepository;
import web.pulso.repository.specification.TrocaSpecification;

@Service
@RequiredArgsConstructor
public class TrocaService {

    private final TrocaMapper trocaMapper;
    private final TrocaRepository trocaRepository;
    private final ProfissionalRepository profissionalRepository;
    private final PlantaoRepository plantaoRepository;


    /**
     * 
     * Cria uma nova solicitação de troca de plantão
     * O profissional de origem é sempre obrigatório
     * O profissional de destino é opcional (define se é simples ou dupla)
     */
    @Transactional
    public TrocaResponseDTO criar(TrocaRequestDTO dto, Long idUsuario) {

        // Validações basicas
        if (idUsuario == null)
            throw new BadRequestException("O campo profissionalOrigemId é obrigatório.");

        if (dto.getPlantaoOrigemId() == null)
            throw new BadRequestException("O campo plantaoOrigemId é obrigatório.");

        // Buscas
        Profissional profissionalOrigem = profissionalRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new NotFoundException(
                        "Profissional de origem não encontrado com ID " + idUsuario));

        Plantao plantaoOrigem = plantaoRepository.findById(dto.getPlantaoOrigemId())
                .orElseThrow(() -> new NotFoundException(
                        "Plantão de origem não encontrado com ID " + dto.getPlantaoOrigemId()));

        Troca troca = trocaMapper.toEntity(dto);
        troca.setProfissionalOrigem(profissionalOrigem);
        troca.setPlantaoOrigem(plantaoOrigem);
        troca.setStatus(StatusTroca.PENDENTE); // Sempre inicia como pendente
        troca.setDataSolicitacao(LocalDateTime.now());

        // Troca dupla ou simples
        if (dto.getProfissionalDestinoId() != null) {
            Profissional profissionalDestino = profissionalRepository.findById(dto.getProfissionalDestinoId())
                    .orElseThrow(() -> new NotFoundException(
                            "Profissional de destino não encontrado com ID " + dto.getProfissionalDestinoId()));

            troca.setProfissionalDestino(profissionalDestino);
            troca.setTipo(TipoTroca.DUPLA);

            // plantaoDestino só será preenchido se o profissional destino aceitar a troca
            // posteriormente
            troca.setPlantaoDestino(null);
        } else {
            troca.setTipo(TipoTroca.SIMPLES);
        }

        trocaRepository.save(troca);
        return trocaMapper.toResponseDTO(troca);
    }

    // Metodo para aceitar troca
    @Transactional
    public TrocaResponseDTO aceitarTroca(Long trocaId, Long plantaoDestinoId) {
        Troca troca = trocaRepository.findById(trocaId)
                .orElseThrow(() -> new NotFoundException("Troca não encontrada com ID " + trocaId));

        if (troca.getStatus() != StatusTroca.PENDENTE)
            throw new ConflictException("A troca deve estar com status PENDENTE para poder ser aceita.");

        if (plantaoDestinoId == null)
            throw new BadRequestException("É necessário informar a alocação de destino para aceitar a troca.");

        // Busca o plantão de destino (do profissional que está aceitando)
        Plantao plantaoDestino = plantaoRepository.findById(plantaoDestinoId)
                .orElseThrow(() -> new NotFoundException(
                        "Plantão de destino não encontrado com ID " + plantaoDestinoId));

        Plantao plantaoOrigem = troca.getPlantaoOrigem();
        Profissional profissionalOrigem = troca.getProfissionalOrigem();
        Profissional profissionalDestino = troca.getProfissionalDestino();

        // Profissional de origem
        // Remove o profissional de origem do plantão de origem (se não encontrar não
        // gera erro)
        plantaoOrigem.getProfissionaisConfirmados().remove(profissionalOrigem);
        plantaoOrigem.getProfissionaisCombinados().remove(profissionalOrigem);
        profissionalOrigem.getPlantoesConfirmados().remove(plantaoOrigem);
        profissionalOrigem.getPlantoesCombinados().remove(plantaoOrigem);

        // Adiciona o profissional de origem ao plantão de destino
        plantaoDestino.getProfissionaisConfirmados().add(profissionalOrigem);
        profissionalOrigem.getPlantoesConfirmados().add(plantaoDestino);

        // Profissional de destino
        // Remove o profissional de destino do plantão de destino
        plantaoDestino.getProfissionaisConfirmados().remove(profissionalDestino);
        plantaoDestino.getProfissionaisCombinados().remove(profissionalDestino);
        profissionalDestino.getPlantoesConfirmados().remove(plantaoDestino);
        profissionalDestino.getPlantoesCombinados().remove(plantaoDestino);

        // Adiciona o profissional de destino ao plantão de origem
        plantaoOrigem.getProfissionaisConfirmados().add(profissionalDestino);
        profissionalDestino.getPlantoesConfirmados().add(plantaoOrigem);

        plantaoRepository.saveAll(List.of(plantaoOrigem, plantaoDestino));

        troca.setStatus(StatusTroca.CONFIRMADO);
        troca.setDataResposta(LocalDateTime.now());
        trocaRepository.save(troca);

        return trocaMapper.toResponseDTO(troca);
    }

    // Metodo para recusar troca
    @Transactional
    public TrocaResponseDTO recusarTroca(Long trocaId) {
        Troca troca = trocaRepository.findById(trocaId)
                .orElseThrow(() -> new NotFoundException("Troca não encontrada com ID " + trocaId));

        if (troca.getStatus() != StatusTroca.PENDENTE)
            throw new ConflictException("A troca deve estar com status PENDENTE para poder ser recusada.");

        troca.setStatus(StatusTroca.RECUSADO);
        trocaRepository.save(troca);

        return trocaMapper.toResponseDTO(troca);
    }

    public TrocaResponseDTO responderTroca(RespostaTrocaRequestDTO dto, Long idUsuario) {
        Boolean eDestino = trocaRepository.existsByIdAndProfissionalDestinoUsuarioId(dto.getTrocaId(), idUsuario);
        if (!eDestino)
            throw new ForbiddenException("Apenas o profissional de destino pode responder a troca");

        TrocaResponseDTO resposta;

        if (Boolean.TRUE.equals(dto.getAceita()))
            resposta = aceitarTroca(dto.getTrocaId(), dto.getPlantaoDestinoId());
        else
            resposta = recusarTroca(dto.getTrocaId());
        return resposta;
    }

    @Transactional
    public TrocaResponseDTO atualizar(Long id, TrocaRequestDTO dto) {
        Troca trocaExistente = trocaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Troca não encontrada com ID " + id));

        if (!trocaExistente.getStatus().equals(StatusTroca.PENDENTE))
            throw new ConflictException("Só é possível atualizar trocas com status PENDENTE.");

        // Atualiza comentario (se vier no DTO)
        if (dto.getComentario() != null && !dto.getComentario().isBlank())
            trocaExistente.setComentario(dto.getComentario());

        // Atualiza o plantão de origem (se vier diferente)
        if (dto.getPlantaoOrigemId() != null
                && !dto.getPlantaoOrigemId().equals(trocaExistente.getPlantaoOrigem().getId())) {
            Plantao plantaoOrigem = plantaoRepository.findById(dto.getPlantaoOrigemId())
                    .orElseThrow(() -> new NotFoundException(
                            "Plantão de origem não encontrado com ID " + dto.getPlantaoOrigemId()));
            trocaExistente.setPlantaoOrigem(plantaoOrigem);
        }

        // Atualiza o profissional de destino (se existir)
        if (dto.getProfissionalDestinoId() != null
                && !dto.getProfissionalDestinoId().equals(trocaExistente.getProfissionalDestino().getId())) {
            Profissional profissionalDestino = profissionalRepository.findById(dto.getProfissionalDestinoId())
                    .orElseThrow(() -> new NotFoundException(
                            "Profissional de destino não encontrado com ID " +
                                    dto.getProfissionalDestinoId()));
            trocaExistente.setProfissionalDestino(profissionalDestino);
            trocaExistente.setTipo(TipoTroca.DUPLA);
        } else {
            trocaExistente.setProfissionalDestino(null);
            trocaExistente.setTipo(TipoTroca.SIMPLES);
        }

        // Sempre que é atualizada, volta para PENDENTE (se ainda não estiver
        // confirmada)
        if (trocaExistente.getStatus() != StatusTroca.CONFIRMADO)
            trocaExistente.setStatus(StatusTroca.PENDENTE);

        trocaRepository.save(trocaExistente);
        return trocaMapper.toResponseDTO(trocaExistente);
    }

    public List<TrocaResponseDTO> listar(
            Long idUsuario,
            String comentario,
            TipoTroca tipo,
            StatusTroca status,
            Long idProfissionalOrigem,
            Long idProfissionalDestino,
            Long idPlantaoOrigem,
            Long idPlantaoDestino,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    ) {

        Specification<Troca> spec = Specification.allOf(
                TrocaSpecification.doUsuario(idUsuario),
                TrocaSpecification.comentario(comentario),
                TrocaSpecification.tipo(tipo),
                TrocaSpecification.status(status),
                TrocaSpecification.profissionalOrigem(idProfissionalOrigem),
                TrocaSpecification.profissionalDestino(idProfissionalDestino),
                TrocaSpecification.plantaoOrigem(idPlantaoOrigem),
                TrocaSpecification.plantaoDestino(idPlantaoDestino),
                TrocaSpecification.dataSolicitacao(dataInicio, dataFim)
        );

        List<Troca> trocas = trocaRepository.findAll(spec);

        return trocas.stream()
                .map(trocaMapper::toResponseDTO)
                .toList();
    }

    public TrocaResponseDTO buscarPorId(Long id) {
        Troca troca = trocaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Troca não encontrada com ID " + id));
        return trocaMapper.toResponseDTO(troca);
    }

    public void deletar(Long id) {
        Troca troca = trocaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Troca não encontrada com ID " + id));

        if (troca.getStatus() == StatusTroca.CONFIRMADO)
            throw new ConflictException("Não é possível excluir uma troca já confirmada.");

        trocaRepository.delete(troca);
    }
}
