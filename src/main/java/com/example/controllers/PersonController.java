package com.example.controllers;

import com.example.model.Person;
import com.example.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonServices personService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Person findById(@PathVariable(value = "id") Long id) {
    return personService.findById(id);
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Person> findAll() {
    return personService.findAll();
  }

  @RequestMapping(value = "", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person create(@RequestBody Person person) {
    return personService.create(person);
  }

  @RequestMapping(value = "", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Person update(@RequestBody Person person) {
    return personService.update(person);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(value = "id") Long id) {
    personService.delete(id);
  }

}
