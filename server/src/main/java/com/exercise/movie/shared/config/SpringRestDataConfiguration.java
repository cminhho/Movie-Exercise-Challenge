package com.exercise.movie.shared.config;

import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.playlist.Playlist;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class SpringRestDataConfiguration {

  public static final String BEFORE_CREATE_EVENT = "beforeCreate";
  public static final String BEFORE_SAVE_EVENT = "beforeSave";

  private final LocalValidatorFactoryBean beanValidator;

  public SpringRestDataConfiguration(
      LocalValidatorFactoryBean beanValidator) {
    this.beanValidator = beanValidator;
  }

  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {
    return new RepositoryRestConfigurer() {
      @Override
      public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(
            Movie.class,
            User.class,
            MovieGenre.class,
            MovieComment.class,
            Playlist.class
        );
      }

      @Override
      public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator(BEFORE_CREATE_EVENT, beanValidator);
        v.addValidator(BEFORE_SAVE_EVENT, beanValidator);
      }
    };
  }
}
