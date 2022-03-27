package com.bosonit.backend.persona.infrastructure.controller;

import com.bosonit.backend.persona.infrastructure.controller.dto.input.PersonaInputDTO;
import com.bosonit.backend.persona.infrastructure.controller.dto.output.PersonaOutputDTO;
import com.bosonit.backend.persona.service.PersonaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class PersonaController {

    @Autowired
    private PersonaService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("persona")
    public ResponseEntity<PersonaOutputDTO> addPersona(@RequestBody PersonaInputDTO personaInputDTO) {
        log.info("Intentando agregar: " + personaInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addPersona(personaInputDTO));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = " https://codepen.io/de4imo/pen/VwMRENP")
    @PostMapping("addperson")
    public ResponseEntity<PersonaOutputDTO> addPerson(
            @RequestBody PersonaInputDTO personaInputDTO) {
        log.info("a ver..." + personaInputDTO);
        PersonaOutputDTO personaOutputDTO = new PersonaOutputDTO();
        BeanUtils.copyProperties(personaInputDTO, personaOutputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaOutputDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("persona/{id}")
    public ResponseEntity<Object> getPersona(
            @PathVariable int id,
            @RequestParam(defaultValue = "simple") String outputType) {
        log.info("Intentando buscar persona con id: " + id + ", output: " + outputType);
        return ResponseEntity.ok().body(service.getPersona(id, outputType));
    }

    @GetMapping("personas")
    public List<PersonaOutputDTO> getPersonas() {
        log.info("Mostrando todas las personas");
        return service.getPersonas();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("persona")
    public ResponseEntity<PersonaOutputDTO> getPersona(@RequestParam String username) {
        log.info("Intentando buscar persona con nombre: " + username);
        return ResponseEntity.ok().body(service.getPersonaByUser(username));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("persona/{id}")
    public ResponseEntity<Void> actPersona(@PathVariable int id, @RequestBody PersonaInputDTO personaInputDTO) {
        log.info("Intentando actualizar: " + personaInputDTO);
        service.actPersona(id, personaInputDTO);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("persona/{id}")
    public ResponseEntity<Void> delPersona(@PathVariable int id) {
        log.info("Intentando borrar persona con id: " + id);
        service.delPersona(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("personaCriteria")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PersonaOutputDTO>> getPersonasFecha(
            @RequestBody PersonaInputDTO personaInputDTO,
            @RequestParam String fecha,
            @RequestParam(defaultValue = "superior", required = false) String tipoConsulta,
            @RequestParam(defaultValue = "name", required = false) String orden) {
        LocalDate date = LocalDate.parse(fecha);
        log.info("Intentando buscar personas con datos: " + personaInputDTO
                + ", con fecha " + tipoConsulta + " a: " + fecha + ", ordedas por: " + orden);
        return ResponseEntity.status(HttpStatus.OK).body(service.getPersonasFecha(personaInputDTO,
                Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), tipoConsulta, orden));
    }
}
