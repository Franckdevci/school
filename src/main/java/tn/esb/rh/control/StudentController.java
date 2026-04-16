package tn.esb.rh.control;

import jakarta.validation.Valid;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esb.rh.entity.Student;
import tn.esb.rh.service.IStudentService;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
@CrossOrigin(origins = "${allowed.origins:http://localhost:4200}")
public class StudentController {

    private IStudentService studentService;

    @PostMapping("/registerStudent")
    public Student registerStudent(@Valid @RequestBody Student student) {
        return studentService.registerStudent(student);
    }

    @GetMapping("/listStudents")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestParam Integer id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/updateStudent")
    public Student updateStudent(@Valid @RequestBody Student student) {
        return studentService.updateStudent(student);
    }

}
