package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.in.web;

import org.codefactory.team07.personalfinancialmanagement.application.usecase.RegisterExpenseUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final RegisterExpenseUseCase useCase;

    public ExpenseController(RegisterExpenseUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public void register(@RequestBody ExpenseDTO dto) {
        useCase.execute(dto.getDescription(), dto.getAmount());
    }
}
