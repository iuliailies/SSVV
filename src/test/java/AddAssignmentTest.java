import domain.Tema;
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

public class AddAssignmentTest {
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
    void addAssignment_test() {
        Tema tema = new Tema("5", "description", 6, 5);
        assertNull(service.addTema(tema));
    }

    @Test
    void addAssignmentDeadlineAbove_test() {
        Tema tema = new Tema("1", "description", 19, 5);
        assertThrows(ValidationException.class, () -> {
            service.addTema(tema);
        });
    }

    @Test
    void addAssignmentInvalidId_test() {
        Tema tema = new Tema("", "description", 6, 5);
        assertThrows(ValidationException.class, () -> {
            service.addTema(tema);
        });
    }

    @Test
    void addAssignmentEmptyDescription_test() {
        Tema tema = new Tema("1", "", 6, 5);
        assertThrows(ValidationException.class, () -> {
            service.addTema(tema);
        });
    }

    @Test
    void addAssignmentPrimireAbove_test() {
        Tema tema = new Tema("1", "description", 4, 20);
        assertThrows(ValidationException.class, () -> {
            service.addTema(tema);
        });
    }

    @Test
    void addAssignmentPrimireAboveDeadline_test() {
        Tema tema = new Tema("1", "description", 4, 6);
        assertThrows(ValidationException.class, () -> {
            service.addTema(tema);
        });
    }
}
