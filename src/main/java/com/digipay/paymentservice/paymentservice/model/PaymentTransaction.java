package com.digipay.paymentservice.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_payment_transaction")
public class PaymentTransaction {
    @JsonProperty("payment_code")
    @Column(name = "payment_id", unique = true, nullable = false)
    private Long paymentId;
    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "destination_card_number", nullable = false)
    private String destinationCardNumber;
    @Column(name = "transaction_date", nullable = false)
    private Integer transactionDate;
    @JsonProperty("amount")
    @Column(name = "amount_transaction", nullable = false)
    private BigDecimal amountTransaction;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProcessorResponse.PaymentResponseStatus result;
    @JsonIgnore
    @ManyToOne
    private Card card;
}
