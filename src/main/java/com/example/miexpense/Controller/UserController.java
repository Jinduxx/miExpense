package com.example.miexpense.Controller;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.payLoad.ExpenseDetail;
import com.example.miexpense.payLoad.LoginDetail;
import com.example.miexpense.repository.ExpenseRepo;
import com.example.miexpense.services.ServiceImplementation.ExpenseServiceImpl;
import com.example.miexpense.services.ServiceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class UserController {

    private final UserServiceImpl userService;
    private ExpenseRepo expenseRepo;
    private ExpenseServiceImpl expenseService;

    @Autowired
    public UserController(UserServiceImpl userService, ExpenseRepo expenseRepo, ExpenseServiceImpl expenseService) {
        this.userService = userService;
        this.expenseRepo = expenseRepo;
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public String showFirstPage(Model model){
        model.addAttribute("login", new LoginDetail());
        int i =1;
        return "index";
    }

    @PostMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("user", new User());
        return "SignUp";
    }

    @GetMapping("/home")
    public String showHome(HttpServletRequest request, @ModelAttribute("user") User user, Model model){

        HttpSession httpSession = request.getSession();
        User user1 = (User) httpSession.getAttribute("user");
        if(user1 == null) {
            httpSession.setAttribute("message", "!!!Please Login");
            return "redirect:/";
        }
        List<ExpenseDetail> expense = expenseService.getExpenses(user1);
        System.out.println(expense);

        model.addAttribute("expense", expense);
        model.addAttribute("userDetail", user1);
        model.addAttribute("expenses", new Expense());
        return "home";
    }

    @PostMapping( "/loginProcess")
    public String loginProcess(HttpServletRequest request, @ModelAttribute("login") LoginDetail loginDetail) {
        User user = userService.getUser(loginDetail.getEmail(), loginDetail.getPassword());
//
        HttpSession httpSession = request.getSession();
        if (user != null) {
            httpSession.setAttribute("user", user);
            return "redirect:/home";
        } else {
            httpSession.setAttribute("message", "Email or Password is wrong!!!");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/processLogout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }


    @PostMapping("/registerProcess")
    public String addUser(HttpServletRequest request, @ModelAttribute("user") User user) {

        HttpSession httpSession = request.getSession();
        //data check

        if (user.getFirstname().length() < 2) {
            httpSession.setAttribute("message", " firstname cannot be less than 2 character long");
            return "redirect:/";
        }

        if (user.getLastname().length() < 2) {
            httpSession.setAttribute("message", "lastname cannot be less than 2 character long");
            return "redirect:/";
        }


        if (user.getEmail() == null) {
            httpSession.setAttribute("message", "email cannot be null");
            return "redirect:/";
        } else if(!isValidEmail(user.getEmail())){
            httpSession.setAttribute("message", "email is not valid");
            return "redirect:/";
        }
        if (user.getPassword().length() < 7) {
            httpSession.setAttribute("message", "password cannot be less than 6 character long");
            return "redirect:/";
        }

        if(userService.createUser(user)){
            httpSession.setAttribute("message", "Successfully registered!!!");
        }else{
            httpSession.setAttribute("message", "Failed to register or email already exist");
        }

        return "redirect:/";
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
