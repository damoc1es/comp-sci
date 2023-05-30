import { useState } from 'react';

export default function EventsForm({addFnc, meters, setMeters}) {
    const [id, setId] = useState('');

    function handleSubmit (event){
        let event0 = {
            meters: meters,
            id: id,
        }

        console.log('An event was submitted: ');
        console.log(event0);
        addFnc(event0);
        event.preventDefault();
    }

    return(
        <form onSubmit={handleSubmit}>
            <label>
                Metrii: <input type="text" value={meters} onChange={e => setMeters(e.target.value)}/>
            </label><br/>

            <input type="submit" value="Adaugă probă"/>
        </form>
    );
}
