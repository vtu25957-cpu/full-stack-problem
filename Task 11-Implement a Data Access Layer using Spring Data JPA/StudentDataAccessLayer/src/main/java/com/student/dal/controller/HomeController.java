package com.student.dal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Student Data Access Layer API is running!<br/><br/>"
                + "Available endpoints:<br/>"
                + "• GET /api/students - Get all students<br/>"
                + "• GET /api/students/{id} - Get student by ID<br/>"
                + "• POST /api/students - Create new student<br/>"
                + "• PUT /api/students/{id} - Update student<br/>"
                + "• DELETE /api/students/{id} - Delete student<br/>"
                + "• GET /api/students/department/{department} - Get students by department<br/>"
                + "• GET /api/students/age-range?minAge=&maxAge= - Get students by age range<br/>"
                + "• GET /api/students/paginated?page=&size= - Get paginated students";
    }
}