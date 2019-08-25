package com.exercise.movie.manga;

import static org.assertj.core.api.Assertions.assertThat;

import com.exercise.movie.MovieApplication;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieApplication.class)
@Slf4j
public class MangaServiceIntegrationTest {

  @Autowired
  private MangaService mangaService;

  @Test
  public void testGetMangasByTitle() {
    List<Manga> mangasByTitle = mangaService.getMangasByTitle("ken");
    assertThat(mangasByTitle).isNotNull().isNotEmpty();
  }

}