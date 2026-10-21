package web.pulso.service.resolver.escala;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;
import web.pulso.exception.business.ForbiddenException;
import web.pulso.exception.business.NotFoundException;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.SetorRepository;

@Component
@RequiredArgsConstructor
public class EscalaContextoResolver {

    private final ProfissionalRepository profissionalRepository;
    private final SetorRepository setorRepository;

    public EscalaContexto resolver(Long usuarioId, Long setorId) {

        Profissional lider = profissionalRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NotFoundException("Líder não encontrado"));

        Setor setor = setorRepository.findById(setorId)
                .orElseThrow(() -> new NotFoundException("Setor não encontrado"));

        if (!lider.eLiderNoSetor(setor)) {
            throw new ForbiddenException("Não é líder desse setor");
        }

        return new EscalaContexto(lider, setor);
    }
}