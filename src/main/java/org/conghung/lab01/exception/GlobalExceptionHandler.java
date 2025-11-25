package org.conghung.lab01.exception;

import org.conghung.lab01.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setSuccess(false);
        apiResponse.setError(ApiResponse.ErrorResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build());
        apiResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setSuccess(false);
        apiResponse.setError(ApiResponse.ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .success(false)
                        .error(ApiResponse.ErrorResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build())
                        .build()
        );
    }

    @ExceptionHandler(value = org.springframework.security.authentication.BadCredentialsException.class)
    ResponseEntity<ApiResponse> handlingBadCredentialsException(org.springframework.security.authentication.BadCredentialsException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .success(false)
                        .error(ApiResponse.ErrorResponse.builder()
                                .code(errorCode.getCode())
                                .message("Incorrect username or password")
                                .build())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        List<ApiResponse.ErrorDetail> details = new ArrayList<>();
        
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            details.add(ApiResponse.ErrorDetail.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build());
        });

        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            // ignore
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setError(ApiResponse.ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .details(details)
                .build());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
