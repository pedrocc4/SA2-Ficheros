package com.bosonit.backend.persona.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class PersonaYaRegistrada extends RuntimeException {
    public PersonaYaRegistrada(String message) {
        super(message);
    }
}
