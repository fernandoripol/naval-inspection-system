package entities;

/**
 * NAVAL INSPECTOR SUBCLASS
 * 
 * Represents a certified naval inspector with official credential documentation,
 * extending the base Military class with specialized inspection credentials[cite: 5].
 */
public class NavalInspector extends Military {

    private String credentialNumber;

    /**
     * CONSTRUCTOR
     * Initializes the naval inspector with name, rank, and official credential number[cite: 5].
     */
    public NavalInspector(String name, String rank, String credentialNumber) {
        super(name, rank, "NAVAL INSPECTOR");
        this.credentialNumber = (credentialNumber != null && !credentialNumber.trim().isEmpty()) ? credentialNumber.trim() : "-";
    }

    public String getCredentialNumber() { return credentialNumber; }

    @Override
    public String getOfficialIdentification() {
        return "NAVAL INSPECTOR (Nº " + credentialNumber + "): " + getRank().toUpperCase() + " " + getName().toUpperCase();
    }
}
