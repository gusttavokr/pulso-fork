package web.pulso.integration.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GoogleCalendarListener {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @EventListener
    public void onPlantaoConfirmado(PlantaoConfirmadoEvent event) {
        try {
            if (event.email() != null && googleCalendarService.isConnected(event.email())) {
                googleCalendarService.addPlantaoToCalendar(event.plantao(), event.email());
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar plantão ao Google Calendar: " + e.getMessage());
        }
    }
}