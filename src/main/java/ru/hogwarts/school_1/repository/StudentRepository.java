package ru.hogwarts.school_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school_1.entity.AllStudent;
import ru.hogwarts.school_1.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByAge(Integer age);
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT(id) FROM Student", nativeQuery = true)
           List<AllStudent> countAllStudent();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    List<AllStudent> findAvarageAge();

    @Query(value = "SELECT name FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<AllStudent> findFiveLastStudent();

}
