package web.pulso.integration.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleCalendarEventDTO {
    
    private String id;
    private String summary;
    private String description;
    private String location;
    
    @JsonProperty("start")
    private EventDateTime start;
    
    @JsonProperty("end")
    private EventDateTime end;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDateTime {
        @JsonProperty("dateTime")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private String dateTime;
        
        @JsonProperty("date")
        private String date;
        
        @JsonProperty("timeZone")
        private String timeZone;
    }
}
