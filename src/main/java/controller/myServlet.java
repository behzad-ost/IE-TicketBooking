package controller;
import query.ClientSearchQuery;
import service.Manager;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/search")
public class myServlet extends HttpServlet {
    public static final java.lang.String HEADER = "<head>\n    <title>نتایج جستجو</title>\n    <meta charset=\"utf-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> \n</head>\n";
    public static final java.lang.String BOOTSTRAP = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
//        RequestDispatcher requestDispatcher;
//        requestDispatcher = request.getRequestDispatcher("/behzad.jsp");
//        requestDispatcher.forward(request, response);

        String[] params = new String[7];
        params[1] = request.getParameter("source-code");
        params[2] = request.getParameter("dest-code");
        params[3] = request.getParameter("date");
        params[4] = request.getParameter("adults");
        params[5] = request.getParameter("children");
        params[6] = request.getParameter("infants");

        Manager manager = new Manager();
        ClientSearchQuery csq = new ClientSearchQuery(params);


        String res = manager.search(csq);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html lang=\"fa\" >" + HEADER +
                "<body>" +
                res+
                "</body></html>");
    }
}
