package agus.ramdan.cdt.event.service;

//import agus.ramdan.cdt.event.repository.DropDataRepository;
//import agus.ramdan.cdt.event.repository.EventLogRepository;
//import agus.ramdan.cdt.event.repository.RawDataRepository;
//import agus.ramdan.cdt.event.repository.TerminalStatusRepository;
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

//    @KafkaListener(topics = "raw-data-topic")
//    public void consumeRawData(String event) {
//        log.info("Consumed RawData: {}", event);
//    }
//
    @KafkaListener(topics = "raw-dto-topic")
    public void consumeRawData(RawDTO event) {
        log.info("Consumed RawData: {}", event.getRequestId());
        val prosess = RawProcessDTO.builder().level(1)
                .requestId(event.getRequestId())
                .timestamp(event.getTimestamp())
                .data(event.getData())
                .build();
        kafkaProducerService.send(prosess);
    }

    @KafkaListener(topics = "raw-process-dto-topic")
    public void consumeRawProcessDto(RawProcessDTO event) {
        log.info("Consumed RawProcessDto: {}", event.getRequestId());
        val data = event.getData();
        if (data instanceof Map){
            kafkaProducerService.send(
                    RawMapDTO.builder()
                            .requestId(event.getRequestId())
                            .timestamp(event.getTimestamp())
                            .level(event.getLevel())
                            .data((Map<String, Object>) data)
                            .build()
            );
        }else if (data instanceof Iterable){
            kafkaProducerService.send(
                    RawListDTO.builder()
                            .requestId(event.getRequestId())
                            .timestamp(event.getTimestamp())
                            .level(event.getLevel()+1)
                            .data((List<Object>)data)
                            .build()
            );
        } else {
            log.error("Data not valid");
            kafkaProducerService.send(
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
        if(event.getData().containsKey("terminal_id") && event.getData().containsKey("timestamp")) {
            kafkaProducerService.send(
                    event.getData().get("timestamp") instanceof Long ?
                            TerminalEventDTO.builder()
                                    .terminalId((String) event.getData().get("terminal_id"))
                                    .timestamp((Long) event.getData().get("timestamp"))
                                    .data(event.getData())
                                    .build() :
                            TerminalEventDTO.builder()
                                    .terminalId((String) event.getData().get("terminal_id"))
                                    .timestamp(Long.parseLong(event.getData().get("timestamp").toString()))
                                    .data(event.getData())
                                    .build()
            );
        } else {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                kafkaProducerService.send(
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
            kafkaProducerService.send(
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
