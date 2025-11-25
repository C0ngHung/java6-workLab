package org.conghung.lab01.service.impl;

import lombok.RequiredArgsConstructor;
import org.conghung.lab01.model.Student;
import org.conghung.lab01.repository.StudentDAO;
import org.conghung.lab01.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    @Override
    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    @Override
    public Student findById(String id) {
        return studentDAO.findById(id).orElse(null);
    }

    @Override
    public Student create(Student student) {
        if (student.getId() == null) {
            throw new RuntimeException("id is null");
        }
        return studentDAO.save(student);
    }

    @Override
    public Student update(Student student) {
        Student updatedStudent = studentDAO.findById(student.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        updatedStudent.setId(student.getId());
        updatedStudent.setName(student.getName());
        updatedStudent.setMark(student.getMark());
        updatedStudent.setGender(student.isGender());

        return studentDAO.save(updatedStudent);
    }


    @Override
    public void deleteById(String id) {
        studentDAO.deleteById(id);
    }
}
