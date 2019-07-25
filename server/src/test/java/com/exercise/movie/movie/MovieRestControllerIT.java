package com.exercise.movie.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.shared.TestUtil;
import com.exercise.movie.shared.enumeration.Language;
import com.exercise.movie.shared.enumeration.MediaType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@Link MovieRestController} REST controller.
 */
@SpringBootTest(classes = MovieApplication.class)
public class MovieRestControllerIT {

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
    private MovieRepository movieRepository;

    @Mock
    private MovieService movieServiceMock;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restMovieMockMvc;

    private Movie movie;

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
        movie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovie() throws Exception {
        int databaseSizeBeforeCreate = this.movieRepository.findAll().size();

        // Create the Movie
        restMovieMockMvc.perform(post("/api/movie")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isCreated());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate + 1);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMovie.getVoteAverage()).isEqualTo(DEFAULT_VOTE_AVERAGE);
        assertThat(testMovie.getVoteCount()).isEqualTo(DEFAULT_VOTE_COUNT);
        assertThat(testMovie.isVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testMovie.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testMovie.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
        assertThat(testMovie.getPosterPath()).isEqualTo(DEFAULT_POSTER_PATH);
        assertThat(testMovie.getOriginalLanguage()).isEqualTo(DEFAULT_ORIGINAL_LANGUAGE);
        assertThat(testMovie.getOriginalTitle()).isEqualTo(DEFAULT_ORIGINAL_TITLE);
        assertThat(testMovie.getBackdropPath()).isEqualTo(DEFAULT_BACKDROP_PATH);
        assertThat(testMovie.isAdult()).isEqualTo(DEFAULT_ADULT);
        assertThat(testMovie.getOverview()).isEqualTo(DEFAULT_OVERVIEW);
        assertThat(testMovie.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void createMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieRepository.findAll().size();

        // Create the Movie with an existing ID
        movie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc.perform(post("/api/movie")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieRepository.findAll().size();
        // set the field null
        movie.setTitle(null);

        // Create the Movie, which fails.

        restMovieMockMvc.perform(post("/api/movie")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isBadRequest());

        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeTest);
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

    @SuppressWarnings({"unchecked"})
    public void getAllMoviesWithEagerRelationshipsIsEnabled() throws Exception {
        MovieRestController movieRestController = new MovieRestController(movieServiceMock);
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMovieMockMvc = MockMvcBuilders.standaloneSetup(movieRestController)
            .setCustomArgumentResolvers(pageableArgumentResolver).build();

        restMovieMockMvc.perform(get("/api/movie?eagerload=true"))
            .andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMoviesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MovieRestController movieRestController = new MovieRestController(movieServiceMock);
        when(movieServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
        MockMvc restMovieMockMvc = MockMvcBuilders.standaloneSetup(movieRestController)
            .setCustomArgumentResolvers(pageableArgumentResolver).build();

        restMovieMockMvc.perform(get("/api/movie?eagerload=true"))
            .andExpect(status().isOk());

        verify(movieServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMovie() throws Exception {
        // Initialize the database
        movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc.perform(get("/api/movie/{id}", movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.voteAverage").value(DEFAULT_VOTE_AVERAGE.intValue()))
            .andExpect(jsonPath("$.voteCount").value(DEFAULT_VOTE_COUNT.intValue()))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO.booleanValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.intValue()))
            .andExpect(jsonPath("$.posterPath").value(DEFAULT_POSTER_PATH.toString()))
            .andExpect(jsonPath("$.originalLanguage").value(DEFAULT_ORIGINAL_LANGUAGE.toString()))
            .andExpect(jsonPath("$.originalTitle").value(DEFAULT_ORIGINAL_TITLE.toString()))
            .andExpect(jsonPath("$.backdropPath").value(DEFAULT_BACKDROP_PATH.toString()))
            .andExpect(jsonPath("$.adult").value(DEFAULT_ADULT.booleanValue()))
            .andExpect(jsonPath("$.overview").value(DEFAULT_OVERVIEW.toString()))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get("/api/movie/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovie() throws Exception {
        // Initialize the database
        movieService.save(movie);

        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).get();
        // Disconnect from session so that the updates on updatedMovie are not directly saved in db
        em.detach(updatedMovie);
        updatedMovie
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

        restMovieMockMvc.perform(put("/api/movie")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovie)))
            .andExpect(status().isOk());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
        Movie testMovie = movieList.get(movieList.size() - 1);
        assertThat(testMovie.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMovie.getVoteAverage()).isEqualTo(UPDATED_VOTE_AVERAGE);
        assertThat(testMovie.getVoteCount()).isEqualTo(UPDATED_VOTE_COUNT);
        assertThat(testMovie.isVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testMovie.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testMovie.getPopularity()).isEqualTo(UPDATED_POPULARITY);
        assertThat(testMovie.getPosterPath()).isEqualTo(UPDATED_POSTER_PATH);
        assertThat(testMovie.getOriginalLanguage()).isEqualTo(UPDATED_ORIGINAL_LANGUAGE);
        assertThat(testMovie.getOriginalTitle()).isEqualTo(UPDATED_ORIGINAL_TITLE);
        assertThat(testMovie.getBackdropPath()).isEqualTo(UPDATED_BACKDROP_PATH);
        assertThat(testMovie.isAdult()).isEqualTo(UPDATED_ADULT);
        assertThat(testMovie.getOverview()).isEqualTo(UPDATED_OVERVIEW);
        assertThat(testMovie.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovie() throws Exception {
        int databaseSizeBeforeUpdate = movieRepository.findAll().size();

        // Create the Movie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc.perform(put("/api/movie")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovie() throws Exception {
        // Initialize the database
        movieService.save(movie);

        int databaseSizeBeforeDelete = movieRepository.findAll().size();

        // Delete the movie
        restMovieMockMvc.perform(delete("/api/movie/{id}", movie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movie> movieList = movieRepository.findAll();
        assertThat(movieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movie.class);
        Movie movie1 = new Movie();
        movie1.setId(1L);
        Movie movie2 = new Movie();
        movie2.setId(movie1.getId());
        assertThat(movie1).isEqualTo(movie2);
        movie2.setId(2L);
        assertThat(movie1).isNotEqualTo(movie2);
        movie1.setId(null);
        assertThat(movie1).isNotEqualTo(movie2);
    }
}
