package tn.esb.rh.service;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esb.rh.entity.Student;
import tn.esb.rh.repository.StudentRepository;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private StudentRepository studentRepository;

    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(Integer id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Student student) {
        Student std = studentRepository.findById(student.getRollNumber())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + student.getRollNumber()));
        std.setName(student.getName());
        std.setAddress(student.getAddress());
        std.setPercentage(student.getPercentage());
        return studentRepository.save(std);
    }

}
