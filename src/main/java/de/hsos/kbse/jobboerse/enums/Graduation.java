/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.enums;

/**
 *
 * @author soere
 */
public enum Graduation {
    Schule("Noch in schulischer Ausbildung"),
    Haupt("Haupt-/Volksschulabschluss"),
    Real("Real- oder gleichwertiger Abschluss"),    
    Hoch("Fach-/Hochschulreife"),
    BachelorA("Bachelor of Arts (B.A.)"),
    BachelorSc("Bachelor of Science (B.Sc.)"),
    BachelorLL("Bachelor of Laws (LL.B.)"),
    BachelorEd("Bachelor of Education (B.Ed.)"),
    BachelorEng("Bachelor of Engineering (B.Eng.)"),
    BachelorFA("Bachelor of Fine Arts (B.F.A.)"),
    BachelorMus("Bachelor of Music (B.Mus.)"),
    BachelorMA("Bachelor of Musical Arts (B.M.A.)"),
    MasterA("Master of Arts (M.A.)"),
    MasterSc("Master of Science (M.Sc.)"),
    MasterLL("Master of Laws (LL.M.)"),
    MasterEd("Master of Education (M.Ed.)"),
    MasterEng("Master of Engineering (M.Eng.)"),
    MasterFA("Master of Fine Arts (M.F.A.)"),    
    MasterMus("Master of Music (M.Mus.)");    
    
    private final String grad;
    
    private Graduation(String grad){
       this.grad = grad;
    }

    public String getGrad() {
        return grad;
    }
    
    
}
