import domain.Student;
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
    
    @Test
    void addStudentNormal_test() {
        Student student = new Student("100", "Iulia", 934, "mail@mail.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addStudentEmptyName() {
        Student student = new Student("101", "", 934, "mail@mail.com");
        assertThrows(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }
}
