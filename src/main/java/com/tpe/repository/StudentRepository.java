package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    // Spring Data JPA içinde existById() var fakat Spring Data JPA bize sondaki eki istediğimiz
// değişken ismi ile değiştirmemize izin veriyor, mevcut metodu bu şekilde türetebiliyoruz.


    List<Student> findByLastName(String lastName);

    // JPQL  ******
    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")  // Student yerine allies olarak  s  kullanacagiz
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    //Native SQL
    @Query(value="SELECT * FROM Student s WHERE s.grade=:pGrade", nativeQuery = true)
    List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);

    @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id")
    Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);


}
