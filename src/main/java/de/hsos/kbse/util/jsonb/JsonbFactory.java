/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.util.jsonb;

import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 *
 * @author soere
 */

public class JsonbFactory {
    @Produces
    public Jsonb createJsonb() {
        return JsonbBuilder.create();
    }
}
