package com.example.jobs;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record JobRequest(
    @NotBlank @Size(max = 255) String desc,
    @NotNull @DecimalMin("0.00") BigDecimal salary
) {}
