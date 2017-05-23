package service.authentication;

/**
 * Created by behzad on 5/19/17.
 */

import org.apache.log4j.Logger;

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
import service.common.DBQuery;

@Path("/login")
public class Authentication {
    final static String KEY = "behzad";
    final static Logger logger = Logger.getLogger(Authentication.class);

    private DBQuery query = new DBQuery();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

//    public String returnVersion() {
//        return "JwtSecurityExample Status is OK...";
//    }
    public AuthResponse authenticateCredentials(AuthCredentials authCredentials)
            throws Exception {
        String username = authCredentials.getUsername();
        String password = authCredentials.getPassword();

        AuthResponse response = new AuthResponse();
        String role;
        try {
            role = validateUser(username, password);
        }catch (Exception e){
            response.setStatus("failed");
            return response;
        }

        response.setStatus("success");
        String compactJws =  createJWT(role);
        response.setToken(compactJws);
        logger.debug("JWS: " + compactJws);
        return response;
    }

    private String validateUser(String username, String password) {
        try {
            Connection connection = query.setupDB();
            ResultSet rs = query.searchForUser(connection, username, password);
            if (rs.next()) {
                return rs.getString("role");
            } else {
                return "user";
                // TODO: 5/22/17 throw auth failure
            }
        } catch (SQLException | ClassNotFoundException e) {
            return "user";
            // TODO: 5/22/17 throw auth failure
        }

//        if(Objects.equals(username, "admin") && Objects.equals(password, "admin")) {
//            return "Admin";
//        }
//        else if (Objects.equals(username, "client") && Objects.equals(password, "client")){
//            return "Client";
//        }
//        else
//            return "User";
//            throw AuthFailure();
    }

    private String createJWT(String role) {
        return Jwts.builder()
                .setSubject(role)
                .setId("1")
                .setHeaderParam("key","Value")
                .setHeaderParam("name","12345")
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

}