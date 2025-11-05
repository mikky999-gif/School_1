package ru.hogwarts.school_1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school_1.entity.AllStudent;
import ru.hogwarts.school_1.service.StudentService;

import java.util.List;

@RestController
public class AllStudentController {

    private final StudentService studentService;

    public AllStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/count-all-student")
    public List<AllStudent> countAllStudent(){
        return studentService.countAllStudent();
    }

    @GetMapping("/find-avarage-age")
    public List<AllStudent> findAvarageAge() {
        return studentService.findAvarageAge();
    }

    @GetMapping("/find-five-last-student")
    public List<AllStudent> findFiveLastStudent() {
        return studentService.findFiveLastStudent();
    }

}
