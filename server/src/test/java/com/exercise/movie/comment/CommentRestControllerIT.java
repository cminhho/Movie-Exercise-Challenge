package com.exercise.movie.comment;

import com.exercise.movie.MovieApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MovieApplication.class)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Slf4j
public class CommentRestControllerIT {
  public static String DEFAULT_REVIEW = "The movie was exciting";
  public static MovieComment createEntity() {
    MovieComment movieComment = new MovieComment().review(DEFAULT_REVIEW);
    return movieComment;
  }
}
