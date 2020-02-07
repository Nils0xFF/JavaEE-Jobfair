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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@RequestScoped
public class AdminDashboardFace {

    @NotBlank
    private String requirementName, benefitName, jobFieldName;

    private List<Requirement> requirements;
    private List<Benefit> benefits;
    private List<JobField> jobFields;

    @Inject
    private BenefitRepository benefitRepo;

    @Inject
    private JobFieldRepository jobFieldRepo;

    @Inject
    private RequirementRepository requirementRepo;

    @PostConstruct
    public void init() {
        requirements = requirementRepo.findAll();
        benefits = benefitRepo.findAll();
        jobFields = jobFieldRepo.findAll();
    }

    @Transactional
    public void createRequirement() {
        try {
            requirementRepo.create(requirementName, requirementName);
        } catch (EntityExistsException e) {
            nameAlreadyExitsError();
        }

        init();
    }

    @Transactional
    public void updateRequirement(Long id, String newName) {
        try {
            requirementRepo.update(id, newName, newName);
        } catch (IllegalArgumentException ex) {
            nameAlreadyExitsError();
        }
    }

    @Transactional
    public void createBenefit() {
        try {
            benefitRepo.create(benefitName, benefitName);
        } catch (EntityExistsException e) {
            nameAlreadyExitsError();
        }

        init();
    }

    @Transactional
    public void updateBenefit(Long id, String newName) {
        try {
            benefitRepo.update(id, newName, newName);
        } catch (IllegalArgumentException ex) {
            nameAlreadyExitsError();
        }
    }

    @Transactional
    public void createJobField() {
        try {
            jobFieldRepo.create(jobFieldName);
            init();
        } catch (EntityExistsException ex) {
            nameAlreadyExitsError();
        }
    }

    @Transactional
    public void updateJobField(Long id, String newName) {
        try {
            jobFieldRepo.updateName(id, newName);
        } catch (IllegalArgumentException ex) {
            nameAlreadyExitsError();
        }
    }

    private void nameAlreadyExitsError() {
        FacesContext.getCurrentInstance().addMessage("creationGrowl",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Der Name existiert bereits", null));
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

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public List<JobField> getJobFields() {
        return jobFields;
    }

    public void setJobFields(List<JobField> jobFields) {
        this.jobFields = jobFields;
    }

}
