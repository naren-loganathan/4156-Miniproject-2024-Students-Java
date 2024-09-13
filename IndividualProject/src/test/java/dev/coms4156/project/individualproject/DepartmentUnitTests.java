package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains unit tests for the department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  @BeforeEach
  public void setupDepartmentForTesting() {
    Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> courseHashMap = new HashMap<>();
    courseHashMap.put("Test ID", testCourse);
    testDepartment = new Department("Test Code", courseHashMap, "Test Chair", 0);
  }

  @Test
  public void toStringTest() {
    String expectedResult =
        "Test Code Test ID: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void addAndDropPersonFromMajorTest() {
    assertEquals(0, testDepartment.getNumberOfMajors());
    testDepartment.addPersonToMajor();
    assertEquals(1, testDepartment.getNumberOfMajors());
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }

  public static Department testDepartment;
}

