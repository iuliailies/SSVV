import domain.Student;
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
import validation.ValidationException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AddStudentTest {
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

     @AfterEach
     void cleanup() throws FileNotFoundException {

         PrintWriter pw = new PrintWriter("src/test/resources/testStudents.xml");

         pw.write(
                 "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                         "<Entities>\n" +
                         "    \n" +
                         "</Entities>");

         pw.close();
         service.getAllStudenti();
     }

    @Test
    void addStudentNormal_test() {
        Student student = new Student("101", "Iulia", 934, "mail@mail.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudentSameId() {
        Student student1 = new Student("102", "Iulia", 934, "mail@mail.com");
        Student student2 = new Student("102", "Iulia", 934, "mail@mail.com");
        service.addStudent(student1);
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student2);
        });
    }

    @Test
    void addStudentInvalidId() {
        Student student = new Student("", "Iulia", 934, "mail@mail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    void addStudentNonNumericalId() {
        Student student = new Student("10a9", "Iulia", 934, "mail@mail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    void addStudentEmptyName() {
        Student student = new Student("101", "", 934, "mail@mail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    void addStudentEmptyEmail() {
        Student student = new Student("111", "Iulia", 934, "");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    void addStudentInvalidEmail() {
        Student student = new Student("111", "Iulia", 934, "email");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    void addStudentInvalidGroupLower() {
        Student student = new Student("112", "Iulia", -934, "mail@mail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    void addStudentInvalidGroupHigher() {
        Student student = new Student("112", "Iulia", 1001, "mail@mail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

}
