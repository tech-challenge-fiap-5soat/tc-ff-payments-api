package com.tc.ff.payments_api.entrypoint.controller;

import static com.tc.ff.payments_api.common.constants.PathConstants.PAYMENTS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tc.ff.payments_api.entrypoint.controller.payload.request.RegisterPendingPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.request.UpdateRegisteredPaymentRequest;
import com.tc.ff.payments_api.entrypoint.controller.payload.response.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = PAYMENTS, produces = APPLICATION_JSON_VALUE)
public interface PaymentController {

    @Operation(
            summary = "Register a new Payment",
            tags = {"payment"},
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = PaymentResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class))),
                @ApiResponse(
                        responseCode = "406",
                        description = "Not Acceptable",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class)))
            })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<PaymentResponse> create(@RequestBody @Valid RegisterPendingPaymentRequest request);

    @Operation(
            summary = "Update status of an existing Payment",
            tags = {"payment"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Status updated",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = PaymentResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class))),
                @ApiResponse(
                        responseCode = "406",
                        description = "Not Acceptable",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class)))
            })
    @PutMapping(path = "/{orderId}")
    ResponseEntity<PaymentResponse> update(
            @PathVariable String paymentId, @RequestBody @Valid UpdateRegisteredPaymentRequest request);
}
