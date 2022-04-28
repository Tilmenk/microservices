package com.tomtenk.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail =
                this.studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Optional<Student> studentToDelete = this.studentRepository.findById(id);
        if (studentToDelete.isPresent()) {
            studentRepository.delete(studentToDelete.get());
        } else {
            throw new IllegalStateException("student does not exist");
        }
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student studentToUpdate =
                this.studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("student does not exist"));
        if (name != null) {
            studentToUpdate.setName(name);
        }
        if (email != null) {
            studentToUpdate.setEmail(email);
        }
    }
}
