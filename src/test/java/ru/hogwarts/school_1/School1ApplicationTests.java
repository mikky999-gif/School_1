package ru.hogwarts.school_1;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school_1.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class School1ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

	@Autowired
    private TestRestTemplate restTemplate;





/*

    @GetMapping("/{studentId}/faculty")
    public ResponseEntity<Faculty> getFacultyOfStudent(@PathVariable Long studentId) {
        Optional<Faculty> faculty = studentService.getFacultyOfStudent(studentId);
        return faculty.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        return studentService.findStudent(student.getId())
                .map(s -> ResponseEntity.ok(studentService.editStudent(student)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Integer age) {
        if (age != null && age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/by-age-range")
    public ResponseEntity<List<Student>> findStudentsByAgeRange(
            @RequestParam("min") Integer minAge,
            @RequestParam("max") Integer maxAge
    ) {
        List<Student> students = studentService.findByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(students);
    }

 */

}
