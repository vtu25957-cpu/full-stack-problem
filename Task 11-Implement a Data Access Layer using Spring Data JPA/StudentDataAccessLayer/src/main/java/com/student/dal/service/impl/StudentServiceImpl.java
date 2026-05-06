package com.student.dal.service.impl;

import com.student.dal.entity.Student;
import com.student.dal.exception.StudentNotFoundException;
import com.student.dal.repository.StudentRepository;
import com.student.dal.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    @Override
    public Student createStudent(Student student) {
        student.setEnrollmentDate(java.time.LocalDate.now());
        return studentRepository.save(student);
    }
    
    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setAge(studentDetails.getAge());
        student.setDepartment(studentDetails.getDepartment());
        student.setActive(studentDetails.getActive());
        
        return studentRepository.save(student);
    }
    
    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }
    
    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
    
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    
    @Override
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department, Sort.by(Sort.Direction.ASC, "name"));
    }
    
    @Override
    public List<Student> getStudentsByAgeRange(Integer minAge, Integer maxAge) {
        return studentRepository.findStudentsByAgeRange(minAge, maxAge, 
            Sort.by(Sort.Direction.ASC, "age"));
    }
    
    @Override
    public Page<Student> getAllStudentsPaginated(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
    
    @Override
    public Page<Student> getStudentsByDepartmentPaginated(String department, Pageable pageable) {
        return studentRepository.findByDepartment(department, pageable);
    }
    
    @Override
    public List<Student> getActiveStudentsByDepartment(String department) {
        return studentRepository.findByDepartmentAndActive(department, true);
    }
    
    @Override
    public Long getStudentCountByDepartment(String department) {
        return studentRepository.countByDepartment(department);
    }
}