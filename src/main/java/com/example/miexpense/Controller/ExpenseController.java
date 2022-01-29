package com.example.miexpense.Controller;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.services.ExpenseService;
import com.example.miexpense.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping("/makePurchases")
    public String createExpense(Model model, HttpSession session){

        User user1 = (User) session.getAttribute("user");
        User user = userService.findUser(user1.getId());
        model.addAttribute("expense", new Expense());
        model.addAttribute("user", user);
        return "purchases";
    }

    @PostMapping(value="/calculate", params="action=purchase")
    public String addExpenses(@ModelAttribute("expense") Expense expense, Model model, HttpSession session){

        User user1 = (User) session.getAttribute("user");
        User user = userService.findUser(user1.getId());
        expense.setUser(user);
        if(expenseService.makeExpenses(expense, user1.getId()))
            model.addAttribute("message", "Expense made successfully");
        else
            model.addAttribute("message", "Error Making Expenses");
        return "redirect:/home";
    }

    @PostMapping(value="/calculate", params="action=calculate")
    public String calculatePurchase(@ModelAttribute("expense") Expense expense, Model model, HttpSession session){

        User user1 = (User) session.getAttribute("user");
        User user = userService.findUser(user1.getId());
        expense.setTotalAmount(expenseService.calculatePurchase(expense));

        model.addAttribute("expense", expense);
        model.addAttribute("user", user);
        return "purchases";
    }

    @GetMapping(value = "/editpage/{id}")
    public String editPage(@PathVariable("id") Long expenseId,
                           Model model, HttpSession session) {

        User user1 = (User)session.getAttribute("user");

        User user = userService.findUser(user1.getId());
        if(user == null) return "redirect:/";

        List<Expense> exp = expenseService.getExpenseById(expenseId);

        model.addAttribute("exp", exp.get(0));
        model.addAttribute("user", user);
        return "editpage";
    }

    @PostMapping(value="/editpagesubmit", params="action=editSubmit")
    public String editExpense(@ModelAttribute("exp") Expense expense, Model model, HttpSession session){

        User user1 = (User) session.getAttribute("user");
        User user = userService.findUser(user1.getId());
        if(user == null) return "redirect:/";

        if(expenseService.editExpense(expense.getId(),expense.getDetails(),
                expense.getNoOfItems(), expense.getAmountPerPurchase(), expense.getTotalAmount())) {
            model.addAttribute("message", "Expense edited successfully");
        }else{
            model.addAttribute("message", "Error editing Expenses!");
        }
        return "redirect:/home";
    }

    @PostMapping(value="/editpagesubmit", params="action=editcalculate")
    public String calculateEditPurchase(@ModelAttribute("exp") Expense expense, Model model, HttpSession session){
        User user1 = (User) session.getAttribute("user");
        User user = userService.findUser(user1.getId());
        expense.setTotalAmount(expenseService.calculatePurchase(expense));

        model.addAttribute("exp", expense);
        model.addAttribute("user", user);
        return "editpage";
    }

    @GetMapping(value = "/deletePost/{id}")
    public String deletePost(@PathVariable("id") Long expenseId,HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        if(expenseService.deleteExpenses(expenseId, user.getId())){
            model.addAttribute("message", "Post deleted successfully");
        }else {
            model.addAttribute("message", "Error deleting post! or you don't have access to delete this post");
        }
        return "redirect:/home";
    }

}
