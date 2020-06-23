let attractionCache = 'all ';
let mainCont = document.getElementById('managerview');
let babyCont = document.getElementById('babydiv');
const httpRequest = new XMLHttpRequest();

function getAttractions(attractionVal){

    if (!httpRequest) {
        console.log('Failed to create an XMLHttpRequest instance');
        displayAttractions(null);
        return null;
    }
	httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === 4 && httpRequest.status === 200) {
	    	let response = JSON.parse(httpRequest.response);
            console.log(httpRequest.response)
            attractionCache = attractionVal;
            localStorage.setItem('attractionCache', JSON.stringify(attractionCache));
            displayAttractions(response);
	    }
	};
	httpRequest.open("GET","attractionServlet");
    httpRequest.setRequestHeader('find',attractionVal);
    httpRequest.send();
}

function displayAttractions(attractions){
    const errMsg = 'Failed to load attractions';
    if (attractions === null || attractions === undefined) {
        mainCont.innerText = errMsg;
    }
    else if(attractions.attractions != null){
        clearDisplay();
        clearBabyDisplay();
    	let attractionArr = attractions.attractions;
        for(let attraction of attractions.attractions){
        	let div = document.createElement('div');
            div.innerHTML = '</br>'+ attraction.name +', ID#: '+ attraction.id
                            +'<br/>Rating: ' + attraction.rating
                            +'<br/>Status: '+ attraction.status;
            mainCont.appendChild(div);
        }
    } else{
        clearBabyDisplay();
    	let div = document.createElement('div');
        div.innerHTML = '</br>'+ attractions.name +', ID#: '+ attractions.id
                        +'<br/>Rating: ' + attractions.rate
                        +'<br/>Status: '+ attractions.status;
        babyCont.appendChild(div);
       }
}

function findById(){
   let id = document.getElementById('id').value;
   if (!httpRequest) {
        console.log('Failed to create an XMLHttpRequest instance');
        displayAttractions(null);
        return null;
    }
	httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === 4 && httpRequest.status === 200) {
	    	let response = JSON.parse(httpRequest.response);
            console.log(httpRequest.response)
            //attractionCache = attractionVal;
            localStorage.setItem('attractionCache', JSON.stringify(attractionCache));
            displayAttractions(response);
	    }
	};
	httpRequest.open("GET","attractionServlet");
    httpRequest.setRequestHeader('find','id');
    httpRequest.setRequestHeader('id',id);
    httpRequest.send();
}


function clearDisplay() {
    document.getElementById('managerview').innerHTML = '';
}
function clearBabyDisplay() {
    document.getElementById('babydiv').innerHTML = '';
}

function applyFilter(attractLoc) {
    if (!attractionCache) {
        console.log('How did this happen?');
        return;
    }

    if (attractLoc.length != 0) {
        clearDisplay();
        if(attractLoc=='id'){
        let form = document.createElement('form');
            form.setAttribute('name','idForm');
        let input = document.createElement('input');
            input.setAttribute('type','number');
            input.setAttribute('id','id');
            form.appendChild(input);
        let submit = document.createElement('button');
        	submit.setAttribute('id', 'submitButton')
            submit.setAttribute('type','button');
            submit.innerText = 'submit';
            submit.setAttribute('onclick', 'findById()');
            form.appendChild(submit);
            mainCont.appendChild(form);
        }
        else{
        getAttractions(attractLoc);
        }
    }
    else {
        getAttractions(attractionCache);
    }
}

function init() {
    getAttractions(attractionCache);
}
init();

function addAttraction(){
 let data = {
         id: document.getElementById("attractnumber").value,
         add: 'add'
     }
     $.ajax({
         url: 'attractionServlet',
         type: 'POST',
         dataType: 'json',
         data: JSON.stringify(data),
         contentType: 'application/json',
         mimeType: 'application/json',
         success: function (response) {
         displayAttractions(response);
         }
     })
}

function removeAttraction(){
 let data = {
         id: document.getElementById("attractnumber").value, //changed from empID to id
         delete: 'delete'
     }
     $.ajax({
         url: 'attractionServlet',
         type: 'DELETE',
         dataType: 'json',
         data: JSON.stringify(data),
         contentType: 'application/json',
         mimeType: 'application/json',
         success: function (response) {
         displayAttractions(response);
         }
     })
}