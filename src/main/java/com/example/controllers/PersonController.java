package com.example.controllers;

import com.example.data.vo.v1.PersonVO;
import com.example.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.util.MediaType.*;

@RestController
@RequestMapping("/api/persons/v1")
public class PersonController {

  @Autowired
  private PersonServices personService;

  @GetMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  public List<PersonVO> findAll() {
    return personService.findAll();
  }

  @GetMapping(value = "/{id}", produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  public PersonVO findById(@PathVariable(value = "id") Long id) {
    return personService.findById(id);
  }

  @PostMapping(consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML},
               produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  public PersonVO create(@RequestBody PersonVO person) {
    return personService.create(person);
  }

  @PutMapping(consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML},
              produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  public PersonVO update(@RequestBody PersonVO person) {
    return personService.update(person);
  }

  @DeleteMapping(value = "/{id}")
  //@ResponseStatus(HttpStatus.NO_CONTENT)//TODO: this is a simple way to change the response status code
  public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
