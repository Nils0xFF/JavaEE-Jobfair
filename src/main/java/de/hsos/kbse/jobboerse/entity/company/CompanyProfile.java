package de.hsos.kbse.jobboerse.entity.company;

import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Benefit;
import de.hsos.kbse.jobboerse.enums.WorkerCount;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author lennartwoltering
 */
@Vetoed
@Entity
public class CompanyProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    @Lob
    private String description;
    private WorkerCount workercount;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contact;
    @ManyToMany
    private List<Benefit> benefits;

    public static class Builder {

        private String name;
        private String description;
        private WorkerCount workercount;
        private Address address;
        private Contact contact;
        private List<Benefit> benefits;

        private Builder() {
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public Builder description(final String value) {
            this.description = value;
            return this;
        }

        public Builder workercount(final WorkerCount value) {
            this.workercount = value;
            return this;
        }

        public Builder address(final Address value) {
            this.address = value;
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

        public CompanyProfile build() {
            return new CompanyProfile(name, description, workercount, address, contact, benefits);
        }
    }

    public CompanyProfile() {
    }

    public static CompanyProfile.Builder builder() {
        return new CompanyProfile.Builder();
    }

    private CompanyProfile(final String name, final String description, final WorkerCount workercount, final Address address, final Contact contact, final List<Benefit> benefits) {
        this.name = name;
        this.description = description;
        this.workercount = workercount;
        this.address = address;
        this.contact = contact;
        this.benefits = benefits;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkerCount getWorkercount() {
        return workercount;
    }

    public void setWorkercount(WorkerCount workercount) {
        this.workercount = workercount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address adress) {
        this.address = adress;
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
    
    public void addBenefit(Benefit benefit) {
        this.benefits.add(benefit);
    }
    
    public void removeBenefit(Benefit benefit) {
        this.benefits.remove(benefit);
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
        if (!(object instanceof CompanyProfile)) {
            return false;
        }
        CompanyProfile other = (CompanyProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.CompanyProfile[ id=" + id + " ]";
    }
    
}
