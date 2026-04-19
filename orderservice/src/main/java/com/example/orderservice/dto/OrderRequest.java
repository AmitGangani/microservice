package com.example.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {

    @NotNull
    private String userId;

    @NotNull
    private String productName;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
}
