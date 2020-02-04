/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author nilsgeschwinde
 */

@Named
@ViewScoped
public class AdminDashboardFace implements Serializable{
    
    @Inject
    private BenefitRepository benefitRepo;
    
    @Inject
    private JobFieldRepository jobFieldRepo;
    
    @Inject
    private RequirementRepository requirementRepo;
    
    
    public List<Requirement> getRequirements(){
        return requirementRepo.findAll();
    }
    
    public List<Benefit> getBenefits(){
        return benefitRepo.findAll();
    }
    
    public List<JobField> getJobFields(){
        return jobFieldRepo.findAll();
    }
    
}
