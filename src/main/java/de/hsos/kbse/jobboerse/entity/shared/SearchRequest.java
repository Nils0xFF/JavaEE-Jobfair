/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.shared;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.company.JobField;
import de.hsos.kbse.jobboerse.entity.user.WeightedJob;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    
    @OneToMany()
    private List<WeightedJob> foundJobs;
    
    @ManyToMany
    private List<Benefit> benefits;
    
    @ManyToMany
    private List<Requirement> requirements;
    
    @OneToOne
    private JobField jobfield;

    public static class Builder {

        private List<WeightedJob> foundJobs;
        private List<Benefit> benefits;
        private List<Requirement> requirements;
        private JobField jobField;

        private Builder() {
        }

        public Builder foundJobs(final List<WeightedJob> value) {
            this.foundJobs = value;
            return this;
        }

        public Builder benefits(final List<Benefit> value) {
            this.benefits = value;
            return this;
        }
        
        public Builder jobField(final JobField value) {
            this.jobField = value;
            return this;
        }

        public Builder requirements(final List<Requirement> value) {
            this.requirements = value;
            return this;
        }

        public SearchRequest build() {
            return new SearchRequest(foundJobs, benefits, requirements, jobField);
        }
    }

    public SearchRequest() {
    }

    public static SearchRequest.Builder builder() {
        return new SearchRequest.Builder();
    }

    private SearchRequest(final List<WeightedJob> foundJobs, final List<Benefit> benefits, final List<Requirement> requirements, final JobField jobfield) {
        this.foundJobs = foundJobs;
        this.benefits = benefits;
        this.requirements = requirements;
        this.jobfield = jobfield;
    }

    public JobField getJobfield() {
        return jobfield;
    }

    public void setJobfield(JobField jobfield) {
        this.jobfield = jobfield;
    }
    
    public List<WeightedJob> getFoundJobs() {
        return foundJobs;
    }

    public void setFoundJobs(List<WeightedJob> foundJobs) {
        this.foundJobs = foundJobs;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
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
