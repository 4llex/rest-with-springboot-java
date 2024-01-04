package com.example.services;

import com.example.controllers.PersonController;
import com.example.data.vo.v1.PersonVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.DozerMapper;
import com.example.model.Person;
import com.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

  @Autowired
  private PersonRepository personRepository;
  private final Logger logger = Logger.getLogger(PersonServices.class.getName());

  public List<PersonVO> findAll() {
    logger.info("Finding All person!");

    var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
    persons.stream().forEach(
        p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel())
    );
    return persons;
  }

  public PersonVO findById(Long id) {
    logger.info("Finding one person!");

    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    var vo = DozerMapper.parseObject(entity, PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    return vo;
  }

  public PersonVO create(PersonVO person) {
    if (person == null) {
      throw new RequiredObjectIsNullException();
    }
    logger.info("Creating one person!");

    var entity = DozerMapper.parseObject(person, Person.class);

    var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public PersonVO update(PersonVO person) {
    if (person == null) {
      throw new RequiredObjectIsNullException();
    }
    logger.info("updating one person!");

    var entity = personRepository.findById(person.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public void delete(Long id) {
    logger.info("Deleting one person!");

    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    personRepository.delete(entity);
  }
}
