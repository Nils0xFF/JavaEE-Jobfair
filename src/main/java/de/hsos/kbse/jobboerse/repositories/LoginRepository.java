/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse.repositories;

import de.hsos.kbse.jobboerse.entity.company.Company;
import de.hsos.kbse.jobboerse.entity.facades.LoginFacade;
import de.hsos.kbse.jobboerse.entity.shared.Login;
import de.hsos.kbse.jobboerse.entity.user.SeekingUser;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author soere
 */

@RequestScoped
public class LoginRepository {
    
    @Inject
    private LoginFacade logins;
    
    public LoginRepository() { }
    
    /*
    Funktionen haben sprechende Bezeichner
    */
    
    public Login create(String email, String password) throws Exception {
        Login login = Login.builder().email(email).password(password).build();
        logins.create(login);
        return logins.findByEmail(email);
    }
    
    public Login find(Long id) throws Exception {
        return logins.find(id);
    }    
    public Login findByEmail(String email) throws Exception {
        return logins.findByEmail(email);
    }
    
    public List<Login> findAll() throws Exception {
        return logins.findAll();
    }
    
    public void update(Long id, Login login) throws Exception {
        Login old = logins.find(id);
        login.setId(old.getId());
        logins.edit(login);
    }
    
    public void update(String email, Login login) throws Exception {
        Login old = logins.findByEmail(email);
        login.setId(old.getId());
        logins.edit(login);
    }
    
    public void update(Long id, String email, String password) throws Exception {
        Login old = logins.find(id);
        old.setEmail(email);
        old.setPassword(password);
        logins.edit(old);
    }
    
    public void update(String email, String sub, String password) throws Exception {
        Login old = logins.findByEmail(email);
        old.setEmail(sub);
        old.setPassword(password);
        logins.edit(old);
    }
    
    public void updateEmail(Long id, String email) throws Exception {
        Login old = logins.find(id);
        old.setEmail(email);
        logins.edit(old);
    }
    
    public void updateName(String email, String sub) throws Exception {
        Login old = logins.findByEmail(email);
        old.setEmail(sub);
        logins.edit(old);
    }
    
    public void updatePassword(Long id, String password) throws Exception {
        Login old = logins.find(id);
        old.setPassword(password);
        logins.edit(old);
    }
    
    public void updatePassword(String email, String password) throws Exception {
        Login old = logins.findByEmail(email);
        old.setPassword(password);
        logins.edit(old);
    }
    
    public void updateGroup(Long id, String group) throws Exception {
        Login old = logins.find(id);
        old.setGroup_name(group);
        logins.edit(old);
    }
    
    public void updateGroup(String email, String group) throws Exception {
        Login old = logins.findByEmail(email);
        old.setGroup_name(group);
        logins.edit(old);
    }
    
    public void updateUser(Long id, SeekingUser user) {
        Login old = logins.find(id);
        old.setSeekingUser(user);
        logins.edit(old);
    }
    
    public void updateUser(String email, SeekingUser user) throws Exception {
        Login old = logins.findByEmail(email);
        old.setSeekingUser(user);
        logins.edit(old);
    }
    
    public void updateCompany(Long id, Company comp) {
        Login old = logins.find(id);
        old.setCompany(comp);
        logins.edit(old);
    }
    
    public void updateCompany(String email, Company comp) throws Exception {
        Login old = logins.findByEmail(email);
        old.setCompany(comp);
        logins.edit(old);
    }
    
    public void delete(Long id) throws Exception {
        logins.remove(logins.find(id));
    }
    
    public void delete(String email) throws Exception {
        logins.remove(logins.findByEmail(email));
    }
}
