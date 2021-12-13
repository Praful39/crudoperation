package com.crud.crud.controller;

import java.net.URI; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crud.crud.model.Course;
import com.crud.crud.model.Student;
import com.crud.crud.repository.CourseRepository;
import com.crud.crud.repository.StudentRepository;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	private final StudentRepository studentrepository;
	private final CourseRepository courserepository;
	
	@Autowired
    public CourseController(CourseRepository courserepository, StudentRepository studenrepository) {
        this.courserepository = courserepository;
        this.studentrepository = studenrepository;
	}
	
	@PostMapping("/course")
    public ResponseEntity<Course> create(@RequestBody Course course) {
        Optional<Student> optionalStudent = studentrepository.findById(course.getStudent().getId());
        if (!optionalStudent.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        course.setStudent(optionalStudent.get());

        Course savedCourse = courserepository.save(course);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedCourse.getId()).toUri();

        return ResponseEntity.created(location).body(savedCourse);
    }
	
	 @PutMapping("/course/{id}")
	    public ResponseEntity<Course> update(@RequestBody Course course, @PathVariable Long id) {
	        Optional<Student> optionalStudent = studentrepository.findById(course.getStudent().getId());
	        if (!optionalStudent.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        Optional<Course> optionalCourse = courserepository.findById(id);
	        if (!optionalCourse.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        course.setStudent(optionalStudent.get());
	        course.setId(optionalCourse.get().getId());
	        courserepository.save(course);

	        return ResponseEntity.noContent().build();
	    }
	 @DeleteMapping("/course/{id}")
	    public ResponseEntity<Course> delete(@PathVariable Long id) {
	        Optional<Course> optionalCourse = courserepository.findById(id);
	        if (!optionalCourse.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        courserepository.delete(optionalCourse.get());

	        return ResponseEntity.noContent().build();
	    }
	 @GetMapping
	    public ResponseEntity<Page<Course>> getAll(Pageable pageable) {
	        return ResponseEntity.ok(courserepository.findAll(pageable));
	    }

	    @GetMapping("/course/{id}")
	    public ResponseEntity<Course> getById(@PathVariable Long id) {
	        Optional<Course> optionalCourse = courserepository.findById(id);
	        if (!optionalCourse.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        return ResponseEntity.ok(optionalCourse.get());
	    }
    }
