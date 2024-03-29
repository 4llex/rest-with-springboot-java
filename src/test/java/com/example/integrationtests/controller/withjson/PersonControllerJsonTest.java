package com.example.integrationtests.controller.withjson;

import com.example.configs.TestConfigs;
import com.example.data.vo.v1.security.TokenVO;
import com.example.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.integrationtests.vo.AccountCredentialsVO;
import com.example.integrationtests.vo.PersonVO;
import com.example.integrationtests.vo.wrappers.WrapperPersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

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
	@Order(0)
	void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		var accessToken = given()
			.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(user)
				.when()
			.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(TokenVO.class)
						.getAccessToken();

		specification =  new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
	}

	@Test
	@Order(1)
	void testCreate() throws IOException {
		mockPerson();

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
				.when().post()
			.then().statusCode(200)
			.extract()
				.body()
					.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);

		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertTrue(persistedPerson.getId() > 0);

		assertEquals("Eric", persistedPerson.getFirstName());
		assertEquals("Forman", persistedPerson.getLastName());
		assertEquals("Wisconsin", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(2)
	void testUpdate() throws IOException {
		person.setLastName("da Silva");

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
			.when().post()
			.then().statusCode(200)
			.extract()
			.body()
			.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);

		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Eric", persistedPerson.getFirstName());
		assertEquals("da Silva", persistedPerson.getLastName());
		assertEquals("Wisconsin", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(3)
	void testDisablePersonById() throws IOException {

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.getId())
				.when().patch("{id}")
				.then().statusCode(200)
					.extract()
						.body()
						.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);

		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Eric", persistedPerson.getFirstName());
		assertEquals("da Silva", persistedPerson.getLastName());
		assertEquals("Wisconsin", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(4)
	void testFindById() throws IOException {
		mockPerson();

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.getId())
			.when().get("{id}")
			.then().statusCode(200)
			.extract()
			.body()
			.asString();

		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);

		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Eric", persistedPerson.getFirstName());
		assertEquals("da Silva", persistedPerson.getLastName());
		assertEquals("Wisconsin", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}

	@Test
	@Order(5)
	void testDelete() throws IOException {

		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}

	@Test
	@Order(6)
	void testFindAll() throws IOException {

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.queryParams("page",0, //TODO: if change pageable params, assertions also need to change
				"size",12,
						"direction","asc")
				.when().get()
			.then().statusCode(200)
				.extract()
				.body()
				.asString();

		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		var people = wrapper.getEmbedded().getPersons();

		PersonVO foundPersonOne = people.get(0);

		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());
		assertFalse(foundPersonOne.getEnabled());//TODO: check if enabled in database

		assertEquals(761, foundPersonOne.getId());

		assertEquals("Abbie", foundPersonOne.getFirstName());
		assertEquals("McGoogan", foundPersonOne.getLastName());
		assertEquals("6614 Eggendart Pass", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());

		PersonVO foundPersonFive = people.get(4);

		assertNotNull(foundPersonFive.getId());
		assertNotNull(foundPersonFive.getFirstName());
		assertNotNull(foundPersonFive.getLastName());
		assertNotNull(foundPersonFive.getAddress());
		assertNotNull(foundPersonFive.getGender());
		assertFalse(foundPersonFive.getEnabled());//TODO: check if enabled in database

		assertEquals(617, foundPersonFive.getId());

		assertEquals("Addia", foundPersonFive.getFirstName());
		assertEquals("Mawer", foundPersonFive.getLastName());
		assertEquals("34910 Morningstar Pass", foundPersonFive.getAddress());
		assertEquals("Female", foundPersonFive.getGender());
	}

	@Test
	@Order(7)
	void testFindAllWithoutToken() throws IOException {
		RequestSpecification specificationWithoutToken =  new RequestSpecBuilder()
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();

		given().spec(specificationWithoutToken)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.when().get()
			.then().statusCode(403);
	}

	@Test
	@Order(8)
	void testFindByName() throws IOException {

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("firstName", "Alex")
			.queryParams("page",0, //TODO: if change pageable params, assertions also need to change
					"size",6,
							"direction","asc")
			.when().get("findPersonByName/{firstName}")
			.then().statusCode(200)
			.extract()
			.body()
			.asString();

		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		var people = wrapper.getEmbedded().getPersons();

		PersonVO foundPersonOne = people.get(0);

		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());
		assertTrue(foundPersonOne.getEnabled());//TODO: check if enabled in database

		assertEquals(1, foundPersonOne.getId());

		assertEquals("Alex", foundPersonOne.getFirstName());
		assertEquals("Rosa", foundPersonOne.getLastName());
		assertEquals("Cambui", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());
	}

	@Test
	@Order(9)
	void testHATEOAS() throws IOException {

		var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.queryParams("page",0, //TODO: if change pageable params, assertions also need to change
					"size",10,
							"direction","asc")
			.when().get()
			.then().statusCode(200)
			.extract()
			.body()
			.asString();

		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/persons/v1/761\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/persons/v1/396\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/persons/v1/550\"}}}"));

		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":1005,\"totalPages\":101,\"number\":0}}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/persons/v1?direction=asc&page=100&size=10&sort=firstName,asc\"}}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/persons/v1?direction=asc&page=1&size=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/persons/v1?page=0&size=10&direction=asc\"}"));
		//assertTrue(content.contains("\"prev\":{\"href\":\"http://localhost:8888/api/persons/v1?direction=asc&page=2&size=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/api/persons/v1?direction=asc&page=0&size=10&sort=firstName,asc\"}"));
	}

	private void mockPerson() {
		person.setFirstName("Eric");
		person.setLastName("Forman");
		person.setAddress("Wisconsin");
		person.setGender("Male");
		person.setEnabled(true);
	}

}
