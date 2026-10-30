package com.parking.cli;

import java.util.*;
import java.time.*;

import com.parking.model.*;
import com.parking.repository.ParkingRepository;
import com.parking.service.ParkingService;
public class ParkingCLI {
 private Scanner sc=new Scanner(System.in);private ParkingRepository repo=new ParkingRepository();private ParkingService service=new ParkingService(repo);
 public void start(){while(true){
  System.out.println("\n===== PARKING LOT SYSTEM =====");
  System.out.println("1 Add floor (Admin)  2 Add slot (Admin)  3 Close floor");
  System.out.println("4 Mark maintenance  5 Change free slot type  6 Vehicle entry");
  System.out.println("7 Pay fee  8 Vehicle exit  9 Lost ticket");
  System.out.println("10 Add monthly pass  11 Reports  12 View floors/tickets  0 Exit");
  try{switch(num("Choose: ")){
   case 1->service.addFloor(num("Floor number: "));
   case 2->service.addSlot(num("Floor: "),num("Slot number: "),txt("Type Bike/Car/Truck: "));
   case 3->service.closeFloor(num("Floor number: "));
   case 4->service.maintenance(num("Floor: "),num("Slot: "));
   case 5->service.changeType(num("Floor: "),num("Slot: "),txt("New type: "));
   case 6->entry();
   case 7->payment(false);
   case 8->service.exit(num("Ticket ID: "));
   case 9->lost();
   case 10->pass();
   case 11->service.reports();
   case 12->show();
   case 0->{System.out.println("Bye!");return;}
   default->System.out.println("Invalid choice.");
  }}catch(RuntimeException e){System.out.println("Error: "+e.getMessage());}
 }}
 private void entry(){Ticket t=service.entry(txt("Registration: "),txt("Vehicle type: "),txt("Owner name: "));System.out.println("Entry successful: "+t);}
 private void payment(boolean lost){int id=num("Ticket ID: ");String method=txt("Method Cash/UPI/Card: ");boolean ok=txt("Payment success? y/n: ").equalsIgnoreCase("y");service.pay(id,method,ok,lost);}
 private void lost(){String reg=txt("Registration number: ");service.lost(reg);int id=0;for(Ticket t:repo.tickets)if(t.getVehicle().getRegistration().equalsIgnoreCase(reg))id=t.getId();System.out.println("Lost ticket penalty Rs.500 added to fee.");payment(true);}
 private void pass(){String reg=txt("Registration: ");LocalDate a=LocalDate.parse(txt("Start YYYY-MM-DD: "));LocalDate b=LocalDate.parse(txt("End YYYY-MM-DD: "));service.addPass(reg,a,b);System.out.println("Monthly pass added.");}
 private void show(){System.out.println(repo.getLot().getName()+" - "+repo.getLot().getAddress());for(Floor f:repo.getLot().getFloors())System.out.println(f);for(Ticket t:repo.tickets)System.out.println(t);for(Payment p:repo.payments)System.out.println(p);}
 private int num(String p){while(true){System.out.print(p);try{return Integer.parseInt(sc.nextLine().trim());}catch(Exception e){System.out.println("Enter a number.");}}}
 private String txt(String p){System.out.print(p);return sc.nextLine().trim();}
}
