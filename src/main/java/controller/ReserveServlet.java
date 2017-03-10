package controller;

import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import service.Flight;
import service.Manager;
import service.Reservation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

/**
 * Created by behzad on 3/3/17.
 */
@WebServlet("/reserve")
public class ReserveServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/reserve.jsp");
        String  number = request.getParameter("number");
        String  origin = request.getParameter("origin");
        String  dest = request.getParameter("dest");
        String  date = request.getParameter("date");
        String  clas = request.getParameter("clas");
        String  adults = request.getParameter("adults");
        String  children = request.getParameter("children");
        String  infants = request.getParameter("infants");
        Manager manager = Manager.getInstance();

            Flight flight = manager.searchFlight(number, origin, dest, date);

        if(flight==null){
            request.setAttribute("error","Found no matching flights");
            requestDispatcher.forward(request, response);
            return;
        }

        request.setAttribute("number",number);
        request.setAttribute("airline",flight.getAirlineCode());
        if(Objects.equals(origin, "THR"))
            origin = "تهران";
        if(Objects.equals(origin, "MHD"))
            origin = "مشهد";
        request.setAttribute("origin",origin);
        if(Objects.equals(dest, "THR"))
            dest = "تهران";
        if(Objects.equals(dest, "MHD"))
            dest = "مشهد";

        request.setAttribute("dest",dest);

        request.setAttribute("date",date);
        request.setAttribute("clas",clas);
        String dtime = flight.getdTime().substring(0,2) + ":" +flight.getdTime().substring(2,4);
        request.setAttribute("dtime",dtime);
        request.setAttribute("model",flight.getPlaneModel());
        String atime = flight.getaTime().substring(0,2) + ":" +flight.getaTime().substring(2,4);
        request.setAttribute("atime",atime);


        ArrayList<Flight.Seat> seats = flight.getSeats();
        Flight.Seat seat = null;
        for(int i = 0 ; i < seats.size() ; i++){
            if(Objects.equals(clas, seats.get(i).getClassName())) {
                seat = seats.get(i);
            }
        }
        assert seat != null;
        request.setAttribute("adults",adults);
        request.setAttribute("aprice",seat.getAdultPrice());
        request.setAttribute("children",children);
        request.setAttribute("cprice",seat.getChildPrice());
        request.setAttribute("infants",infants);
        request.setAttribute("iprice",seat.getInfantPrice());

        requestDispatcher.forward(request, response);
    }


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

        PrintWriter out = response.getWriter();

        String res = Manager.getInstance().makeReservation(crq);



        String[] p = new String[2];
        String[] tp = res.split("\\s+");
        p[1] = tp[0];
        ClientFinalizeQuery cfq = new ClientFinalizeQuery(p);
        res = Manager.getInstance().finalizeReservation(cfq);

        Reservation reservation = Manager.getInstance().findReserveByToken(tp[0]);

//        out.print(res);

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
