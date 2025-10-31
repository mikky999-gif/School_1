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
import ru.hogwarts.school_1.controller.FacultyController;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldReturnStudentsOfFaculty() throws Exception {
        when(facultyService.getStudentsOfFaculty(1L)).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1/students"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnFacultyDetails() throws Exception {
        when(facultyService.findFaculty(1L)).thenReturn(Optional.of(new Faculty()));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewFaculty() throws Exception {
        Faculty newFaculty = new Faculty();
        when(facultyService.addFaculty(newFaculty)).thenReturn(newFaculty);
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFaculty))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldEditFaculty() throws Exception {
        Faculty existingFaculty = new Faculty();
        when(facultyService.editFaculty(existingFaculty)).thenReturn(existingFaculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingFaculty))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSearchFaculties() throws Exception {
        List<Faculty> faculties = new ArrayList<>();
        when(facultyService.searchFaculty("Blue")).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/search?term=Blue"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindFacultiesByColor() throws Exception {
        List<Faculty> faculties = new ArrayList<>();
        when(facultyService.findByColor("Red")).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?color=Red"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}