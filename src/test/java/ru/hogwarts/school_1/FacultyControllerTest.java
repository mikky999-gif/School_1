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
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:";

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldReturnStudentsOfFaculty() {
        Long facultyId = 1L;
        ParameterizedTypeReference<List<Student>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                baseUrl + port + "/faculty/" + facultyId + "/students",
                HttpMethod.GET,
                null,
                typeRef
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldReturnFacultyDetails() {
        Long facultyId = 1L;
        ResponseEntity<Faculty> response = restTemplate
                .getForEntity(baseUrl + port + "/faculty/" + facultyId, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldCreateNewFaculty() {
        Faculty newFaculty = new Faculty();
        HttpEntity<Faculty> request = new HttpEntity<>(newFaculty);
        ResponseEntity<Faculty> response = restTemplate
                .exchange(baseUrl + port + "/faculty", HttpMethod.POST, request, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldUpdateExistingFaculty() {
        Long facultyId = 1L;
        Faculty updatedFaculty = new Faculty();
        HttpEntity<Faculty> request = new HttpEntity<>(updatedFaculty);
        ResponseEntity<Faculty> response = restTemplate
                .exchange(baseUrl + port + "/faculty", HttpMethod.PUT, request, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldDeleteFaculty() {
        Long facultyId = 1L;
        ResponseEntity<Void> response = restTemplate
                .exchange(baseUrl + port + "/faculty/" + facultyId, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSearchFaculties() {
        String searchTerm = "Blue";
        ParameterizedTypeReference<List<Faculty>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                baseUrl + port + "/faculty/search?term=" + searchTerm,
                HttpMethod.GET,
                null,
                typeRef
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    public void shouldFindFacultiesByColor() {
        String color = "Red";
        ParameterizedTypeReference<List<Faculty>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Faculty>> response = restTemplate.exchange(
                baseUrl + port + "/faculty?color=" + color,
                HttpMethod.GET,
                null,
                typeRef
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
    }
}