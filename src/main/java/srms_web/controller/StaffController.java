package srms_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import srms_web.staff.StaffService;
import srms_web.model.AnalyticsSummary;
import srms_web.model.UserSession;

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

@RequestParam(defaultValue="0")
int departmentId,

@RequestParam(defaultValue="0")
int subjectId,

Model model

){

    model.addAttribute(
        "activePage",
        "marks"
    );

    model.addAttribute(
        "content",
        "fragments/staff-marks-content"
    );

    model.addAttribute(
        "departmentId",
        departmentId
    );

    model.addAttribute(
        "subjectId",
        subjectId
    );

    model.addAttribute(
        "departments",
        staffService.getDepartments()
    );

    model.addAttribute(
        "subjects",
        staffService.getSubjects(
            departmentId
        )
    );

    if(subjectId > 0){

        var rows =
        staffService.loadMarks(
            departmentId,
            subjectId
        );

        System.out.println(
            "Rows Loaded = "
            + rows.size()
        );

        model.addAttribute(
            "rows",
            rows
        );

    }else{

        model.addAttribute(
            "rows",
            java.util.Collections.emptyList()
        );

    }

    return "staff-dashboard";

}

//=========================
// SAVE MARKS
//=========================
@PostMapping("/marks/save")
public String saveMarks(

@RequestParam int subjectId,

@RequestParam int departmentId,

@RequestParam List<Integer> studentId,

@RequestParam List<Integer> marks

){

for(int i=0;i<studentId.size();i++){

staffService.saveMark(

studentId.get(i),

subjectId,

marks.get(i)

);

}

return "redirect:/staff/marks?departmentId="
+ departmentId
+ "&subjectId="
+ subjectId;

}

    // =========================
    // ANALYTICS
    // =========================

    @GetMapping("/analytics")
    public String analyticsPage(
            @RequestParam(
                    defaultValue = "0"
            )
            int departmentId,

            Model model,

            HttpSession session
    ) 

    {

        UserSession user =
                (UserSession)
                session.getAttribute("user");

        if(user == null) {

            return "redirect:/login";
        }

        model.addAttribute(
                "activePage",
                "analytics"
        );

        model.addAttribute(
                "departments",
                staffService.getDepartments()
        );

        model.addAttribute(
                "departmentId",
                departmentId
        );

      if(departmentId > 0) {

    AnalyticsSummary summary =
            staffService.getAnalyticsSummary(
                    departmentId
            );

    System.out.println(
            "Total Students = "
            + summary.getTotalStudents()
    );

    model.addAttribute(
            "summary",
            summary
    );

    model.addAttribute(
            "topStudents",
            staffService.getTopStudentsByDepartment(
                    departmentId
            )
    );
}

        model.addAttribute(
                "content",
                "fragments/staff-analytics-content"
        );
        System.out.println(
        "Department = " + departmentId
    );
    System.out.println("Analytics Page Loaded");

        return "staff-dashboard";
    }


}
