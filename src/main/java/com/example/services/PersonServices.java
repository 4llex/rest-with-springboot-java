package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Person;
import com.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

  @Autowired
  private PersonRepository personRepository;
  private final Logger logger = Logger.getLogger(PersonServices.class.getName());

  public List<Person> findAll() {
    logger.info("Finding All person!");

    return personRepository.findAll();
  }

  public Person findById(Long id) {
    logger.info("Finding one person!");

    return personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
  }

  public Person create(Person person) {
    logger.info("Creating one person!");

    return personRepository.save(person);
  }

  public Person update(Person person) {
    logger.info("updating one person!");

    Person entity = personRepository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return personRepository.save(entity);
  }

  public void delete(Long id) {
    logger.info("Deleting one person!");

    Person entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    personRepository.delete(entity);
  }
}
