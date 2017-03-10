package controller;

import org.apache.log4j.Logger;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import service.Manager;
import service.Reservation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by behzad on 3/10/17.
 */
@WebServlet("/tickets")
public class FinalizeServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(ReserveServlet.class);
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/tickets.jsp");
        response.setContentType("text/html; charset=UTF-8;");

        String[] params = new String[10];
        params[1] = request.getParameter("origin");
        params[2] = request.getParameter("dest");
        params[3] = request.getParameter("date");
        params[4] = request.getParameter("airline");
        params[5] = request.getParameter("number");
        params[6] = request.getParameter("clas");
        params[7] = request.getParameter("adults");
        params[8] = request.getParameter("children");
        params[9] = request.getParameter("infants");
        ClientReserveQuery crq = new ClientReserveQuery(params);
        Enumeration paramNames = request.getParameterNames();

        addPersons(request ,paramNames, crq);

        String res = Manager.getInstance().makeReservation(crq);

        String[] p = new String[2];
        String[] tp = res.split("\\s+");
        logger.info("RES " + Manager.getInstance().getCurrentId()+ " " + tp[0] + " " + params[7] + " " + params[8] +" " + params[9]);
        p[1] = tp[0];
        ClientFinalizeQuery cfq = new ClientFinalizeQuery(p);
        Manager.getInstance().finalizeReservation(cfq);
        Reservation reservation = Manager.getInstance().findReserveByToken(tp[0]);
        logger.info("FINRES " + reservation.getToken()+ " " + reservation.getId() + " " + reservation.getTotalPrice());

        request.setAttribute("reservation",reservation);
        request.setAttribute("date",params[3]);

        requestDispatcher.forward(request, response);
    }

    private void addPersons(HttpServletRequest request, Enumeration paramNames, ClientReserveQuery crq) {
        for (int i = 0; i < crq.adults; i++) {
            String[] person = new String[3];
            String paramName;
            paramName = (String)paramNames.nextElement();
            paramName = (String)paramNames.nextElement();
            person[0] = request.getParameter(paramName);
            paramName = (String)paramNames.nextElement();
            person[1] = request.getParameter(paramName);
            paramName = (String)paramNames.nextElement();
            person[2] = request.getParameter(paramName);
            crq.addPerson(person, "adult");
        }
        for (int i = 0; i < crq.childs; i++) {
            String[] person = new String[3];
            String paramName;
            paramName = (String)paramNames.nextElement();
            paramName = (String)paramNames.nextElement();
            person[0] = request.getParameter(paramName);
            paramName = (String)paramNames.nextElement();
            person[1] = request.getParameter(paramName);
            paramName = (String)paramNames.nextElement();
            person[2] = request.getParameter(paramName);
            crq.addPerson(person, "child");
        }
        for (int i = 0; i < crq.infants; i++) {
            String[] person = new String[3];
            String paramName;
            paramName = (String)paramNames.nextElement();
            paramName = (String)paramNames.nextElement();
            person[0] = request.getParameter(paramName);
            paramName = (String)paramNames.nextElement();
            person[1] = request.getParameter(paramName);
            paramName = (String)paramNames.nextElement();
            person[2] = request.getParameter(paramName);
            crq.addPerson(person, "infant");
        }
    }
}
