package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RawMapDTO {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("timestamp")
    private long timestamp;

    private int level;

    private Map<String,Object> data;

}


