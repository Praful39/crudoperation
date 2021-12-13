package com.crud.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.crud.crud.model.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	List<Course> findByStudentId(Long studentId);
	 Optional<Course> findByIdAndStudentId(Long id, Long studentId);

}
