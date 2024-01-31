package com.example.services;

import com.example.controllers.PersonController;
import com.example.data.vo.v1.PersonVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.DozerMapper;
import com.example.model.Person;
import com.example.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

  @Autowired
  private PersonRepository personRepository;
  private final Logger logger = Logger.getLogger(PersonServices.class.getName());

  public Page<PersonVO> findAll(Pageable pageable) {
    logger.info("Finding All person!");

    var personPage = personRepository.findAll(pageable);
    var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
    personVosPage.map(
        p -> p.add(
            linkTo(methodOn(PersonController.class)
                .findById(p.getKey())).withSelfRel()));

    return personVosPage;
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

  @Transactional
  public PersonVO disablePerson(Long id) {
    logger.info("Disabling one person!");

    personRepository.disablePerson(id);

    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    var vo = DozerMapper.parseObject(entity, PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    return vo;
  }

  public void delete(Long id) {
    logger.info("Deleting one person!");

    var entity = personRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    personRepository.delete(entity);
  }
}
