package com.bosonit.backend.persona.service;

import com.bosonit.backend.persona.infrastructure.controller.dto.input.PersonaInputDTO;
import com.bosonit.backend.persona.infrastructure.controller.dto.output.PersonaOutputDTO;
import com.bosonit.backend.persona.infrastructure.controller.mapper.PersonaMapper;
import com.bosonit.backend.persona.infrastructure.exceptions.PersonaNoEncontrada;
import com.bosonit.backend.persona.infrastructure.exceptions.PersonaYaRegistrada;
import com.bosonit.backend.persona.infrastructure.exceptions.UnprocesableException;
import com.bosonit.backend.persona.repository.PersonaRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepositoryJPA repository;

    @Autowired
    private PersonaMapper mapper;

    @Override
    public PersonaOutputDTO addPersona(PersonaInputDTO personaInputDTO) throws UnprocesableException {
        if (repository.findByUsername(personaInputDTO.getUsuario()).isPresent())
            throw new PersonaYaRegistrada("Usuario ya registrado");
        try {
            return mapper.toDTO(repository.save(mapper.toEntity(personaInputDTO)));
        } catch (ConstraintViolationException e) {
            throw new UnprocesableException(e.getMessage());
        }
    }

    @Override
    public PersonaOutputDTO getPersona(Integer id) throws PersonaNoEncontrada {
        return mapper.toDTO(repository
                .findById(id)
                .orElseThrow(() -> new PersonaNoEncontrada
                        ("Persona con id: " + id + ", no encontrado")));
    }

    @Override
    public PersonaOutputDTO getPersonaByUser(String username) throws PersonaNoEncontrada {
        return mapper.toDTO(repository
                .findByUsername(username)
                .orElseThrow(() -> new PersonaNoEncontrada
                        ("Usuario: " + username + ", no encontrado")));
    }

    @Override
    public List<PersonaOutputDTO> getPersonas() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public void actPersona(int id, PersonaInputDTO personaInputDTO)
            throws PersonaNoEncontrada, UnprocesableException {
        if (repository.findById(id).isEmpty())
            throw new PersonaNoEncontrada("Persona con id: " + id + ", no encontrado");

        try {
            mapper.toDTO(repository.save(mapper.toEntity(personaInputDTO)));
        } catch (ConstraintViolationException e) {
            throw new UnprocesableException(e.getMessage());
        }
    }

    @Override
    public void delPersona(int id) throws PersonaNoEncontrada {
        repository.delete((repository
                .findById(id)
                .orElseThrow(() -> new PersonaNoEncontrada
                        ("Persona con id: " + id + ", no encontrado"))));
    }
}
