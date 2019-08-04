package com.exercise.movie.shared.auditor;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // use Spring Security to retrive the currently logged-in user(s)
        return Optional.of(Arrays.asList("system")
                .get(new Random().nextInt(1)));
    }

}