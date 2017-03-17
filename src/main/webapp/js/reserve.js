setDefaults();

function handlePassengers(){
    var adults = document.getElementById("adults").value;
    var children = document.getElementById("children").value;
    var infants = document.getElementById("infants").value;
    var passengersDiv = document.getElementById("passengers");
    if(checkAvailability(adults, children, infants) < 0){
        return;
    }
    setPrices(adults, children, infants);
    var r = 1;
    var origin = document.getElementById("origin").value;
    var dest = document.getElementById("dest").value;
    var airline = document.getElementById("airline").value;
    var clas = document.getElementById("clas").value;
    var date = document.getElementById("date").value;
    var number = document.getElementById("number").value;
    var adults = document.getElementById("adults").value;
    var children = document.getElementById("children").value;
    var infants = document.getElementById("infants").value;

    passengersDiv.innerHTML = "";

    addAdultsRows(passengersDiv, r, adults);
    r+=Number(adults);
    addChildrenRows(passengersDiv, r, children);
    r+=Number(children);
    addInfantsRows(passengersDiv, r, infants);
    addHiddenData(passengersDiv, origin, dest, clas, airline, date, number, adults, children, infants);
    addButtons(passengersDiv);
 }

function checkAvailability(adults, children, infants){
    var availableSeats = Number(document.getElementById("availableseats").value);
    if(availableSeats < Number(adults) + Number(children) + Number(infants)){
        alert("ظرفیت پرواز :" + availableSeats + "نفر");
        return -1;
    }
    return 1;

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


 function addAdultsRows(passengersDiv, r, size){
    for(var i = 0 ; i < size ; i++ ){
        passengersDiv.innerHTML += '<div class="row info-fields"><div class="col-md-2 col-md-push-10 velvet"><i class="step fa fa-child " ></i><i >'+r+'- بزرگسال</i></div><div class="col-md-2 col-md-push-6"><select list="genders" class="field" name="agender'+i+'" ><option value="male">آقای</option><option value="female">خانم</option></select></div><div class="col-md-2 col-md-push-2"><input class="field" name="aname'+i+'" placeholder="نام (انگلیسی)"></div><div class="col-md-3 col-md-pull-3" ><input class="field" name="alastname'+i+'" placeholder="نام‌خانوادگی (انگلیسی)"></div><div class="col-md-3 col-md-pull-9"><input class="field" name="aid'+i+'" placeholder="شماره ملی"></div></div>';
            r++;
    }
 }
 function addChildrenRows(passengersDiv, r, size){
     for(var i = 0 ; i < size ; i++ ){
        passengersDiv.innerHTML += '<div class="row info-fields"><div class="col-md-2 col-md-push-10 velvet"><i class="step fa fa-child " ></i><i >'+r+'- خردسال</i></div><div class="col-md-2 col-md-push-6"><select list="genders" class="field" name="cgender'+i+'" ><option value="male">آقای</option><option value="female">خانم</option></select></div><div class="col-md-2 col-md-push-2"><input class="field" name="cname'+i+'" placeholder="نام (انگلیسی)"></div><div class="col-md-3 col-md-pull-3" ><input class="field" name="clastname'+i+'" placeholder="نام‌خانوادگی (انگلیسی)"></div><div class="col-md-3 col-md-pull-9"><input class="field" name="cid'+i+'" placeholder="شماره ملی"></div></div>';
         r++;
    }
 }
 function addInfantsRows(passengersDiv, r, size){
      for(var i = 0 ; i < size ; i++ ){
        passengersDiv.innerHTML += '<div class="row info-fields"><div class="col-md-2 col-md-push-10 velvet"><i class="step fa fa-child small-icon" ></i><i >'+r+'- نوزاد</i></div><div class="col-md-2 col-md-push-6"><select list="genders" class="field" name="igender'+i+'" ><option value="male">آقای</option><option value="female">خانم</option></select></div><div class="col-md-2 col-md-push-2"><input class="field" name="iname'+i+'" placeholder="نام (انگلیسی)"></div><div class="col-md-3 col-md-pull-3" ><input class="field" name="ilastname'+i+'" placeholder="نام‌خانوادگی (انگلیسی)"></div><div class="col-md-3 col-md-pull-9"><input class="field" name="iid'+i+'" placeholder="شماره ملی"></div></div>';
        r++;
      }
 }

function addButtons(passengersDiv) {
    passengersDiv.innerHTML += '<button type="submit" class="btn-submit" value="پرداخت و ثبت نهایی"><i>پرداخت و ثبت نهایی</i><i class="step fa fa-angle-left" ></i></button><button type="submit" formaction="/ali" class="btn-cancel">انصراف</button>';
}
function addHiddenData(passengersDiv, origin, dest, airline, clas, date, number, adults, children, infants) {
//    passengersDiv.innerHTML = "";
    passengersDiv.innerHTML += '<input type="hidden" name="origin" id="origin" value="'+origin+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="dest" id="dest" value="'+dest+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="airline" id="airline" value="'+airline+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="number" id="number" value="'+number+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="clas" id="clas" value="'+clas+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="date" id="date" value="'+date+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="adults" value="'+adults+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="children" value="'+children+'"/>';
    passengersDiv.innerHTML += '<input type="hidden" name="infants" value="'+infants+'"/>';

}

function setDefaults() {
   var aSelect = document.getElementById("adults");
   var cSelect = document.getElementById("children");
   var iSelect = document.getElementById("infants");
   var adults = Number(document.getElementById("dadults").value);
   var children = Number(document.getElementById("dchildren").value);
   var infants = Number(document.getElementById("dinfants").value);

   for (var i = 0 ; i < aSelect.length; i++) {
        if(aSelect[i].value == adults)
            aSelect[i].selected = "true";
   }
   for (var i = 0 ; i < cSelect.length; i++) {
        if(cSelect[i].value == children)
            cSelect[i].selected = "true";
   }
   for (var i = 0 ; i < iSelect.length; i++) {
        if(iSelect[i].value == infants)
            iSelect[i].selected = "true";
   }
}