package de.hsos.kbse.jobboerse.entity.shared;

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
public class Benefit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String name;
    private String description;

    public static class Builder {

        private String name;
        private String description;

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

        public Benefit build() {
            return new Benefit(name, description);
        }
    }

    public Benefit() {
    }

    public static Benefit.Builder builder() {
        return new Benefit.Builder();
    }

    private Benefit(final String name, final String description) {
        this.name = name;
        this.description = description;
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
        if (!(object instanceof Benefit)) {
            return false;
        }
        Benefit other = (Benefit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.Benefit[ id=" + id + " ]";
    }
    
}
