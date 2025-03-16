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
public class TerminalStatusDTO {
    @JsonProperty("terminal_id")
    private String terminalId;
    private String name;
    @JsonProperty("request_id")
    private String requestId;
    private long timestamp;
    private String value;

}
