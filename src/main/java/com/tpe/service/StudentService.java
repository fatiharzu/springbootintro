package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll()
    {

        return studentRepository.findAll();
    }

    public void createStudent(Student student)
    {
        if(studentRepository.existsByEmail(student.getEmail()))
        {
            throw new ConflictException("E-mail is already exist!!");
        }
            studentRepository.save(student);
    }

    public Student findStudent(Long id)
    {
        return  studentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Student not find with id : "+ id));

    }

    public void deleteStudent(Long id)
    {
        Student student = findStudent(id);
        studentRepository.delete(student);
    }


    public void updateStudent(Long id, StudentDTO studentDTO)
    {
        boolean existEmail = studentRepository.existsByEmail(studentDTO.getEmail());
        Student student = findStudent(id);

        if (existEmail && ! studentDTO.getEmail().equals(student.getEmail()))
        {
            throw new ConflictException("Email is already exist");

                    /*
                      POJO CLASS , DTO
            1) kendi email : mrc , mrc --> TRUE && FALSE (UPDATE OLUR)
            2) kendi email : mrc, ahmt ve DB'de zaten ahmt diye biri var --> TRUE && TRUE (EXCEPTION ATAR)
            3) kendi email : mrc, mhmt ama DB'de mhmt diye biri yok --> FALSE &&  TRUE (EXCEPTION ATAR)
         */
        }
        student.setName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setGrade(studentDTO.getGrade());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        studentRepository.save(student);
    }

    public Page<Student> getAllWithPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public List<Student> findStudent(String lastName)
    {
       return studentRepository.findByLastName(lastName); //lastName
    }

    public List<Student> findAllEqualsGrade(Integer grade) {
        return studentRepository.findAllEqualsGradeWithSQL(grade);
    }

    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found with id : "+ id));
    }
}
