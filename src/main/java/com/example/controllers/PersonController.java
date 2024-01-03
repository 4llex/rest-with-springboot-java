package com.example.controllers;

import com.example.data.vo.v1.PersonVO;
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

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonVO> findAll() {
    return personService.findAll();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO findById(@PathVariable(value = "id") Long id) {
    return personService.findById(id);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO create(@RequestBody PersonVO person) {
    return personService.create(person);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
