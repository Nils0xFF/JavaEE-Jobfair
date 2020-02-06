/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.util.autocomplete;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.repositories.BenefitRepository;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author lennartwoltering
 */
@Named("Autocomplete")
@RequestScoped
public class Autocomplete {
    
    @Inject
    private BenefitRepository benefitRepo;
    
    @Inject
    private RequirementRepository requirementRepo;
    
    @Inject
    private JobFieldRepository jobFieldRepo;
    
    public List<Benefit> completeBenefitData(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Benefit> allBenefits = benefitRepo.findAll();
        return allBenefits.stream().filter(t -> t.getName().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
    
    public List<Requirement> completeRequirementData(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Requirement> allRequirements = requirementRepo.findAll();
        return allRequirements.stream().filter(t -> t.getName().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<JobField> completeJobFieldData(String query) {
        String queryLowerCase = query.toLowerCase();
        List<JobField> allRequirements = jobFieldRepo.findAll();
        return allRequirements.stream().filter(t -> t.getName().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
    
}
