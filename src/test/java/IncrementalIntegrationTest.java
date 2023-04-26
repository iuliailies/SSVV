import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNull;

public class IncrementalIntegrationTest {
    Service service;

    @BeforeEach
    void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo("src/test/resources/testStudents.xml");
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo("src/test/resources/testAssignments.xml");
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo("src/test/resources/testGrades.xml");
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    void addStudent_test() {
        Student student = new Student("133", "Iulia", 934, "mail@mail.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addAssignmentIntegration_test() {
        Student student = new Student("107", "Iulia", 934, "mail@mail.com");
        assertNull(service.addStudent(student));
        Tema tema = new Tema("11", "description", 6, 5);
        assertNull(service.addTema(tema));
    }

    @Test
    void addGradeIntegration_test() {
        Student student = new Student("116", "Iulia", 934, "mail@mail.com");
        Tema tema = new Tema("17", "description", 6, 5);
        String[] date = "2018-11-16".split("-");
        LocalDate dataPredare = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        Nota nota = new Nota("4", student.getID(), tema.getID(), 9.5, dataPredare);
        service.addStudent(student);
        service.addTema(tema);
        assertNull(service.addNota(nota, "some feedback"));
    }

}
