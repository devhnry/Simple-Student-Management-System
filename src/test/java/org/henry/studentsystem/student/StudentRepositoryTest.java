package org.henry.studentsystem.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static java.time.Month.JUNE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void ifStudentExistByEmail() {
        //given
        String email = "taiwoh782@gmail.com";
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );
        underTest.save(student);
        //when
        Student exist = underTest.findStudentByEmail(email).orElseThrow();
        //then
        Student expectedStudent = new Student(
                student.getStudentID(),
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );
        assertThat(exist).isEqualTo(expectedStudent);

    }
}