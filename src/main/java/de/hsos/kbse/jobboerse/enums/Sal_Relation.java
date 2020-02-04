package de.hsos.kbse.jobboerse.enums;

/**
 *
 * @author lennartwoltering
 */

public enum Sal_Relation {
    NJ("Nebenjob"),
    HT("Teilzeit"),
    VT("Vollzeit"),
    WS("Werkstudent");
    
    private final String desc;
    
    private Sal_Relation(String desc){
        this.desc = desc;
    }
    
    public String getDesc(){
        return desc;
    }    
}