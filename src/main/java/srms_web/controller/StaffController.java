package srms_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import srms_web.staff.StaffService;

@Controller
@RequestMapping("/staff")
public class StaffController {

@Autowired
private StaffService staffService;

// =========================
// DEFAULT
// =========================

@GetMapping("")
public String home() {

    return "redirect:/staff/students";

}

// =========================
// STUDENTS
// =========================

@GetMapping("/students")
public String students(

        Model model

) {

    model.addAttribute(

            "students",

            staffService.getAllStudents()

    );

    model.addAttribute(

            "activePage",

            "students"

    );

    model.addAttribute(

            "content",

            "fragments/staff-students-content"

    );

    return "staff-dashboard";

}

// =========================
// SAVE ALL CHANGES
// =========================

@PostMapping("/students/save")
public String saveStudents(

        @RequestParam("rollNumber")
        List<String> rollNumbers,

        @RequestParam("name")
        List<String> names,

        @RequestParam("gender")
        List<String> genders,

        @RequestParam("dob")
        List<String> dobs

) {

    staffService.updateStudents(

            rollNumbers,

            names,

            genders,

            dobs

    );

    return "redirect:/staff/students";

}

// =========================
// MARKS
// =========================

@GetMapping("/marks")
public String marks(

        Model model

) {

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
public String analytics(

        Model model

)
 {

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