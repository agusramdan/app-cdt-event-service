package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {

    @JsonProperty("request_id")
    private String requestId;
    private long timestamp;
    private String message;
}
