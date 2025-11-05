package ru.hogwarts.school_1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.model.Student;
import ru.hogwarts.school_1.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping("/{facultyId}/students")
    public ResponseEntity<List<Student>> getStudentsOfFaculty(@PathVariable Long facultyId) {
        List<Student> students = facultyService.getStudentsOfFaculty(facultyId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFaculty(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        return facultyService.findFaculty(faculty.getId())
                .map(f -> ResponseEntity.ok(facultyService.editFaculty(faculty)))
                .orElse(ResponseEntity.badRequest().build());
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> searchFaculty(@RequestParam String term) {
        List<Faculty> faculties = facultyService.searchFaculty(term.toLowerCase());
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/longest-name")
    public ResponseEntity<String> getLongestFacultyName() {
        Optional<String> longestName = facultyService.findLongestFacultyName();

        return longestName.isPresent()
                ? ResponseEntity.ok(longestName.get())
                : ResponseEntity.noContent().build();
    }

}