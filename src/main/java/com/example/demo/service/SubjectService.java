package com.example.demo.service;

import com.example.demo.dto.CreateSubjectDto;
import com.example.demo.dto.UpdateSubjectDto;
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

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found with id: " + id));
    }

    public Subject createSubject(CreateSubjectDto dto) {

        Subject subject = new Subject();
        subject.setName(dto.getName());

        return subjectRepository.save(subject);
    }

    public Subject updateSubject(Long id, UpdateSubjectDto dto) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found with id: " + id));

        subject.setName(dto.getName());

        return subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found with id: " + id));

        subjectRepository.delete(subject);
    }
}