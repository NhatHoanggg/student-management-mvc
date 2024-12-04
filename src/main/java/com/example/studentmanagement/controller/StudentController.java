package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    @GetMapping("/new")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/create-student";
    }

    @PostMapping("/new")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }


    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "students/edit-student";
    }


    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return "redirect:/students";
        }
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());
        studentService.saveStudent(existingStudent);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
