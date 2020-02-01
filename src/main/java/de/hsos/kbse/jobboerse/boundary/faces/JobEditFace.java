/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
import de.hsos.kbse.jobboerse.repositories.JobFieldRepository;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import de.hsos.kbse.jobboerse.repositories.RequirementRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.security.enterprise.SecurityContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 *
 * @author nilsgeschwinde
 */
@Named
@ViewScoped
public class JobEditFace implements Serializable {

    private Long id;

    @Inject
    private SecurityContext context;

    @Inject
    private JobRepository jobRepository;

    @Inject
    JobFieldRepository jobFieldRepo;

    @Inject
    RequirementRepository requirementRepo;

    private Job jobDetails;

    @NotEmpty
    private String jobname;
    @NotEmpty
    private String desc;
    @Pattern(regexp = "^[^0-9]+$")
    @NotEmpty
    private String street;
    @NotEmpty
    private String housenumber;
    @NotEmpty
    @Pattern(regexp = "^[^0-9]+$")
    private String city;
    @NotEmpty
    private String postalcode, country;
    @Pattern(regexp = "^[^a-zA-Z]+$")
    private String salary;
    @Enumerated(EnumType.STRING)
    private Sal_Relation relation;
    private List<Requirement> wishedRequirement;
    private List<NeededRequirement> weightedRequirements;
    private JobField jobfield;

    private void init() {
        System.out.println(id);
        jobDetails = jobRepository.find(id);
        jobname = jobDetails.getName();
        desc = jobDetails.getDescription();

        street = jobDetails.getAddress().getStreet();
        housenumber = jobDetails.getAddress().getHousenumber();
        city = jobDetails.getAddress().getCity();
        postalcode = jobDetails.getAddress().getPostalcode();
        country = jobDetails.getAddress().getCountry();

        salary = jobDetails.getSalary().toString();
        relation = jobDetails.getRelation();

        // Get list of wished requirements using the wished requirements
        wishedRequirement = jobDetails.getNeeded().stream().map(NeededRequirement::getRequirement).collect(Collectors.toList());
        weightedRequirements = jobDetails.getNeeded();
        jobfield = jobDetails.getJobfield();
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
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
        weightedRequirements = new ArrayList<>();
        for (Requirement req : this.wishedRequirement) {
            NeededRequirement toInsert = NeededRequirement.builder()
                    .requirement(req)
                    .weight(1)
                    .build();
            weightedRequirements.add(toInsert);
        }
        return weightedRequirements;
    }

    public void setWeightedRequirements(List<NeededRequirement> weightedRequirements) {
        this.weightedRequirements = weightedRequirements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        init();
    }
    

}
