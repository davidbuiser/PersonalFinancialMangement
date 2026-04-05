package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.out.persistance;

import org.codefactory.team07.personalfinancialmanagement.domain.model.Expense;
import org.codefactory.team07.personalfinancialmanagement.domain.port.out.ExpenseRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final JpaExpenseRepository jpaRepository;

    @Override
    public void save(Expense expense) {
        ExpenseEntity entity = new ExpenseEntity(
            null,
            expense.getDescription(),
            expense.getAmount(),
            expense.getCategory().name(),
            expense.getDate()
        );
        jpaRepository.save(entity);
    }

    @Override
    public double getTotalSpent() {
        // Sumamos todos los montos de la tabla
        return jpaRepository.findAll().stream()
                .mapToDouble(ExpenseEntity::getAmount)
                .sum();
    }
}