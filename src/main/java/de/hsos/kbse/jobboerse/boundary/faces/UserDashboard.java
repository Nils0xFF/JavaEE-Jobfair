/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.algorithm.MatchingAlgorithm;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;

/**
 *
 * @author lennartwoltering
 */
@Named("UserDashboardFace")
@ViewScoped
public class UserDashboard implements Serializable{
    
    DecimalFormat df = new DecimalFormat("#.##");
    
    @Inject
    GeneralUserRepository userRepo;
    
    @Inject
    SecurityContext context;
    
    @Inject
    MatchingAlgorithm matching;
    
    @PostConstruct
    public void init(){
        df.setRoundingMode(RoundingMode.CEILING);
    }
   
    public List<WeightedJob> getAvailableJobs(){
       return matching.findSuitableJobs(context.getCallerPrincipal().getName());
    }
    
    public String requirementToString(WeightedJob job){
        return job.getJob().getNeeded().stream().map(req -> req.getRequirement().getName()).collect(Collectors.joining(","));
    }
    
    public String benefitsToString(WeightedJob job){
        return job.getJob().getCompany().getProfile().getBenefits().stream().map(bene -> bene.getName()).collect(Collectors.joining(","));
    }

    public DecimalFormat getDf() {
        return df;
    }
    
    
    
}
