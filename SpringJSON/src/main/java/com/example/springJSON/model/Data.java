package com.example.springJSON.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

  private int id;
  private String body;

  public Data() {
  }

  public int getId() {
    return this.id;
  }

  public String getUser() {
    return this.body;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setQuote(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "Value{" +
        "id=" + id +
        ", quote='" + body + '\'' +
        '}';
  }
}