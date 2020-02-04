/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.user;

import de.hsos.kbse.jobboerse.entity.company.Job;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author soere
 */
@Vetoed
@Entity
public class SeekingUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    private boolean completed;
    
    @OneToOne
    private Login login;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private User_Profile profile;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private SearchRequest searchrequest; 
    
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private List<Job> favorites;

    public static class Builder {

        private boolean completed;
        private User_Profile profile;

        private Builder() {
        }

        public Builder profile(final User_Profile value) {
            this.profile = value;
            return this;
        }

        public SeekingUser build() {
            return new SeekingUser(false, profile);
        }
    }

    public SeekingUser() {
    }

    public static SeekingUser.Builder builder() {
        return new SeekingUser.Builder();
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private SeekingUser(final boolean completed, final User_Profile profile) {
        this.completed = completed;
        this.favorites = new ArrayList<>();
        this.searchrequest = new SearchRequest();
        if(profile == null){
            this.profile = new User_Profile();
        }else{
            this.profile = profile;
        }
    }

    public SearchRequest getSearchrequest() {
        return searchrequest;
    }

    public void setSearchrequest(SearchRequest searchrequest) {
        this.searchrequest = searchrequest;
    }
    
    public boolean hasCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User_Profile getProfile() {
        return profile;
    }

    public void setProfile(User_Profile profile) {
        this.profile = profile;
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
        if (!(object instanceof SeekingUser)) {
            return false;
        }
        SeekingUser other = (SeekingUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.user.User[ id=" + id + " ]";
    }
    
}
