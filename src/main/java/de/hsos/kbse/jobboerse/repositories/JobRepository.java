/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.facades.JobFacade;
import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author soere
 */
@RequestScoped
public class JobRepository {
    
    @Inject
    private JobFacade jf;
    
    public JobRepository() { }
    
    public void create(String name, Sal_Relation relation, Double salary, String description, Address address, Company company, List<NeededRequirement> needed) throws Exception {
        Job job = Job.builder()
                .name(name)
                .relation(relation)
                .salary(salary)
                .description(description)
                .address(address)
                .company(company)
                .needed(needed)
                .build();
        jf.create(job);
    }
    
    public List<Job> findAll() throws Exception {
        return jf.findAll();
    }
    
    public void update(Long id, Job job) throws Exception {
        Job old = jf.find(id);
        job.setId(old.getId());
        job.setCompany(old.getCompany());
        jf.edit(job);
    }
    
    public void update(Long id, String name, Sal_Relation relation, Double salary, String description, Address address, Company company, List<NeededRequirement> needed) throws Exception {
        Job old = jf.find(id);
        old.setName(name);
        old.setRelation(relation);
        old.setSalary(salary);
        old.setDescription(description);
        old.setAddress(address);
        old.setCompany(company);
        old.setNeeded(needed);
        jf.edit(old);
    }
    
    public void updateName(Long id, String name) throws Exception {
        Job old = jf.find(id);
        old.setName(name);
        jf.edit(old);
    }
    
    public void updateRelation(Long id, Sal_Relation relation) throws Exception {
        Job old = jf.find(id);
        old.setRelation(relation);
        jf.edit(old);
    }
    
    public void updateSalary(Long id, Double salary) throws Exception {
        Job old = jf.find(id);
        old.setSalary(salary);
        jf.edit(old);
    }
    
    public void updateDescription(Long id, String desc) throws Exception {
        Job old = jf.find(id);
        old.setDescription(desc);
        jf.edit(old);
    }
    
    public void updateAddress(Long id, Address address) throws Exception {
        Job old = jf.find(id);
        old.setAddress(address);
        jf.edit(old);
    }
    
    public void updateCompany(Long id, Company company) throws Exception {
        Job old = jf.find(id);
        old.setCompany(company);
        jf.edit(old);
    } 
    
    public void updateNeededRequirements(Long id, List<NeededRequirement> needed) throws Exception {
        Job old = jf.find(id);
        old.setNeeded(needed);
        jf.edit(old);
    }
    
    public void addNeededRequirement(Long id, NeededRequirement needed) throws Exception {
        Job old = jf.find(id);
        old.addNeededRequirement(needed);
        jf.edit(old);        
    }
    
    public void removeNeededRequirement(Long id, NeededRequirement needed) throws Exception {
        Job old = jf.find(id);
        old.removeNeededRequirement(needed);
        jf.edit(old);
    }
    
    public List<Job> findJobsByJobField(String name) throws Exception {
        return jf.findJobsByJobField(name);
    }
    
    public void delete(Long id) throws Exception {
        Job found = jf.find(id);
        found.setCompany(null);
        jf.remove(found);
    }
    
    public Job find(Long id) throws Exception {
        return jf.find(id);
    }
    
}
