package service.authentication;

/**
 * Created by behzad on 5/19/17.
 */

import org.apache.log4j.Logger;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Objects;

import io.jsonwebtoken.impl.crypto.MacProvider;
import org.hsqldb.rights.User;
import service.common.DBQuery;

@Path("/login")
public class Authentication {
    final static String KEY = "behzad";
    final static Logger logger = Logger.getLogger(Authentication.class);


    private DBQuery query = new DBQuery();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AuthResponse authenticateCredentials(AuthCredentials authCredentials) {
        logger.debug("Logging In ...");
        String username = authCredentials.getUsername();
        String password = authCredentials.getPassword();

        AuthResponse response = new AuthResponse();
        UserInfo userInfo;
        userInfo = validateUser(username, password);
        if(userInfo == null) {
            response.setStatus("failed");
            return response;
        }
        response.setStatus("success");
        String compactJws =  createJWT(userInfo.getRole(),userInfo.getId());
        response.setToken(compactJws);
        logger.debug("JWS: " + compactJws);
        return response;
    }

    private UserInfo validateUser(String username, String password) {
        logger.debug("Validation: " + username + password);
        try {
            Connection connection = query.setupDB();
            ResultSet rs = query.searchForUser(connection, username, password);
            if (rs.next()) {
                UserInfo userInfo = new UserInfo(rs.getString("uid"),rs.getString("role"));
                return userInfo;
            } else {
                logger.debug("Not Found!");
                return null;
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }

    private String createJWT(String role, String id) {
        return Jwts.builder()
                .setSubject(role)
                .setId(id)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }
}