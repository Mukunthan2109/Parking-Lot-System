package com.parking.model;
public class Vehicle {
 private String registration,type,owner;
 public Vehicle(String r,String t,String o){registration=r.toUpperCase();type=t.toUpperCase();owner=o;}
 public String getRegistration(){return registration;} public String getType(){return type;} public String getOwner(){return owner;}
 public String toString(){return registration+" "+type+" Owner:"+owner;}
}
