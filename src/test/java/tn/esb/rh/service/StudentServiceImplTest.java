package tn.esb.rh.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esb.rh.entity.Student;
import tn.esb.rh.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl studentService;

    Student student = new Student(1, "name1", "adress1", 20.00);
    List<Student> listStudents = new ArrayList<>() {
        {
            add(new Student(2, "name2", "adress2", 30.00));
            add(new Student(3, "name3", "adress3", 10.00));
        }
    };

    @Test
    @Order(1)
    public void testRetrieveAllStudents() {
        Mockito.when(studentRepository.findAll()).thenReturn(listStudents);

        List<Student> result = studentService.getStudents();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    @Order(2)
    public void testAddStudent() {
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        Student result = studentService.registerStudent(student);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("name1", result.getName());
        Assertions.assertEquals(20.00, result.getPercentage());
    }

    @Test
    @Order(3)
    public void testUpdateStudent() {
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        Student updated = new Student(1, "nameUpdated", "adressUpdated", 99.00);
        Student result = studentService.updateStudent(updated);

        Assertions.assertNotNull(result);
    }

    @Test
    @Order(4)
    public void testUpdateStudent_NotFound() {
        Mockito.when(studentRepository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                studentService.updateStudent(new Student(99, "x", "y", 10.00))
        );
    }

    @Test
    @Order(5)
    public void testDeleteStudent_NotFound() {
        Mockito.when(studentRepository.findById(99)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () ->
                studentService.deleteStudent(99)
        );
    }

}
