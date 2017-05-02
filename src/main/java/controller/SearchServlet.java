package controller;
import org.apache.log4j.Logger;
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

    final static Logger logger = Logger.getLogger(SearchServlet.class);

    public static final String HEADER = "<head>\n    <title>Results</title>\n   <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
            "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n" +
            "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
            "    <link rel=\"stylesheet\" href=\"css/layout.css\">\n" +
            "    <link rel=\"stylesheet\" href=\"css/result.css\"> +" +
            " <script src=\"js/result-v1.js\"></script> " +
            " \n</head>\n";

    public void doPost(HttpServletRequest request,
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

        logger.info("SRCH " + csq.originCode + " " + csq.destCode + " " + csq.date);

        String res = manager.search(csq);

        ArrayList<Flight> flights = manager.getFlights();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
//        response.setHeader();
        int available = 0;
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
                "                <a href=\"/ali/\">" +
                "                    <i class=\"step fa fa-arrow-right\" ></i>\n" +
                "                    <i class=\"step\" >بازگشت به صفحه جستجو</i>\n" +
                "                </div></a>\n" +
                "                <div id=\"numofflights\">\n <i>" );

        out.println(flights.size());
        out.println("</i>\n<i>پرواز با مشخصات دلخواه شما پیدا شد.  </i>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div id=\"main-grey\">");

        out.println("                <div id=\"sorts\">\n" +
                "                    <select class=\"sort-btn\" onchange=\"\" id=\"seat-classes\">\n" +
                "                        <option>همه</option>\n" +
                "                        <option>Y</option>\n" +
                "                        <option>M</option>\n" +
                "                        <option>F</option>\n" +
                "                        <option>J</option>\n" +
                "                    </select>\n" +
                "                    \n" +
                "                    <select id=\"sort-type\" class=\"sort-btn\" onchange=\"\">\n" +
                "                        <option>صعودی</option>\n" +
                "                        <option>نزولی</option>\n" +
                "                    </select>\n" +
                "                    \n" +
                "                    <select id=\"airline-codes\" class=\"sort-btn\">\n" +
                "                        <option>همه</option>\n" +
                "                        <option>IR</option>\n" +
                "                        <option>W5</option>\n" +
                "                    </select>\n" +
                "                    <button name=\"submit\" class=\"sort-btn\" onclick=\"filter()\">فیلتر کردن</button>\n" +
                "                </div>");
        out.println("<div id=\"all-tickets\">\n");
        for(int i = 0 ; i < flights.size() ; i++){
            String airlineCode = flights.get(i).getAirlineCode();
            String number = flights.get(i).getNumber();
            logger.debug("Must Be Persian: " + convertNumToPersian(number));
            String date = flights.get(i).getDate();
            String newDate = "";
            if (date.equals("05Feb")) {
                newDate = "۱۷ بهمن";
            } else if (date.equals("06Feb")) {
                newDate = "۱۸ بهمن";
            } else if (date.equals("04Feb")) {
                newDate = "۱۶ بهمن";
            } else {
                newDate = date;
            }
            // TODO: 3/10/17 Create Date Changer to Persian Calendar
            String origin = flights.get(i).getOrigin();
            String dest = flights.get(i).getDestination();
            String departure = flights.get(i).getdTime();
//            departure = convertNumToPersian(departure);
            String arrival = flights.get(i).getaTime();
//            arrival = convertNumToPersian(arrival);
            String model = flights.get(i).getPlaneModel();
            ArrayList<Flight.Seat> seats = flights.get(i).getSeats();
            for(int j = 0 ; j < seats.size() ; j++){
                if(seats.get(j).getAvailable() - (Integer.parseInt(params[4])+Integer.parseInt(params[5])+ Integer.parseInt(params[6]))>-1){
                    available++;
                    out.println("<div class=\"row ticket\" name=\"search-result\">\n" +
                            "                        <div class=\"col-md-3 class-plane\">\n" +
                            "                            <div class=\"info\">");
                    out.println("<i class=\"step fa fa-eur\" ></i>");
                    out.println("<i>" + airlineCode + "</i>");
                    out.println("<i>" + number + "</i>\n</div>");
                    out.println("<div class=\"info\">\n" +
                            "                            <i class=\"step fa fa-calendar-o\" ></i>\n" );
                    out.println("<i>"+newDate+"</i>\n</div>\n</div>");
                    out.println("<div class=\"col-md-6 from-to\" >\n" +
                            "                        <div class=\"src-dest\">");
                    String src = origin;
                    if(Objects.equals(origin, "THR")) {
                        src = "تهران";
                    }
                    else if(Objects.equals(origin, "MHD")) {
                        src = "مشهد";
                    }

                    String des = origin;
                    if(Objects.equals(dest, "THR")) {
                        des = "تهران";
                    }
                    else if(Objects.equals(dest, "MHD")) {
                        des = "مشهد";
                    }

                    logger.debug("Model: " + model);

                    out.println("<i class=\"step\" >"+src + " " +convertNumToPersian(departure.substring(0,2))+":"+convertNumToPersian(departure.substring(2,4)) +"</i>");
                    out.println("<i class=\"step fa fa-angle-double-left\"></i>");
                    out.println("<i class=\"step\" >"+des + " " +convertNumToPersian(departure.substring(0,2))+":"+convertNumToPersian(arrival.substring(2,4))+"</i></div>\n");
                    out.println("<div class=\"plane-class\">\n" +
//                            "<div class=\"pull-right\">\n" +
                            "<i class=\"fa fa-plane\"></i>\n");
                    out.println("<i class=\"info\">"+model+"</i>");
//                    out.println("</div>\n");
                    out.println("" +
//                            "<div class=\"pull-right\">\n" +
                            "<i class=\"fa fa-suitcase\" ></i>\n");
                    out.println("<i class=\"info\" >"+seats.get(j).getAvailable()+"</i>\n");
                    out.println("<i class=\"info\" >صندلی باقیمانده کلاس</i>\n");
                    out.println("<i class=\"info\">" + seats.get(j).getClassName() + "</i>\n");
                    out.println("</div>\n</div>");

                    int tPrice = csq.adults * seats.get(j).getAdultPrice() +
                            csq.childs * seats.get(j).getChildPrice() +
                            csq.infants * seats.get(j).getInfantPrice();


                    out.println("<form class=\"col-md-3 price\" id=\"reserve\" action=\"/ali/reserve\" method=\"POST\">");
                    out.println("<input type=\"hidden\" name=\"number\" value=\""+number+"\" />");
                    out.println("<input type=\"hidden\" name=\"origin\" value=\""+origin+"\" />");
                    out.println("<input type=\"hidden\" name=\"dest\" value=\""+dest+"\" />");
                    out.println("<input type=\"hidden\" name=\"date\" value=\""+date+"\" />");
                    out.println("<input type=\"hidden\" name=\"clas\" value=\""+seats.get(j).getClassName()+"\" />");
                    out.println("<input type=\"hidden\" name=\"adults\" value=\""+params[4]+"\" />");
                    out.println("<input type=\"hidden\" name=\"children\" value=\""+params[5]+"\" />");
                    out.println("<input type=\"hidden\" name=\"infants\" value=\""+params[6]+"\" />");

                    out.println("<button class=\"col-md-3 price\" type=\"submit\" >");
//                    out.println("<div class=\"col-md-3 price\">\n");
                    out.println("<div class=\"price-info\">\n");
                    out.println("<i name=\"price\">"+ tPrice + "</i>\n" +
                            "<i>ریال</i>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"info\">\n" +
                            "                            <i >رزرو آنلاین</i>\n" +
                            "                        </div>\n" +
                            "                                </button>\n</form>\n</div>");
                }
            }
        }
        out.println("</div>");
        if(flights.size()==0) {
            response.setStatus(404);
        }
        else if(flights.size()==1) {
            request.setAttribute("flights",1);
        }
        request.setAttribute("available",available);



//        out.println(res);
        out.println("</section>\n" +
                "    </div>\n" +
                "    <div id=\"footer\">\n" +
                "        <p>سیدعلی اخوانی - بهزاد اوسط | دانشکده\u200Cی فنی دانشگاه تهران</p>\n" +
                "    </div>\n" +
                "</div>");
        out.println( "</body></html>");
    }

    private String convertNumToPersian(int num) {
        return convertNumToPersian(Integer.toString(num));
    }
    private String convertNumToPersian(String num) {
        String persianNum = "";
        for (int i = 0; i < num.length(); i++) {
            switch (num.charAt(i)) {
                case '1':
                    persianNum += "۱";
                    break;
                case '2':
                    persianNum += "۲";
                    break;
                case '3':
                    persianNum += "۳";
                    break;
                case '4':
                    persianNum += "۴";
                    break;
                case '5':
                    persianNum += "۵";
                    break;
                case '6':
                    persianNum += "۶";
                    break;
                case '7':
                    persianNum += "۷";
                    break;
                case '8':
                    persianNum += "۸";
                    break;
                case '9':
                    persianNum += "۹";
                    break;
                case '0':
                    persianNum += "۰";
                    break;
            }
        }
        return persianNum;
    }
}