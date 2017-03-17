function handlePassengers(){

    var adults = document.getElementById("adults").value;
    var children = document.getElementById("children").value;
    var infants = document.getElementById("infants").value;
    var passengersDiv = document.getElementById("passengers");

    setPrices(adults, children, infants);

    passengersDiv.innerHTML = "";
    var r = 1;
    addAdultsRows(passengersDiv, r, adults);
    r+=Number(adults);
    addChildrenRows(passengersDiv, r, children);
    r+=Number(children);
    addInfantsRows(passengersDiv, r, infants);
 }

function setPrices(adults, children, infants){
    var aprice = document.getElementById("aprice").value;
    var cprice = document.getElementById("cprice").value;
    var iprice = document.getElementById("iprice").value;
    document.getElementById("atotalprice").innerHTML = Number(adults) * Number(aprice);
    document.getElementById("atotalprice").innerHTML += " ریال ";

    document.getElementById("ctotalprice").innerHTML = Number(children) * Number(cprice);
    document.getElementById("ctotalprice").innerHTML += " ریال ";

    document.getElementById("itotalprice").innerHTML = Number(infants) * Number(iprice);
    document.getElementById("itotalprice").innerHTML += " ریال ";

    document.getElementById("totalprice").innerHTML = Number(infants) * Number(iprice) +
                                                      Number(children) * Number(cprice) +
                                                      Number(adults) * Number(aprice);
    document.getElementById("totalprice").innerHTML += " ریال ";
}


 function addAdultsRows(passengersDiv, r, adults){
    for(var i = 0 ; i < adults ; i++ ){
        passengersDiv.innerHTML += '<div class="row info-fields"><div class="col-md-2 col-md-push-10 velvet"><i class="step fa fa-child " ></i><i >'+r+'- بزرگسال</i></div><div class="col-md-2 col-md-push-6"><select list="genders" class="field" name="agender'+r+'" ><option value="male">آقای</option><option value="female">خانم</option></select></div><div class="col-md-2 col-md-push-2"><input class="field" name = aname "'+r+'"placeholder="نام (انگلیسی)"></div><div class="col-md-3 col-md-pull-3" ><input class="field" name="alastname'+r+'" placeholder="نام‌خانوادگی (انگلیسی)"></div><div class="col-md-3 col-md-pull-9"><input class="field" "name=aid'+r+'" placeholder="شماره ملی"></div></div>';
            r++;
    }
 }
 function addChildrenRows(passengersDiv, r, adults){
     for(var i = 0 ; i < adults ; i++ ){
        passengersDiv.innerHTML += '<div class="row info-fields"><div class="col-md-2 col-md-push-10 velvet"><i class="step fa fa-child " ></i><i >'+r+'- خردسال</i></div><div class="col-md-2 col-md-push-6"><select list="genders" class="field" name="cgender'+r+'" ><option value="male">آقای</option><option value="female">خانم</option></select></div><div class="col-md-2 col-md-push-2"><input class="field" name = cname "'+r+'"placeholder="نام (انگلیسی)"></div><div class="col-md-3 col-md-pull-3" ><input class="field" name="clastname'+r+'" placeholder="نام‌خانوادگی (انگلیسی)"></div><div class="col-md-3 col-md-pull-9"><input class="field" "name=cid'+r+'" placeholder="شماره ملی"></div></div>';
         r++;
    }
 }
 function addInfantsRows(passengersDiv, r, adults){
      for(var i = 0 ; i < adults ; i++ ){
        passengersDiv.innerHTML += '<div class="row info-fields"><div class="col-md-2 col-md-push-10 velvet"><i class="step fa fa-child small-icon" ></i><i >'+r+'- نوزاد</i></div><div class="col-md-2 col-md-push-6"><select list="genders" class="field" name="igender'+r+'" ><option value="male">آقای</option><option value="female">خانم</option></select></div><div class="col-md-2 col-md-push-2"><input class="field" name = iname "'+r+'"placeholder="نام (انگلیسی)"></div><div class="col-md-3 col-md-pull-3" ><input class="field" name="ilastname'+r+'" placeholder="نام‌خانوادگی (انگلیسی)"></div><div class="col-md-3 col-md-pull-9"><input class="field" "name=iid'+r+'" placeholder="شماره ملی"></div></div>';
              r++;
      }
 }