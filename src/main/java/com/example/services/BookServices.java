package com.example.services;

import com.example.controllers.BookController;
import com.example.data.vo.v1.BookVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.DozerMapper;
import com.example.model.Book;
import com.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

  @Autowired
  private BookRepository bookRepository;
  private final Logger logger = Logger.getLogger(BookServices.class.getName());

  public Page<BookVO> findAll(Pageable pageable) {
    logger.info("Finding All book!");

    var bookPage = bookRepository.findAll(pageable);
    var bookVosPage = bookPage.map(b -> DozerMapper.parseObject(b, BookVO.class));
    bookVosPage.map(
        b -> b.add(
            linkTo(methodOn(BookController.class)
                .findById(b.getKey())).withSelfRel()));

    return bookVosPage;
  }

  public BookVO findById(Long id) {
    logger.info("Finding one book!");

    var entity = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    var vo = DozerMapper.parseObject(entity, BookVO.class);
    vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
    return vo;
  }

  public BookVO create(BookVO book) {
    if (book == null) {
      throw new RequiredObjectIsNullException();
    }
    logger.info("Creating one book!");

    var entity = DozerMapper.parseObject(book, Book.class);

    var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
    vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public BookVO update(BookVO book) {
    if (book == null) {
      throw new RequiredObjectIsNullException();
    }
    logger.info("updating one book!");

    var entity = bookRepository.findById(book.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    entity.setAuthor(book.getAuthor());
    entity.setLaunchDate(book.getLaunchDate());
    entity.setPrice(book.getPrice());
    entity.setTitle(book.getTitle());

    var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
    vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public void delete(Long id) {
    logger.info("Deleting one book!");

    var entity = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

    bookRepository.delete(entity);
  }
}
