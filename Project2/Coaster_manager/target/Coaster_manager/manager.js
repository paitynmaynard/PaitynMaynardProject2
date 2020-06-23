let attractionCache = 'all ';

function getAttractions(){
const httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Failed to create an XMLHttpRequest instance');
        displayAttractions(null);
        return null;
    }
 httpRequest.onreadystatechange = function() {
         if (httpRequest.readyState === XMLHttpRequest.DONE) {
                response = httpRequest.response;
                console.log(httpRequest.response)
                 attractionCache = this.response;
                 localStorage.setItem('attractionCache', JSON.stringify(attractionCache));
                 displayAttractions(response);
             }
             else {
                 console.log('Error: ' + httpRequest.status + ' ' + httpRequest.statusText);
                 displayAttractions(null);
             }

        };
httpRequest.open("GET","attractionServlet");
httpRequest.setRequestHeader('Content-type', '\'application/json; charset=utf-8\'');
httpRequest.setRequestHeader('find',attractionCache);
httpRequest.responseType = 'json';
httpRequest.send();
}

function displayAttractions(attractions){
  clearDisplay();
    const errMsg = 'Failed to load attractions';
    var mainCont = document.getElementById('managerview');

    if (attractions === null || attractions === undefined) {
        mainCont.innerText = errMsg;
    }
    else {
        for(i=0; i<attractions.length;i++){
            var div = document.createElement('div');
            div.innerHtml('<img scr='+attractions[i].url+'><br/>'+ attractions[i].name +', ID#:'+attractions[i].id+'<br/> Rating:'+ attractions[i].rate+'<br/>Status: '+ attractions[i].status);
            mainCont.appendChild(div);
        }
    }
}

function clearDisplay() {
    document.getElementById('managerview').innerHTML = '';
}

function applyFilter(attractLoc) {
    if (!attractionCache) {
        console.log('How did this happen?');
        return;
    }

    if (attractLoc.length != 0) {
        getAttractions(attractLoc);
    }
    else {
        getAttractions(attractionCache);
    }
}

function init() {
    getAttractions();
}
init();
