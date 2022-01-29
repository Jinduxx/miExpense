package com.example.miexpense.services.ServiceImplementation;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.repository.ExpenseRepo;
import com.example.miexpense.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {


    @Mock
    private ExpenseRepo expenseRepo;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private Expense expense;

    private User user;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(10L);

        expense = new Expense();
        expense.setUser(user);
        expense.setId(1L);
        expense.setDetails("sugar");
        expense.setNoOfItems(0.0);
        expense.setAmountPerPurchase(200.0);
        expense.setTotalAmount(expense.getNoOfItems() * expense.getAmountPerPurchase());
    }

//    @Test
//    void makeExpenses() {
//        //mock user Repo & expense Repo
//
//        when(userRepo.findUserById(anyLong())).thenReturn(user);
//
//        when(expenseRepo.save(any(Expense.class))).thenReturn(expense);
//
//        System.out.println(expense.getUser().getId());
//        System.out.println(expense);
//        //call method to be tested
//        boolean success = expenseService.makeExpenses(expense, 10L);
//        assertTrue(success);
//
//        //assertion
//        verify(expenseRepo, times(1)).save(any(Expense.class));
//        verify(userRepo, times(1)).findUserById(anyLong());
//
//    }

    @Test
    void shouldTestForGettingPostById() {
        //mock Post Repo
        when(expenseRepo.findExpenseById(anyLong())).thenReturn(List.of(expense));

        // call method to be tested
        expenseService.getExpenseById(expense.getId());

        //assertion
        assertEquals("sugar", expense.getDetails());
        assertEquals(0.0, expense.getNoOfItems());
        assertEquals(200.0, expense.getAmountPerPurchase());
        assertEquals(0.0, expense.getTotalAmount());
    }

//    @Test
//    void shouldTestForEditingExpense() {
//        when(expenseRepo.findExpenseById(anyLong())).thenReturn(List.of(expense));
//
//        List<Expense> exp = expenseService.getExpenseById(expense.getId());
//        boolean result = expenseService.editExpense(exp.get(0).getId(), "fish", 1.0, 20.0, 20.0);
//        assertTrue(result);
//
//        assertEquals("fish", expense.getDetails());
//        assertEquals(1.0, expense.getNoOfItems());
//        assertEquals(20.0, expense.getAmountPerPurchase());
//        assertEquals(20.0, expense.getTotalAmount());
//    }

//    @Test
//    void deleteExpenses() {
//    }
}