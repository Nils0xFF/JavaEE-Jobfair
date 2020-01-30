/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.algorithm;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import de.hsos.kbse.jobboerse.repositories.SearchRepository;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import de.hsos.kbse.jobboerse.repositories.GeneralUserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

/**
 *
 * @author lennartwoltering
 */
public class BasicMatchingAlgorithm implements MatchingAlgorithm {

    @Inject
    private SearchRepository searchRepo;
    @Inject
    private JobRepository jobRepo;
    @Inject
    private GeneralUserRepository userRepo;

    @Override
    public List<WeightedJob> findSuitableJobs(String email) {
        SearchRequest userRequest = searchRepo.getSearchRequest(email);
        List<Requirement> fullfilledRequirements = userRepo.getUserByEmail(email).getProfile().getFullfiledRequirements();
        List<WeightedJob> suitableJobs = new ArrayList<>();
        Set<Job> availableJobs = new HashSet<>();
        userRequest.getJobfield().forEach((jobfield) -> {
            availableJobs.addAll(jobRepo.findJobsByJobField(jobfield.getName()));
        });
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
                if(!foundBenefit) percentageBenefits -= userRequest.getWishedBenefits().size() / initPercentageBenfits;
            }
            for (NeededRequirement cmpyRequirement : available.getNeeded()) {
                boolean foundRequirement = false;
                for (Requirement userRequirement : fullfilledRequirements) {
                    if (cmpyRequirement.getRequirement().getName().equals(userRequirement.getName())) {
                        foundRequirement = true;
                        break; 
                    }
                }
                if(!foundRequirement) percentageRequirements -=  available.getNeeded().size() / initPercentageRequirements;
                
            }
            foundJob.setJob(available);
            foundJob.setBenefitPercentage(percentageBenefits);
            foundJob.setRequirementPercentage(percentageRequirements);
            foundJob.setTotalPercentage((percentageBenefits + percentageRequirements) / 2);
            suitableJobs.add(foundJob);
        }
        searchRepo.updateFoundJobs(email, suitableJobs);
        return suitableJobs;

    }

}
