package de.hsos.kbse.jobboerse.entity.shared;

import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
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
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String city;
    
    private String postalcode;
    private String street;
    private String housenumber;
    private String country;

    public static class Builder {

        private String city;
        private String postalcode;
        private String street;
        private String housenumber;
        private String country;

        private Builder() {
        }

        public Builder city(final String value) {
            this.city = value;
            return this;
        }

        public Builder postalcode(final String value) {
            this.postalcode = value;
            return this;
        }

        public Builder street(final String value) {
            this.street = value;
            return this;
        }

        public Builder housenumber(final String value) {
            this.housenumber = value;
            return this;
        }

        public Builder country(final String value) {
            this.country = value;
            return this;
        }

        public Address build() {
            return new Address(city, postalcode, street, housenumber, country);
        }
    }

    public Address() {
    }

    public static Address.Builder builder() {
        return new Address.Builder();
    }

    private Address(final String city, final String postalcode, final String street, final String housenumber, final String country) {
        this.city = city;
        this.postalcode = postalcode;
        this.street = street;
        this.housenumber = housenumber;
        this.country = country;
    }

    
    
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.address[ id=" + id + " ]";
    }
    
}
