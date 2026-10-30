package com.parking.model;
import java.time.*;
public class Ticket {
 private int id,floor,slot; private Vehicle vehicle; private LocalDateTime entry,paidAt; private String status="ACTIVE";
 public Ticket(int id,Vehicle v,int f,int s){this.id=id;vehicle=v;floor=f;slot=s;entry=LocalDateTime.now();}
 public int getId(){return id;} public int getFloor(){return floor;} public int getSlot(){return slot;} public Vehicle getVehicle(){return vehicle;}
 public LocalDateTime getEntry(){return entry;} public LocalDateTime getPaidAt(){return paidAt;} public String getStatus(){return status;}
 public void paid(){status="PAID";paidAt=LocalDateTime.now();} public void close(){status="CLOSED";} public void lost(){status="LOST";}
 public String toString(){return "Ticket "+id+" "+vehicle+" Floor:"+floor+" Slot:"+slot+" Entry:"+entry+" Status:"+status;}
}
