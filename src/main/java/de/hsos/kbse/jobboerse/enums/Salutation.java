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
public enum Salutation {
    mister("Herr"),
    misses("Frau"),
    diverse("");
    
    private final String salut;
    
    private Salutation(String salut) {
        this.salut = salut;
    }
}
