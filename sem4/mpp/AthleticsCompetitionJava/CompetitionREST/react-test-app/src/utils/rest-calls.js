import {COMPETITION_EVENTS_BASE_URL} from './consts';

function status(response) {
    console.log("Response status " + response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

export function GetEvents() {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let myInit = {
        method: 'GET',
        headers: headers,
        mode: 'cors'
    };

    let request = new Request(COMPETITION_EVENTS_BASE_URL, myInit);

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        }
    );
}

export function DeleteEvent(id) {
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    let antet = {
        method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'
    };

    const deleteURL = COMPETITION_EVENTS_BASE_URL + '/' + id;
    return fetch(deleteURL, antet)
        .then(status)
        .then(response => {
            console.log("Delete status " + response.status);
            return response.text();
        }).catch(e => {
            console.log("Error " + e );
            alert("Nu s-a putut șterge.");
            return Promise.reject(e);
        }
    );
}

export function AddEvent(event0) {
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type", "application/json");

    let antet = {
        method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(event0)
    };

    return fetch(COMPETITION_EVENTS_BASE_URL, antet)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        }
    );
}

export function UpdateEvent(event0, meters) {
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type", "application/json");

    let updated = { "meters": meters };
    
    let antet = { method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(updated)
    };

    const updateURL = COMPETITION_EVENTS_BASE_URL + '/' + event0;
    return fetch(updateURL, antet)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        }
    );
}
