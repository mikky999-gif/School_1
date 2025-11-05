package ru.hogwarts.school_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_1.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> { // добавляем public перед interface
    List<Faculty> findAllByColor(String color);
}