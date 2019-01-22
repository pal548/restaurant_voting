package ru.mycompany.restaurantVoting.web.rest.util;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class RestUtil {
    public static <T> ResponseEntity<T> getResposeEntityFromOptional(Optional<T> optional) {
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(optional.get());
        }
    }
}
