package entities;

/**
 * VESSEL CLASS ENUM
 * 
 * Defines the standard legal and operational categories of vessels 
 * subject to naval inspection under Brazilian maritime regulations.
 */
public enum VesselClass {
    JET_SKI("JET SKI"),
    SPORTS_AND_RECREATION("SPORTS AND RECREATION"),
    BARGE("BARGE"),
    PASSENGER_TRANSPORT("PASSENGER TRANSPORT"),
    TOURISM_SUPPORT("TOURISM SUPPORT");

    private final String description;

    VesselClass(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
