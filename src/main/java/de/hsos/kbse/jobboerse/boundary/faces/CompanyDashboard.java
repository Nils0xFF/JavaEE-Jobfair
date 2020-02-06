/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.boundary.faces;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 *
 * @author lennartwoltering
 */
@Named("CompanyDashboard")
@RequestScoped
public class CompanyDashboard implements Serializable {

    @Inject
    private CompanyRepository companyRepo;

    @Inject
    private JobRepository jobRepo;

    @Inject
    private SecurityContext context;

    @Transactional
    public void deleteJob(Long id) {
        companyRepo.removeJobFromCompany(context.getCallerPrincipal().getName(), id);
        try{
            reload();
        }catch(IOException exe){
            
        }
    }

    public void createJob(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/createJob");
        } catch (IOException ex) {
            Logger.getLogger(CompanyDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public List<Job> getAllJobs() {
        return companyRepo.findJobsByCompany(context.getCallerPrincipal().getName());
    }
    
    public String requirementToString(Job job){
        return job.getNeeded().stream().map(req -> req.getRequirement().getName() + " (" + req.getWeight() + ")" ).collect(Collectors.joining(", "));
    }

}
