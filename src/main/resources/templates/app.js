var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

// function sendName() {
//
//     formId.onsubmit = async (e) => {
//
//         e.preventDefault();
//         datos = new FormData();
//         //datos.append('nombre', document.querySelector('#nombre'));
//         datos.append('multipartFile', document.querySelector('#file'));
//         //let multipartFile = document.querySelector('#file').value;
//         let response = await fetch('http://localhost:8080/fichero', {
//             method: 'POST',
//             //headers: {'Content-Type': 'multipart/form-data'},
//             body: new FormData(document.querySelector('#formId'))
//         });
//
//         let aux = await response.json();
//         console.log(aux.message);
//
//     };
// }

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    // $("#send").click(function () {
    //     sendName();
    // });
});

async function verFichero() {
    let dato = document.querySelector('#id').value;
    const request = await fetch('http://localhost:8080/fichero/' + dato, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const ficheroInfo = await request.json();
    console.log(ficheroInfo);
    let fichero = '';
    fichero += ' - Nombre: ' + ficheroInfo.nombre + '<br>' +
        'Fecha: ' + ficheroInfo.fecha_subida + '<br>';

    document.querySelector('#verFichero').outerHTML = fichero;
}