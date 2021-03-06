/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.user;

import de.hsos.kbse.jobboerse.entity.shared.Address;
import de.hsos.kbse.jobboerse.entity.shared.Requirement;
import de.hsos.kbse.jobboerse.enums.Graduation;
import de.hsos.kbse.jobboerse.enums.Salutation;
import de.hsos.kbse.jobboerse.enums.Title;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author soere
 */
@Vetoed
@Entity
public class User_Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Salutation salutation;
    private Title title;
    private String firstname;
    private String lastname;
    @Lob
    private String description;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @CascadeOnDelete
    private Address address;

    @ManyToMany
    private List<Requirement> fullfiledRequirements;

    private String telefon;
    private Graduation grad;

    public static class Builder {

        private String firstname;
        private String lastname;
        private Salutation salutation;
        private Title title;
        private String description;
        private Date birthday;
        private Address address;
        private String telefon;
        private Graduation grad;
        private List<Requirement> fullfilledRequirements;

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

        public Builder salutation(final Salutation value) {
            this.salutation = value;
            return this;
        }

        public Builder title(final Title value) {
            this.title = value;
            return this;
        }

        public Builder description(final String value) {
            this.description = value;
            return this;
        }

        public Builder birthday(final Date value) {
            this.birthday = value;
            return this;
        }

        public Builder address(final Address value) {
            this.address = value;
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

        public Builder fullfilledRequirements(final List<Requirement> value) {
            this.fullfilledRequirements = value;
            return this;
        }

        public User_Profile build() {
            return new User_Profile(salutation, title, firstname, lastname, description, telefon, address, grad, birthday, fullfilledRequirements);

        }
    }

    public User_Profile() {
    }

    public static User_Profile.Builder builder() {
        return new User_Profile.Builder();
    }

    private User_Profile(final Salutation salutation, final Title title, final String firstname, final String lastname, final String description, final String telefon, final Address address, final Graduation grad, final Date birthday, final List<Requirement> fullfilledRequirements) {
        this.salutation = salutation;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.birthday = birthday;
        if (address == null) {
            this.address = new Address();
        } else {
            this.address = address;
        }
        this.telefon = telefon;
        this.grad = grad;
        this.fullfiledRequirements = fullfilledRequirements;
    }

    public List<Requirement> getFullfiledRequirements() {
        return fullfiledRequirements;
    }

    public void setFullfiledRequirements(List<Requirement> fullfiledRequirements) {
        this.fullfiledRequirements = fullfiledRequirements;
    }
    
    public void addRequirement(Requirement req) {
        this.fullfiledRequirements.add(req);
    }
    
    public void removeRequirement(Requirement req) {
        this.fullfiledRequirements.remove(req);
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
