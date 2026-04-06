package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.in.web;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class ExpenseDTO {
    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private double amount;

    @NotBlank(message = "La categoría es obligatoria")
    private String category; // Viene como String y lo convertimos a Enum

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede estar en el futuro")
    private LocalDate date;
}