/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.facades.SearchRequestFacade;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import de.hsos.kbse.jobboerse.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class SearchRepo {
    @Inject 
    private SearchRequestFacade searchrequests;
    
    @Inject
    private UserRepository users;
    
    public boolean createSearchRequirements(String email, List<Requirement> fullfilledRequirements, List<Benefit> wishedBenefits, JobField jobfield){
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            SearchRequest toInsert = SearchRequest.builder()
                    .requirements(fullfilledRequirements)
                    .benefits(wishedBenefits)
                    .foundJobs(new ArrayList<>())
                    .jobField(jobfield)
                    .build();
            foundUser.setSearchrequest(toInsert);
            foundUser.setCompleted(true);
            users.edit(foundUser);
            return true;
        }
        return false;
    }
    
    public SearchRequest getSearchRequest(String email){
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest();
        }
        return null;
    }
    
    public JobField getJobField(String email){
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest().getJobfield();
        }
        return null;
    }
    
    public List<Requirement> getFullfilledRequirements(String email){
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest().getRequirements();
        }
        return null;
    }
    
    public List<Benefit> getWishedBenefits(String email){
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest().getBenefits();
        }
        return null;
    }
    
    public List<WeightedJob> getFoundJobs(String email){
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest().getFoundJobs();
        }
        return null;
    }
    
    public List<WeightedJob> getOrderedFoundJobs(String email){
        List<WeightedJob> foundJobs = getFoundJobs(email);
        if(foundJobs.size() > 0) Collections.sort(foundJobs);
        return foundJobs;
    }
}
