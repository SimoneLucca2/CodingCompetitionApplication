// ItemListContext.js
import React, { createContext, useState, useContext } from 'react';

// Create the context
const ItemListContext = createContext();

// Provider component
export const ItemListProvider = ({ children }) => {
    const [items, setItems]
        = useState([]);

    // Function to add an item to the list
    const addItem = item => {
        setItems(prevItems => [...prevItems, item]);
    };

    return (
        <ItemListContext.Provider value={{ items, addItem }}>
            {children}
        </ItemListContext.Provider>
    );
};

// Custom hook to use the context
export const useItemList = () => useContext(ItemListContext);
