package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains unit tests for the route controller class.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {
  /**
   * Set up department mapping for testing.
   */
  @BeforeEach
  public void setupMappingForTesting() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);

    mapping = new HashMap<>();
    mapping.put("COMS", compSci);

    myFileDatabase = new MyFileDatabase(1, "test-db");
    myFileDatabase.setMapping(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);

    routeController = new RouteController();
  }

  @Test
  public void retrieveDepartmentTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
        routeController.retrieveDepartment("NOTCOMS"));
    ResponseEntity<?> res = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals(mapping.get("COMS").toString(), res.getBody());
  }

  @Test
  public void retrieveCourseTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
        routeController.retrieveCourse("NOTCOMS", 1004));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.retrieveCourse("COMS", 4242));
    ResponseEntity<?> res = routeController.retrieveCourse("COMS", 1004);
    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals(mapping.get("COMS").getCourseSelection().get("1004").toString(), res.getBody());
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.isCourseFull("COMS", 4242));
    assertEquals(new ResponseEntity<>(false, HttpStatus.OK),
        routeController.isCourseFull("COMS", 1004));
  }

  @Test
  public void getMajorCtFromDeptTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
        routeController.getMajorCtFromDept("NOTCOMS"));
    ResponseEntity<?> res = routeController.getMajorCtFromDept("COMS");
    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals("There are: 2700 majors in the department", res.getBody());
  }

  @Test
  public void identifyDeptChairTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
        routeController.identifyDeptChair("NOTCOMS"));
    ResponseEntity<?> res = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals("Luca Carloni is the department chair.", res.getBody());
  }

  @Test
  public void findCourseLocationTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.findCourseLocation("COMS", 4242));
    assertEquals(new ResponseEntity<>("417 IAB is where the course is located.", HttpStatus.OK),
        routeController.findCourseLocation("COMS", 1004));
  }

  @Test
  public void findCourseInstructorTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.findCourseInstructor("COMS", 4242));
    assertEquals(
        new ResponseEntity<>("Adam Cannon is the instructor for the course.", HttpStatus.OK),
        routeController.findCourseInstructor("COMS", 1004));
  }

  @Test
  public void findCourseTimeTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.findCourseTime("COMS", 4242));
    assertEquals(
        new ResponseEntity<>("The course meets at: 11:40-12:55", HttpStatus.OK),
        routeController.findCourseTime("COMS", 1004));
  }

  @Test
  public void addOrDropMajorFromDeptTest() {
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
        routeController.addMajorToDept("NOTCOMS"));
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
        routeController.removeMajorFromDept("NOTCOMS"));

    ResponseEntity<?> res = routeController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals("Attribute was updated successfully", res.getBody());

    res = routeController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, res.getStatusCode());
    assertEquals("Attribute was updated or is at minimum", res.getBody());
  }

  @Test
  public void setEnrolledStudentCountAndDropTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.setEnrollmentCount("COMS", 4242, 0));
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.dropStudent("COMS", 4242));

    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.setEnrollmentCount("COMS", 1004, 0));
    assertEquals(new ResponseEntity<>("Student has not been dropped.", HttpStatus.BAD_REQUEST),
        routeController.dropStudent("COMS", 1004));

    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.setEnrollmentCount("COMS", 1004, 250));
    assertEquals(new ResponseEntity<>("Student has been dropped.", HttpStatus.OK),
        routeController.dropStudent("COMS", 1004));
  }

  @Test
  public void changeCourseInfoTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.changeCourseTeacher("COMS", 4242, "Joe"));
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.changeCourseTeacher("COMS", 1004, "Joe"));
    assertEquals(
        new ResponseEntity<>("Joe is the instructor for the course.", HttpStatus.OK),
        routeController.findCourseInstructor("COMS", 1004));
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.changeCourseTeacher("COMS", 1004, "Adam Cannon"));

    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.changeCourseLocation("COMS", 4242, "Antarctica"));
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.changeCourseLocation("COMS", 1004, "Antarctica"));
    assertEquals(
        new ResponseEntity<>("Antarctica is where the course is located.", HttpStatus.OK),
        routeController.findCourseLocation("COMS", 1004));
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.changeCourseLocation("COMS", 1004, "417 IAB"));

    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
        routeController.changeCourseTime("COMS", 4242, "00:00-02:00"));
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.changeCourseTime("COMS", 1004, "00:00-02:00"));
    assertEquals(
        new ResponseEntity<>("The course meets at: 00:00-02:00", HttpStatus.OK),
        routeController.findCourseTime("COMS", 1004));
    assertEquals(new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK),
        routeController.changeCourseTime("COMS", 1004, "11:40-12:55"));
  }

  private HashMap<String, Department> mapping;
  private MyFileDatabase myFileDatabase;
  private RouteController routeController;
}

