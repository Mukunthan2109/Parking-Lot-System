# Parking Lot System

A Java-based parking management system that supports floor management, slot allocation, vehicle entry/exit, monthly passes, payments, and reporting.

## Features

- Add and manage parking floors
- Add slots by vehicle type
- Close floors and mark maintenance
- Vehicle entry and exit tracking
- Fee collection and payment processing
- Lost ticket handling
- Monthly pass management
- Reports for parking activity

## Project Structure

```text
src/
  com/
    parking/
      Main.java
      cli/
        ParkingCLI.java
      model/
      repository/
      service/
```

## How to Run

1. Open the project in a Java IDE or terminal.
2. Compile the project:

```bash
javac -d out $(find src -name "*.java")
```

3. Run the application:

```bash
java -cp out com.parking.Main
```

## Notes

This project provides a command-line interface for managing a parking lot and simulating the core workflows of a real parking management system.

## Author

Mukunthan
"Contributor: Mukunthan"  
