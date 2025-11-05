package ru.hogwarts.school_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldReturnFacultyForStudent() {
        Long studentId = 1L;
        ResponseEntity<Faculty> response = restTemplate.getForEntity(baseUrl + port + "/student/" + studentId + "/faculty", Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldReturnStudentDetails() {
        Long studentId = 1L;
        ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + port + "/student/" + studentId, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }


    @Test
    public void shouldCreateNewStudent() {
        Student newStudent = new Student();
        HttpEntity<Student> request = new HttpEntity<>(newStudent);
        ResponseEntity<Student> response = restTemplate.exchange(baseUrl + port + "/student", HttpMethod.POST, request, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldUpdateExistingStudent() {
        Long studentId = 1L;
        Student updatedStudent = new Student();
        HttpEntity<Student> request = new HttpEntity<>(updatedStudent);
        ResponseEntity<Student> response = restTemplate
                .exchange(baseUrl + port + "/student", HttpMethod.PUT, request, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldDeleteStudent() {
        Long studentId = 1L;
        ResponseEntity<Void> response = restTemplate
                .exchange(baseUrl + port + "/student/" + studentId, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldFindStudentsByAge() {
        Integer age = 20;
        ParameterizedTypeReference<List<Student>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Student>> response = restTemplate.exchange(baseUrl + port + "/student?age=" + age, HttpMethod.GET, null, typeRef);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldFindStudentsByAgeRange() {
        Integer minAge = 18;
        Integer maxAge = 25;
        ParameterizedTypeReference<List<Student>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                baseUrl + port + "/student/by-age-range?min={min}&max={max}",
                HttpMethod.GET,
                null,
                typeRef,
                minAge,
                maxAge
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }
}