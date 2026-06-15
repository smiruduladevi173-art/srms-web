package srms_web.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
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
    public String loginPageAgain() {

        return "login";

    }

    @PostMapping("/login")
    public String login(

            String username,

            String password,

            HttpSession session

    ) {

        System.out.println(
                "LOGIN BUTTON CLICKED"
        );

        UserSession user =
                Authentication.login(
                        username,
                        password
                );

        if (user != null) {

            session.setAttribute(
                    "user",
                    user
            );

            if (
                    user.getRole().equalsIgnoreCase(
                            "STUDENT"
                    )
            ) {

                return "redirect:/student/dashboard";

            }

            if (
                    user.getRole().equalsIgnoreCase(
                            "STAFF"
                    )
            ) {

                return "redirect:/staff-dashboard";

            }

            if (
                    user.getRole().equalsIgnoreCase(
                            "ADMIN"
                    )
            ) {

                return "redirect:/admin";

            }

        }

        return "redirect:/";

    }

    // =========================
    // LOGOUT
    // =========================

    @GetMapping("/logout")
    public String logout(

            HttpSession session

    ) {

        session.invalidate();

        return "redirect:/";

    }

}