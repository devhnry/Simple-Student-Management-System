package org.henry.onlinebankingsystem.student;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.JUNE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService underTest;

    @Test
    void canGetAllStudents() {
        underTest.getStudents();
        verify(studentRepository).findAll();
    }

    @Test
    void canGetAStudent(){
        String email = "taiwoh782@gmail.com";
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );

        given(studentRepository.findStudentByEmail(email)).willReturn(Optional.of(student));

        Student result = underTest.getStudent(email);
        assertThat(result).isEqualTo(student);
    }

    @Test
    void studentDoesNotExist(){
        String email = "taiwoh782@gmail.com";

        given(studentRepository.findStudentByEmail(email)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getStudent(email))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("student with email " + email + " does not exist");
    }

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canAddNewStudent() {
        String email = "taiwoh782@gmail.com";
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );
        underTest.addNewStudent(student);

        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(argumentCaptor.capture());

        Student capturedStudent = argumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowEmailTaken() {
        //given
        String email = "taiwoh782@gmail.com";
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );

        //when
        given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(Optional.of(student));


        //then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("email taken");
        verify(studentRepository, never()).save(any());

    }

    @Test
    void canDeleteStudent() {
        String email = "taiwoh782@gmail.com";
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );

        given(studentRepository.existsById(student.getStudentID())).willReturn(true);

        underTest.deleteStudent(student.getStudentID());
    }

    @Test
    void throwExceptionWhileDeletingStudent() {
        String email = "taiwoh782@gmail.com";
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );

        given(studentRepository.existsById(student.getStudentID())).willReturn(false);

        assertThatThrownBy(() -> underTest.deleteStudent(student.getStudentID()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("student with id " + student.getStudentID() + " does not exist");
    }

    @Test
    void canUpdateStudent(){
        Student existingStudent = new Student(
                "John",
                "john@example.com",
                LocalDate.of(2000, 1, 1));
        Student updatedStudent = new Student(
                "John",
                "john_updated@example.com",
                LocalDate.of(2000, 1, 1));

        given(studentRepository.findStudentByEmail(existingStudent.getEmail())).willReturn(Optional.of(existingStudent));

        ResponseEntity<Student> responseEntity = underTest.updateStudent(existingStudent.getEmail(), updatedStudent);

        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(argumentCaptor.capture());
        Student newStudent = argumentCaptor.getValue();

        // Verify behavior
        assertThat(newStudent).isEqualTo(responseEntity.getBody());
    }

}
