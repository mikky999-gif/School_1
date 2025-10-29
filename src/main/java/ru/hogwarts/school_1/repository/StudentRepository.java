package ru.hogwarts.school_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_1.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(Integer age);
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);
}
