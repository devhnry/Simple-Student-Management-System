package org.henry.onlinebankingsystem.student;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractThrowableAssert;
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
//import static org.mockito.Mockito.when;
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
        Student student = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );

        given(studentRepository.findStudentByEmail(email)).willReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> underTest.getStudent(email))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("student with email " + email + "does not exist");
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
                .hasMessage("student with id " + student.getStudentID() + "does not exist");
    }

    @Test
    void canUpdateStudent(){
        //given
        String email = "taiwoh782@gmail.com";
        Student existingStudent = new Student(
                "Henry",
                email,
                LocalDate.of(2004, JUNE,5)
        );
        //when
        when(studentRepository.existsById(existingStudent.getStudentID())).thenReturn(true);

        Student updatingStudent = new Student("Henry 2", "taiwoh785@gmail.com", LocalDate.of(2006, JUNE,5));

        ResponseEntity<Student> response =  underTest.updateStudent(updatingStudent.getStudentID(), updatingStudent);

        //then
//        assertThatThrownBy(() -> underTest.updateStudent(existingStudent.getStudentID(), existingStudent))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessage("student with email " + email + " does not exist");

//        underTest.updateStudent(updatingStudent.getStudentID(), updatingStudent);
        assertThat(200).isEqualTo(response.getStatusCode());
        verify(studentRepository).save(updatingStudent);

        assertThat("Henry2").isEqualTo(existingStudent.getName());
        assertThat("taiwoh782@gmail.com").isEqualTo(existingStudent.getEmail());
        assertThat(LocalDate.of(2006, JUNE, 5)).isEqualTo(existingStudent.getDob());
//        String studentID = "test@example.com";
//        Student existingStudent = new Student("Existing", "existing@example.com", LocalDate.of(2000, 1, 1));
//        when(studentRepository.findStudentByEmail(studentID)).thenReturn(Optional.of(existingStudent));
//
//        Student updatedStudent = new Student("Updated", "updated@example.com", LocalDate.of(2001, 2, 2));
//
//        ResponseEntity<Student> responseEntity = studentService.updateStudent(studentID, updatedStudent);
//
//        // Verify that the method returns ResponseEntity with OK status
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        // Verify that the existing student is updated and saved
//        verify(studentRepository, times(1)).save(existingStudent);
//        assertEquals("Updated", existingStudent.getName());
//        assertEquals("updated@example.com", existingStudent.getEmail());
//        assertEquals(LocalDate.of(2001, 2, 2), existingStudent.getDob());

    }
}