/**
 * Created by ali on 3/17/17.
 */
function filter() {
    var seatClass = document.getElementById("seat-classes").value;
    var airlineName = document.getElementById("airline-codes").value;
    var sortType = document.getElementById("sort-type").value;
    
    var ticketList = document.getElementById("all-tickets");
    var tickets = ticketList.childNodes;
    var ticketsArr = [];
    ticketsArr = removeJunks(tickets);
    ticketList.innerHTML = "";
    
    ticketsArr = initializeTickets(ticketsArr);    

    if (sortType == 'صعودی')
        ticketsArr = sort(ticketsArr, "asc");
    else
        ticketsArr = sort(ticketsArr, "desc");
    
    if (seatClass != 'همه') {
        for (var i in ticketsArr) {
            var seatName = getSeatClassOfDiv(ticketsArr[i]);
            if (seatName != seatClass) {
                ticketsArr[i].style.display = "none";
            }
        }
    }
    
    if (airlineName != 'همه') {
        for (var i in ticketsArr) {
            var airline = getAirlineNameOfDiv(ticketsArr[i]);
            if (airline != airlineName) {
                ticketsArr[i].style.display = "none";
            }
        }
    }    
    
    appendTickets(ticketsArr, ticketList);
}

function appendTickets(tickets, ticketList) {
    for (i = 0; i < tickets.length; i++) {
        ticketList.appendChild(tickets[i]);
    }    
}

function getPriceOfDiv(ticketsArrElement) {
    var price;
    var priceDiv = ticketsArrElement.getElementsByClassName("price-info");
    var infoDiv = priceDiv[0].getElementsByTagName("i")
    price = infoDiv[0].innerHTML;
    return price;
}

function getSeatClassOfDiv(ticketsArrElement) {
    var seatClass;
    var seatClassDiv = ticketsArrElement.getElementsByClassName("plane-class");
    var infoDiv = seatClassDiv[0].getElementsByTagName("i")
    seatClass = infoDiv[5].innerHTML;
    return seatClass;
}

function getAirlineNameOfDiv(ticketsArrElement) {
    var airlineName;
    var airlineNameDiv = ticketsArrElement.getElementsByClassName("info");
    var infoDiv = airlineNameDiv[0].getElementsByTagName("i")
    airlineName = infoDiv[1].innerHTML;
    return airlineName;
}

function removeJunks(arr) {
    var result = [];
    for (var i in arr) {
        if (arr[i].nodeType == 1) { // remove text nodes
            result.push(arr[i]);
        }
    }
    return result;
}

function sort(ticketsArr, type) {
    for (i = 0; i < ticketsArr.length; i++) {
        var price1 = getPriceOfDiv(ticketsArr[i]);
        for(j = 0; j < ticketsArr.length; j++) {
            var price2 = getPriceOfDiv(ticketsArr[j]);
            
            if (type == 'asc') {
                if (Number(price1) < Number(price2)) {
                    var tmp = ticketsArr[i];
                    ticketsArr[i] = ticketsArr[j];
                    ticketsArr[j] = tmp;
                }
            } else {
                if (Number(price1) > Number(price2)) {
                    var tmp = ticketsArr[i];
                    ticketsArr[i] = ticketsArr[j];
                    ticketsArr[j] = tmp;
                }                
            }
        }
    }
    return ticketsArr;
}

function initializeTickets(ticketsArr) {
    for (var i in ticketsArr) {
        ticketsArr[i].style.display = "";
    }
    return ticketsArr;
}