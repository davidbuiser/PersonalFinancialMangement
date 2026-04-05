package org.codefactory.team07.personalfinancialmanagement.application.usecase;

import org.codefactory.team07.personalfinancialmanagement.domain.model.Expense;
import org.codefactory.team07.personalfinancialmanagement.domain.port.out.ExpenseRepository;

public class RegisterExpenseUseCase {

    private final ExpenseRepository repository;

    public RegisterExpenseUseCase(ExpenseRepository repository) {
        this.repository = repository;
    }

    public void execute(String description, double amount) {
        Expense expense = new Expense(description, amount);
        repository.save(expense);
    }
}
