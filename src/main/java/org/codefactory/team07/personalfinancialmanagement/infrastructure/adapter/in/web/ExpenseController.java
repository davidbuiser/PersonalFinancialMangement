package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.in.web;

import java.util.List;

import org.codefactory.team07.personalfinancialmanagement.application.usecase.GetExpenseHistoryUseCase;
import org.codefactory.team07.personalfinancialmanagement.application.usecase.RegisterExpenseUseCase;
import org.codefactory.team07.personalfinancialmanagement.domain.model.Category;
import org.codefactory.team07.personalfinancialmanagement.domain.model.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            if (dto.getCategory() == null || dto.getCategory().isBlank()) {
                return ResponseEntity.badRequest().body("Error en los datos: la categoría es obligatoria");
            }

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

    private final GetExpenseHistoryUseCase getHistoryUseCase; // Inyectar el nuevo caso de uso

    @GetMapping
    public ResponseEntity<List<Expense>> getHistory() {
        return ResponseEntity.ok(getHistoryUseCase.execute());
    }
    
}