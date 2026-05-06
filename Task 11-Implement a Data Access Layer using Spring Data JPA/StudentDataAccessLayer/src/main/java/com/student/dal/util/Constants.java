package com.student.dal.util;

public final class Constants {
    
    private Constants() {}
    
    // API Endpoints
    public static final String BASE_URL = "/api/students";
    public static final String DEPARTMENT_URL = "/department/{department}";
    public static final String AGE_RANGE_URL = "/age-range";
    public static final String PAGINATED_URL = "/paginated";
    
    // Messages
    public static final String STUDENT_NOT_FOUND = "Student not found with id: ";
    public static final String STUDENT_CREATED = "Student created successfully";
    public static final String STUDENT_UPDATED = "Student updated successfully";
    public static final String STUDENT_DELETED = "Student deleted successfully";
    
    // Validation
    public static final int MIN_AGE = 16;
    public static final int MAX_AGE = 100;
    public static final int NAME_MIN_LENGTH = 2;
    public static final int NAME_MAX_LENGTH = 50;
}