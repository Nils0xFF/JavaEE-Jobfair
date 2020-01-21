package de.hsos.kbse.jobboerse.enums;

/**
 *
 * @author lennartwoltering
 */
public enum Title {
    Prof("Professor"),
    Dok("Doktor"),
    ProfDok("Professor Dr."),
    Emtpy("");
    
    public final String address;
    
    private Title(String address){
        this.address = address;
    }
    
}
