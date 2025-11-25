package org.conghung.lab01.controller;

import lombok.RequiredArgsConstructor;
import org.conghung.lab01.model.Student;
import org.conghung.lab01.service.StudentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class StudentRestApi {

    private final StudentService studentService;

    @GetMapping("students")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("students/{id}")
    public Student findById(@PathVariable("id") String id) {
        return studentService.findById(id);
    }

    @PostMapping("students")
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping("students/{id}")
    public Student update(@PathVariable("id") String id, @RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("students/{id}")
    public void delete(@PathVariable("id") String id) {
        studentService.deleteById(id);
    }
}
