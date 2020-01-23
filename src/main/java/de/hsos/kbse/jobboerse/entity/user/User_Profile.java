/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.user;

import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.enums.Graduation;
import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author soere
 */
@Vetoed
@Entity
public class User_Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String firstname;
    private String lastname;
    private String description;
    @OneToOne
    private Address address;
    private String email;
    private String telefon;
    private Graduation grad;

    public static class Builder {

        private String firstname;
        private String lastname;
        private String description;
        private Address address;
        private String email;
        private String telefon;
        private Graduation grad;

        private Builder() {
        }

        public Builder firstname(final String value) {
            this.firstname = value;
            return this;
        }

        public Builder lastname(final String value) {
            this.lastname = value;
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

        public Builder email(final String value) {
            this.email = value;
            return this;
        }

        public Builder telefon(final String value) {
            this.telefon = value;
            return this;
        }

        public Builder grad(final Graduation value) {
            this.grad = value;
            return this;
        }

        public User_Profile build() {
            return new User_Profile(firstname, lastname, description, address, email, telefon, grad);
        }
    }

    public User_Profile() {
    }

    public static User_Profile.Builder builder() {
        return new User_Profile.Builder();
    }

    private User_Profile(final String firstname, final String lastname, final String description, final Address address, final String email, final String telefon, final Graduation grad) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.address = address;
        this.email = email;
        this.telefon = telefon;
        this.grad = grad;
    }
    
    
    
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Graduation getGrad() {
        return grad;
    }

    public void setGrad(Graduation grad) {
        this.grad = grad;
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
        if (!(object instanceof User_Profile)) {
            return false;
        }
        User_Profile other = (User_Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.user.User_Profile[ id=" + id + " ]";
    }
    
}
