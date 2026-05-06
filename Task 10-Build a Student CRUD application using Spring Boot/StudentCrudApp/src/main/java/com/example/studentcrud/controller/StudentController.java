package com.example.studentcrud.controller;

import com.example.studentcrud.entity.Student;
import com.example.studentcrud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // Home page - List all students
    @GetMapping("/")
    public String home(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "index";
    }
    
    // Show add student form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }
    
    // Save new student - FIXED VERSION
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Saving student: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("Date of Birth: " + student.getDateOfBirth());
            
            // Check if email already exists
            if (studentService.emailExists(student.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Email already exists!");
                return "redirect:/students/add";
            }
            
            // If date is empty, set to null
            if (student.getDateOfBirth() == null) {
                // You can either leave it null or set a default
                // student.setDateOfBirth(LocalDate.now()); // Uncomment to set current date
            }
            
            studentService.saveStudent(student);
            redirectAttributes.addFlashAttribute("success", "Student added successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // This will show the full error in console
            redirectAttributes.addFlashAttribute("error", "Error saving student: " + e.getMessage());
        }
        return "redirect:/students/";
    }
    
    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
            model.addAttribute("student", student);
            return "edit-student";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Student not found!");
            return "redirect:/students/";
        }
    }
    
    // Update student - FIXED VERSION
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, 
                               RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Updating student ID: " + id);
            System.out.println("Date of Birth: " + student.getDateOfBirth());
            
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error updating student: " + e.getMessage());
        }
        return "redirect:/students/";
    }
    
    // View student details
    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
            model.addAttribute("student", student);
            return "view-student";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Student not found!");
            return "redirect:/students/";
        }
    }
    
    // Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/students/";
    }
    
    // Search students
    @GetMapping("/search")
    public String searchStudents(@RequestParam(required = false) String name, 
                                @RequestParam(required = false) String department, 
                                Model model) {
        List<Student> students;
        
        if (name != null && !name.isEmpty()) {
            students = studentService.searchStudents(name);
            model.addAttribute("searchTerm", name);
        } else if (department != null && !department.isEmpty()) {
            students = studentService.getStudentsByDepartment(department);
            model.addAttribute("searchTerm", department);
        } else {
            students = studentService.getAllStudents();
        }
        
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        return "index";
    }
}