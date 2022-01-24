package com.epam.esm.service.util;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.exception.BadCredentialsException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The KeycloakUtil class.
 */
@Component
public class KeycloakUtil {
    private static final String USER_ID_ATTRIBUTE = "userId";

    @Value("${keycloak.auth-server-url}")
    private String authenticationUrl;

    @Value("${keycloak.admin-realm}")
    private String adminRealm;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    @Value("${keycloak.admin-client-id}")
    private String adminClientId;

    @Value("${keycloak.application-realm}")
    private String applicationRealm;

    @Value("${keycloak.application-client-id}")
    private String clientId;

    @Value("${keycloak.application-client-secret}")
    private String clientSecret;

    private Keycloak adminKeycloak;

    @PostConstruct
    private void init() {
        adminKeycloak = Keycloak.getInstance(authenticationUrl, adminRealm, adminUsername, adminPassword, adminClientId);
    }

    /**
     * Gets access token.
     *
     * @param username the username
     * @param password the password
     * @return the access token
     */
    public String getAccessToken(String username, String password) throws BadCredentialsException {
        Keycloak userKeycloak = Keycloak.getInstance(authenticationUrl, applicationRealm, username, password, clientId);
        try {
            return userKeycloak.tokenManager().getAccessTokenString();
        } catch (NotAuthorizedException e) {
            throw new BadCredentialsException();
        }
    }

    /**
     * Add user to keycloak boolean.
     *
     * @param user     the user
     * @param password the password
     * @return true if user added, false if not
     */
    public boolean addUserToKeycloak(User user, String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(Stream.of(credential).collect(Collectors.toList()));
        userRepresentation.singleAttribute(USER_ID_ATTRIBUTE, String.valueOf(user.getId()));
        Response response = adminKeycloak
                .realm(applicationRealm)
                .users()
                .create(userRepresentation);
        return response.getStatus() == HttpStatus.CREATED.value();
    }

    /**
     * Is user exists.
     *
     * @param username the username
     * @return true if exists, false if not
     */
    public boolean isUserExists(String username) {
        return !adminKeycloak.realm(applicationRealm).users().search(username).isEmpty();
    }
}
