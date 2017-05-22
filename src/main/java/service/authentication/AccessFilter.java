package service.authentication;

/**
 * Created by behzad on 5/22/17.
 */

import io.jsonwebtoken.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AccessFilter implements Filter{
    final static String KEY = "behzad";
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("In The Access Filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token  = httpRequest.getHeader("token");
        if(token == null) {
            response.getWriter().write("Please provide a token");
            return;
        }
        try {
            Claims tokenBody = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();

            logger.debug("Token Subject: " + tokenBody.getSubject());
            if(Objects.equals(tokenBody.getSubject(), "Admin"))
//                response.set
                chain.doFilter(request, response);
            else {

            }
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
