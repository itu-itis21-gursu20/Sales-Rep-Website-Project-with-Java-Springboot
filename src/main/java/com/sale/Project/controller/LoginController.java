// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.controller;
import com.sale.Project.form.LoginForm;
import com.sale.Project.model.SalesRep;
import com.sale.Project.repository.SalesRepRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final SalesRepRepository salesRepRepository;

    public LoginController(SalesRepRepository salesRepRepository) {
        this.salesRepRepository = salesRepRepository;
    }

    //to get login page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginForm() {
        return "login";
    }


    //checking for login credentials
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model, HttpServletRequest request) {

        if (salesRepRepository.findByUsernameEqualsAndPasswordEquals(loginForm.getUsername(), loginForm.getPassword()).isPresent()) { // if username and password is same with database

            SalesRep salesRep = salesRepRepository.findByUsernameEqualsAndPasswordEquals(loginForm.getUsername(), loginForm.getPassword()).get(); // getting salesrep object which is entered by salesrep in login form
            request.getSession().setAttribute("salesRep", salesRep); // setting this to session
            return "redirect:/consumers/list";

        }

        model.addAttribute("invalidCredentials", true); // else it shows a warning message in the screen
        return "login";

    }

}
