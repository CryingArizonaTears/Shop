package by.shop.exceptionHandler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ApiException {
    String name;
    String message;
    Throwable throwable;
    HttpStatus httpStatus;
    LocalDateTime localDateTime;
}
