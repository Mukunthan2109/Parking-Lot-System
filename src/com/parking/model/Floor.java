package com.parking.model;
import java.util.*;
public class Floor {
 private int number; 
 private boolean open=true; 
 private List<Slot> slots=new ArrayList<>();
 public Floor(int n){number=n;} 
 public int getNumber(){return number;} 
 public boolean isOpen(){return open;}
 public void setOpen(boolean b){open=b;} 
 public List<Slot> getSlots(){return slots;}
 public void addSlot(Slot s){for(Slot x:slots)if(x.getNumber()==s.getNumber())throw new IllegalArgumentException("Slot number already exists on floor.");slots.add(s);}
 public String toString(){return "Floor "+number+" "+(open?"OPEN":"CLOSED")+" "+slots;}
}
