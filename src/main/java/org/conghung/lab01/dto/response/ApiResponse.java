package org.conghung.lab01.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;
    private String message;
    @Builder.Default
    private String timestamp = Instant.now().toString();

    @Getter
    @Builder
    public static class ErrorResponse {
        private String code;
        private String message;
        private List<ErrorDetail> details;
    }

    @Getter
    @Builder
    public static class ErrorDetail {
        private String field;
        private String message;
    }
}
