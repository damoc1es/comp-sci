import {useEffect} from 'react';
import { useState } from 'react';
import EventsTable from './EventsTable.jsx';
import './EventsApp.css'
import EventsForm from "./EventsForm.jsx";
import {GetEvents, DeleteEvent, AddEvent, UpdateEvent} from './utils/rest-calls.js'


export default function EventsApp() {
	const [events, setEvents] = useState([{"meters": "15", "id": "00000000-0000-0000-0000-000000000000"}]);
    const [meters, setMeters] = useState('');

	function addFnc(event0){
		// console.log('Inside add function ' + event0);
		AddEvent(event0)
			.then(res => GetEvents())
			.then(events => setEvents(events))
			.catch(erorr => console.log('Add error: ', erorr));
	}

	function deleteFnc(event0){
		// console.log('Inside delete function ' + event0);
		DeleteEvent(event0)
			.then(res => GetEvents())
			.then(events => setEvents(events))
			.catch(error => console.log('Delete error: ', error));
	}

    function updateFnc(event0, mtrs) {
        // console.log('Inside update function ' + event0);
        UpdateEvent(event0, mtrs)
            .then(res => GetEvents())
            .then(events => setEvents(events))
            .catch(error => console.log('Update error: ', error));
    }

	useEffect( () => {
		// console.log('Inside useEffect')
		GetEvents().then(events => setEvents(events));
	}, []);

	return (
        <div className="EventsApp">
			<h1> Probe </h1>
            <EventsForm addFnc={addFnc} meters={meters} setMeters={setMeters}/>
            <br/><br/>
            <EventsTable events={events} meters={meters} deleteFnc={deleteFnc} updateFnc={updateFnc}/>
		</div>
    );
}
