package org.henry.studentsystem.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping(path = "{email}")
    public Student getStudentByEmail(@PathVariable("email") String email){
        return studentService.getStudent(email);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = {"{studentID}"})
    public void deleteStudent(@PathVariable("studentID") String studentID){
        studentService.deleteStudent(studentID);

    }
    @PutMapping(path = "{studentID}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("studentID") String studentID, @RequestBody Student student){
        studentService.updateStudent(studentID, student);
        return ResponseEntity.ok(student);
    }

}
