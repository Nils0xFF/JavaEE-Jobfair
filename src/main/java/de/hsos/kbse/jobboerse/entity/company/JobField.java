package de.hsos.kbse.jobboerse.entity.company;

import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author lennartwoltering
 */
@Vetoed
@Entity
public class JobField implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @Column(unique = true)
    private String name;

    public static class Builder {

        private String name;

        private Builder() {
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public JobField build() {
            return new JobField(name);
        }
    }

    public JobField() {
    }

    public static JobField.Builder builder() {
        return new JobField.Builder();
    }

    private JobField(final String name) {
        this.name = name;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof JobField)) {
            return false;
        }
        JobField other = (JobField) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.JobField[ id=" + id + " ]";
    }
    
}
