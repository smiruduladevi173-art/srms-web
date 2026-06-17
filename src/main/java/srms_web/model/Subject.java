package srms_web.model;

public class Subject{

private int id;

private String subjectName;

private int semester;

private int departmentId;

private String departmentName;


// ID

public int getId(){
return id;
}

public void setId(
int id
){
this.id=id;
}


// SUBJECT

public String getSubjectName(){
return subjectName;
}

public void setSubjectName(
String subjectName
){
this.subjectName=
subjectName;
}


// SEMESTER

public int getSemester(){
return semester;
}

public void setSemester(
int semester
){
this.semester=
semester;
}


// DEPARTMENT

public int getDepartmentId(){
return departmentId;
}

public void setDepartmentId(
int departmentId
){
this.departmentId=
departmentId;
}


// DEPARTMENT NAME

public String getDepartmentName(){
return departmentName;
}

public void setDepartmentName(
String departmentName
){
this.departmentName=
departmentName;
}

}