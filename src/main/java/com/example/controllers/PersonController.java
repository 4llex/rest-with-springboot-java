package com.example.controllers;

import com.example.data.vo.v1.PersonVO;
import com.example.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.util.MediaType.*;

@RestController
@RequestMapping("/api/persons/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

  @Autowired
  private PersonServices personService;

  @GetMapping(produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  @Operation(summary = "Finds all People", description = "Finds all People", tags = {"People"},
    responses = {
      @ApiResponse(description = "Success", responseCode = "200",
          content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
            )
          }),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
      @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    }
  )
  public ResponseEntity<Page<PersonVO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "limit", defaultValue = "12") Integer limit
      ) {

      Pageable pageable = PageRequest.of(page, limit);
    return ResponseEntity.ok(personService.findAll(pageable));
  }

  @CrossOrigin(origins = {"http://localhost:8080"})
  @GetMapping(value = "/{id}", produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  @Operation(summary = "Finds a Person", description = "Finds a Person", tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonVO.class))
          ),
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
      }
  )
  public PersonVO findById(@PathVariable(value = "id") Long id) {
    return personService.findById(id);
  }

  @CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"})
  @PostMapping(consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML},
               produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  @Operation(summary = "Adds a new Person", description = "Adds a new Person by passing a JSON, XML or YML representation of the person", tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonVO.class))
          ),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
      }
  )
  public PersonVO create(@RequestBody PersonVO person) {
    return personService.create(person);
  }

  @PutMapping(consumes = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML},
              produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
  @Operation(summary = "Updates a Person", description = "Updates a Person by passing a JSON, XML or YML representation of the person", tags = {"People"},
      responses = {
          @ApiResponse(description = "Success", responseCode = "200",
              content = @Content(schema = @Schema(implementation = PersonVO.class))
          ),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
      }
  )
  public PersonVO update(@RequestBody PersonVO person) {
    return personService.update(person);
  }

    @PatchMapping(value = "/{id}", produces = {APPLICATION_JSON, APPLICATION_XML, APPLICATION_YML})
    @Operation(summary = "Disable a specific Person by Id", description = "Disable a specific Person by Id", tags = {"People"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                content = @Content(schema = @Schema(implementation = PersonVO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        }
    )
    public PersonVO disablePerson(@PathVariable(value = "id") Long id) {
        return personService.disablePerson(id);
    }

  @DeleteMapping(value = "/{id}")
  //@ResponseStatus(HttpStatus.NO_CONTENT)//TODO: this is a simple way to change the response status code
  @Operation(summary = "Deletes a Person", description = "Deletes a Person", tags = {"People"},
      responses = {
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
      }
  )
  public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
