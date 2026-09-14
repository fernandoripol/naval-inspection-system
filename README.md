
# Naval Inspection System

Java-based console system developed for managing and controlling naval inspections, military team registrations, multi-day operational chronologies, vessel surveys, and generating official naval inspection reports.

## Features & Business Rules
- **Official Operational Report Generation:** Automatically compiles 15 official report sections required by the Brazilian Navy, including vessel classifications, infractions, and fuel consumption (CLG).
- **Multi-day Chronology Support:** Handles complex operations spanning multiple days, locations, and custom dates.
- **Embedded Local Database:** Utilizes **SQLite** for lightweight, self-contained, and persistent local data storage without requiring external servers.
- **Dynamic Input & Customization:** Interactive console workflows allowing real-time editing of report details, military staff, and credential management.

## Project Structure & Architecture
The project follows a rigorous object-oriented package structure:
- **`application`**: Contains the main entry point (`Main.java`) responsible for handling console user input, orchestrating core operations, and managing execution loops.
- **`entities`**: Holds the core domain classes representing the business model:
  - `NavalInspector`: Represents inspectors with official credential tracking.
  - `TeamMember`: Represents individual support team members.
  - `Vessel` & `VesselClass`: Models inspected boats/ships and standard classifications (Moto-aquática, Esporte e Recreio, Balsa, etc.).
  - `Boarding`: Manages boarding details, infraction notices (A.I), seizure records (A.A), and custodians.
  - `InspectionReport`: Manages the complete inspection documentation and export operations.
- **`services`**: Contains logic for data processing, database management (`DatabaseService.java`), and exportable file generation (`Exportable.java`).
- **`exceptions`**: Custom domain exceptions (`DomainException`) for runtime validation errors and robust error handling.

## Technologies Used
- **Java** (JDK 11+)
- **SQLite** (Embedded Database & JDBC)
- **VS Code** (Development Environment)
- **Git / GitHub** (Version Control)

## How to Run
1. Make sure you have the JDK installed.
2. Compile the source code located in the `src` folder:
   ```bash
   javac -d bin src/application/Main.java src/entities/*.java src/services/*.java src/exceptions/*.java src/exceptions/*.java

    Run the application:
    Bash

    java -cp bin application.Main
