package controller;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/search")
public class myServlet extends HttpServlet {
    public static final java.lang.String BOOTSTRAP_HEADER = "<head>\n    <title>Bootstrap Example</title>\n    <meta charset=\"utf-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n</head>\n";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/behzad.jsp");
        requestDispatcher.forward(request, response);

//        String from = request.getParameter("from");
//        String to = request.getParameter("to");
//        String amount = request.getParameter("amount");
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html>" + BOOTSTRAP_HEADER +
//                "<body><span class=\"label label-danger\">" +
//                amount + "$ is transferred from " +
//                from + " to " + to + ".</span>\n" +
//                "</body></html>");
    }
}
