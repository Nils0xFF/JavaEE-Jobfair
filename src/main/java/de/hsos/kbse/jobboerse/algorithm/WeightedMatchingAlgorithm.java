/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.algorithm;

import de.hsos.kbse.jobboerse.algorithm.qualifiers.Weighted;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import de.hsos.kbse.jobboerse.repositories.SearchRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author lennartwoltering
 */
@Weighted
public class WeightedMatchingAlgorithm implements MatchingAlgorithm, Serializable {

    @Inject
    private SearchRepository searchRepo;
    @Inject
    private JobRepository jobRepo;
    @Inject
    private GeneralUserRepository userRepo;

    /**
     * This algorithm searches for jobs that fullfill the jobfields the user is interested in.
     * It creates for every Job a percentage of accordance. The Difference towards the basic algorithm 
     * is that this algorithm uses the Weight of the requirement
     * 
     * @param email the email address for the user
     * @return available jobs for the user with the specified email
     */
    @Override
    @Transactional
    public List<WeightedJob> findSuitableJobs(String email) {
        SeekingUser user = userRepo.getUserByEmail(email);
        SearchRequest userRequest = user.getSearchrequest();
        List<Requirement> fullfilledRequirements = user.getProfile().getFullfiledRequirements();
        List<WeightedJob> suitableJobs = new ArrayList<>();
        Set<Job> availableJobs = new HashSet<>();
        for (JobField jobfield : userRequest.getJobfield()) {
            try {
                List<Job> jobs = jobRepo.findJobsByJobField(jobfield.getName());
                availableJobs.addAll(jobs);
            } catch (Exception ex) {
                
            }
        }
        //Arquillian Test Suite kann nicht mit Lambda Funktionen umgehen
        /*
        userRequest.getJobfield().forEach((jobfield) -> {
            List<Job> jobs = jobRepo.findJobsByJobField(jobfield.getName());
            if(jobs != null){
                availableJobs.addAll(jobs);
            }
        });
         */

        float initPercentageBenfits = 100.0f;
        float initPercentageRequirements = 100.0f;
        for (Job available : availableJobs) {
            float percentageBenefits = initPercentageBenfits;
            float percentageRequirements = initPercentageRequirements;
            WeightedJob foundJob = new WeightedJob();
            for (Benefit userBenefits : userRequest.getWishedBenefits()) {
                boolean foundBenefit = false;
                for (Benefit cmpyBenefits : available.getCompany().getProfile().getBenefits()) {
                    if (cmpyBenefits.getName().equals(userBenefits.getName())) {
                        foundBenefit = true;
                        break;
                    }
                }
                if(!foundBenefit) {
                    percentageBenefits -=  initPercentageBenfits / userRequest.getWishedBenefits().size();
                }
            }
            for (NeededRequirement cmpyRequirement : available.getNeeded()) {
                boolean foundRequirement = false;
                for (Requirement userRequirement : fullfilledRequirements) {
                    if (cmpyRequirement.getRequirement().getName().equals(userRequirement.getName())) {
                        foundRequirement = true;
                        break; 
                    }
                }
                if(!foundRequirement) {
                    percentageRequirements -= initPercentageRequirements / (available.getNeeded().size() * cmpyRequirement.getWeight());
                }
                
            }
            foundJob.setJob(available);
            foundJob.setBenefitPercentage(percentageBenefits);
            foundJob.setRequirementPercentage(percentageRequirements);
            foundJob.setTotalPercentage((percentageBenefits + percentageRequirements) / 2);
            suitableJobs.add(foundJob);
        }
        if (suitableJobs.size() > 0) {
            Collections.sort(suitableJobs, Collections.reverseOrder());
        }
        return suitableJobs;

    }
}
