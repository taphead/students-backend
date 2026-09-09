package com.example.demo.service;

import com.example.demo.dto.CreateSchoolClassDto;
import com.example.demo.dto.SchoolClassResponseDto;
import com.example.demo.dto.UpdateSchoolClassDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Student;
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

    public SchoolClassService(SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository) {

        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<SchoolClassResponseDto> getAllClasses() {

        return schoolClassRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public SchoolClassResponseDto getClassById(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        return mapToResponseDto(schoolClass);
    }

    public SchoolClassResponseDto createClass(CreateSchoolClassDto dto) {

        List<Subject> subjects = subjectRepository.findAllById(dto.getSubjectIds());

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(dto.getName());
        schoolClass.setSubjects(subjects);

        return mapToResponseDto(schoolClassRepository.save(schoolClass));
    }

    public SchoolClassResponseDto updateClass(Long id, UpdateSchoolClassDto dto) {

        SchoolClass schoolClass = schoolClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        List<Subject> subjects = subjectRepository.findAllById(dto.getSubjectIds());

        schoolClass.setName(dto.getName());
        schoolClass.setSubjects(subjects);

        return mapToResponseDto(schoolClassRepository.save(schoolClass));
    }

    public void deleteClass(Long id) {

        SchoolClass schoolClass = schoolClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        schoolClassRepository.delete(schoolClass);
    }

    private SchoolClassResponseDto mapToResponseDto(SchoolClass schoolClass) {

        List<Long> studentIds = schoolClass.getStudents().stream().map(Student::getId).toList();


        List<Long> subjectIds = schoolClass.getSubjects().stream().map(Subject::getId).toList();


        return new SchoolClassResponseDto(

                schoolClass.getId(),

                schoolClass.getName(),

                subjectIds,

                studentIds);
    }
}