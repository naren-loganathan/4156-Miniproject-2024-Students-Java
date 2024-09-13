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

  private HashMap<String, Department> mapping;
  private MyFileDatabase myFileDatabase;
  private RouteController routeController;
}

