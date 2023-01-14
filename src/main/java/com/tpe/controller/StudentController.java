package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")  //  => http://localhost:8088/students
public class StudentController
    {

        @Autowired
        private StudentService studentService;

        // Get All Students
        @GetMapping //  => http://localhost:8088/students + GET
        public ResponseEntity<List<Student>> getAll()
        {
            List<Student> students = studentService.getAll();

            return ResponseEntity.ok(students); // 200 kodunu HTTP Status kodu olarak gönderir
        }

        // Student objesi olusturalim

        @PostMapping  //  => http://localhost:8088/students + POST + JSON      POST (create islemleri icin kullanilir.)
        public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student)
        {
            // @Valid : parametreler valid mi kontrol eder, bu örenekte Student
            //objesi oluşturmak için  gönderilen fieldlar yani
            //name gibi özellikler düzgün set edilmiş mi ona bakar.
            // @RequestBody = gelen parametreyi, requestin bodysindeki bilgisi ,
            //Student objesine map edilmesini sağlıyor.

            studentService.createStudent(student);

            Map<String, String> map = new HashMap<>();
            map.put("message", "Student is created successfully");
            map.put("status", "true");
            return new ResponseEntity<>(map, HttpStatus.CREATED);//201
        }

        // id ile ogrenci cagiralim ReuquestParam ile

        @GetMapping("/query")  //http://localhost:8088/students/query?id=1
        public ResponseEntity<Student> getStudent(@RequestParam("id") Long id)
        {
           Student student = studentService.findStudent(id);
           return ResponseEntity.ok(student);
        }



    }
