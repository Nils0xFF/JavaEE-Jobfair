/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.controllers;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author lennartwoltering
 */
@RequestScoped
public class JobCreationController {

    @Inject
    private CompanyRepository companyRepo;
    
    @Inject
    private JobRepository jobRepo;

    private Job job;
    private Address address;

    /**
     * Creates a job Info and saves it in the job field
     * 
     * @param jobname Represents the jobname
     * @param desc Represents the Description of the job
     * @param jobfield Represents the jobfield so Seeking user can find this job
     * @param requirements Represents the qualifications for the job (with weights)
     * @param salary Represents the salary per hour
     * @param relation Represents the SalaryRelation (enum)
     * @return returns a reference to itself
     */
    public JobCreationController createInfo(String jobname, String desc, JobField jobfield, List<NeededRequirement> requirements, Double salary, Sal_Relation relation) {
        job = Job.builder()
                .name(jobname)
                .description(desc)
                .jobField(jobfield)
                .needed(requirements)
                .salary(salary)
                .relation(relation)
                .build();
        return this;
    }

    /**
     * Creates a job address and saves it in the address field
     * @param street Represents the street where the job is located
     * @param housenumber Represents the housenumber where the job is located
     * @param city Represents the city where the job is located
     * @param postalcode Represents the postalcode of the city
     * @param country Represents the country of the city
     * @return returns a reference to itself
     */
    public JobCreationController createAddress(
            String street, String housenumber, String city,
            String postalcode, String country) {
        address = Address.builder()
                .street(street)
                .housenumber(housenumber)
                .city(city)
                .postalcode(postalcode)
                .country(country)
                .build();
        return this;
    }
    
    /**
     * Completes the Creation of a job and saves it to the database
     * @param email the email of the company 
     */
    public void finishCreation(String email){
        job.setAddress(address);
        companyRepo.addJobtoCompany(email, job);
    }
    
    /**
     * Completes the Editing of a job and saves it to the database
     * @param id the id of the job
     */
    public void finishUpdating(Long id){
        job.setAddress(address);
        jobRepo.update(id, job);
    }

}
