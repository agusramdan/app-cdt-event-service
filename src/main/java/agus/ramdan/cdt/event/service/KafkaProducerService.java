package agus.ramdan.cdt.event.service;

import agus.ramdan.cdt.event.dto.*;
import agus.ramdan.cdt.event.dto.TerminalEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTerminalEventDTO(EventLogDTO event) {
        kafkaTemplate.send("event-log-dto-topic", event);
    }
//    public void send(RawDTO event) {
//        kafkaTemplate.send("raw-dto-topic", event);
//    }
//    public void send(TerminalStatusDTO event) {
//        kafkaTemplate.send("terminal-status-dto-topic", event);
//    }
//    public void send(GatewayCallbackDTO event) {
//        kafkaTemplate.send("gateway-callback-topic", event);
//    }
    public void sendRawListDTO(RawListDTO event) {
        kafkaTemplate.send("raw-list-dto-topic", event);
    }
    public void sendRawMapDTO(RawMapDTO event) {
        kafkaTemplate.send("raw-map-dto-topic", event);
    }
    public void sendRawProcessDTO(RawProcessDTO process) {
        kafkaTemplate.send("raw-process-dto-topic", process);
    }

    public void sendDropDTO(DropDTO build) {
        kafkaTemplate.send("drop-data-topic", build);
    }

    public void sendTerminalEventDTO(TerminalEventDTO terminalEventDto) {
        kafkaTemplate.send("terminal-event-dto-topic", terminalEventDto);
    }
    public void sendTerminalEventErrorDTO(TerminalEventErrorDTO terminalEventDto) {
        kafkaTemplate.send("error-terminal-event-dto-topic", terminalEventDto);
    }



}
