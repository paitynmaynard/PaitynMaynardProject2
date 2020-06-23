function loginCall() {
    let data = {
        id: document.getElementById("empId").value, //changed from empID to id
        pword: document.getElementById("password").value,
        add: 'login'
    }
    $.ajax({
        url: 'employeeServlet',
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (response) {
            if(response.adminPriv === 'false'){ //parsing response and doing a task
                window.location.href = 'maintenance.html';
            } else{
                window.location.href = 'managerfront.html';
            }
        }
    })
}

// document.getElementById('login').addEventListener('click', (e) => {
//     e.preventDefault;
//     let email = document.getElementById("email").value;
//     let pword = document.getElementById("password").value;
//     if(email.length != 0 && pword.length != 0){
//         let data = {
//             email: document.getElementById("email").value,
//             pword: document.getElementById("password").value,
//             add: 'login'
//         }
//         $.ajax({
//             url: 'employeeServlet',
//             type: 'POST',
//             dataType: 'json',
//             data: JSON.stringify(data),
//             contentType: 'application/json',
//             mimeType: 'application/json',
//             success: function (response) {
//                 // alert(response);
//                 if (response.adminPriv == "true") {
//                     document.cookie = email;
//                     window.location.replace("http://www.bing.com");
//                 }
//                 else {
//                     window.location.href="login.html"
//                 }
//             }
//         })
//     }
// })


// function login() {
//     let data = {
//         empID: document.getElementById("empId").value,
//         pword: document.getElementById("password").value,
//         add: 'login'
//     }
//     $.ajax({
//         url: '/employeeServlet',
//         type: 'POST',
//         dataType: 'json',
//         data: JSON.stringify(data),
//         contentType: 'application/json',
//         mimeType: 'application/json',
//         success: function (response) {
//
//         }
//     })
// }

// $(document).ready(function() {
//     $("#login").click(function () {
//         let data = {
//             empID: document.getElementById("empId").value,
//             pword: document.getElementById("password").value,
//             add: 'login'
//         }
//         $.ajax({
//             url: '/employeeServlet',
//             type: 'POST',
//             dataType: 'json',
//             data: JSON.stringify(data),
//             contentType: 'application/json',
//             mimeType: 'application/json',
//             success: function (response) {
//
//             }
//         })
//     })
// })