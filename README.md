# Naval Inspection System

Java-based system developed for managing and controlling naval inspections, team registrations, vessel surveys, and issuing operational reports.

## Project Structure & Architecture
The project follows an organized object-oriented package structure:
- **`application`**: Contains the main entry point (`Main.java`) responsible for handling console user input, orchestrating core operations, and managing execution loops.
- **`entities`**: Holds the core domain classes representing the business model:
  - `NavalInspector`: Represents the inspector carrying out the duties.
  - `TeamMember`: Represents individual inspection team members.
  - `Vessel` & `VesselClass`: Models the inspected boats/ships and their respective classes.
  - `Boarding`: Manages boarding details and operational data.
  - `InspectionReport`: Manages the generated inspection documentation.
- **`services`**: Contains logic for data processing and exportable operations (`Exportable.java`).
- **`exceptions`**: Custom domain exceptions (`DomainException`) for runtime validation errors and robust error handling.

## Technologies Used
- **Java** (JDK 11+)
- **VS Code** (Development Environment)
- **Git / GitHub** (Version Control)

## How to Run
1. Make sure you have the JDK installed.
2. Compile the source code located in the `src` folder:
   ```bash
   javac -d bin src/application/Main.java src/entities/*.java src/services/*.java src/exceptions/*.java

3.   Run the application:


java -cp bin application.Main