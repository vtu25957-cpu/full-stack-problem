package com.example.studentcrud.service;

import com.example.studentcrud.entity.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Create or Update
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    // Read All
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    // Read by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    // Update
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setPhoneNumber(studentDetails.getPhoneNumber());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setDepartment(studentDetails.getDepartment());
        student.setSemester(studentDetails.getSemester());
        student.setAddress(studentDetails.getAddress());
        student.setCgpa(studentDetails.getCgpa());
        
        return studentRepository.save(student);
    }
    
    // Delete
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    // Search by department
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }
    
    // Search by name
    public List<Student> searchStudents(String name) {
        return studentRepository.searchByName(name);
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }
}