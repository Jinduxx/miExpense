package com.example.miexpense.services;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.payLoad.ExpenseDetail;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    boolean makeExpenses(Expense expense, Long userId);
    List<Expense> getExpenseById(Long expenseId);
    List<ExpenseDetail> getExpenses(User user);
    boolean deleteExpenses(Long expenseId, Long userId);
    double calculatePurchase(Expense expense);
    boolean editExpense(Long expenseId, String details, double noOfItems, double amountPerPurchase, double totalAmount);
    int getAmountOfExpensesByUser(@NonNull Long userId);
    List<Expense> getUsersById(Long id);
}
