package de.hsos.kbse.jobboerse.entity.shared;

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
public class NeededRequirement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Requirement requirement;
    
    private Integer weight;

    public static class Builder {

        private Requirement requirement;
        private Integer weight;

        private Builder() {
        }

        public Builder requirement(final Requirement value) {
            this.requirement = value;
            return this;
        }

        public Builder weight(final Integer value) {
            this.weight = value;
            return this;
        }

        public NeededRequirement build() {
            return new NeededRequirement(requirement, weight);
        }
    }

    public NeededRequirement() {
    }

    public static NeededRequirement.Builder builder() {
        return new NeededRequirement.Builder();
    }

    private NeededRequirement(final Requirement requirement, final Integer weight) {
        this.requirement = requirement;
        this.weight = weight;
    }
    
    

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
        if (!(object instanceof NeededRequirement)) {
            return false;
        }
        NeededRequirement other = (NeededRequirement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.NeededRequirement[ id=" + id + " ]";
    }
    
}
