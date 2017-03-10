package controller;
import query.ClientSearchQuery;
import service.Flight;
import service.Manager;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;


@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    public static final String HEADER = "<head>\n    <title>Results</title>\n   <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n" +
            "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
            "    <link rel=\"stylesheet\" href=\"css/layout.css\">\n" +
            "    <link rel=\"stylesheet\" href=\"css/style3.css\"> \n</head>\n";

    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String[] params = new String[7];
        params[1] = request.getParameter("source-code");
        params[2] = request.getParameter("dest-code");
        params[3] = request.getParameter("date");
        params[4] = request.getParameter("adults");
        params[5] = request.getParameter("children");
        params[6] = request.getParameter("infants");

        Manager manager = Manager.getInstance();

        ClientSearchQuery csq = new ClientSearchQuery(params);

        String res = manager.search(csq);

        ArrayList<Flight> flights = manager.getFlights();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
//        response.setHeader();

        out.println("<html lang=\"fa\">" + HEADER +
                "<body>");
        out.println("<div id=\"container\">\n" +
                "<header>\n" +
                "        <div id=\"companyLogo\" class=\"headerItem\">\n" +
                "            <img alt=\"logo\" src=\"imgs/LogoBlack.png\" class=\"img\">\n" +
                "        </div>\n" +
                "        <div id=\"companyName\" class=\"headerItem\">\n" +
                "         <i>اکبر تیکت</i>\n" +
                "         <i class=\"fa fa-registered\" aria-hidden=\"true\" id=\"registered\"></i></div>\n" +
                "        <div id=\"user\" class=\"dropdown\">\n" +
                "                <div class=\"drop-button\">\n" +
                "                    <i class=\"fa fa-user\" aria-hidden=\"true\" id=\"userLogo\"></i>\n" +
                "                    <i id=\"user-name\">نام کاربر</i>\n" +
                "                </div>\n" +
                "                <div class=\"dropdown-content\">\n" +
                "                    <a href=\"#\">پروفایل</a>\n" +
                "                    <a href=\"#\">تنظیمات</a>\n" +
                "                    <a href=\"#\">خروج</a>                        \n" +
                "                </div>\n" +
                "            </div>\n" +
                "    </header>");
        out.println("<div class=\"container\">\n" +
                "        <section id=\"steps\">\n" +
                "            <i class=\"step hiden\" >جستجوی پرواز</i>\n" +
                "            <i class=\"step fa fa-chevron-left hiden\" ></i>\n" +
                "            <i class=\"step active\" >انتخاب پرواز</i>\n" +
                "            <i class=\"step fa fa-chevron-left active hiden\" ></i>\n" +
                "            <i class=\"step hiden\" >ورود اطلاعات</i>\n" +
                "            <i class=\"step fa fa-chevron-left hiden\" ></i>\n" +
                "            <i class=\"step hiden\" >دریافت بلیت</i>\n" +
                "        </section>");


        out.println("<section id=\"result-main\">\n" +
                "            <div id=\"control-bar\">\n" +
                "                <div id=\"back\">\n" +
                "                    <i class=\"step fa fa-arrow-right\" ></i>\n" +
                "                    <i class=\"step\" >بازگشت به صفحه جستجو</i>\n" +
                "                </div>\n" +
                "                <div id=\"numofflights\">\n <i>" );

        out.println(flights.size());
        out.println("</i>\n<i>پرواز با مشخصات دلخواه شما پیدا شد.  </i>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div id=\"main-grey\">");
        for(int i = 0 ; i < flights.size() ; i++){
            String airlineCode = flights.get(i).getAirlineCode();
            String number = flights.get(i).getNumber();
            String date = flights.get(i).getDate();
            String origin = flights.get(i).getOrigin();
            String dest = flights.get(i).getDestination();
            String departure = flights.get(i).getdTime();
            String arrival = flights.get(i).getaTime();
            String model = flights.get(i).getPlaneModel();
            ArrayList<Flight.Seat> seats = flights.get(i).getSeats();
            for(int j = 0 ; j < seats.size() ; j++){
                out.println("<div class=\"row ticket\">\n" +
                        "                    <div class=\"col-md-3 class-plane\">\n" +
                        "                        <div class=\"info\">\n");
                out.println("<i>"+airlineCode + number + "</i> </div>");
                out.println("<div class=\"info\">\n" +
                        "                            <i class=\"step fa fa-calendar-o\" ></i>\n" );
                out.println("<i>"+date+"</i>\n</div> </div>");
                out.println("<div class=\"col-md-6 from-to\" >\n" +
                        "                        <div class=\"src-dest\">");
                String src = origin;
                if(Objects.equals(origin, "THR"))
                    src = "تهران";
                else if(Objects.equals(origin, "MHD"))
                    src = "مشهد";

                String des = origin;
                if(Objects.equals(dest, "THR"))
                    des = "تهران";
                else if(Objects.equals(dest, "MHD"))
                    des = "مشهد";


                out.println("<i class=\"step\" >"+src + " " +departure.substring(0,2)+":"+departure.substring(2,4) +"</i>");
                out.println("<i class=\"step fa fa-angle-double-left\" ></i>");
                out.println("<i class=\"step\" >"+des + " " +departure.substring(0,2)+":"+arrival.substring(2,4)+"</i></div>");
                out.println("<div class=\"plane-class\">\n" +
                        "                            <i class=\"fa fa-plane\" ></i>");
                out.println("<i class=\"info\" >"+model+"</i>");
                out.println("&nbsp;\n" +
                        "                            &nbsp;\n" +
                        "                            <i class=\"fa fa-suitcase\" ></i>");

                out.println("<i class=\"info\" >"+seats.get(j).getAvailable()+"</i>");
                out.println("<i class=\"info\" > صندلی باقیمانده کلاس " +seats.get(j).getClassName()+"</i></div>\n" +
                        "                    </div>");
                int tPrice = csq.adults * seats.get(j).getAdultPrice() +
                        csq.childs * seats.get(j).getChildPrice() +
                        csq.infants * seats.get(j).getInfantPrice();
                out.println("<a href=\"/ali/reserve?number="+number+"&origin="+origin+"&dest="+dest+"&date="+date+"&clas="+seats.get(j).getClassName()+"&adults="+params[4]+"&children="+params[5]+"&infants="+params[6]+"\"> <div class=\"col-md-3 price\" >\n" +
                        "                        <div class=\"info\">\n" +
                        "                            <i >"+tPrice+"ریال </i>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"info\">\n" +
                        "                            <i >رزرو آنلاین</i>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div></a>");
            }
        }
//        out.println(res);
        out.println("</section>\n" +
                "    </div>\n" +
                "    <div id=\"footer\">\n" +
                "        <p>سیدعلی اخوانی - بهزاد اوسط | دانشکده\u200Cی فنی دانشگاه تهران</p>\n" +
                "    </div>\n" +
                "</div>");
        out.println( "</body></html>");
    }
}