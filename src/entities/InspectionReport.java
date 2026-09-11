package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import exceptions.DomainException;
import services.Exportable;

/**
 * INSPECTION REPORT ENTITY
 * 
 * Manages the complete naval inspection report, consolidating operational data,
 * team staff, vessel boardings, statistics, fuel consumption, and official output generation[cite: 8, 10].
 */
public class InspectionReport implements Exportable {

    private String delegation;
    private LocalDate operationDate;
    private String oms;
    private LocalTime departureTime;
    private LocalTime returnTime;
    private String operationLocation;
    private String vehicle;
    private String officialVessel;
    private String occurrences;
    private double fuelGasoline;
    private double fuelDiesel;

    private List<Military> militaryTeam;
    private List<Boarding> boardings;

    private static final DateTimeFormatter FMT_EXTENDED_DATE = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
    private static final DateTimeFormatter FMT_TIME = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * CONSTRUCTOR
     * Initializes the inspection report parameters with defensive fallback values.
     */
    public InspectionReport(String delegation, LocalDate operationDate, String oms, LocalTime departureTime, 
                            String operationLocation, String vehicle, String officialVessel, 
                            String occurrences) {
        
        this.delegation = (delegation == null || delegation.trim().isEmpty()) ? "DELEGACIA FLUVIAL DE FURNAS" : delegation.trim();
        this.operationDate = (operationDate != null) ? operationDate : LocalDate.now();
        this.oms = (oms != null && !oms.trim().isEmpty()) ? oms.trim() : "82/2026";
        this.departureTime = (departureTime != null) ? departureTime : LocalTime.of(9, 0);
        this.returnTime = null; 
        this.operationLocation = (operationLocation != null && !operationLocation.trim().isEmpty()) ? operationLocation.trim() : "-";
        this.vehicle = (vehicle != null && !vehicle.trim().isEmpty()) ? vehicle.trim() : "-";
        this.officialVessel = (officialVessel != null && !officialVessel.trim().isEmpty()) ? officialVessel.trim() : "-";
        this.occurrences = (occurrences != null && !occurrences.trim().isEmpty()) ? occurrences.trim() : "None.";
        
        this.militaryTeam = new ArrayList<>();
        this.boardings = new ArrayList<>();
    }

    public void addMilitary(Military m) {
        if (m == null) throw new DomainException("Null military member cannot be added.");
        this.militaryTeam.add(m);
    }

    public void addBoarding(Boarding b) {
        if (b == null) throw new DomainException("Null boarding record cannot be added.");
        this.boardings.add(b);
    }

    public void setReturnTime(LocalTime h) {
        if (h != null && h.isBefore(this.departureTime)) {
            throw new DomainException("Return time cannot be earlier than departure time.");
        }
        this.returnTime = h;
    }

    public void setFuel(double gasoline, double diesel) {
        this.fuelGasoline = gasoline;
        this.fuelDiesel = diesel;
    }

    public List<Boarding> getBoardings() { return this.boardings; }
    public List<Military> getMilitaryTeam() { return this.militaryTeam; }

    @Override
    public void generateReportFile() {
        printOfficialReport();
    }

    /**
     * Consolidates and prints the official formatted report to the console.
     */
    public void printOfficialReport() {
        String depTimeStr = this.departureTime.format(FMT_TIME);
        String retTimeStr = (this.returnTime == null) ? "17:30" : this.returnTime.format(FMT_TIME);

        System.out.println("\n=========================================================================================");
        System.out.println("                                BRAZILIAN NAVY");
        System.out.println("                           " + this.delegaciaUpper());
        System.out.println("                       NAVAL INSPECTION OFFICIAL REPORT");
        System.out.println("=========================================================================================");
        
        System.out.println("01) ASSIGNED TASK: WATERWAY TRAFFIC INSPECTION");
        System.out.println("02) DATE/PERIOD: " + this.operationDate.format(FMT_EXTENDED_DATE) + " | OMS: " + this.oms);
        System.out.println("    TEAM DEPARTURE: " + depTimeStr + " | TEAM RETURN: " + retTimeStr);
        
        System.out.println("\n03) CHRONOLOGY / OPERATION LOCATION:");
        System.out.println("    PIN: " + this.operationLocation);
        
        System.out.println("\n04) INSPECTED VESSELS:");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-18s | %-16s | %-6s | %-6s | %-6s | %-25s | %-5s\n", 
                          "VESSEL", "REGISTRATION", "N.I", "S.R", "CUST", "CLASS", "DATE");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        int jetAb = 0, spoAb = 0, barAb = 0, pasAb = 0, touAb = 0;
        int jetNot = 0, spoNot = 0, barNot = 0, pasNot = 0, touNot = 0;
        int jetSei = 0, spoSei = 0, barSei = 0, pasSei = 0, touSei = 0;

        for (Boarding b : boardings) {
            System.out.printf("%-18s | %-16s | %-6s | %-6s | %-6s | %-25s | %-5s\n",
                    b.getVessel().getName(),
                    b.getVessel().getRegistration(),
                    b.getInfractionNotice(),
                    b.getSeizureRecord(),
                    b.getLegalCustodian(),
                    b.getVessel().getVesselClass(),
                    b.getFormattedDate());

            String c = b.getVessel().getVesselClass();
            if (c.equals("JET SKI")) {
                jetAb++; if (b.hasNotification()) jetNot++; if (b.hasSeizure()) jetSei++;
            } else if (c.equals("SPORTS AND RECREATION")) {
                spoAb++; if (b.hasNotification()) spoNot++; if (b.hasSeizure()) spoSei++;
            } else if (c.equals("BARGE")) {
                barAb++; if (b.hasNotification()) barNot++; if (b.hasSeizure()) barSei++;
            } else if (c.equals("PASSENGER TRANSPORT")) {
                pasAb++; if (b.hasNotification()) pasNot++; if (b.hasSeizure()) pasSei++;
            } else {
                touAb++; if (b.hasNotification()) touNot++; if (b.hasSeizure()) touSei++;
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        
        System.out.println("\n📊 CONSOLIDATED STATISTICAL TABLE:");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-14s | %-12s | %-17s | %-6s | %-24s | %-15s\n", 
                          "TOTAL", "JET SKI", "SPORTS & REC", "BARGE", "PASSENGER TRANSPORT", "TOURISM SUPPORT");
        System.out.printf("%-14s | %-12d | %-17d | %-6d | %-24d | %-15d\n", "BOARDINGS", jetAb, spoAb, barAb, pasAb, touAb);
        System.out.printf("%-14s | %-12d | %-17d | %-6d | %-24d | %-15d\n", "NOTIFICATIONS", jetNot, spoNot, barNot, pasNot, touNot);
        System.out.printf("%-14s | %-12d | %-17d | %-6d | %-24d | %-15d\n", "SEIZURES", jetSei, spoSei, barSei, pasSei, touSei);
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("\n05) EMPLOYED MEANS:");
        System.out.println("    VEHICLE: " + this.vehicle + " | VESSEL: " + this.officialVessel);

        System.out.println("\n06) RESPONSIBLE MILITARY STAFF:");
        for (Military m : militaryTeam) {
            System.out.println("    " + m.getOfficialIdentification());
        }

        System.out.println("\n09) OCCURRENCES:");
        System.out.println("    " + this.occurrences);

        System.out.println("\n15) FUEL CONSUMPTION (CLG):");
        System.out.println("Gasoline: " + this.fuelGasoline + " liters.");
        System.out.println("Diesel: " + this.fuelDiesel + " liters.");
        System.out.println("=========================================================================================\n");
    }

    private String delegaciaUpper() {
        return this.delegation.toUpperCase();
    }
}
