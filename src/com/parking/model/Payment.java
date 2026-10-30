package com.parking.model;
import java.time.*;
public class Payment {
 private int id,ticketId; private double amount; private String method,status; private LocalDateTime date=LocalDateTime.now();
 public Payment(int i,int t,double a,String m,String s){id=i;ticketId=t;amount=a;method=m;status=s;}
 public int getTicketId(){return ticketId;} public double getAmount(){return amount;} public String getStatus(){return status;} public LocalDateTime getDate(){return date;}
 public String toString(){return "Receipt "+id+" Ticket:"+ticketId+" Amount:Rs."+amount+" "+method+" "+status+" "+date;}
}
