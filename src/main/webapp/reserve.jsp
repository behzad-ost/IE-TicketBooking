<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="fa" dir="rtl">

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/layout.css">
    <link rel="stylesheet" href="css/style2.css">
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
            <i class="step active" >ورود اطلاعات</i>
            <i class="step fa fa-chevron-left active hiden" ></i>
            <i class="step hiden" >دریافت بلیت</i>
        </section>


        <% if (request.getParameter("error") == null) { %>
            <section id="reserve-main">
                        <div id="control-bar">
                            <a href="/ali/">
                                <div id="back">
                                    <i class="step fa fa-arrow-right" ></i>
                                    <i class="step" >بازگشت به صفحه نتایج</i>
                                </div>
                            </a>
                            <div id="time">
                                <i>زمان باقیمانده:  </i>
                                <i class="red">۰۴:۵۹</i>
                            </div>
                        </div>
                        <div id="main-grey">
                            <div id="fight-label">مشخصات پرواز انتخابی شما</div>
                            <div class="row" id="ticket-info">
                                <div class="col-md-3" id="agency-date">
                                    <div class="info">
                                        <i class="step fa fa-suitcase" ></i>
                                              <i>کلاس ${requestScope.clas}</i>
                                    </div>
                                    <div class="info">
                                        <i class="step fa fa-plane" ></i>
                                        <i >${requestScope.model}</i>
                                    </div>
                                </div>
                                <div class="col-md-6" id="from-to">

                                    <% String des = request.getParameter("dest"); %>
                                    <i class="step" >${requestScope.origin}${requestScope.atime}</i>
                                    <i class="step fa fa-angle-double-left" ></i>
                                    <% String src = request.getParameter("origin"); %>
                                    <i class="step" >${requestScope.dest}${requestScope.dtime}</i>

                                </div>
                                <div class="col-md-3" id="class-plane">
                                    <div class="info">
                                        <i class="step fa fa-eur" ></i>
                                        <i >${requestScope.airline}${requestScope.number}</i>
                                    </div>
                                    <div class="info">
                                        <i class="step fa fa-calendar-o" ></i>
                                        <i > ${requestScope.date}</i>
                                    </div>
                                </div>
                            </div>
                            <div id="payment-label">صورتحساب سفر</div>
                            <div id="payment" >
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th class="text-right"></th>
                                        <th class="text-right">به ازای هر نفر</th>
                                        <th class="text-right">تعداد</th>
                                        <th class="text-right">مجموع</th>
                                    </tr>
                                    </thead>
                                    <tbody id="table-body">
                                    <tr>
                                        <td class="text-right">بزرگسال</td>
                                        <td class="text-right">${requestScope.aprice} ریال</td>
                                        <td class="text-right">${requestScope.adults} نفر</td>
                                        <td class="text-right">${requestScope.adults * requestScope.aprice} ریال</td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">کودک زیر ۱۲ سال</td>
                                        <td class="text-right">${requestScope.cprice} ریال</td>
                                        <td class="text-right">${requestScope.children} نفر</td>
                                        <td class="text-right">${requestScope.children * requestScope.cprice} ریال</td>
                                    </tr>
                                    <tr>
                                        <td class="text-right">نوزاد زیر ۳ سال</td>
                                        <td class="text-right">${requestScope.iprice} ریال</td>
                                        <td class="text-right">${requestScope.infants} نفر</td>
                                        <td class="text-right">${requestScope.infants * requestScope.iprice} ریال</td>
                                    </tr>
                                    <tr id="sum">
                                        <td class="text-right">مجموع</td>
                                        <td></td>
                                        <td></td>
                                        <td class="text-right">${requestScope.adults * requestScope.aprice + requestScope.infants * requestScope.iprice + requestScope.children * requestScope.cprice} ریال</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div id="passengers-label">اطلاعات</div>
                            <form id="passengers" action="/ali/tickets" method="POST">
                                <% int r = 1; for(int i = 0 ; i < Integer.parseInt(request.getParameter("adults")) ; i++) { %>
                                <div class="row info-fields">
                                    <div class="col-md-2 col-md-push-10 velvet">
                                        <i class="step fa fa-child " ></i>
                                        <i ><% out.print(r);%>- بزرگسال</i>
                                    </div>
                                    <div class="col-md-2 col-md-push-6">
                                        <select list="genders" class="field" <% out.println("name=agender"+i);%>  >
                                            <option value="male">آقای</option>
                                            <option value="female">خانم</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2 col-md-push-2">
                                        <input class="field" placeholder="نام (انگلیسی)" <% out.println("name=aname"+i);%>>
                                    </div>
                                    <div class="col-md-3 col-md-pull-3" >
                                        <input class="field" placeholder="نام‌خانوادگی (انگلیسی)" <% out.println("name=alastname"+i);%>>
                                    </div>
                                    <div class="col-md-3 col-md-pull-9">
                                        <input class="field" placeholder="شماره ملی" <% out.println("name=aid"+i);%>>
                                    </div>
                                </div>
                                <% r++;} %>
                                <% for(int i = 0 ; i < Integer.parseInt(request.getParameter("children")) ; i++) { %>
                                <div class="row info-fields">
                                    <div class="col-md-2 col-md-push-10 velvet">
                                        <i class="step fa fa-child " ></i>
                                        <i ><% out.print(r);%>- خردسال</i>
                                    </div>
                                    <div class="col-md-2 col-md-push-6">
                                        <select list="genders" class="field" <% out.println("name=cgender"+i);%>>
                                            <option value="male">آقای</option>
                                            <option value="female">خانم</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2 col-md-push-2">
                                        <input class="field" placeholder="نام (انگلیسی)" <% out.println("name=cname"+i);%>>
                                    </div>
                                    <div class="col-md-3 col-md-pull-3" >
                                        <input class="field" placeholder="نام‌خانوادگی (انگلیسی)" <% out.println("name=clastname"+i);%>>
                                    </div>
                                    <div class="col-md-3 col-md-pull-9">
                                        <input class="field" placeholder="شماره ملی" <% out.println("name=cid"+i);%>>
                                    </div>
                                </div>
                                <% r++;} %>
                                <% for(int i = 0 ; i < Integer.parseInt(request.getParameter("infants")) ; i++) { %>
                                <div class="row info-fields">
                                    <div class="col-md-2 col-md-push-10 velvet">
                                        <i class="step fa fa-child small-icon" ></i>
                                        <i ><% out.print(r);%>- نوزاد</i>
                                    </div>
                                    <div class="col-md-2 col-md-push-6">
                                        <select list="genders" class="field" <% out.println("name=igender"+i);%>>
                                            <option value="male">آقای</option>
                                            <option value="female">خانم</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2 col-md-push-2">
                                        <input class="field" placeholder="نام (انگلیسی)" <% out.println("name=iname"+i);%>>
                                    </div>
                                    <div class="col-md-3 col-md-pull-3" >
                                        <input class="field" placeholder="نام‌خانوادگی (انگلیسی)" <% out.println("name=ilastname"+i);%>">
                                    </div>
                                    <div class="col-md-3 col-md-pull-9">
                                        <input class="field" placeholder="شماره ملی" <% out.println("name=iid"+i);%>>
                                    </div>
                                </div>
                                <% r++;} %>
                                <input type="hidden" name="origin" <% out.println("value="+request.getParameter("origin"));%> />
                                <input type="hidden" name="dest" <% out.println("value="+request.getParameter("dest"));%> />
                                <input type="hidden" name="date" value=${requestScope.date} />
                                <input type="hidden" name="airline" value=${requestScope.airline} />
                                <input type="hidden" name="number" value=${requestScope.number} />
                                <input type="hidden" name="clas" value=${requestScope.clas} />
                                <input type="hidden" name="adults" value=${requestScope.adults} />
                                <input type="hidden" name="children" value=${requestScope.children} />
                                <input type="hidden" name="infants" value=${requestScope.infants} />


                                <button type="submit" class="btn-submit" value="پرداخت و ثبت نهایی">
                                    <i>پرداخت و ثبت نهایی</i>
                                    <i class="step fa fa-angle-left" ></i>
                                </button>
                                <button type="submit" formaction="/ali" class="btn-cancel">انصراف</button>
                                </form>
                        </div>
                    </section>
        <% } else { %>
               <h2><% out.println(request.getParameter("error")); %></h2>
        <% } %>
    </div>
    <div id="footer">
        <p>سیدعلی اخوانی - بهزاد اوسط | دانشکده‌ی فنی دانشگاه تهران</p>
    </div>
</div>

</body>
</html>