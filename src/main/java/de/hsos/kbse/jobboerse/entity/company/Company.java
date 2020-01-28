package de.hsos.kbse.jobboerse.entity.company;

import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author lennartwoltering
 */
@Vetoed
@Entity
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Job> jobs;
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyProfile profile;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contact;
    @ManyToMany
    private List<Benefit> benefits;


    public static class Builder {

        private List<Job> jobs;
        private CompanyProfile profile;
        private Contact contact;
        private List<Benefit> benefits;

        private Builder() {
        }

        public Builder jobs(final List<Job> value) {
            this.jobs = value;
            return this;
        }

        public Builder profile(final CompanyProfile value) {
            this.profile = value;
            return this;
        }

        public Builder contact(final Contact value) {
            this.contact = value;
            return this;
        }

        public Builder benefits(final List<Benefit> value) {
            this.benefits = value;
            return this;
        }

        public Company build() {
            return new Company(jobs, profile, contact, benefits);
        }
    }

    public Company() {
    }

    public static Company.Builder builder() {
        return new Company.Builder();
    }

    private Company(final List<Job> jobs, final CompanyProfile profile, final Contact contact, final List<Benefit> benefits) {
        this.jobs = jobs;
        this.profile = profile;
        this.contact = contact;
        this.benefits = benefits;
    }

    
    
    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public CompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(CompanyProfile profile) {
        this.profile = profile;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
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
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.company.Company[ id=" + id + " ]";
    }
    
}
