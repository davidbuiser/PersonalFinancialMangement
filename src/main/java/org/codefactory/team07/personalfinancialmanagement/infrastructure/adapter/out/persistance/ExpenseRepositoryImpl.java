package org.codefactory.team07.personalfinancialmanagement.infrastructure.adapter.out.persistance;

import org.codefactory.team07.personalfinancialmanagement.domain.model.Expense;
import org.codefactory.team07.personalfinancialmanagement.domain.port.out.ExpenseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final JpaExpenseRepository jpaRepository;

    public ExpenseRepositoryImpl(JpaExpenseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Expense expense) {
        ExpenseEntity entity = new ExpenseEntity(
            expense.getDescription(),
            expense.getAmount()
        );
        jpaRepository.save(entity);
    }
}
