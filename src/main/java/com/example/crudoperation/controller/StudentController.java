/**
 * Author: Theekshana Harischandra
 * User:wasat
 * Date:9/4/2025
 * Time:4:02 PM
 */

package com.example.crudoperation.controller;

import com.example.crudoperation.model.Student;
import com.example.crudoperation.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getOneStudent(@PathVariable Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            return studentRepository.save(student);
        }).orElse(null);
    }
}
