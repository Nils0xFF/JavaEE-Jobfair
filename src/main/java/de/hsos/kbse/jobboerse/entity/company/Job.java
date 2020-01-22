package de.hsos.kbse.jobboerse.entity.company;

import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.NeededRequirement;
import de.hsos.kbse.jobboerse.enums.SAL_Relation;
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
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    private SAL_Relation relation;
    
    private String name;
    
    private Double salary;
    
    private String description;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private Address address;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private Contact contact;
    
    @ManyToMany
    private List<NeededRequirement> needed;

    public static class Builder {

        private SAL_Relation relation;
        private String name;
        private Double salary;
        private String description;
        private Address address;
        private Contact contact;
        private List<NeededRequirement> needed;

        private Builder() {
        }

        public Builder relation(final SAL_Relation value) {
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

        public Builder contact(final Contact value) {
            this.contact = value;
            return this;
        }

        public Builder needed(final List<NeededRequirement> value) {
            this.needed = value;
            return this;
        }

        public Job build() {
            return new Job(relation, name, salary, description, address, contact, needed);
        }
    }

    public Job() {
    }

    public static Job.Builder builder() {
        return new Job.Builder();
    }

    private Job(final SAL_Relation relation, final String name, final Double salary, final String description, final Address address, final Contact contact, final List<NeededRequirement> needed) {
        this.relation = relation;
        this.name = name;
        this.salary = salary;
        this.description = description;
        this.address = address;
        this.contact = contact;
        this.needed = needed;
    }

    
    
    public SAL_Relation getRelation() {
        return relation;
    }

    public void setRelation(SAL_Relation relation) {
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
