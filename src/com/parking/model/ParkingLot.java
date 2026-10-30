package com.parking.model;
import java.util.*;
public class ParkingLot {
 private String name,address; private List<Floor> floors=new ArrayList<>();
 public ParkingLot(String n,String a){name=n;address=a;}
 public String getName(){return name;} public String getAddress(){return address;} public List<Floor> getFloors(){return floors;}
 public void addFloor(Floor f){for(Floor x:floors)if(x.getNumber()==f.getNumber())throw new IllegalArgumentException("Floor exists.");floors.add(f);}
}
