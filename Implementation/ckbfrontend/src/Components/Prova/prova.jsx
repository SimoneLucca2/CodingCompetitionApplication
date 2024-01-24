import React from 'react';
import './prova.css'; // Assicurati di importare il file CSS qui
import { useItemList } from "../../Context/ItemListContext";


const Prova = () => {
    const { items } = useItemList();

    return (
        <div className="prova-container">
            <h2>Elenco Tornei</h2>
            {items.length > 0 ? (
                <ul>
                    {items.map((tournament, index) => (
                        <li key={index}>Nome Torneo: {tournament.name}</li>
                    ))}
                </ul>
            ) : (
                <p>Nessun torneo presente nella lista.</p>
            )}
        </div>
    );
};

export default Prova;
