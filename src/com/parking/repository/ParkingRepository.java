package com.parking.repository;
import java.util.*;
import com.parking.model.*;
public class ParkingRepository {
 private ParkingLot lot=new ParkingLot("City Parking","Coimbatore");
 public List<Vehicle> vehicles=new ArrayList<>(); public List<Ticket> tickets=new ArrayList<>();
 public List<Payment> payments=new ArrayList<>(); public List<MonthlyPass> passes=new ArrayList<>();
 public ParkingRepository(){
  Floor a=new Floor(1);a.addSlot(new Slot(1,"BIKE"));a.addSlot(new Slot(2,"CAR"));a.addSlot(new Slot(3,"TRUCK"));
  Floor b=new Floor(2);b.addSlot(new Slot(1,"BIKE"));b.addSlot(new Slot(2,"CAR"));lot.addFloor(a);lot.addFloor(b);
 }
 public ParkingLot getLot(){return lot;}
}
