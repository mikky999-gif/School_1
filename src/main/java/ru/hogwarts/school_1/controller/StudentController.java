package ru.hogwarts.school_1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.model.Student;
import ru.hogwarts.school_1.repository.StudentRepository;
import ru.hogwarts.school_1.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

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

    @GetMapping("/names_start_from_A")
    public ResponseEntity<List<String>> getStudentNamesStartingWithA() {
        List<String> names = studentService.getStudentNamesStartingWithA();
        return names.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(names);
    }

    @GetMapping("/avg_age_of_student")
    public ResponseEntity<Double> getAverageAge() {
        Double averageAge = studentService.getAverageAge();
        return averageAge != null
                ? ResponseEntity.ok(averageAge)
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/sum-parallel")
    public ResponseEntity<Long> getSumParallel() {
        Long sum = studentService.getSumParallel();
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/students/print-parallel")
    public void printStudentsInParallel() {
        List<Student> allStudents = studentRepository.findAll();

        if (allStudents.size() < 6) {
            System.err.println("Требуется минимум шесть студентов.");
            return;
        }

        for (int i = 0; i < 2; i++) {
            System.out.println(allStudents.get(i).getName());
        }

        IntStream.range(2, 6)
                .parallel()
                .forEach(index -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.printf("%s: %s%n", threadName, allStudents.get(index).getName());
                });
    }
    synchronized void safePrint(String message) {
        System.out.println(message);
    }

    @GetMapping("/students/print-synchronized")
    public void printStudentsSynchronized() {
        List<Student> allStudents = studentRepository.findAll();

        if (allStudents.size() < 6) {
            System.err.println("Необходимо минимум шесть студентов");
            return;
        }

        safePrint(allStudents.get(0).getName());
        safePrint(allStudents.get(1).getName());

        Runnable thirdAndFourthTask = () -> {
            safePrint(allStudents.get(2).getName());
            safePrint(allStudents.get(3).getName());
        };
        new Thread(thirdAndFourthTask).start();

        Runnable fifthAndSixthTask = () -> {
            safePrint(allStudents.get(4).getName());
            safePrint(allStudents.get(5).getName());
        };
        new Thread(fifthAndSixthTask).start();
    }

}