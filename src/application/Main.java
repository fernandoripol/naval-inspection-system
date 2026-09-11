package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import entities.Boarding;
import entities.VesselClass;
import entities.Vessel;
import entities.NavalInspector;
import entities.TeamMember;
import entities.InspectionReport;

/**
 * MAIN APPLICATION CLASS
 * 
 * Entry point for the Naval Inspection System. Manages user console interaction,
 * operational data input, team registration, vessel boardings, and report generation.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== NAVAL INSPECTION SYSTEM - BRAZILIAN NAVY ===");
        System.out.print("Enter OMS number (e.g., 82/2026): ");
        String oms = scanner.nextLine();

        System.out.print("PIN / Location Area (e.g., Peixoto Dam): ");
        String pin = scanner.nextLine();

        System.out.print("Employed Vehicle (e.g., GMF-8D72): ");
        String vehicle = scanner.nextLine();

        System.out.print("Employed Vessel (or XXXX): ");
        String officialVessel = scanner.nextLine();

        InspectionReport report = new InspectionReport("DELEGACIA FLUVIAL DE FURNAS", LocalDate.now(), oms, LocalTime.of(9, 0), pin, vehicle, officialVessel, "Routine inspection.");

        // Quick registration of the fixed operational team
        report.addMilitary(new TeamMember("FABIO PALMA", "CT(EN)", "TEAM LEADER"));
        report.addMilitary(new TeamMember("SANTOS FARIAS", "2°SG-MO", "NAVAL INSPECTOR / DRIVER"));
        report.addMilitary(new NavalInspector("FERNANDO RODRIGUES RIPOL", "3°SG-CN", "123456"));
        report.addMilitary(new TeamMember("GUIMARÃES", "1°SG-EF", "ASSISTANT"));
        report.addMilitary(new TeamMember("TAVARES", "CB-MS", "ASSISTANT"));

        int option = -1;
        while (option != 0) {
            System.out.println("\n--- NAVAL INSPECTION MENU ---");
            System.out.println("1 - Add Inspected Vessel");
            System.out.println("2 - Register Fuel Consumption (CLG)");
            System.out.println("3 - Generate and Print Official Report");
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");
            
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                continue;
            }

            if (option == 1) {
                System.out.print("Vessel Name: ");
                String vesselName = scanner.nextLine();

                System.out.print("Registration Number: ");
                String registration = scanner.nextLine();

                System.out.println("Choose Vessel Class:");
                System.out.println("1 - JET SKI");
                System.out.println("2 - SPORTS AND RECREATION");
                System.out.println("3 - BARGE");
                System.out.println("4 - PASSENGER TRANSPORT");
                System.out.println("5 - TOURISM SUPPORT");
                System.out.print("Option: ");
                
                int classOption = scanner.nextInt();
                scanner.nextLine();

                VesselClass vesselClass = VesselClass.SPORTS_AND_RECREATION;
                if (classOption == 1) vesselClass = VesselClass.JET_SKI;
                else if (classOption == 2) vesselClass = VesselClass.SPORTS_AND_RECREATION;
                else if (classOption == 3) vesselClass = VesselClass.BARGE;
                else if (classOption == 4) vesselClass = VesselClass.PASSENGER_TRANSPORT;
                else if (classOption == 5) vesselClass = VesselClass.TOURISM_SUPPORT;

                Vessel vessel = new Vessel(vesselName, registration, vesselClass);

                System.out.print("Infraction Notice Number (or -): ");
                String infraction = scanner.nextLine();

                System.out.print("Seizure Record Number (or -): ");
                String seizure = scanner.nextLine();

                System.out.print("Legal Custodian (or -): ");
                String custodian = scanner.nextLine();

                Boarding boarding = new Boarding(vessel, infraction, seizure, custodian, LocalDate.now());
                report.addBoarding(boarding);
                System.out.println("✅ Vessel added successfully!");

            } else if (option == 2) {
                System.out.print("Gasoline liters spent: ");
                double gas = scanner.nextDouble();
                System.out.print("Diesel liters spent: ");
                double diesel = scanner.nextDouble();
                scanner.nextLine();

                report.setFuel(gas, diesel);
                System.out.println("✅ Fuel consumption registered!");

            } else if (option == 3) {
                report.generateReportFile();
            }
        }
        scanner.close();
        System.out.println("System shutting down. Have a safe return to the OM, military!");
    }
}
