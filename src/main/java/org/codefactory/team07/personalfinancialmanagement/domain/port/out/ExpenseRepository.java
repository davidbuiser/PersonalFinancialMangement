package org.codefactory.team07.personalfinancialmanagement.domain.port.out;

import org.codefactory.team07.personalfinancialmanagement.domain.model.Expense;

public interface ExpenseRepository {
    void save(Expense expense);
    
    double getTotalSpent(); 
}