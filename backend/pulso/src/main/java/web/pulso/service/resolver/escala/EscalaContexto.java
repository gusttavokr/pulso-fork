package web.pulso.service.resolver.escala;

import web.pulso.entity.Profissional;
import web.pulso.entity.Setor;

public record EscalaContexto(
        Profissional lider,
        Setor setor
) {}