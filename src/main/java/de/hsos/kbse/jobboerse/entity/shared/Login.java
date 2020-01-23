/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.shared;

import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
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
public class Login implements Serializable {

    @OneToOne(mappedBy = "login",
            cascade = CascadeType.ALL)
    private SeekingUser seekingUser;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String password;
    private String group_name;
    private String email;

    public static class Builder {

        private SeekingUser seekingUser;
        private String password;
        private String group_name;
        private String email;

        private Builder() {
        }

        public Builder seekingUser(final SeekingUser value) {
            this.seekingUser = value;
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
            return new Login(seekingUser, password, group_name, email);
        }
    }

    public Login() {
    }

    public static Login.Builder builder() {
        return new Login.Builder();
    }

    private Login(final SeekingUser seekingUser, final String password, final String group_name, final String email) {
        this.seekingUser = seekingUser;
        this.password = password;
        this.group_name = group_name;
        this.email = email;
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
