package ru.hogwarts.school_1.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school_1.model.Student;
import ru.hogwarts.school_1.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }


    public Optional<Student> findStudent(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student editStudent(Student updatedStudent) {
        return studentRepository.save(updatedStudent);
    }


    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findByAge(Integer age) {
        return studentRepository.findAllByAge(age); // Предположительно такое поведение реализовано в репозитории
    }
}