package com.example.miexpense.repository;

import com.example.miexpense.model.Expense;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    List<Expense> findExpenseById(Long expenseId);
    List<Expense> findAllByUserId(Long userId);
    @Transactional
    void deleteExpenseByIdAndUserId(Long expenseId, Long userId);
    List<Expense> findExpenseByUserId(Long userId);
    @Query("SELECT sum(totalAmount) from Expense")
    int sumOfExpenses();

    @Query(value = "SELECT sum(total_amount) from expense where user_id =?1 ", nativeQuery = true)
    int sumOfExpensesByUser(@NonNull Long userId);


 }
