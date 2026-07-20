package com.example.demo.service;

import com.example.demo.dto.CreateSchoolClassDto;
import com.example.demo.dto.UpdateSchoolClassDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Subject;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SchoolClassRepository;
import com.example.demo.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;

    public SchoolClassService(
            SchoolClassRepository schoolClassRepository,
            SubjectRepository subjectRepository) {

        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass getClassById(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Class not found with id: " + id));
    }

    public SchoolClass createClass(CreateSchoolClassDto dto) {

        List<Subject> subjects = subjectRepository.findAllById(dto.getSubjectIds());

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(dto.getName());
        schoolClass.setSubjects(subjects);

        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass updateClass(Long id, UpdateSchoolClassDto dto) {

        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Class not found with id: " + id));

        List<Subject> subjects = subjectRepository.findAllById(dto.getSubjectIds());

        schoolClass.setName(dto.getName());
        schoolClass.setSubjects(subjects);

        return schoolClassRepository.save(schoolClass);
    }

    public void deleteClass(Long id) {

        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Class not found with id: " + id));

        schoolClassRepository.delete(schoolClass);
    }
}