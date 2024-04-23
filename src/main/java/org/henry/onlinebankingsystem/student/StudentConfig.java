package org.henry.onlinebankingsystem.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JUNE;

@Configuration
public class StudentConfig {

//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository repository) {
//        return args -> {
//            Student henry = new Student("Henry",
//                    "henrytaiwo@gmail.com",
//                    LocalDate.of(2000, JUNE,5));
//
//            Student patricia = new Student(
//                    "Patricia",
//                    "patricia@gmail.com",
//                    LocalDate.of(2004, JUNE,5));
//
//            repository.saveAll(
//                    List.of(henry, patricia)
//            );
//        };
//    }
}
