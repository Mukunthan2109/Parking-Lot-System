package com.parking.model;
public class Slot {
 private int number; private String type, status="FREE";
 public Slot(int number,String type){this.number=number;this.type=type.toUpperCase();}
 public int getNumber(){return number;} public String getType(){return type;} public String getStatus(){return status;}
 public void setStatus(String s){status=s;}
 public void setType(String t){if(!status.equals("FREE"))throw new IllegalStateException("Only FREE slot type can change.");type=t.toUpperCase();}
 public String toString(){return "Slot "+number+" "+type+" "+status;}
}
