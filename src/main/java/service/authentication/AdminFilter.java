package service.authentication;

import io.jsonwebtoken.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class AdminFilter implements Filter {
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("In The Admin Filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        Enumeration<String> headerNames = httpRequest.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                System.out.println("Header: " + httpRequest.getHeader(headerNames.nextElement()));
//            }
//        }
        String token  = httpRequest.getHeader("token");
        if(token == null) {
            response.getWriter().write("Please provide a token");
            return;
        }
        try {
            Claims tokenBody =Jwts.parser().setSigningKey("behzad").parseClaimsJws(token).getBody();
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey("behzad")
                    .parseClaimsJws(token);
            logger.debug("Token Subject: " + tokenBody.getSubject());
            if(!Objects.equals(tokenBody.getSubject(), "Admin"))
                response.getWriter().write("!!!Restricted zone!!!");
            else
                chain.doFilter(request, response);
        } catch (MissingClaimException e) {
            logger.debug("NO Claim");
            response.getWriter().write("Please provide a token");
        } catch (IncorrectClaimException e) {
            logger.debug("BAD Claim");
            response.getWriter().write("Please provide a valid token");
        }
    }

    @Override
    public void destroy() {

    }
}