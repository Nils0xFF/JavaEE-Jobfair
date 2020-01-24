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
    
    @OneToOne
    private Job job;
    private float percentage;

    public static class Builder {

        private Job job;
        private float percentage;

        private Builder() {
        }

        public Builder job(final Job value) {
            this.job = value;
            return this;
        }

        public Builder percentage(final float value) {
            this.percentage = value;
            return this;
        }

        public WeightedJob build() {
            return new WeightedJob(job, percentage);
        }
    }

    public WeightedJob() {
    }

    public static WeightedJob.Builder builder() {
        return new WeightedJob.Builder();
    }

    private WeightedJob(final Job job, final float percentage) {
        this.job = job;
        this.percentage = percentage;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
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
        return Float.compare(this.getPercentage(),o.getPercentage()); //To change body of generated methods, choose Tools | Templates.
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
