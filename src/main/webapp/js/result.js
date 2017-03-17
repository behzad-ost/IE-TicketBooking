/**
 * Created by ali on 3/17/17.
 */

function sort(type) {
    var a, i, j;
    var ticketList = document.getElementById("all-tickets");
    var tickets = ticketList.childNodes;
    var ticketsArr = [];
    ticketsArr = removeJunks(tickets);
    ticketList.innerHTML = "";

    for (i = 0; i < ticketsArr.length; i++) {
        var price1 = getPriceOfDiv(ticketsArr[i]);
        for(j = i + 1; j < ticketsArr.length; j++) {
            var price2 = getPriceOfDiv(ticketsArr[j]);
            if (type == 'asc') {
                if (Number(price1) > Number(price2)) {
                    var tmp = ticketsArr[i];
                    ticketsArr[i] = ticketsArr[j];
                    ticketsArr[j] = tmp;
                }
            } else {
                if (Number(price1) < Number(price2)) {
                    var tmp = ticketsArr[i];
                    ticketsArr[i] = ticketsArr[j];
                    ticketsArr[j] = tmp;
                }                
            }
        }
    }
    
    appendTickets(ticketsArr, ticketList);
    console.log("ez");
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

function removeJunks(arr) {
    var result = [];
    for (var i in arr) {
        if (arr[i].nodeType == 1) { // remove text nodes
            result.push(arr[i]);
        }
    }
    return result;
}