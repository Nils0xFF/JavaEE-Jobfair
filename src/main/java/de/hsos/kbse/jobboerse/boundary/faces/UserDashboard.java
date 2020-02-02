/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.algorithm.MatchingAlgorithm;
import de.hsos.kbse.jobboerse.algorithm.qualifiers.Basic;
import de.hsos.kbse.jobboerse.algorithm.qualifiers.Weighted;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.SearchRepository;
import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 *
 * @author lennartwoltering
 */
@Named("UserDashboardFace")
@ViewScoped
public class UserDashboard implements Serializable{
    
    DecimalFormat df = new DecimalFormat("#.##");
    
    
    List<JobField> wishedJobfiels;
    List<Benefit> wishedBenefits;
    List<WeightedJob> availableJobs;
    
    @Inject
    GeneralUserRepository userRepo;
    
    @Inject
    SecurityContext context;
    
    @Inject @Basic
    MatchingAlgorithm matching;
    
    @Inject
    JobFieldRepository jobfieldRepo;
    
    @Inject
    SearchRepository searchRepo;
    
    @PostConstruct
    public void init(){
        df.setRoundingMode(RoundingMode.CEILING);
        SeekingUser user = userRepo.getUserByEmail(context.getCallerPrincipal().getName());
        if(user != null){
            wishedBenefits = user.getSearchrequest().getWishedBenefits();
            wishedJobfiels = user.getSearchrequest().getJobfield();
        }
    }
    
    @Transactional
    public void updateSearchRequest() throws IOException{
        searchRepo.updateRequest(context.getCallerPrincipal().getName(), wishedBenefits, wishedJobfiels);
        updateAvailableJobs();
        
        //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        //ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public List<JobField> getWishedJobfiels() {
        return wishedJobfiels;
    }
    
    public List<JobField> getAllJobfields(){
        return jobfieldRepo.findAll();
    }

    public void setWishedJobfiels(List<JobField> wishedJobfiels) {
        this.wishedJobfiels = wishedJobfiels;
    }

    public List<Benefit> getWishedBenefits() {
        return wishedBenefits;
    }

    public void setWishedBenefits(List<Benefit> wishedBenefits) {
        this.wishedBenefits = wishedBenefits;
    }
    
    
   public void updateAvailableJobs(){
       availableJobs = matching.findSuitableJobs(context.getCallerPrincipal().getName());
   }
    
    public List<WeightedJob> getAvailableJobs(){
       availableJobs = matching.findSuitableJobs(context.getCallerPrincipal().getName());
       return availableJobs;
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