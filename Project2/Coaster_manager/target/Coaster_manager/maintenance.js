let ticketCache = 'active';
let mainCont = document.getElementById('maintenance');

const httpRequest = new XMLHttpRequest();

function getTickets(ticketVal){

    if (!httpRequest) {
        console.log('Failed to create an XMLHttpRequest instance');
        displayTickets(null);
        return null;
    }
	httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === 4 && httpRequest.status === 200) {
	    	let response = JSON.parse(httpRequest.response);
            console.log(httpRequest.response)
            ticketCache = ticketVal;
            console.log(ticketVal);
            console.log(ticketCache);
            localStorage.setItem('ticketCache', JSON.stringify(ticketCache));
            displayTickets(response);
	    }
	};
	httpRequest.open("GET","maintenanceTicketServlet");
    httpRequest.setRequestHeader('find',ticketVal);
    httpRequest.send();
}

function displayTickets(tickets){
  clearDisplay();
    const errMsg = 'Failed to load maintenance tickets';
    if (tickets === null || tickets === undefined) {
        mainCont.innerText = errMsg;
    }
    else if(Array.isArray(tickets) === true) {
    	let ticketArr = tickets;
        for(let ticket of tickets){
        	let div = document.createElement('div');
            div.innerHTML = '</br> Maintenance Ticket ID#: '+ ticket.mainId
                          +'<br/> Attraction ID#: '+ ticket.attractionId
                          +'<br/> Status of Attraction: '+ ticket.status
                          +'<br/> Employee ID#: ' + ticket.employeeId
                          +'<br/> Creation Date: '+ ticket.startDate;
                          +'<br/> Resolution Date: '+ ticket.endDate;
            mainCont.appendChild(div);
        }
     }
     else{
        let div = document.createElement('div');
            div.innerHTML = '</br> Maintenance Ticket ID#: '+ tickets.mainId
                          +'<br/> Attraction ID#: '+ tickets.attractionId
                          +'<br/> Status of Attraction: '+ tickets.status
                          +'<br/> Employee ID#: ' + tickets.employeeId
                          +'<br/> Creation Date: '+ tickets.startDate;
                          +'<br/> Resolution Date: '+ tickets.endDate;
        mainCont.appendChild(div);
     }
}
function findById(){
   let id = document.getElementById('mainid').value;
   if (!httpRequest) {
        console.log('Failed to create an XMLHttpRequest instance');
        displayTickets(null);
        return null;
    }
	httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === 4 && httpRequest.status === 200) {
	    	let response = JSON.parse(httpRequest.response);
            console.log(httpRequest.response)
            //attractionCache = attractionVal;
            localStorage.setItem('ticketCache', JSON.stringify(ticketCache));
            displayTickets(response);
	    }
	};
	httpRequest.open("GET","maintenanceTicketServlet");
    httpRequest.setRequestHeader('find','id');
    httpRequest.setRequestHeader('id',id);
    httpRequest.send();
}

function findByAttraction(){
   let id = document.getElementById("attractid").value;
   if (!httpRequest) {
        console.log('Failed to create an XMLHttpRequest instance');
        displayTickets(null);
        return null;
    }
	httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === 4 && httpRequest.status === 200) {
	    	let response = JSON.parse(httpRequest.response);
            console.log(httpRequest.response)
            //attractionCache = attractionVal;
            localStorage.setItem('ticketCache', JSON.stringify(ticketCache));
            displayTickets(response);
	    }
	};
	httpRequest.open("GET","maintenanceTicketServlet");
    httpRequest.setRequestHeader('find','attraction');
    httpRequest.setRequestHeader('id',id);
    httpRequest.send();
}

//function addTicket(){
// let data = {
//         attractionId: document.getElementById("attractnumber").value,
//         employeeId : document.getElementById("empId").value,
//         status: 'Down',
//         description : document.getElementById("desc").value,
//         isActive : true
//     }
//     $.ajax({
//         url: 'maintenanceTicketServlet',
//         type: 'POST',
//         dataType: 'json',
//         data: JSON.stringify(data),
//         contentType: 'application/json',
//         mimeType: 'application/json',
//         success: function (response) {
//         displayTickets(response);
//         }
//     })
//}
//
//function resolveTicket(){
// let data = {
//         mainId: document.getElementById("mainnumber").value,
//         status:'Operational',
//         endDate:document.getElementById("date").value,
//         isActive: false
//     }
//     $.ajax({
//         url: 'maintenanceTicketServlet',
//         type: 'PUT',
//         dataType: 'json',
//         data: JSON.stringify(data),
//         contentType: 'application/json',
//         mimeType: 'application/json',
//         success: function (response) {
//         displayTickets(response);
//         }
//     })
//}

function clearDisplay() {
    document.getElementById('maintenance').innerHTML = '';
}
function clearBabyDisplay() {
    document.getElementById('babydiv').innerHTML = '';
}

function applyFilter(ticketLoc) {
    if (!ticketCache) {
         console.log('How did this happen?');
         return;
     }

     if (ticketLoc.length != 0) {
         clearDisplay();
         if(ticketLoc=='id'){
             let form = document.createElement('form');
                 form.setAttribute('name','idForm');
             let input = document.createElement('input');
                 input.setAttribute('type','number');
                 input.setAttribute('id','mainid');
                 form.appendChild(input);
             let submit = document.createElement('button');
                submit.setAttribute('id', 'submitButton')
                 submit.setAttribute('type','button');
                 submit.innerText = 'submit';
                 submit.setAttribute('onclick', 'findById()');
                 form.appendChild(submit);
                 mainCont.appendChild(form);
         }
         else if(ticketLoc=='attraction'){
            let form = document.createElement('form');
                form.setAttribute('name','idForm');
            let input = document.createElement('input');
                 input.setAttribute('type','number');
                 input.setAttribute('id','attractid');
                    form.appendChild(input);
            let submit = document.createElement('button');
                 submit.setAttribute('id', 'submitButton')
                 submit.setAttribute('type','button');
                 submit.innerText = 'submit';
                 submit.setAttribute('onclick', 'findByAttraction()');
                    form.appendChild(submit);
                      mainCont.appendChild(form);
         }
         else{
         getTickets(ticketLoc);
         }
     }
     else {
         getTickets(ticketCache);
     }
}
function init() {
    getTickets(ticketCache);
}
init();