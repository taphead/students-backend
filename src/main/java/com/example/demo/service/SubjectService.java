package com.example.demo.service;

import com.example.demo.dto.CreateSubjectDto;
import com.example.demo.dto.SubjectResponseDto;
import com.example.demo.dto.UpdateSubjectDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Subject;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectResponseDto> getAllSubjects() {
        return subjectRepository
                .findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public SubjectResponseDto getSubjectById(Long id) {
        Subject subject = subjectRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found with id: " + id));

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
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found with id: " + id));

        subject.setName(dto.getName());

        Subject updatedSubject = subjectRepository.save(subject);
        return mapToResponseDto(updatedSubject);
    }

    public void deleteSubject(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found with id: " + id));

        subjectRepository.delete(subject);
    }

    private SubjectResponseDto mapToResponseDto(
            Subject subject
    ) {

        List<Long> classIds =
                subject
                        .getClasses()
                        .stream()
                        .map(SchoolClass::getId)    // schoolClass -> schoolClass.getId()
                        .toList();


        return new SubjectResponseDto(

                subject.getId(),

                subject.getName(),

                classIds
        );
    }
}