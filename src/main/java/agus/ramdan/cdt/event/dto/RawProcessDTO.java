package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RawProcessDTO {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("timestamp")
    private long timestamp;
    private String key;
    private int level;

    private Object data;

}


