package srms_web.model;

public class StudentMark {

private int studentId;

private String rollNumber;

private String name;

private Integer marks;

public int getStudentId() {
return studentId;
}

public void setStudentId(
int studentId
){
this.studentId=studentId;
}

public String getRollNumber(){
return rollNumber;
}

public void setRollNumber(
String rollNumber
){
this.rollNumber=rollNumber;
}

public String getName(){
return name;
}

public void setName(
String name
){
this.name=name;
}

public Integer getMarks(){
return marks;
}

public void setMarks(
Integer marks
){
this.marks=marks;
}

}