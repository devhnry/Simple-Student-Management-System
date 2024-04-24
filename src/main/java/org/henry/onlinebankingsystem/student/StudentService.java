package org.henry.onlinebankingsystem.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(String email){
        return studentRepository.findStudentByEmail(email).
                orElseThrow(() ->
                        new IllegalStateException("student with email " + email + " does not exist"));
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional =
                studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
        System.out.println(student);
    }

    public void deleteStudent(String studentID) {
        boolean exists = studentRepository.existsById(studentID);
        if(!exists){
            throw new IllegalStateException("student with id " + studentID + " does not exist");
        }
        studentRepository.deleteById(studentID);
    }


    @Transactional
    public ResponseEntity<Student> updateStudent(String studentID, Student student) {
        Student existingStudent = getStudent(studentID);

        if(student.getName() != null){
            existingStudent.setName(student.getName());
        }
        if(student.getEmail() != null){
            existingStudent.setEmail(student.getEmail());
        }
        if(student.getDob() != null){
            existingStudent.setDob(LocalDate.parse(String.valueOf(student.getDob())));
        }

        studentRepository.save(existingStudent);

        return ResponseEntity.ok(existingStudent);
    }
}
