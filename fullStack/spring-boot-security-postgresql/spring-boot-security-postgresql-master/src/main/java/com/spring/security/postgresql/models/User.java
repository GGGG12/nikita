package com.spring.security.postgresql.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table( name = "users",
        uniqueConstraints = {
          @UniqueConstraint(columnNames = "username"),
          @UniqueConstraint(columnNames = "email")
        })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  //@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @ManyToMany(cascade =  CascadeType.DETACH)
  @JoinTable
  private Set<Role> roles = new HashSet<>();


  @Size(max = 20)
  private String surname;

  @Size(max = 20)
  private String name;

  @Size(max = 20)
  private String patronymic;

  @Size(max = 20)
  private String country;

  @Size(max = 20)
  private String city;

  @Size(max = 20)
  private String zipcode;

  @Size(max = 50)
  private String street;

  @Size(max = 20)
  private String house;

  @Size(max = 20)
  private String phone;

  public User() {
  }

//  public User(String username, String email, String password) {
//    this.username = username;
//    this.email = email;
//    this.password = password;
//  }


  public User(String username, String email, String password, String surname, String name, String patronymic, String country, String city, String zipcode, String street, String house, String phone) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.country = country;
    this.city = city;
    this.zipcode = zipcode;
    this.street = street;
    this.house = house;
    this.phone = phone;
  }

  public Long getId() { return id; }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouse() {
    return house;
  }

  public void setHouse(String house) {
    this.house = house;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
