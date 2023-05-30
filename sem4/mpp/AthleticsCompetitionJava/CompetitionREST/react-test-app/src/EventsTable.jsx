import './EventsApp.css'

function EventsRow({event0, deleteFnc, updateFnc, meters}) {
    function handleDelete(event) {
        console.log('Delete for ' + event0.id);
        deleteFnc(event0.id);
    }

    function handleUpdate(event) {
        console.log('Update for ' + event0.id);
        updateFnc(event0.id, meters);
    }

    return (
        <tr>
            <td>{event0.id}</td>
            <td>{event0.meters}</td>
            <td>
                <button onClick={handleDelete}>Delete</button>
                <button onClick={handleUpdate}>Update</button>
            </td>
        </tr>
    );
}


export default function EventsTable({events, deleteFnc, updateFnc, meters}) {
    console.log(events);
    let rows = [];
    
    events.forEach((event0) => {
        rows.push(<EventsRow event0={event0}  key={event0.id} deleteFnc={deleteFnc} updateFnc={updateFnc} meters={meters}/>);
    });

    return (
        <div className="EventsTable">
            <table className="center">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Metrii</th>
                        <th>Acțiuni</th>
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
        </div>
    );
}