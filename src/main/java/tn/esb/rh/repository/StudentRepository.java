package tn.esb.rh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esb.rh.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
