package br.com.desktop.domain.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "payment_cancellation")
@TypeAlias("PaymentCancellation")

public class PaymentCancellation {
    @Id
    private String id;

    private Long customerId;
    private Long puId;
    private LocalDateTime createdAt;
    private LocalDateTime UpdatedAt;
    private Status status;
    private Boolean hasError;

    public enum Status {
        RECEIVED_MAILING,
        OSS_SEND,
        OSS_OK,
        CX_SEND,
        CX_RECEIVED
    }
}
