package com.example.demo.controller;

import com.example.demo.dto.CreateSchoolClassDto;
import com.example.demo.dto.UpdateSchoolClassDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.service.SchoolClassService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    // GET /classes
    @GetMapping
    public List<SchoolClass> getAllClasses() {
        return schoolClassService.getAllClasses();
    }

    // GET /classes/{id}
    @GetMapping("/{id}")
    public SchoolClass getClassById(@PathVariable Long id) {
        return schoolClassService.getClassById(id);
    }

    // POST /classes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolClass createClass(
            @Valid @RequestBody CreateSchoolClassDto dto) {

        return schoolClassService.createClass(dto);
    }

    // PUT /classes/{id}
    @PutMapping("/{id}")
    public SchoolClass updateClass(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSchoolClassDto dto) {

        return schoolClassService.updateClass(id, dto);
    }

    // DELETE /classes/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClass(id);
    }
}