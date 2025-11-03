package ru.hogwarts.school_1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school_1.entity.AllStudent;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.model.Student;
import ru.hogwarts.school_1.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Optional<Faculty> getFacultyOfStudent(Long studentId) {
        log.debug("Invoked method 'getFacultyOfStudent' with parameter {}", studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return Optional.ofNullable(student.getFaculty());
        } else {
            log.warn("No student found with id={}", studentId);
            return Optional.empty();
        }
    }

    @Transactional
    public Student addStudent(Student student) {
        log.info("Invoked method 'addStudent'");
        return studentRepository.save(student);
    }


    public Optional<Student> findStudent(Long id) {
        log.debug("Invoked method 'findStudent' with parameter {}", id);
        return studentRepository.findById(id);
    }

    @Transactional
    public Student editStudent(Student updatedStudent) {
        log.info("Invoked method 'editStudent' с new data {}", updatedStudent);
        return studentRepository.save(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        log.info("Invoked method 'deleteStudent' с id={}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> findByAge(Integer age) {
        log.debug("Invoked method 'findByAge' с parameter {}", age);
        return studentRepository.findAllByAge(age);
    }

    public List<Student> findByAgeBetween(Integer minAge, Integer maxAge) {
        log.debug("Invoked method 'findByAgeBetween' с parameters {} to {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<AllStudent> countAllStudent() {
        log.info("Invoked method 'countAllStudent'");
        return studentRepository.countAllStudent();
    }

    public List<AllStudent> findAvarageAge() {
        log.info("Invoked method 'findAvarageAge'");
        return studentRepository.findAvarageAge();
    }

    public List<AllStudent> findFiveLastStudent() {
        log.info("Invoked method 'findFiveLastStudent'");
        return studentRepository.findFiveLastStudent();
    }
}



/*package ru.hogwarts.school_1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school_1.entity.AllStudent;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.model.Student;
import ru.hogwarts.school_1.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @Autowired)
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Faculty> getFacultyOfStudent(Long studentId) {
        log.debug("Invoked method 'getFacultyOfStudent' with parameter {}", studentId);

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return Optional.ofNullable(student.getFaculty());
        }
        else {
            log.warn("No student found with id={}", studentId);
            return Optional.empty();
    }

    @Transactional
    public Student addStudent(Student student) {
            log.info("Invoked method 'addStudent'");
            return studentRepository.save(student);
    }

    public Optional<Student> findStudent(Long id) {
            log.debug("Invoked method 'findStudent' with parameter {}", id);
            return studentRepository.findById(id);
    }

    @Transactional
    public Student editStudent(Student updatedStudent) {
            log.info("Invoked method 'editStudent' with new data {}", updatedStudent);
            return studentRepository.save(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
            log.info("Invoked method 'deleteStudent' with id={}", id);
            studentRepository.deleteById(id);
    }

    public List<Student> findByAge(Integer age) {
            log.debug("Invoked method 'findByAge' with parameter {}", age);
            return studentRepository.findAllByAge(age);
    }

    public List<Student> findByAgeBetween(Integer minAge, Integer maxAge) {
            log.debug("Invoked method 'findByAgeBetween' with parameters {} to {}", minAge, maxAge);
            return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<AllStudent> countAllStudent() {
            log.info("Invoked method 'countAllStudent'");
            return studentRepository.countAllStudent();
    }

    public List<AllStudent> findAvarageAge() {
            log.info("Invoked method 'findAvarageAge'");
            return studentRepository.findAvarageAge();
    }

    public List<AllStudent> findFiveLastStudent() {
            log.info("Invoked method 'findFiveLastStudent'");
            return studentRepository.findFiveLastStudent();
    }

}
*/