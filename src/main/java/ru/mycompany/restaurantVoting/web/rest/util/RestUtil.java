package ru.mycompany.restaurantVoting.web.rest.util;

import org.springframework.http.ResponseEntity;
import ru.mycompany.restaurantVoting.HasId;
import ru.mycompany.restaurantVoting.web.exceptions.IllegalRequestDataException;

import java.util.Optional;

public class RestUtil {
    public static <T> ResponseEntity<T> getResponseEntityFromOptional(Optional<T> optional) {
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(optional.get());
        }
    }

    public static ResponseEntity<Void> getResponseEntityNoContentOrNotFound(boolean found) {
        if (!found) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
}
