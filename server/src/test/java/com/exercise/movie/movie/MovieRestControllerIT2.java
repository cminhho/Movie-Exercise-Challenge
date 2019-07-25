package com.exercise.movie.movie;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.shared.enumeration.Language;
import com.exercise.movie.shared.enumeration.MediaType;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@Link MovieRestController} REST controller.
 */
//@Slf4j
//@SpringBootTest(classes = MovieApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MovieApplication.class)
@WebAppConfiguration
public class MovieRestControllerIT2 {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_VOTE_AVERAGE = 1L;
    private static final Long UPDATED_VOTE_AVERAGE = 2L;

    private static final Long DEFAULT_VOTE_COUNT = 1L;
    private static final Long UPDATED_VOTE_COUNT = 2L;

    private static final Boolean DEFAULT_VIDEO = false;
    private static final Boolean UPDATED_VIDEO = true;

    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.MOVIE;
    private static final MediaType UPDATED_MEDIA_TYPE = MediaType.SONG;

    private static final Long DEFAULT_POPULARITY = 1L;
    private static final Long UPDATED_POPULARITY = 2L;

    private static final String DEFAULT_POSTER_PATH = "AAAAAAAAAA";
    private static final String UPDATED_POSTER_PATH = "BBBBBBBBBB";

    private static final Language DEFAULT_ORIGINAL_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_ORIGINAL_LANGUAGE = Language.ENGLISH;

    private static final String DEFAULT_ORIGINAL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BACKDROP_PATH = "AAAAAAAAAA";
    private static final String UPDATED_BACKDROP_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ADULT = false;
    private static final Boolean UPDATED_ADULT = true;

    private static final String DEFAULT_OVERVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVERVIEW = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_DATE = "BBBBBBBBBB";

    @Autowired
    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private MovieService movieServiceMock;

    @Autowired
    @MockBean
    private MovieService movieService;

    private MockMvc restMovieMockMvc;

    private Movie movie;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieRestController movieRestController = new MovieRestController(movieService);
        this.restMovieMockMvc = MockMvcBuilders.standaloneSetup(movieRestController)
            .setCustomArgumentResolvers(pageableArgumentResolver).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
            .title(DEFAULT_TITLE)
            .voteAverage(DEFAULT_VOTE_AVERAGE)
            .voteCount(DEFAULT_VOTE_COUNT)
            .video(DEFAULT_VIDEO)
            .mediaType(DEFAULT_MEDIA_TYPE)
            .popularity(DEFAULT_POPULARITY)
            .posterPath(DEFAULT_POSTER_PATH)
            .originalLanguage(DEFAULT_ORIGINAL_LANGUAGE)
            .originalTitle(DEFAULT_ORIGINAL_TITLE)
            .backdropPath(DEFAULT_BACKDROP_PATH)
            .adult(DEFAULT_ADULT)
            .overview(DEFAULT_OVERVIEW)
            .releaseDate(DEFAULT_RELEASE_DATE);
        return movie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createUpdatedEntity(EntityManager em) {
        Movie movie = new Movie()
            .title(UPDATED_TITLE)
            .voteAverage(UPDATED_VOTE_AVERAGE)
            .voteCount(UPDATED_VOTE_COUNT)
            .video(UPDATED_VIDEO)
            .mediaType(UPDATED_MEDIA_TYPE)
            .popularity(UPDATED_POPULARITY)
            .posterPath(UPDATED_POSTER_PATH)
            .originalLanguage(UPDATED_ORIGINAL_LANGUAGE)
            .originalTitle(UPDATED_ORIGINAL_TITLE)
            .backdropPath(UPDATED_BACKDROP_PATH)
            .adult(UPDATED_ADULT)
            .overview(UPDATED_OVERVIEW)
            .releaseDate(UPDATED_RELEASE_DATE);
        return movie;
    }

    @Before
    public void initTest() {
    }

    @Test
    @Transactional
    public void getAllMovies() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);


        // Get all the movieList
        restMovieMockMvc.perform(get("/api/movie?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].voteAverage").value(hasItem(DEFAULT_VOTE_AVERAGE.intValue())))
            .andExpect(jsonPath("$.[*].voteCount").value(hasItem(DEFAULT_VOTE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO.booleanValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.intValue())))
            .andExpect(jsonPath("$.[*].posterPath").value(hasItem(DEFAULT_POSTER_PATH.toString())))
            .andExpect(jsonPath("$.[*].originalLanguage").value(hasItem(DEFAULT_ORIGINAL_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].originalTitle").value(hasItem(DEFAULT_ORIGINAL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].backdropPath").value(hasItem(DEFAULT_BACKDROP_PATH.toString())))
            .andExpect(jsonPath("$.[*].adult").value(hasItem(DEFAULT_ADULT.booleanValue())))
            .andExpect(jsonPath("$.[*].overview").value(hasItem(DEFAULT_OVERVIEW.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())));
    }
}
