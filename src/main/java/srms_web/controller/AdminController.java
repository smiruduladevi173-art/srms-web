package srms_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import srms_web.model.Student;


import srms_web.admin.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String departments(
            Model model
    ){

        model.addAttribute(
                "activePage",
                "departments"
        );

        model.addAttribute(
                "departments",
                adminService.getAllDepartments()
        );

        model.addAttribute(
                "view",
                "fragments/admin-departments-content"
        );

        return "admin-dashboard";
    }

//=============================
// ADD DEPARTMENT (controller)
//============================= 

@PostMapping("/admin/departments/add")
public String addDepartment(
        String departmentName
){

    adminService.addDepartment(
            departmentName
    );

    return "redirect:/admin";
}

//=======DELERE DEPARTMENT========//
@PostMapping(
"/admin/departments/delete"
)
public String deleteDepartment(
        int id,
        RedirectAttributes redirectAttributes
){

    if(
    !adminService
    .canDeleteDepartment(id)
    ){

        redirectAttributes
        .addFlashAttribute(
                "error",
                "Cannot delete department. Students are assigned to it."
        );

    }else{

        adminService
        .deleteDepartment(id);

        redirectAttributes
        .addFlashAttribute(
                "message",
                "Department deleted successfully."
        );

    }

    return
    "redirect:/admin?view=departments";
}



//==============================================================================//
//=====================
// SUBJECT PAGE
//=====================

@GetMapping(
"/admin/subjects"
)

public String subjects(

@RequestParam(
required=false
)
Integer departmentId,

@RequestParam(
required=false
)
Integer semester,

Model model

){

model.addAttribute(
"activePage",
"subjects"
);

model.addAttribute(
"departments",
adminService
.getAllDepartments()
);


if(

departmentId!=null

&&

semester!=null

){

model.addAttribute(

"subjects",

adminService
.getSubjects(
departmentId,
semester
)

);

}


model.addAttribute(

"view",

"fragments/admin-subjects-content"

);


return
"admin-dashboard";

}



//=====================
// FILTER SUBJECTS
//=====================

@PostMapping(
"/admin/subjects/filter"
)

public String filterSubjects(

@RequestParam
int departmentId,

@RequestParam
int semester,

RedirectAttributes redirect

){

redirect
.addAttribute(
"departmentId",
departmentId
);

redirect
.addAttribute(
"semester",
semester
);

return
"redirect:/admin/subjects";

}



//=====================
// ADD SUBJECT
//=====================

@PostMapping(
"/admin/subjects/add"
)

public String addSubject(

@RequestParam
int departmentId,

@RequestParam
int semester,

@RequestParam
String subjectName,

RedirectAttributes redirect

){

adminService
.addSubject(

departmentId,

semester,

subjectName

);

redirect
.addFlashAttribute(

"message",

"Subject Added Successfully"

);

redirect
.addAttribute(
"departmentId",
departmentId
);

redirect
.addAttribute(
"semester",
semester
);

return
"redirect:/admin/subjects";

}



//=====================
// DELETE SUBJECT
//=====================

@PostMapping(
"/admin/subjects/delete"
)

public String deleteSubject(

@RequestParam
int subjectId,

@RequestParam
int departmentId,

@RequestParam
int semester,

RedirectAttributes redirect

){

boolean deleted=

adminService
.deleteSubject(
subjectId
);


if(
deleted
){

redirect
.addFlashAttribute(

"message",

"Subject Deleted Successfully"

);

}

else{

redirect
.addFlashAttribute(

"error",

"Delete Failed"

);

}


redirect
.addAttribute(
"departmentId",
departmentId
);

redirect
.addAttribute(
"semester",
semester
);

return
"redirect:/admin/subjects";

}

//=====================
// STUDENTS PAGE
//=====================

@GetMapping(
"/admin/students"
)

public String students(

@RequestParam(
required=false
)
String search,

Model model

){

model.addAttribute(

"activePage",

"students"

);

model.addAttribute(

"departments",

adminService
.getAllDepartments()

);

model.addAttribute(

"students",

adminService
.getStudents(
search
)

);

model.addAttribute(

"search",

search

);

model.addAttribute(

"view",

"fragments/admin-students-content"

);

return
"admin-dashboard";

}

//=====================
// ADD STUDENT
//=====================

@PostMapping(
"/admin/students/add"
)

public String addStudent(

Student student,

RedirectAttributes redirect

){

String result=

adminService
.addStudent(
student
);

if(

result.contains(
"exists"
)

||

result.equals(
"Failed"
)

){

redirect
.addFlashAttribute(

"error",

result

);

}

else{

redirect
.addFlashAttribute(

"message",

result

);

}

return
"redirect:/admin/students";

}

//=====================
// SEARCH STUDENT
//=====================

@PostMapping(
"/admin/students/search"
)

public String searchStudents(

@RequestParam
String search,

RedirectAttributes redirect

){

redirect
.addAttribute(

"search",

search

);

return
"redirect:/admin/students";

}

//=====================
// DELETE STUDENT
//=====================

@PostMapping(
"/admin/students/delete"
)

public String deleteStudent(

@RequestParam
int studentId,

RedirectAttributes redirect

){

boolean deleted=

adminService
.deleteStudent(
studentId
);

if(
deleted
){

redirect
.addFlashAttribute(

"message",

"Student deleted successfully"

);

}

else{

redirect
.addFlashAttribute(

"error",

"Delete failed"

);

}

return
"redirect:/admin/students";

}








}   