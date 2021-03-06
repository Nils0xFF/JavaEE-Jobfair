/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.controllers.JobCreationController;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * @author lennartwoltering, nilsgeschwinde
 */
@Named("JobCreation")
@ViewScoped
public class JobCreationFace implements Serializable {

    @NotBlank
    private String jobname;
    @NotBlank
    private String desc;
    @Pattern(regexp = "^[^0-9]+$")
    @NotBlank
    private String street;
    @NotBlank
    private String housenumber;
    @NotBlank
    @Pattern(regexp = "^[^0-9]+$")
    private String city;
    @NotBlank
    private String postalcode, country;
    @PositiveOrZero
    private Double salary;
    @Enumerated(EnumType.STRING)
    private Sal_Relation relation;
    private List<Requirement> wishedRequirement;
    private List<NeededRequirement> finishedWeightedRequirements = new ArrayList<>();
    private List<NeededRequirement> newWeightedRequirements = new ArrayList<>();

    private JobField jobfield;

    @Inject
    private JobFieldRepository jobFieldRepo;

    @Inject
    private RequirementRepository requirementRepo;

    @Inject
    private JobCreationController jobCntrl;

    @Inject
    private SecurityContext context;

    @Transactional
    public void createJob() {
        jobCntrl.createInfo(jobname, desc, jobfield, finishedWeightedRequirements, salary, relation)
                .createAddress(street, housenumber, city, postalcode, country)
                .finishCreation(context.getCallerPrincipal().getName());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard");
        } catch (IOException ex) {
            Logger.getLogger(CompanyRegisterFace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<Requirement> getWishedRequirement() {
        return wishedRequirement;
    }

    public void setWishedRequirement(List<Requirement> wishedRequirement) {
        this.wishedRequirement = wishedRequirement;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public List<JobField> getJobfields() {
        return jobFieldRepo.findAll();
    }

    public List<Requirement> getAllRequirements() {
        return requirementRepo.findAll();
    }

    public JobField getJobfield() {
        return jobfield;
    }

    public void setJobfield(JobField jobfield) {
        this.jobfield = jobfield;
    }

    public Sal_Relation getRelation() {
        return relation;
    }

    public void setRelation(Sal_Relation relation) {
        this.relation = relation;
    }

    public Sal_Relation[] getRelations() {
        return Sal_Relation.values();
    }

    public List<NeededRequirement> getWeightedRequirements() {
        newWeightedRequirements = new ArrayList<>();
        if (this.wishedRequirement != null) {
            for (Requirement req : this.wishedRequirement) {
                boolean found = false;
                for (NeededRequirement old : this.finishedWeightedRequirements) {
                    if (req.equals(old.getRequirement())) {
                        newWeightedRequirements.add(old);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    NeededRequirement toInsert = NeededRequirement.builder()
                            .requirement(req)
                            .weight(1)
                            .build();
                    newWeightedRequirements.add(toInsert);
                }
            }
        }
        finishedWeightedRequirements = newWeightedRequirements;
        return newWeightedRequirements;
    }

    public void setWeightedRequirements(List<NeededRequirement> weightedRequirements) {
        this.newWeightedRequirements = weightedRequirements;
    }

    public List<NeededRequirement> getFinishedRequirements() {
        return finishedWeightedRequirements;
    }

}
