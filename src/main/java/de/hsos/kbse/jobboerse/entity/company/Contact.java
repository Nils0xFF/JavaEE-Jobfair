package de.hsos.kbse.jobboerse.entity.company;

import de.hsos.kbse.jobboerse.enums.Title;
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
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Title title;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    public static class Builder {

        private Title title;
        private String firstname;
        private String lastname;
        private String email;
        private String phone;

        private Builder() {
        }

        public Builder title(final Title value) {
            this.title = value;
            return this;
        }

        public Builder firstname(final String value) {
            this.firstname = value;
            return this;
        }

        public Builder lastname(final String value) {
            this.lastname = value;
            return this;
        }

        public Builder email(final String value) {
            this.email = value;
            return this;
        }

        public Builder phone(final String value) {
            this.phone = value;
            return this;
        }

        public Contact build() {
            return new Contact(title, firstname, lastname, email, phone);
        }
    }

    public Contact() {
    }

    public static Contact.Builder builder() {
        return new Contact.Builder();
    }

    private Contact(final Title title, final String firstname, final String lastname, final String email, final String phone) {
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.Contact[ id=" + id + " ]";
    }
    
}
