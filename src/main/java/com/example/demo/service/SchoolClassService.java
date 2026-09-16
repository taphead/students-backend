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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "name");
    private static final Set<String> ALLOWED_DIRECTION_FIELDS = Set.of("asc", "desc");

    public SchoolClassService(SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository) {

        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
    }

    public Page<SchoolClassResponseDto> getAllClasses(int page, int size, String sortBy, String direction) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy.toLowerCase())) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        if (!ALLOWED_DIRECTION_FIELDS.contains(direction.toLowerCase())) {
            throw new IllegalArgumentException("Invalid direction field: " + direction);
        }

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SchoolClass> schoolClassPage = schoolClassRepository.findAll(pageable);

        return schoolClassPage.map(this::mapToResponseDto);
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