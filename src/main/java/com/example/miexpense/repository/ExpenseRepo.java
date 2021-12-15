package com.example.miexpense.repository;

import com.example.miexpense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    List<Expense> findExpenseById(Long expenseId);
    List<Expense> findAllByUserId(Long userId);
    Expense deleteExpenseById(Long expenseId);
    Expense findExpenseByUserId(Long userId);
 }
