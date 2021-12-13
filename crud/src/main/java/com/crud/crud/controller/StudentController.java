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
import com.crud.crud.model.Student;
import com.crud.crud.repository.CourseRepository;
import com.crud.crud.repository.StudentRepository;

@RestController 
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	private final StudentRepository studentrepository;
	private final CourseRepository courserepository;
	
	@Autowired
	 public StudentController(StudentRepository studentrepository, CourseRepository courserepository) {
        this.studentrepository = studentrepository;
        this.courserepository = courserepository;
    }
	
	 @PostMapping("/student")
	    public ResponseEntity<Student> create(@RequestBody Student student) {
	        Student savedStudent = studentrepository.save(student);
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(savedStudent.getId()).toUri();

	        return ResponseEntity.created(location).body(savedStudent);
	    }
	 
	 @PutMapping("/student/{id}")
	    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
	        Optional<Student> optionalStudent = studentrepository.findById(id);
	        if (!optionalStudent.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        student.setId(optionalStudent.get().getId());
	        studentrepository.save(student);

	        return ResponseEntity.noContent().build();
	    }
	 @DeleteMapping("/student/{id}")
	    public ResponseEntity<Student> delete(@PathVariable Long id) {
	        Optional<Student> optionalStudent = studentrepository.findById(id);
	        if (!optionalStudent.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        studentrepository.delete(optionalStudent.get());

	        return ResponseEntity.noContent().build();
	    }
	 @GetMapping("/student/{id}")
	    public ResponseEntity<Student> getById(@PathVariable Long id) {
	        Optional<Student> optionalStudent = studentrepository.findById(id);
	        if (!optionalStudent.isPresent()) {
	            return ResponseEntity.unprocessableEntity().build();
	        }

	        return ResponseEntity.ok(optionalStudent.get());
	    }
	 @GetMapping("/student")
	    public ResponseEntity<Page<Student>> getAll(Pageable pageable) {
	        return ResponseEntity.ok(studentrepository.findAll(pageable));
	 }

}