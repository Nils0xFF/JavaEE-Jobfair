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
import javax.transaction.Transactional;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@ViewScoped
public class AdminDashboardFace implements Serializable {

    private String requirementName;
    private String benefitName;
    private String jobFieldName;

    @Inject
    private BenefitRepository benefitRepo;

    @Inject
    private JobFieldRepository jobFieldRepo;

    @Inject
    private RequirementRepository requirementRepo;

    @Transactional
    public void createRequirement() {
        requirementRepo.create(requirementName, "");
    }

    @Transactional
    public void createBenefit() {
        benefitRepo.create(benefitName, "");
    }

    public void createJobField() {
        benefitRepo.create(jobFieldName, "");
    }

    public List<Requirement> getRequirements() {
        return requirementRepo.findAll();
    }

    public List<Benefit> getBenefits() {
        return benefitRepo.findAll();
    }

    public List<JobField> getJobFields() {
        return jobFieldRepo.findAll();
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public void setBenefitName(String benefitName) {
        this.benefitName = benefitName;
    }

    public String getJobFieldName() {
        return jobFieldName;
    }

    public void setJobFieldName(String jobFieldName) {
        this.jobFieldName = jobFieldName;
    }

}
