package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawListDTO {
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("timestamp")
    private long timestamp;
    private int level;
    private List<Object> data;
}


