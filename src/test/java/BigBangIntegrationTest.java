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
import java.io.PrintWriter;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNull;

public class BigBangIntegrationTest {
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

//    @AfterEach
//    void cleanup() throws FileNotFoundException {
//
//        PrintWriter pw1 = new PrintWriter("src/test/resources/testStudents.xml");
//
//        pw1.write(
//                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
//                        "<Entities>\n" +
//                        "    \n" +
//                        "</Entities>");
//
//        pw1.close();
//        service.getAllStudenti();
//
//        PrintWriter pw2 = new PrintWriter("src/test/resources/testAssignments.xml");
//
//        pw2.write(
//                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
//                        "<Entities>\n" +
//                        "    \n" +
//                        "</Entities>");
//
//        pw2.close();
//        service.getAllTeme();
//
//        PrintWriter pw3 = new PrintWriter("src/test/resources/testGrades.xml");
//
//        pw3.write(
//                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
//                        "<Entities>\n" +
//                        "    \n" +
//                        "</Entities>");
//
//        pw3.close();
//        service.getAllNote();
//    }

    @Test
    void addStudentNormal_test() {
        Student student = new Student("105", "Iulia", 934, "mail@mail.com");
        assertNull(service.addStudent(student));
    }

    @Test
    void addAssignment_test() {
        Tema tema = new Tema("10", "description", 6, 5);
        assertNull(service.addTema(tema));
    }

    @Test
    void addGrade_test() {
        String[] date = "2018-11-16".split("-");
        LocalDate dataPredare = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        Nota nota = new Nota("1", "100", "5", 9.5, dataPredare);
        assertNull(service.addNota(nota, "some feedback"));
    }

    @Test
    void integration_test() {
        Student student = new Student("114", "Iulia", 934, "mail@mail.com");
        Tema tema = new Tema("15", "description", 6, 5);
        String[] date = "2018-11-16".split("-");
        LocalDate dataPredare = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        Nota nota = new Nota("3", student.getID(), tema.getID(), 9.5, dataPredare);
        service.addStudent(student);
        service.addTema(tema);
        assertNull(service.addNota(nota, "some feedback"));
    }
}
