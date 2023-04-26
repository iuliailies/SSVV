import domain.Student;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepoMocksTest {
    private Service service;

    private StudentXMLRepo studentXMLRepository = mock(StudentXMLRepo.class);
    private TemaXMLRepo temaXMLRepository = mock(TemaXMLRepo.class);
    private NotaXMLRepo notaXMLRepository = mock(NotaXMLRepo.class);

    private StudentValidator studentValidator = mock(StudentValidator.class);
    private TemaValidator temaValidator = mock(TemaValidator.class);
    private NotaValidator notaValidator = mock(NotaValidator.class);

    @BeforeEach
    public void setUp() {
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    void addStudent_allStubs() {
        Student student = new Student("1", "Iulia", 234, "email@gmail.com");
        when(studentXMLRepository.save(any())).thenReturn(null);
        service.addStudent(student);

        verify(studentValidator).validate(any());
        verify(studentXMLRepository).save(any());
    }

    @Test
    void addStudent_repoStub() {
        StudentValidator studentValidatorReal = new StudentValidator();
        service = new Service(studentXMLRepository, studentValidatorReal, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student = new Student("1", "Iulia", 133, "email@gmail.com");
        service.addStudent(student);
        verify(studentXMLRepository).save(any());
    }

    @Test
    void addStudent_noStub() throws IOException {
        StudentValidator studentValidatorReal = new StudentValidator();
        String filenameStudent = "src/test/resources/testStudents.xml";
        StudentXMLRepo studentXMLRepositoryReal = new StudentXMLRepo(filenameStudent);

        service = new Service(studentXMLRepositoryReal, studentValidatorReal, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student = new Student("3", "Iulia", 133, "email@gmail.com");
        assertNull(service.addStudent(student));

        Path file = Paths.get("src/test/resources/testStudents.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    }

    @Test
    void addAssignment_allStubs() {
        Tema tema = new Tema("22", "description", 6, 5);
        when(studentXMLRepository.save(any())).thenReturn(null);
        assertNull(service.addTema(tema));

        verify(temaValidator).validate(any());
        verify(temaXMLRepository).save(any());
    }

    @Test
    void addAssignment_repoStub() {
        TemaValidator temaValidatorReal = new TemaValidator();
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidatorReal, notaXMLRepository, notaValidator);
        Tema tema = new Tema("23", "description", 6, 5);
        service.addTema(tema);
        verify(temaXMLRepository).save(any());
    }

    @Test
    void addAssignment_noStub() throws IOException {
        TemaValidator temaValidatorReal = new TemaValidator();
        String filenameStudent = "src/test/resources/testStudents.xml";
        TemaXMLRepo temaXMLRepositoryReal = new TemaXMLRepo(filenameStudent);

        service = new Service(studentXMLRepository, studentValidator, temaXMLRepositoryReal, temaValidatorReal, notaXMLRepository, notaValidator);
        Tema tema = new Tema("23", "description", 6, 5);
        assertNull(service.addTema(tema));

        Path file = Paths.get("src/test/resources/testAssignments.xml");
        Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    }
}
