package service.authentication;

/**
 * Created by behzad on 5/22/17.
 */

import io.jsonwebtoken.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AccessFilter implements Filter{
    HashMap<String, String> urlMap = new HashMap<>();

    final static String KEY = "behzad";
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            File fXmlFile = new File("/home/behzad/map.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("url");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    urlMap.put(eElement.getElementsByTagName("url-pattern").item(0).getTextContent(),eElement.getAttribute("role"));
                }
            }
            logger.info("URL Map ready!");
        } catch (Exception e) {
            logger.debug("Error in XML File");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("In The Access Filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURL().toString();
//        logger.info("Request url: " + url);
        String [] urlParts = url.split("/");

        String service = urlParts[urlParts.length-2];
        String endPoint = urlParts[urlParts.length-1];

        logger.info("EndPoint: " + service +" "+ endPoint);
        String[] endPointParts = endPoint.split("\\?");
        endPoint = endPointParts[0];
//        String params = endPointParts[1];

        String token  = httpRequest.getHeader("token");
        String roleAllowed = urlMap.get(service+"/"+endPoint);

        logger.info("Allowed Role: " + roleAllowed);

        if(token == null) {
            ((HttpServletResponse) response).setStatus(401);
            response.getWriter().write("{'error':'please provide a token'}");
            return;
        }
        try {
            Claims tokenBody = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
            logger.info("Token Subject: " + tokenBody.getSubject());
            logger.info("Token ID: " + tokenBody.getId());

            if(Objects.equals(tokenBody.getSubject(), roleAllowed) || roleAllowed==null ){
                httpResponse.addHeader("role",tokenBody.getSubject());
                httpResponse.addHeader("id",tokenBody.getId());
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).setStatus(401);
                response.getWriter().write("{'error':'you do have access to this area'}");
            }
        } catch (MissingClaimException e) {
            ((HttpServletResponse) response).setStatus(401);
            response.getWriter().write("{'error':'please provide a token'}");
        } catch (IncorrectClaimException e) {
            ((HttpServletResponse) response).setStatus(401);
            response.getWriter().write("{'error':'please provide a valid token'}");
        }
    }

    @Override
    public void destroy() {

    }
}
