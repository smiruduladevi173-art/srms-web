package srms_web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import srms_web.staff.StaffService;


@Controller
@RequestMapping("/staff")
public class StaffController {

    // =========================
    // DEFAULT PAGE
    // =========================

    @GetMapping("")
    public String home() {

        return "redirect:/staff/students";
    }

    // =========================
    // STUDENTS
    // =========================
@GetMapping("/students")
public String students(Model model) {

    model.addAttribute(
            "students",
            staffService.getAllStudents()
    );

    model.addAttribute(
            "contentFragment",
            "fragments/staff-students-content"
    );

    return "staff-dashboard";
}

        @Autowired
private StaffService staffService;



    

    // =========================
    // MARKS
    // =========================

    @GetMapping("/marks")
    public String marks(Model model) {

        model.addAttribute(
                "activePage",
                "marks"
        );

        model.addAttribute(
                "content",
                "fragments/staff-marks-content"
        );

        return "staff-dashboard";
    }

    // =========================
    // ANALYTICS
    // =========================

    @GetMapping("/analytics")
    public String analytics(Model model) {

        model.addAttribute(
                "activePage",
                "analytics"
        );

        model.addAttribute(
                "content",
                "fragments/staff-analytics-content"
        );

        return "staff-dashboard";
    }
}