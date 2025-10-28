package ru.hogwarts.school_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school_1.model.Faculty;
import ru.hogwarts.school_1.repository.FacultyRepository;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Transactional
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(Long id) {
        return facultyRepository.findById(id);
    }

    @Transactional
    public Faculty editFaculty(Faculty updatedFaculty) {
        return facultyRepository.save(updatedFaculty);
    }


    @Transactional
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }


    public List<Faculty> findByColor(String color) {
        return facultyRepository.findAllByColor(color); // Предположительно такое поведение реализовано в репозитории
    }

}