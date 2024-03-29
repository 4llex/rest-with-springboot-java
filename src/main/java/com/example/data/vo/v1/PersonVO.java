package com.example.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "firstName", "lastName", "address", "gender", "enabled"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1334414548362400146L;

  @Mapping("id")//TODO: usado para mappeamento no dozer
  @JsonProperty("id")//TODO: usa para retornar "id" ao inves de "key" no json de resposta
  private Long key;
  private String firstName;
  private String lastName;
  private String address;
  private String gender;
  private Boolean enabled;

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public PersonVO() {}

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    PersonVO personVO = (PersonVO) o;
    return Objects.equals(key, personVO.key) && Objects.equals(firstName, personVO.firstName) && Objects.equals(lastName, personVO.lastName) && Objects.equals(address, personVO.address) && Objects.equals(gender, personVO.gender) && Objects.equals(enabled, personVO.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), key, firstName, lastName, address, gender, enabled);
  }
}
