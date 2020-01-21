package de.hsos.kbse.jobboerse.enums;

/**
 *
 * @author lennartwoltering
 */
public enum SAL_Relation {
    NJ("Nebenjob"),
    HT("Halbtags"),
    VT("Volltags"),
    WS("Werkstudent");
    
    private final String desc;
    
    private SAL_Relation(String desc){
        this.desc = desc;
    }
    
    
    
}
