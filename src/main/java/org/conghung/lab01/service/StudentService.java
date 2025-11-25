package org.conghung.lab01.service;

import org.conghung.lab01.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(String id);

    Student create(Student student);

    Student update(Student student);

    void deleteById(String id);
}
