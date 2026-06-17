package srms_web.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import srms_web.auth.Authentication;
import srms_web.model.UserSession;

@Controller
public class LoginController {

    @GetMapping("/")
    public String loginPage() {

        return "login";

    }

    @GetMapping("/login")
    public String loginPageAgain()
    
    {

        return "login";

    }

    @PostMapping("/login")
    public String login(

            String username,

            String password,

            HttpSession session,

            Model model

    ) {

        System.out.println(
                "LOGIN BUTTON CLICKED"
        );

        UserSession user =

                Authentication.login(
                        username,
                        password
                );
if (user == null) {

    model.addAttribute(
            "error",
            "Invalid username or password"
    );

    return "login";

}

        session.setAttribute(
                "user",
                user
        );

        session.setAttribute(
                "userId",
                user.getUserId()
        );

        session.setAttribute(
                "username",
                user.getUsername()
        );

        session.setAttribute(
                "role",
                user.getRole()
        );

        // STUDENT LOGIN

        if (

                user.getRole().equalsIgnoreCase(
                        "STUDENT"
                )

        ) {

            
return "redirect:/student/student-dashboard";
        }

        // STAFF LOGIN

        if (

                user.getRole().equalsIgnoreCase(
                        "STAFF"
                )

        ) {

            return
                    "redirect:/staff/students";

        }

        // ADMIN LOGIN

        if (

                user.getRole().equalsIgnoreCase(
                        "ADMIN"
                )

        ) {

            return
                    "redirect:/admin";

        }

        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout(

            HttpSession session

    ) {

        session.invalidate();

        return "redirect:/";

    }
}