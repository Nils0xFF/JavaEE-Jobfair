/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.entity.shared;

import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author soere
 */
@Vetoed
@Entity
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Lob
    private byte[] data;
    
    private String dataType;

    public static class Builder {

        private byte[] data;
        private String dataType;

        private Builder() {
        }

        public Builder data(final byte[] value) {
            this.data = value;
            return this;
        }

        public Builder dataType(final String value) {
            this.dataType = value;
            return this;
        }

        public Picture build() {
            return new Picture(data, dataType);
        }
    }

    public static Picture.Builder builder() {
        return new Picture.Builder();
    }

    private Picture(final byte[] data, final String dataType) {
        this.data = data;
        this.dataType = dataType;
    }
    
    public Picture(){
        
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public void setData(byte[] data) {
        this.data = data;
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
        if (!(object instanceof Picture)) {
            return false;
        }
        Picture other = (Picture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.kbse.jobboerse.entity.shared.Picture[ id=" + id + " ]";
    }
    
}
