/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.algorithm;

import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import java.util.List;

/**
 *
 * @author lennartwoltering
 */
public interface MatchingAlgorithm {
    public List<WeightedJob> findSuitableJobs(String email);
}
