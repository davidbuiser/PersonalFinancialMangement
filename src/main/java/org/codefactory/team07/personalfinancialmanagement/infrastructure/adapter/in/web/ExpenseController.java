package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.in.web;

import org.codefactory.team07.personalfinancialmanagement.application.usecase.RegisterExpenseUseCase;
import org.codefactory.team07.personalfinancialmanagement.domain.model.Category;
import org.codefactory.team07.personalfinancialmanagement.domain.model.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor // Lombok genera el constructor para inyectar el UseCase
public class ExpenseController {
    private final RegisterExpenseUseCase useCase;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody ExpenseDTO dto) {
        try {
            Expense expense = new Expense(
                dto.getDescription(), 
                dto.getAmount(), 
                Category.valueOf(dto.getCategory().toUpperCase()), 
                dto.getDate()
            );
            return ResponseEntity.ok(useCase.execute(expense));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en los datos: " + e.getMessage());
        }
    }
}