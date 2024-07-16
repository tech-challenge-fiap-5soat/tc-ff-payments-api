package com.tc.ff.payments_api.common.exception;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerAdviceTest {

    @InjectMocks
    private ExceptionHandlerAdvice exceptionHandlerAdvice;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private ConstraintViolationException constraintViolationException;

    @Mock
    private BusinessException businessException;

    @Test
    void testHandleMethodArgumentNotValidException() {
        when(methodArgumentNotValidException.getMessage()).thenReturn("Method argument not valid");

        ResponseEntity<ProblemDetail> response =
                exceptionHandlerAdvice.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail problemDetail = response.getBody();
        assertNotNull(problemDetail);
        assertEquals("Method argument not valid", problemDetail.getDetail());
        requireNonNull(problemDetail).getProperties().containsKey("timeStamp");
    }

    @Test
    void testHandleConstraintViolationException() {
        when(constraintViolationException.getMessage()).thenReturn("Constraint violation");

        ResponseEntity<ProblemDetail> response =
                exceptionHandlerAdvice.handleConstraintViolationException(constraintViolationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail problemDetail = response.getBody();
        assertNotNull(problemDetail);
        assertEquals("Constraint violation", problemDetail.getDetail());
        requireNonNull(problemDetail).getProperties().containsKey("timeStamp");
    }

    @Test
    void testHandleBusinessException() {
        when(businessException.getMessage()).thenReturn("Business exception");
        ResponseEntity<ProblemDetail> response = exceptionHandlerAdvice.handleBusinessException(businessException);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        ProblemDetail problemDetail = response.getBody();
        assertNotNull(problemDetail);
        assertEquals("Business exception", problemDetail.getDetail());
        requireNonNull(problemDetail).getProperties().containsKey("timeStamp");
    }
}
