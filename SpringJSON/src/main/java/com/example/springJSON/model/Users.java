package com.example.springJSON.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.simple.JSONObject;   
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {

  private String name;
//  private JSONObject picture;
//  private Data data;

  public Users() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public Data getData() {
//    return data;
//  }
//
//  public void setValue(Data data) {
//    this.data = data;
//  }

  @Override
  public String toString() {
//	System.out.println(picture.toJSONString());
    return "Quote{" +
        "type='" + name + '\'' +
        ", value=" + "GUCCI" +
        '}';
  }
}