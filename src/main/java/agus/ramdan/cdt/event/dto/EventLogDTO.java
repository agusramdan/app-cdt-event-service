package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventLogDTO {
    private String id;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("terminal_id")
    private String terminalId;
    @JsonProperty("event_type")
    private String eventType;
    private String message;
    private long timestamp;
}


