package web.pulso.integration.calendar;

import web.pulso.entity.Plantao;

public record PlantaoConfirmadoEvent(Plantao plantao, String email) {}