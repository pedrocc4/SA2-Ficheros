package com.bosonit.backend.persona.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonaNoEncontrada extends RuntimeException {
    public PersonaNoEncontrada(String message) {
        super(message);
    }
}
