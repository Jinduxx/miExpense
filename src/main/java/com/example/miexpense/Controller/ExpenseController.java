package com.example.miexpense.Controller;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
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

    private final ExpenseServiceImpl expenseService;
    private final UserServiceImpl userService;

    @Autowired
    public ExpenseController(ExpenseServiceImpl expenseService, UserServiceImpl userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping("/makePurchases/{id}")
    public String createExpense(@PathVariable("id") Long userId, Model model){

        User user = userService.findUser(userId);
        model.addAttribute("expense", new Expense());
        model.addAttribute("user", user);
        return "purchases";
    }

    @RequestMapping(value="/calculate/{id}", method=RequestMethod.POST, params="action=purchase")
    public String addExpenses(@PathVariable("id") Long userId, @ModelAttribute("expense") Expense expense, Model model){
        User user = userService.findUser(userId);

        expense.setUser(user);

        if(expenseService.makeExpenses(expense, userId))
            model.addAttribute("message", "Expense made successfully");
        else
            model.addAttribute("message", "Error Making Expenses");
        return "redirect:/home";
    }

    @RequestMapping(value="/calculate/{id}", method=RequestMethod.POST, params="action=calculate")
    public String calculatePurchase(@PathVariable("id") Long userId, @ModelAttribute("expense") Expense expense, Model model){

        User user = userService.findUser(userId);
        expense.setTotalAmount(expenseService.calculatePurchase(expense));

        model.addAttribute("expense", expense);
        model.addAttribute("user", user);
        return "purchases";
    }

    @GetMapping(value = "/editpage/{id}/{userId}")
    public String editPage(@PathVariable("id") Long expenseId, @PathVariable("userId") Long userId,
                           Model model) {

        User user = userService.findUser(userId);

        if(user == null) return "redirect:/";

        List<Expense> exp = expenseService.getExpenseById(expenseId);

        model.addAttribute("exp", exp.get(0));
        model.addAttribute("user", user);
        return "editpage";
    }

    @RequestMapping(value="/editpagesubmit/{id}", method=RequestMethod.POST, params="action=editSubmit")
    public String editExpense(@PathVariable("id") Long userId, @ModelAttribute("exp") Expense expense, Model model){
        System.out.println(userId);
        User user = userService.findUser(userId);
        System.out.println(user);
        if(user == null) return "redirect:/";

        if(expenseService.editExpense(expense.getId(),expense.getDetails(),
                expense.getNoOfItems(), expense.getAmountPerPurchase(), expense.getTotalAmount())) {
            model.addAttribute("message", "Expense edited successfully");
        }else{
            model.addAttribute("message", "Error editing Expenses!");
        }
        return "redirect:/home";
    }

    @RequestMapping(value="/editpagesubmit/{id}", method=RequestMethod.POST, params="action=editcalculate")
    public String calculateEditPurchase(@PathVariable("id") Long userId, @ModelAttribute("exp") Expense expense, Model model){

        User user = userService.findUser(userId);
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
