/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.facades.CompanyFacade;
import de.hsos.kbse.jobboerse.entity.facades.CompanyProfileFacade;
import de.hsos.kbse.jobboerse.entity.facades.ContactFacade;
import de.hsos.kbse.jobboerse.entity.facades.PictureFacade;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author soere
 */
@RequestScoped
public class CompanyRepository {
    
    @Inject
    private CompanyFacade companyf;
    @Inject
    private CompanyProfileFacade companyprofilef;
    @Inject
    private ContactFacade contactf;
    @Inject
    private PictureFacade picturef;
    
    public CompanyRepository() { }
    
    
}
