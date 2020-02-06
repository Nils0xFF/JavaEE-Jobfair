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
    
    public void finishCreation(String email){
        job.setAddress(address);
        companyRepo.addJobtoCompany(email, job);
    }
    
    public void finishUpdating(Long id){
        job.setAddress(address);
        jobRepo.update(id, job);
    }

}
