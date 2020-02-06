/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.shared;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author lennartwoltering
 */
@Vetoed
@Entity
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String password;
    @NotNull
    @Column(nullable = false)
    private String group_name;

    @NotNull
    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @CascadeOnDelete
    @JsonbTransient
    private SeekingUser seekingUser;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @CascadeOnDelete
    @JsonbTransient
    private Company company;

    public static class Builder {

        private SeekingUser seekingUser;
        private String password;
        private String group_name;
        private String email;
        private Company company;

        private Builder() {
        }

        public Builder seekingUser(final SeekingUser value) {
            this.seekingUser = value;
            return this;
        }

        public Builder company(final Company value) {
            this.company = value;
            return this;
        }

        public Builder password(final String value) {
            this.password = value;
            return this;
        }

        public Builder group_name(final String value) {
            this.group_name = value;
            return this;
        }

        public Builder email(final String value) {
            this.email = value;
            return this;
        }

        public Login build() {
            return new Login(seekingUser, company, password, group_name, email);
        }
    }

    @PreRemove
    private void prepareRemove() {
        if (this.company != null) {
            this.company.setLogin(null);
        }

        if (this.seekingUser != null) {

            this.seekingUser.setLogin(null);
            this.seekingUser = null;
        }
    }

    public Login() {
    }

    public static Login.Builder builder() {
        return new Login.Builder();
    }

    private Login(final SeekingUser seekingUser, final Company company, final String password, final String group_name, final String email) {
        this.seekingUser = seekingUser;
        this.company = company;
        this.password = password;
        this.group_name = group_name;
        this.email = email;
    }

    public SeekingUser getSeekingUser() {
        return seekingUser;
    }

    public void setSeekingUser(SeekingUser seekingUser) {
        this.seekingUser = seekingUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
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
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.shared.Login[ id=" + id + " ]";
    }

}
