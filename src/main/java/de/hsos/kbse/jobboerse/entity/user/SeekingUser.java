/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.user;

import de.hsos.kbse.jobboerse.entity.shared.SearchRequest;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
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
public class SeekingUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    private boolean firstVisit;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private User_Profile profile;
    
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval=true)
    private SearchRequest searchrequest;
    //Login?

    public static class Builder {

        private boolean firstVisit;
        private User_Profile profile;

        private Builder() {
        }

        public Builder profile(final User_Profile value) {
            this.profile = value;
            return this;
        }

        public SeekingUser build() {
            return new SeekingUser(true, profile);
        }
    }

    public SeekingUser() {
    }

    public static SeekingUser.Builder builder() {
        return new SeekingUser.Builder();
    }

    private SeekingUser(final boolean firstVisit, final User_Profile profile) {
        this.firstVisit = firstVisit;
        this.profile = profile;
    }

    public SearchRequest getSearchrequest() {
        return searchrequest;
    }

    public void setSearchrequest(SearchRequest searchrequest) {
        this.searchrequest = searchrequest;
    }
    
    public boolean isFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(boolean firstVisit) {
        this.firstVisit = firstVisit;
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
