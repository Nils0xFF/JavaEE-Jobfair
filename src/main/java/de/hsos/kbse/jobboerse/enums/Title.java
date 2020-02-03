package de.hsos.kbse.jobboerse.enums;

/**
 *
 * @author lennartwoltering
 */
public enum Title {
    Emtpy(""),
    Dok("Doktor"),
    Prof("Professor"),
    ProfDok("Professor Dr.");
    
    
    public final String address;
    
    private Title(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    
    
}
