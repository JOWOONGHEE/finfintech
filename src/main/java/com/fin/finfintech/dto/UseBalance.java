package com.fin.finfintech.dto;

import com.fin.finfintech.aop.AccountLockIdInterface;
import com.fin.finfintech.type.TransactionResultType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class UseBalance {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request implements AccountLockIdInterface {
        @NotNull
        @Min(1)
        private Long userId;

        @NotBlank
        @Size(min = 10, max = 10)
        private String accountNumber;

        @NotNull
        @Min(10)
        @Max(1000_000_000)
        private Long amount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String accountNumber;
        private TransactionResultType transactionResult;
        private String transactionId;
        private Long amount;
        private LocalDateTime transactedAt;

        public static Response from(TransactionDto transactionDto) {
            return Response.builder()
                    .accountNumber(transactionDto.getAccountNumber())
                    .transactionResult(transactionDto.getTransactionResultType())
                    .transactionId(transactionDto.getTransactionId())
                    .amount(transactionDto.getAmount())
                    .transactedAt(transactionDto.getTransactedAt())
                    .build();
        }
    }
}
