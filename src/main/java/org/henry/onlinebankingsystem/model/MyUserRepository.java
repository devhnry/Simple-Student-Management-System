package org.henry.onlinebankingsystem.model;

import org.henry.onlinebankingsystem.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

//    @Override
//    Optional<MyUser> findById(Long aLong);
    Optional<MyUser> findByUsername(String username);
}
