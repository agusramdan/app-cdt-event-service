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
public class GatewayCallbackDTO {
    private String id;
    @JsonProperty("gateway_code")
    private String gatewayCode;
    private long timestamp;
    private Object data;

}


