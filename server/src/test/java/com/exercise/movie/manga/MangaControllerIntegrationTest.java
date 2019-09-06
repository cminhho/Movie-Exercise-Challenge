package com.exercise.movie.manga;


import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MangaControllerIntegrationTest {

  MockMvc mockMvc;

  @Autowired
  protected WebApplicationContext wac;

  @Autowired
  MangaController mangaController;

  @Before
  public void setup() throws Exception {
    this.mockMvc = standaloneSetup(this.mangaController).build();
  }

  @Test
  public void testSearchSync() throws Exception {
    mockMvc.perform(get("/manga/sync/ken").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*.title", hasItem(is("Hokuto no Ken"))));
  }

  @Test
  public void testSearchASync() throws Exception {
    MvcResult result = mockMvc.perform(get("/manga/async/ken").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(request().asyncStarted())
        .andDo(print())
        .andReturn();

    mockMvc.perform(asyncDispatch(result))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*.title", hasItem(is("Hokuto no Ken"))));
  }

}