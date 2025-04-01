package agus.ramdan.cdt.event.service;

import agus.ramdan.cdt.event.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = "raw-dto-topic")
    public void consumeRawData(RawDTO event) {
        log.info("Consumed RawData: {}", event.getRequestId());
        val prosess = RawProcessDTO.builder().level(1)
                .requestId(event.getRequestId())
                .timestamp(event.getTimestamp())
                .data(event.getData())
                .build();
        kafkaProducerService.sendRawProcessDTO(prosess);
    }

    @KafkaListener(topics = "raw-process-dto-topic")
    public void consumeRawProcessDto(RawProcessDTO event) {
        log.info("Consumed RawProcessDto: {}", event.getRequestId());
        val data = event.getData();
        if (data instanceof Map){
            kafkaProducerService.sendRawMapDTO(
                    RawMapDTO.builder()
                            .requestId(event.getRequestId())
                            .timestamp(event.getTimestamp())
                            .level(event.getLevel())
                            .data((Map<String, Object>) data)
                            .build()
            );
        }else if (data instanceof Iterable){
            kafkaProducerService.sendRawListDTO(
                    RawListDTO.builder()
                            .requestId(event.getRequestId())
                            .timestamp(event.getTimestamp())
                            .level(event.getLevel()+1)
                            .data((List<Object>)data)
                            .build()
            );
        } else {
            log.error("Data not valid request_id={},level={},data={}",event.getRequestId(),event.getLevel(),data);
            kafkaProducerService.sendDropDTO(
                    DropDTO.builder()
                            .requestId(event.getRequestId())
                            .timestamp(event.getTimestamp())
                            .level(event.getLevel())
                            .data(data)
                            .build()
            );
        }

    }

    @KafkaListener(topics = "raw-map-dto-topic")
    public void consumeRawMapDto(RawMapDTO event) {
        log.info("Consumed RawMapDto: {}", event.getRequestId());
        if(event.getData().containsKey("terminal_id") && (event.getData().containsKey("timestamp") || event.getData().containsKey("updated_on"))){
            Long timestamp =null;
            if (event.getData().containsKey("timestamp")) {
                try {
                    Object t = event.getData().get("timestamp");
                    if (t instanceof Long){
                        timestamp = (Long) t;
                    }else {
                        timestamp = Long.parseLong(t.toString());
                    }
                }catch (Exception e){
                    kafkaProducerService.sendTerminalEventErrorDTO(
                            TerminalEventErrorDTO.builder()
                                    .terminalId((String) event.getData().get("terminal_id"))
                                    .timestamp(timestamp)
                                    .data(event.getData())
                                    .message("timestamp error : " +e.getMessage())
                                    .build()
                    );
                    return;
                }

            }else if (event.getData().containsKey("updated_on")) {
                Object t = event.getData().get("updated_on");
                try {
                    if (t instanceof Long) {
                        timestamp = (Long) t;
                    } else {
                        timestamp = Long.parseLong(t.toString());
                    }
                }catch (Exception e){
                    kafkaProducerService.sendTerminalEventErrorDTO(
                            TerminalEventErrorDTO.builder()
                                    .terminalId((String) event.getData().get("terminal_id"))
                                    .timestamp(timestamp)
                                    .data(event.getData())
                                    .message("updated_on error : " +e.getMessage())
                                    .build()
                    );
                    return;
                }
            }
            kafkaProducerService.sendTerminalEventDTO(TerminalEventDTO.builder()
                                    .terminalId((String) event.getData().get("terminal_id"))
                                    .timestamp(timestamp)
                                    .data(event.getData())
                                    .build());
        } else {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                kafkaProducerService.sendRawProcessDTO(
                        RawProcessDTO.builder()
                                .requestId(event.getRequestId())
                                .timestamp(event.getTimestamp())
                                .level(event.getLevel() + 1)
                                .key(entry.getKey())
                                .data(entry.getValue())
                                .build()
                );
            }
        }
    }
    @KafkaListener(topics = "raw-list-dto-topic")
    public void consumeRawListDto(RawListDTO event) {
        log.info("Consumed RawListDto: {}", event.getRequestId());
        for (Object data : event.getData()) {
            kafkaProducerService.sendRawProcessDTO(
                    RawProcessDTO.builder()
                            .requestId(event.getRequestId())
                            .timestamp(event.getTimestamp())
                            .level(event.getLevel() + 1)
                            .data(data)
                            .build()
            );
        }
    }
}
