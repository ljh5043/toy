package thecommerce.toy.global.event;

import lombok.Builder;
import lombok.Data;

//  로그 이벤트 객체
@Data
@Builder
public class LogEvent {

    private String message;

    public static LogEvent createLogEvent(String message) {
        return LogEvent.builder()
                .message(message)
                .build();
    }

}
