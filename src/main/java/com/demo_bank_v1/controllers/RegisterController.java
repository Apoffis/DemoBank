package com.demo_bank_v1.controllers;

import com.demo_bank_v1.helpers.HTML;
import com.demo_bank_v1.helpers.Token;
import com.demo_bank_v1.mail_messenger.MailMessenger;
import com.demo_bank_v1.models.User;
import com.demo_bank_v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Random;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public ModelAndView getRegister() {
        ModelAndView getRegisterPage = new ModelAndView("register");
        System.out.println("in Register Page Register Controller");
        getRegisterPage.addObject("PageTitle", "Register");
        return getRegisterPage;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("registerUser") User user,
                                 BindingResult result,
                                 @RequestParam("first_name") String first_name,
                                 @RequestParam("last_name") String last_name,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirm_password") String confirm_password) throws MessagingException {
        ModelAndView registrationPage = new ModelAndView("register");
//      check For Errors:
        if (result.hasErrors() && confirm_password.isEmpty()) {
            registrationPage.addObject("confirm_pass", "The confirm Field is required");
            return registrationPage;
        }

        // TODO: 3/11/2022 CHECK FOR PASSWORD MATHS:
        if (!password.equals(confirm_password)) {
            registrationPage.addObject("passwordMisMatch", "Password do not match.");
            return registrationPage;
        }

        // TODO: 3/11/2022 GET TOKEN STRING:
        String token = Token.generateToken();

        // TODO: 3/11/2022 GENERATE RANDOM CODE:
        Random rand = new Random();
        int bound = 123;
        int code = bound * rand.nextInt(bound);

        // TODO: 3/11/2022 GET EMAIL HTML BODY:
        String emailBody = HTML.htmlEmailTemplate(token, code);

        // TODO: 3/11/2022 HASH PASSWORD:
        String hashed_password = BCrypt.hashpw(password, BCrypt.gensalt());

        // TODO: 3/11/2022 REGISTER USER:
        userRepository.registerUser(first_name, last_name, email, hashed_password, token, code);

        // TODO: 3/11/2022 SEND EMAIL NOTIFICATION:
        MailMessenger.htmlEmailMessenger("no-reply@somecompony.com", email, "Verify Account", emailBody);

        // TODO: 3/11/2022 RETURN TI REGISTER PAGE:
        String successMessage = "Account Registered Successfully, Pleas Check your Email and Verify Account!";
        registrationPage.addObject("success", successMessage);

        return registrationPage;
    }

}
