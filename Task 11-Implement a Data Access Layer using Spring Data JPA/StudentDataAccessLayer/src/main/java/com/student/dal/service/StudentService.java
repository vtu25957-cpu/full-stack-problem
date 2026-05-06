package com.student.dal.service;

import com.student.dal.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    Student getStudentById(Long id);
    void deleteStudent(Long id);
    
    List<Student> getAllStudents();
    List<Student> getStudentsByDepartment(String department);
    List<Student> getStudentsByAgeRange(Integer minAge, Integer maxAge);
    
    Page<Student> getAllStudentsPaginated(Pageable pageable);
    Page<Student> getStudentsByDepartmentPaginated(String department, Pageable pageable);
    
    List<Student> getActiveStudentsByDepartment(String department);
    Long getStudentCountByDepartment(String department);
}