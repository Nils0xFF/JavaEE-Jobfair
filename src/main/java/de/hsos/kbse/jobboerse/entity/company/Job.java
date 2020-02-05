package de.hsos.kbse.jobboerse.entity.company;

import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.enums.Sal_Relation;
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
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Sal_Relation relation;
    
    private String name;
    
    private Double salary;
    
    @Lob
    private String description;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private Address address;
    
    @OneToOne
    private Company company;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<NeededRequirement> needed;
    
    @OneToOne
    private JobField jobfield;

    public static class Builder {

        private Sal_Relation relation;
        private String name;
        private Double salary;
        private String description;
        private Address address;
        private Company company;
        private JobField jobfield;
        private List<NeededRequirement> needed;

        private Builder() {
        }

        public Builder jobField(final JobField value){
            this.jobfield = value;
            return this;
        }
        
        public Builder relation(final Sal_Relation value) {
            this.relation = value;
            return this;
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public Builder salary(final Double value) {
            this.salary = value;
            return this;
        }

        public Builder description(final String value) {
            this.description = value;
            return this;
        }

        public Builder address(final Address value) {
            this.address = value;
            return this;
        }
        
        public Builder company(final Company value) {
            this.company = value;
            return this;
        }

        public Builder needed(final List<NeededRequirement> value) {
            this.needed = value;
            return this;
        }

        public Job build() {
            return new Job(relation, name, salary, description, address, company, needed, jobfield);
        }
    }

    public Job() { }

    public static Job.Builder builder() {
        return new Job.Builder();
    }

    private Job(final Sal_Relation relation, final String name, final Double salary, final String description, final Address address, final Company company, final List<NeededRequirement> needed, final JobField jobfield) {
        this.relation = relation;
        this.name = name;
        this.salary = salary;
        this.description = description;
        this.address = address;
        this.company = company;
        this.needed = needed;
        this.jobfield = jobfield;
    }    
    
    public Sal_Relation getRelation() {
        return relation;
    }

    public void setRelation(Sal_Relation relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<NeededRequirement> getNeeded() {
        return needed;
    }

    public void setNeeded(List<NeededRequirement> needed) {
        this.needed = needed;
    }
    
    public void addNeededRequirement(NeededRequirement needed) {
        this.needed.add(needed);
    }
    
    public void removeNeededRequirement(NeededRequirement needed) {
        this.needed.remove(needed);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobField getJobfield() {
        return jobfield;
    }

    public void setJobfield(JobField jobfield) {
        this.jobfield = jobfield;
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
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.Job[ id=" + id + " ]";
    }
    
}
