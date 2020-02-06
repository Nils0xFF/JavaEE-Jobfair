/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.facades.SearchRequestFacade;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class SearchRepository {
    @Inject 
    private SearchRequestFacade searchrequests;
    
    @Inject
    private GeneralUserRepository users;
    
    /**
     * Creates new Search Requirements for a User
     * @param email email of the user   
     * @param wishedBenefits Represents the wishedBenefits
     * @param jobfield Represents the wishedjobfields
     * @return returns true if the request was successfully created
     */
    public boolean createSearchRequirements(String email, List<Benefit> wishedBenefits, List<JobField> jobfield) {
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            SearchRequest toInsert = SearchRequest.builder()
                    .benefits(wishedBenefits)
                    .jobField(jobfield)
                    .build();
            foundUser.setSearchrequest(toInsert);
            foundUser.setCompleted(true);
            users.edit(foundUser);
            return true;
        }
        return false;
    }
    
    /**
     * Creates new Search Requirements for a User
     * @param email email of the user
     * @param search Search Request that should be saved
     * @throws IllegalArgumentException 
     */
    public void createSearchRequirements(String email, SearchRequest search) throws IllegalArgumentException {
        SeekingUser foundUser = users.getUserByEmail(email);
        if (foundUser != null) {
            foundUser.setSearchrequest(search);
            foundUser.setCompleted(true);
            users.edit(foundUser);
        } else {
            throw new IllegalArgumentException("User not found!");
        }
    }
    
    public SearchRequest getSearchRequest(String email) {
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest();
        }
        return null;
    }
    
    public List<JobField> getJobField(String email) {
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest().getJobfield();
        }
        return null;
    }
    
    public List<Benefit> getWishedBenefits(String email) {
        SeekingUser foundUser = users.getUserByEmail(email);
        if(foundUser != null){
            return foundUser.getSearchrequest().getWishedBenefits();
        }
        return null;
    }
    
    /**
     * Updates a Search Request from a User
     * @param email email of the User   
     * @param wishedBenefits Represents the wished Benefits of the user
     * @param wishedJobfield Represents the wished Jobfields of the user
     */
    public void updateRequest(String email, List<Benefit> wishedBenefits, List<JobField> wishedJobfield) {
        SearchRequest request = getSearchRequest(email);
        if(request != null){
            request.setJobfield(wishedJobfield);
            request.setWishedBenefits(wishedBenefits);
            searchrequests.edit(request);
        }
    }
    
}
