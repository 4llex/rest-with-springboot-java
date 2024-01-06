package com.example.integrationtests.controller.withjson;

import com.example.configs.TestConfigs;
import com.example.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.integrationtests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static PersonVO person;

	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}

	@Test
	@Order(1)
	void testCreate() throws IOException {
		mockPerson();
		specification =  new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "https://erudio.com.br")
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
			.when().post()
			.then().statusCode(200)
			.extract()
				.body()
					.asString();

		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
		person = createdPerson;

		assertNotNull(createdPerson);

		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());

		assertTrue(createdPerson.getId() > 0);

		assertEquals("Eric", createdPerson.getFirstName());
		assertEquals("Clapton", createdPerson.getLastName());
		assertEquals("London - England", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
	}

	private void mockPerson() {
		person.setFirstName("Eric");
		person.setLastName("Clapton");
		person.setAddress("London - England");
		person.setGender("Male");
	}

}
