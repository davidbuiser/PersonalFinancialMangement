package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.in.web;

import java.time.LocalDate;

public record ExpenseResponseDTO(
    String description,
    double amount,
    String category,
    LocalDate date
) {
}
