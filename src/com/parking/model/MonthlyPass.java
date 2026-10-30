package com.parking.model;
import java.time.*;
public class MonthlyPass {
 private String registration; private LocalDate start,end;
 public MonthlyPass(String r,LocalDate s,LocalDate e){registration=r.toUpperCase();start=s;end=e;}
 public boolean valid(String r){LocalDate d=LocalDate.now();return registration.equalsIgnoreCase(r)&&!d.isBefore(start)&&!d.isAfter(end);}
}
