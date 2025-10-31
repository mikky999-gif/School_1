package ru.hogwarts.school_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school_1.controller.StudentController;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.model.Student;
import ru.hogwarts.school_1.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
}

    @Test
    public void shouldReturnFacultyForStudent() throws Exception {
        when(studentService.getFacultyOfStudent(1L)).thenReturn(Optional.of(new Faculty()));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/faculty"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStudentDetails() throws Exception {
        when(studentService.findStudent(1L)).thenReturn(Optional.of(new Student()));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewStudent() throws Exception {
        Student newStudent = new Student();
        when(studentService.addStudent(newStudent)).thenReturn(newStudent);
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldEditStudent() throws Exception {
        Student existingStudent = new Student();
        when(studentService.editStudent(existingStudent)).thenReturn(existingStudent);
        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingStudent))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindStudentsByAge() throws Exception {
        List<Student> students = new ArrayList<>();
        when(studentService.findByAge(20)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("/student?age=20"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindStudentsByAgeRange() throws Exception {
        List<Student> students = new ArrayList<>();
        when(studentService.findByAgeBetween(18, 25)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-age-range?min=18&max=25"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}