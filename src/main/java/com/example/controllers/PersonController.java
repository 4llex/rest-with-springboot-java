package com.example.controllers;

import com.example.model.Person;
import com.example.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonServices personService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Person findById(@PathVariable(value = "id") Long id) {
    return personService.findById(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> findAll() {
    return personService.findAll();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Person create(@RequestBody Person person) {
    return personService.create(person);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Person update(@RequestBody Person person) {
    return personService.update(person);
  }

  @DeleteMapping(value = "/{id}")
  //@ResponseStatus(HttpStatus.NO_CONTENT)//TODO: this is a simple way to change the response status code
  public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
