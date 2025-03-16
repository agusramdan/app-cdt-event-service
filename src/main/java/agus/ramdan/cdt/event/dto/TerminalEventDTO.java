package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class TerminalEventDTO {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("timestamp")
    private long timestamp;

    private int level;

    @JsonProperty("terminal_id")
    private String terminalId;

    private Map<String,Object> data;

}


