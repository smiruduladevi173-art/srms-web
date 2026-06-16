package srms_web.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import srms_web.auth.Authentication;

@Controller
public class LoginController {

    // =========================
    // LOGIN PAGE
    // =========================

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

        HttpSession session,

        Model model

) {

        System.out.println(
                "LOGIN BUTTON CLICKED"
        );

        Object studentId =

                Authentication.login(
                        username,
                        password
                );

        if (studentId != null) {

            session.setAttribute(
                    "studentId",
                    studentId
            );

            return
                    "redirect:/student/dashboard";

        }

        return
                "redirect:/";

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

