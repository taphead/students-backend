package com.example.demo.service;

import com.example.demo.dto.CreateSubjectDto;
import com.example.demo.dto.SubjectResponseDto;
import com.example.demo.dto.UpdateSubjectDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Subject;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.specification.SubjectSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "name");
    private static final Set<String> ALLOWED_DIRECTION_FIELDS = Set.of("asc", "desc");

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Page<SubjectResponseDto> getAllSubjects(int page, int size, String sortBy, String direction, String name,
                                                   Long id) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy.toLowerCase())) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        if (!ALLOWED_DIRECTION_FIELDS.contains(direction.toLowerCase())) {
            throw new IllegalArgumentException("Invalid direction field: " + direction);
        }

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy.toLowerCase()).descending();
        } else {
            sort = Sort.by(sortBy.toLowerCase()).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Subject> specification = (root, query, cb) -> cb.conjunction();

        if (name != null && !name.isEmpty()) {
            specification = specification.and(SubjectSpecification.hasName(name));
        }

        if (id != null) {
            specification = specification.and(SubjectSpecification.hasId(id));
        }

        Page<Subject> subjectPage = subjectRepository.findAll(specification, pageable);

        return subjectPage.map(this::mapToResponseDto);
    }

    public SubjectResponseDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        return mapToResponseDto(subject);
    }

    public SubjectResponseDto createSubject(CreateSubjectDto dto) {

        Subject subject = new Subject();
        subject.setName(dto.getName());

        Subject savedSubject = subjectRepository.save(subject);

        return mapToResponseDto(savedSubject);
    }

    public SubjectResponseDto updateSubject(Long id, UpdateSubjectDto dto) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        subject.setName(dto.getName());

        Subject updatedSubject = subjectRepository.save(subject);
        return mapToResponseDto(updatedSubject);
    }

    public void deleteSubject(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        subjectRepository.delete(subject);
    }

    private SubjectResponseDto mapToResponseDto(Subject subject) {

        List<Long> classIds = subject.getClasses().stream()
                .map(SchoolClass::getId)    // schoolClass -> schoolClass.getId()
                .toList();


        return new SubjectResponseDto(

                subject.getId(),

                subject.getName(),

                classIds);
    }
}