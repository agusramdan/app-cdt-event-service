package agus.ramdan.cdt.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Tidak dapat di prosess dengan alasan salah satu dibawah ini.
 * 1. Map tidak mempunyai attribut terminal_id,timestamp sehingga
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropDTO {

    @JsonProperty("request_id")
    private String requestId;
    private long timestamp;
    private int level;
    private Object data;

}


