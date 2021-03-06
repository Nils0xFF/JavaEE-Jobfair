/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.shared;

import de.hsos.kbse.jobboerse.entity.company.JobField;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author soere
 */
@Vetoed
@Entity
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToMany
    private List<Benefit> wishedBenefits;
    
    @ManyToMany
    private List<JobField> jobfield;

    public static class Builder {

        private List<Benefit> benefits;
        private List<Requirement> requirements;
        private List<JobField> jobField;

        private Builder() {
        }

        public Builder benefits(final List<Benefit> value) {
            this.benefits = value;
            return this;
        }
        
        public Builder jobField(final List<JobField> value) {
            this.jobField = value;
            return this;
        }

        public Builder requirements(final List<Requirement> value) {
            this.requirements = value;
            return this;
        }

        public SearchRequest build() {
            return new SearchRequest(benefits, requirements, jobField);
        }
    }

    public SearchRequest() {
    }

    public static SearchRequest.Builder builder() {
        return new SearchRequest.Builder();
    }

    private SearchRequest(final List<Benefit> benefits, final List<Requirement> requirements, final List<JobField> jobfield) {
        this.wishedBenefits = benefits;
        this.jobfield = jobfield;
    }

    public List<JobField> getJobfield() {
        return jobfield;
    }

    public void setJobfield(List<JobField> jobfield) {
        this.jobfield = jobfield;
    }
    
    public void addJobField(JobField field) {
        this.jobfield.add(field);
    }
    
    public void removeJobfield(JobField field) {
        this.jobfield.remove(field);
    }
    
    public List<Benefit> getWishedBenefits() {
        return wishedBenefits;
    }

    public void setWishedBenefits(List<Benefit> wishedBenefits) {
        this.wishedBenefits = wishedBenefits;
    }
    
    public void addWishedBenefit(Benefit benefit) {
        this.wishedBenefits.add(benefit);
    }
    
    public void removeWishedBenefit(Benefit benefit) {
        this.wishedBenefits.remove(benefit);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SearchRequest)) {
            return false;
        }
        SearchRequest other = (SearchRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.shared.SearchRequest[ id=" + id + " ]";
    }
    
}
