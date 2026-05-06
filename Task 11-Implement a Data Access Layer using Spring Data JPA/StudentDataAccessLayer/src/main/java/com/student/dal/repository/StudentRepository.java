package com.student.dal.repository;

import com.student.dal.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Find by department
    List<Student> findByDepartment(String department);
    List<Student> findByDepartment(String department, Sort sort);
    
    // Find by age
    List<Student> findByAgeGreaterThanEqual(Integer age);
    List<Student> findByAgeBetween(Integer startAge, Integer endAge);
    
    // Find by department and active status
    List<Student> findByDepartmentAndActive(String department, Boolean active);
    
    // Find by name containing
    List<Student> findByNameContainingIgnoreCase(String name);
    
    // Pagination methods
    Page<Student> findByDepartment(String department, Pageable pageable);
    Page<Student> findByAge(Integer age, Pageable pageable);
    
    // Custom query
    @Query("SELECT s FROM Student s WHERE s.age BETWEEN :minAge AND :maxAge")
    List<Student> findStudentsByAgeRange(@Param("minAge") Integer minAge, 
                                         @Param("maxAge") Integer maxAge, 
                                         Sort sort);
    
    // Count methods
    Long countByDepartment(String department);
    boolean existsByEmail(String email);
}