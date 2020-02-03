/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.kbse.jobboerse;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author lennartwoltering
 */
@RememberMe(
       cookieMaxAgeSeconds = 3600
)
@CustomFormAuthenticationMechanismDefinition(
  loginToContinue = @LoginToContinue(loginPage = "/pages/public/index.xhtml"))
@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "java:app/JindiJob",
    callerQuery = "select password from login where email = ?",
    groupsQuery = "select group_name from login where email = ?",
    hashAlgorithm = Pbkdf2PasswordHash.class,
    priorityExpression = "30",
    hashAlgorithmParameters = {
        "Pbkdf2PasswordHash.Iterations=3072",
        "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512", 
        "Pbkdf2PasswordHash.SaltSizeBytes=64"
    }
)
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}
