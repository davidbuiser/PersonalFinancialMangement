package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.in.web;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

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
    private final GetExpenseHistoryUseCase getHistoryUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody ExpenseDTO dto) {
        Expense expense = new Expense(
            dto.getDescription(),
            dto.getAmount(),
            Category.valueOf(dto.getCategory().trim().toUpperCase(Locale.ROOT)),
            dto.getDate()
        );
        String resultMessage = useCase.execute(expense);

        return ResponseEntity.ok(ApiResponse.success(resultMessage, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponseDTO>>> getHistory() {
        List<ExpenseResponseDTO> expenses = getHistoryUseCase.execute().stream()
            .map(expense -> new ExpenseResponseDTO(
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory().name(),
                expense.getDate()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Historial obtenido correctamente", expenses));
    }
    
}