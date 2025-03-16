package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RawListDTO {

    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("timestamp")
    private long timestamp;
    private int level;
    private List<Object> data;
}


