package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains unit tests for the course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(100);
    assertEquals(false, testCourse.isCourseFull());

    testCourse.setEnrolledStudentCount(250);
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void enrollAndDropStudentTest() {
    testCourse.setEnrolledStudentCount(251);
    testCourse.setEnrolledStudentCount(250);
    assertEquals(false, testCourse.enrollStudent());

    assertEquals(true, testCourse.dropStudent());
    testCourse.setEnrolledStudentCount(0);

    assertEquals(false, testCourse.dropStudent());
    assertEquals(true, testCourse.enrollStudent());
  }

  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

