/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.controllers;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.repositories.CompanyRepository;
import de.hsos.kbse.jobboerse.repositories.JobRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author lennartwoltering
 */
@Named("ImageService")
@ApplicationScoped
public class ImageService {

    @Inject
    private CompanyRepository companyRepo;

    @Inject
    private JobRepository jobRepo;

    /**
     * Searches for a CompanyImage based on the JobID
     * Needs a jobID as Requestparameter 
     *
     * @return A StreamedContent with the Image
     * @throws IOException
     */
    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String JobID = context.getExternalContext().getRequestParameterMap().get("jobID");
            Company company = jobRepo.find(Long.parseLong(JobID)).getCompany();
            if (company.getProfile().getProfilePicture() != null) {
                return new DefaultStreamedContent(new ByteArrayInputStream(company.getProfile().getProfilePicture().getData()), company.getProfile().getProfilePicture().getDataType());
            }
            return new DefaultStreamedContent();
        }
    }

    /**
     * Searches for a CompanyImage based on the email
     * Needs a email as Requestparameter 
     *
     * @return A StreamedContent with the Image
     * @throws IOException
     */
    public StreamedContent getImagefromEmail() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String email = context.getExternalContext().getRequestParameterMap().get("email");
            Company company = companyRepo.getCompanyByEmail(email);
            if (company.getProfile().getProfilePicture() != null) {
                return new DefaultStreamedContent(new ByteArrayInputStream(company.getProfile().getProfilePicture().getData()), company.getProfile().getProfilePicture().getDataType());
            }
        }
        return new DefaultStreamedContent();

    }


}
