package com.parking.service;
import java.time.*;import java.util.*;
import com.parking.model.*;import com.parking.repository.ParkingRepository;
public class ParkingService {
 private ParkingRepository r; private int ticketId=1,paymentId=1;
 public ParkingService(ParkingRepository r){this.r=r;}
 private Floor floor(int n){for(Floor f:r.getLot().getFloors())if(f.getNumber()==n)return f;return null;}
 private Slot slot(int f,int s){Floor x=floor(f);if(x!=null)for(Slot z:x.getSlots())if(z.getNumber()==s)return z;return null;}
 public void addFloor(int n){r.getLot().addFloor(new Floor(n));}
 public void addSlot(int f,int n,String type){Floor x=floor(f);if(x==null)throw new IllegalArgumentException("Floor not found.");x.addSlot(new Slot(n,type));}
 public void closeFloor(int n){Floor f=floor(n);if(f==null)throw new IllegalArgumentException("Floor not found.");f.setOpen(false);}
 public void maintenance(int f,int s){Slot x=slot(f,s);if(x==null)throw new IllegalArgumentException("Slot not found.");if(!x.getStatus().equals("FREE"))throw new IllegalArgumentException("Only FREE slot can go under maintenance.");x.setStatus("UNDER_MAINTENANCE");}
 public void changeType(int f,int s,String t){Slot x=slot(f,s);if(x==null)throw new IllegalArgumentException("Slot not found.");x.setType(t);}
 private boolean active(String reg){for(Ticket t:r.tickets)if(t.getVehicle().getRegistration().equalsIgnoreCase(reg)&&!t.getStatus().equals("CLOSED"))return true;return false;}
 public Ticket entry(String reg,String type,String owner){
  type=type.toUpperCase();reg=reg.toUpperCase();
  if(!List.of("BIKE","CAR","TRUCK").contains(type))throw new IllegalArgumentException("Invalid vehicle type.");
  if(active(reg))throw new IllegalArgumentException("Vehicle already parked.");
  Slot best=null;int bestFloor=Integer.MAX_VALUE;
  for(Floor f:r.getLot().getFloors())if(f.isOpen())for(Slot s:f.getSlots()){
   boolean fits=s.getType().equals(type)||(type.equals("BIKE")&&s.getType().equals("CAR"));
   if(fits&&s.getStatus().equals("FREE")&&(f.getNumber()<bestFloor||(f.getNumber()==bestFloor&&(best==null||s.getNumber()<best.getNumber())))){best=s;bestFloor=f.getNumber();}
  }
  if(best==null)throw new IllegalStateException("Parking Full.");
  best.setStatus("OCCUPIED");Vehicle v=new Vehicle(reg,type,owner);r.vehicles.add(v);
  Ticket t=new Ticket(ticketId++,v,bestFloor,best.getNumber());r.tickets.add(t);return t;
 }
 private boolean pass(String reg){for(MonthlyPass p:r.passes)if(p.valid(reg))return true;return false;}
 public double fee(Ticket t,boolean lost){
  if(pass(t.getVehicle().getRegistration()))return lost?500:0;
  long mins=Duration.between(t.getEntry(),LocalDateTime.now()).toMinutes();if(mins<=15)return lost?500:0;
  long hours=(long)Math.ceil((mins-15)/60.0);long days=Math.max(1,(long)Math.ceil(mins/1440.0));
  int rate=switch(t.getVehicle().getType()){case "BIKE"->10;case "CAR"->20;case "TRUCK"->40;default->0;};
  return Math.min(hours,days*8)*rate+(lost?500:0);
 }
 public Payment pay(int id,String method,boolean success,boolean lost){
  Ticket t=find(id);if(t==null||!(t.getStatus().equals("ACTIVE")||t.getStatus().equals("LOST")))throw new IllegalArgumentException("Ticket not active.");
  double amount=fee(t,lost||t.getStatus().equals("LOST"));String status=success?"PAID":"FAILED";
  Payment p=new Payment(paymentId++,id,amount,method,status);r.payments.add(p);if(success)t.paid();
  System.out.println("\n========== RECEIPT ==========\n"+p+"\n============================");return p;
 }
 public void exit(int id){
  Ticket t=find(id);if(t==null||!t.getStatus().equals("PAID"))throw new IllegalArgumentException("Successful payment required.");
  if(Duration.between(t.getPaidAt(),LocalDateTime.now()).toMinutes()>15)throw new IllegalArgumentException("15-minute exit window expired. Pay again.");
  slot(t.getFloor(),t.getSlot()).setStatus("FREE");t.close();System.out.println("Exit completed. Slot is FREE.");
 }
 public void lost(String reg){for(Ticket t:r.tickets)if(t.getVehicle().getRegistration().equalsIgnoreCase(reg)&&t.getStatus().equals("ACTIVE")){t.lost();return;}throw new IllegalArgumentException("Active ticket not found.");}
 public Ticket find(int id){for(Ticket t:r.tickets)if(t.getId()==id)return t;return null;}
 public void addPass(String reg,LocalDate a,LocalDate b){if(b.isBefore(a))throw new IllegalArgumentException("End date before start date.");r.passes.add(new MonthlyPass(reg,a,b));}
 public void reports(){
  System.out.println("--- FLOOR REPORT ---");
  for(Floor f:r.getLot().getFloors()){
   int free=0,occ=0;for(Slot s:f.getSlots()){if(s.getStatus().equals("FREE"))free++;if(s.getStatus().equals("OCCUPIED"))occ++;}
   System.out.println("Floor "+f.getNumber()+" Free:"+free+" Occupied:"+occ);
   for(String type:List.of("BIKE","CAR","TRUCK")){int a=0,b=0;for(Slot s:f.getSlots())if(s.getType().equals(type)){if(s.getStatus().equals("FREE"))a++;if(s.getStatus().equals("OCCUPIED"))b++;}System.out.println(type+" Free:"+a+" Occupied:"+b);}
   if(free+occ>0&&occ*100.0/(free+occ)>90)System.out.println("ALERT: occupancy above 90%");
  }
  System.out.println("--- PARKED VEHICLES ---");
  for(Ticket t:r.tickets)if(!t.getStatus().equals("CLOSED")){System.out.println(t);if(Duration.between(t.getEntry(),LocalDateTime.now()).toHours()>=24)System.out.println("ADMIN ALERT: parked over 24 hours!");}
  double revenue=0;for(Payment p:r.payments)if(p.getStatus().equals("PAID")&&p.getDate().toLocalDate().equals(LocalDate.now()))revenue+=p.getAmount();
  System.out.println("Today's revenue: Rs."+revenue);
 }
 public ParkingRepository repository(){return r;}
}
