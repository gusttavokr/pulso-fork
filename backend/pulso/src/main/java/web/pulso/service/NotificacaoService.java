package web.pulso.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import web.pulso.dto.response.notificacao.NotificacaoDTO;
import web.pulso.exception.business.NotFoundException;
import web.pulso.service.mappers.NotificacaoMapper;
import web.pulso.entity.Notificacao;
import web.pulso.entity.enums.StatusNotificacao;
import web.pulso.repository.NotificacaoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificacaoMapper mapper;


    public void despacho(NotificacaoDTO notificacaoDTO) {
        Notificacao entidade = mapper.toEntity(notificacaoDTO);
        entidade.setStatus(StatusNotificacao.ENVIADA);
        entidade = notificacaoRepo.save(entidade);

        NotificacaoDTO response = mapper.toResponseDTO(entidade);

        messagingTemplate.convertAndSendToUser(
                response.idDestinatario().toString(),
                "/queue/notificacoes",
                response);
    }

    public void foiLido(Long notificacaoId) {
        Notificacao notificacao = notificacaoRepo.findById(notificacaoId)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada"));

        notificacao.setStatus(StatusNotificacao.LIDA);
        notificacao.setDataLeitura(LocalDateTime.now());

        notificacaoRepo.save(notificacao);
    }

    public List<NotificacaoDTO> buscarNotificacaoDeUsuarioId(Long id) {
        return notificacaoRepo.findByDestinatarioId(id).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public NotificacaoDTO buscarPorId(Long id) {
        return mapper.toResponseDTO(
                notificacaoRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("Notificação não encontrada")));
    }

}
