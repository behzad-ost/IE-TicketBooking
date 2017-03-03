package controller;

import service.Flight;
import service.Manager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private Manager manager ;
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
        manager = Manager.getInstance();

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
        Enumeration params = request.getParameterNames();
        PrintWriter out = response.getWriter();
        out.println(request.getParameter("adultsnames"));
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();

            out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName) + request.getParameter(paramName).length());
        }

    }
}
