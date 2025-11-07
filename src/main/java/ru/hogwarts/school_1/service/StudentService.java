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
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Slf4j
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
//добавлено сообщение
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
    //добавлено сообщение
    @Transactional
    public Student addStudent(Student student) {
        log.info("Invoked method 'addStudent'");
        return studentRepository.save(student);
    }

    //добавлено сообщение
    public Optional<Student> findStudent(Long id) {
        log.debug("Invoked method 'findStudent' with parameter {}", id);
        return studentRepository.findById(id);
    }
    //добавлено сообщение
    @Transactional
    public Student editStudent(Student updatedStudent) {
        log.info("Invoked method 'editStudent' с new data {}", updatedStudent);
        return studentRepository.save(updatedStudent);
    }
    //добавлено сообщение
    @Transactional
    public void deleteStudent(Long id) {
        log.info("Invoked method 'deleteStudent' с id={}", id);
        studentRepository.deleteById(id);
    }
    //добавлено сообщение
    public List<Student> findByAge(Integer age) {
        log.debug("Invoked method 'findByAge' с parameter {}", age);
        return studentRepository.findAllByAge(age);
    }
    //добавлено сообщение
    public List<Student> findByAgeBetween(Integer minAge, Integer maxAge) {
        log.debug("Invoked method 'findByAgeBetween' с parameters {} to {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    //добавлено сообщение
    public List<AllStudent> countAllStudent() {
        log.info("Invoked method 'countAllStudent'");
        return studentRepository.countAllStudent();
    }
    //добавлено сообщение
    public List<AllStudent> findAvarageAge() {
        log.info("Invoked method 'findAvarageAge'");
        return studentRepository.findAvarageAge();
    }
    //добавлено сообщение
    public List<AllStudent> findFiveLastStudent() {
        log.info("Invoked method 'findFiveLastStudent'");
        return studentRepository.findFiveLastStudent();
    }

    public List<String> getStudentNamesStartingWithA() {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getName().startsWith("A"))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAge() {
        OptionalDouble averageAge = studentRepository.findAll()
                .stream()
                .mapToDouble(Student::getAge)
                .average();
        return averageAge.isPresent() ? averageAge.getAsDouble() : null;
    }

    public Long getSumParallel() {
        return LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

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