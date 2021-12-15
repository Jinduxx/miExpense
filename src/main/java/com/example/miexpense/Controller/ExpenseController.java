package com.example.miexpense.Controller;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.repository.UserRepo;
import com.example.miexpense.services.ServiceImplementation.ExpenseServiceImpl;
import com.example.miexpense.services.ServiceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ExpenseController {

    private ExpenseServiceImpl expenseService;
    private UserServiceImpl userService;
    private UserRepo userRepo;

    @Autowired
    public ExpenseController(ExpenseServiceImpl expenseService, UserServiceImpl userService, UserRepo userRepo) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @PostMapping("/makePurchases/{id}")
    public String createExpense(@PathVariable("id") Long userId, Model model){

        User user = userRepo.findUserById(userId);
        model.addAttribute("expense", new Expense());
        model.addAttribute("user", user);
        return "purchases";
    }

    @RequestMapping(value="/calculate/{id}", method=RequestMethod.POST, params="action=purchase")
    public String addExpenses(@PathVariable("id") Long userId, @ModelAttribute("expense") Expense expense, Model model){
        User user = userRepo.findUserById(userId);

        expense.setUser(user);

        if(expenseService.makeExpenses(expense, userId))
            model.addAttribute("message", "Expense made successfully");
        else
            model.addAttribute("message", "Error Making Expenses");
        return "redirect:/home";
    }

    @RequestMapping(value="/calculate/{id}", method=RequestMethod.POST, params="action=calculate")
    public String calculatePurchase(@PathVariable("id") Long userId, @ModelAttribute("expense") Expense expense, Model model){

        User user = userRepo.findUserById(userId);
        expense.setTotalAmount(expenseService.calculatePurchase(expense));
//        expense.setBalance(expenseService.calculateMinusBalance(expense));

        model.addAttribute("expense", expense);
        model.addAttribute("user", user);
        return "purchases";
    }

    @GetMapping(value = "/editpage/{id}")
    public String showPost(@PathVariable("id") Long expenseId, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null) return "redirect:/";

        List<Expense> exp = expenseService.getExpenseById(expenseId);

        model.addAttribute("exp", exp.get(0));
        model.addAttribute("user", user);
        return "editpage";
    }

    @RequestMapping(value="/calculate/{id}", method=RequestMethod.POST, params="action=edit")
    public String editExpense(@PathVariable("id") Long userId, @ModelAttribute("exp") Expense expense, Model model){

        User user = userRepo.findUserById(userId);
        if(user == null) return "redirect:/";

        if(expenseService.editExpense(expense.getId(),expense.getDetails(),
                expense.getNoOfItems(), expense.getAmountPerPurchase(), expense.getTotalAmount())) {
            model.addAttribute("message", "Expense edited successfully");
        }else{
            model.addAttribute("message", "Error editing Expenses!");
        }

        return "redirect:/home";
    }

    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") Long expenseId, Model model) {

//        User user = (User) session.getAttribute("user");

        User user = (User) model.getAttribute("user");
        System.out.println(user);

        if(user == null) return "redirect:/";
        Expense expense = expenseService.deleteExpenses(expenseId, user.getId());

        if(expense != null){
            model.addAttribute("message", "Post deleted successfully");
        }else {
            model.addAttribute("message", "Error deleting post! or you don't have access to delete this post");
        }

        return "redirect:/home";
    }

}
