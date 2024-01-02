package com.example.services;

import com.example.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

  private final AtomicLong counter = new AtomicLong();
  private final Logger logger = Logger.getLogger(PersonServices.class.getName());

  public List<Person> findAll() {
    logger.info("Finding All person!");

    List<Person> persons = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      Person person = mockPerson(i);
      persons.add(person);
    }

    return persons;
  }

  public Person findById(String id) {
    logger.info("Finding one person!");

    Person person = new Person();
    person.setId(counter.incrementAndGet());
    person.setFirstName("Alex");
    person.setLastName("Rosa");
    person.setAddress("Cambui-MG");
    person.setGender("Male");

    return person;
  }

  public Person create(Person person) {
    logger.info("Creating one person!");
    return person;
  }

  public Person update(Person person) {
    logger.info("updating one person!");
    return person;
  }

  public void delete(String id) {
    logger.info("Deleting one person!");
  }

  private Person mockPerson(int i) {
    Person person = new Person();

    person.setId(counter.incrementAndGet());
    person.setFirstName("Alex" + i);
    person.setLastName("Rosa" + i);
    person.setAddress("Cambui-MG" + i);
    person.setGender("Male" + i);

    return person;
  }

}
