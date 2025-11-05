package ru.hogwarts.school_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school_1.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColor(String color);
    List<Faculty> findAll();
    @Query("SELECT f FROM Faculty f WHERE LOWER(f.name) LIKE %:searchTerm% OR LOWER(f.color) LIKE %:searchTerm%")
    List<Faculty> findByNameOrColorIgnoreCase(@Param("searchTerm") String searchTerm);

}