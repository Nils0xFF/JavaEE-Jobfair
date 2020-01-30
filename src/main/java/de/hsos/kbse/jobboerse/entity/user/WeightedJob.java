/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.user;

import de.hsos.kbse.jobboerse.entity.company.Job;
import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author lennartwoltering
 */
@Vetoed
@Entity
public class WeightedJob implements Serializable, Comparable< WeightedJob >{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private float BenefitPercentage;
    private float RequirementPercentage;
    private float TotalPercentage;
    
    @OneToOne
    private Job job;

    public static class Builder {

        private Job job;
        private float BenefitPercentage;
        private float RequirementPercentage;
        private float TotalPercentage;

        private Builder() {
        }

        public Builder job(final Job value) {
            this.job = value;
            return this;
        }

        public Builder BenefitPercentage(final float value) {
            this.BenefitPercentage = value;
            return this;
        }

        public Builder RequirementPercentage(final float value) {
            this.RequirementPercentage = value;
            return this;
        }

        public Builder TotalPercentage(final float value) {
            this.TotalPercentage = value;
            return this;
        }

        public WeightedJob build() {
            return new WeightedJob(job, BenefitPercentage, RequirementPercentage, TotalPercentage);
        }
    }

    public static WeightedJob.Builder builder() {
        return new WeightedJob.Builder();
    }

    private WeightedJob(final Job job, final float BenefitPercentage, final float RequirementPercentage, final float TotalPercentage) {
        this.job = job;
        this.BenefitPercentage = BenefitPercentage;
        this.RequirementPercentage = RequirementPercentage;
        this.TotalPercentage = TotalPercentage;
    }
    


    public WeightedJob() {
    }


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public float getBenefitPercentage() {
        return BenefitPercentage;
    }

    public void setBenefitPercentage(float BenefitPercentage) {
        this.BenefitPercentage = BenefitPercentage;
    }

    public float getRequirementPercentage() {
        return RequirementPercentage;
    }

    public void setRequirementPercentage(float RequirementPercentage) {
        this.RequirementPercentage = RequirementPercentage;
    }

    public float getTotalPercentage() {
        return TotalPercentage;
    }

    public void setTotalPercentage(float TotalPercentage) {
        this.TotalPercentage = TotalPercentage;
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
    public int compareTo(WeightedJob o) {
        return Float.compare(this.getTotalPercentage(),o.getTotalPercentage()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeightedJob)) {
            return false;
        }
        WeightedJob other = (WeightedJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.user.WeightedJob[ id=" + id + " ]";
    }
    
}
