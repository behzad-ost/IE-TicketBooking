<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="data.Reservation" %>
<%@ page import="data.Ticket" %>
<%@ page import="data.Manager" %>
<%@ page import="java.util.Objects" %>



<!DOCTYPE html>
<html lang="fa" dir="rtl">

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/layout.css">
    <link rel="stylesheet" href="css/tickets.css">
    <title>اخوانی و اوسط</title>
</head>

<body>
<div id="container">
    <header>
        <div id="companyLogo" class="headerItem">
            <img alt="logo" src="imgs/LogoBlack.png" class="img">
        </div>
        <div id="companyName" class="headerItem">
         <i>اکبر تیکت</i>
         <i class="fa fa-registered" aria-hidden="true" id="registered"></i></div>
        <div id="user" class="dropdown">
                <div class="drop-button">
                    <i class="fa fa-user" aria-hidden="true" id="userLogo"></i>
                    <i id="user-name">نام کاربر</i>
                </div>
                <div class="dropdown-content">
                    <a href="#">پروفایل</a>
                    <a href="#">تنظیمات</a>
                    <a href="#">خروج</a>
                </div>
            </div>
    </header>

    <div class="container">
        <section id="steps">
            <i class="step hiden" >جستجوی پرواز</i>
            <i class="step fa fa-chevron-left hiden" ></i>
            <i class="step hiden" >انتخاب پرواز</i>
            <i class="step fa fa-chevron-left hiden" ></i>
            <i class="step" >ورود اطلاعات</i>
            <i class="step fa fa-chevron-left hiden" ></i>
            <i class="step hiden active" >دریافت بلیت</i>
        </section>


        <section id="ticket-main">
            <div id="control-bar">
                <div id="back">
                    <i class="step" >بلیت‌های صادر شده</i>
                </div>
                <button type="button" id="print-btn" class="btn btn-lg">چاپ همه</button>
            </div>
            <div id="ticket-bar">
                <%
                   Reservation reservation = (Reservation) request.getAttribute("reservation");
                   String origin = reservation.getOriginCode();
                   String dest = reservation.getDestCode();
                   String src = origin;
                   if(Objects.equals(origin, "THR"))
                       src = "تهران";
                   else if(Objects.equals(origin, "MHD"))
                       src = "مشهد";
                   String des = dest;
                   if(Objects.equals(dest, "THR"))
                       des = "تهران";
                   else if(Objects.equals(dest, "MHD"))
                       des = "مشهد";

                   String flightNumber = reservation.getFlightNumber();
                   String airline = reservation.getAirlineCode();
                   String seatClass = reservation.getSeatClass();
                   String[] timeAndModel = Manager.getInstance().getTimeAndModel(flightNumber, origin, dest);
                   String dtime = timeAndModel[0].substring(0,2) + ":" +timeAndModel[0].substring(2,4);
                   String atime = timeAndModel[1].substring(0,2) + ":" +timeAndModel[1].substring(2,4);
                   String date = (String) request.getAttribute("date");
                   ArrayList<Ticket> tickets = Manager.getInstance().getTicketsOfReserve(reservation);

                   for(int i = 0 ; i < tickets.size(); i++){

                %>
                <div class="whole-ticket">
                                    <div class="row first-row">
                                        <div class="col-md-6">
                                            <div class="ticket-num">
                                            <div class="col-md-6 param-value big-param">
                <%out.println(tickets.get(i).getRefCode());%> </div>
                                                   <div class="col-md-6 param-name">کد مرجع</div>
                                               </div>
                                           </div>
                                           <div class="col-md-6">
                                               <div class="ticket-num">
                                                   <div class="col-md-6 param-value big-param">

                <%out.println(tickets.get(i).getNumber());%> </div>

                                <div class="col-md-6 param-name">شماره‌ی بلیت</div>
                            </div>
                        </div>
                    </div>
                    <div class="row second-row">
                    <div class="col-md-2 ticket-num param-value">
                        <%out.println(timeAndModel[2]);%> </div>
                    <div class="col-md-2 ticket-num param-value">
                <%out.println(seatClass);%> </div>
                <div class="col-md-3 ticket-num param-value">
                <%out.println(airline);%> </div>
                <div class="col-md-1 ticket-num param-name">پرواز</div>
                <div class="col-md-3 ticket-num param-value">
                <%out.println(tickets.get(i).getFirstName()+" "+tickets.get(i).getLastName());%> </div>
                <div class="col-md-1 ticket-num param-name">نام</div>
                    </div>
                    <div class="third-row">
                        <div class="col-md-2">
                            <div class="header-name">
                                <i class="param-name">ورود</i>
                            </div>
                            <div class="item-value">
                            <div class="value-box">
                <%out.println(atime);%> </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="header-name">
                                <i class="param-name">خروج</i>
                            </div>
                            <div class="item-value">
                            <div class="value-box">
                <%out.println(dtime);%> </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="header-name">
                                <i class="param-name">تاریخ</i>
                            </div>
                            <div class="item-value">
                            <div class="value-box">
                <%out.println(date);%> </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="header-name">
                                <i class="param-name">به</i>
                            </div>
                            <div class="item-value">
                            <div class="value-box">
                <%out.println(des);%> </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="header-name">
                                <i class="param-name">از</i>
                            </div>
                            <div class="item-value">
                                <div class="value-box">
                <%out.println(src);%> </div>
                            </div>
                        </div>
                    </div>
                </div>
            <%
               }
            %>
            </div>
        </section>
    </div>
    <div id="footer">
        <p>سیدعلی اخوانی - بهزاد اوسط | دانشکده‌ی فنی دانشگاه تهران</p>
    </div>
</div>

</body>
</html>