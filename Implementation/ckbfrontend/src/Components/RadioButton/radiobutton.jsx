import React, { useState } from 'react';

function RadioButton({onChange}) {
    const [choice, setChoice] = useState('');

    const handleChange = (event) => {
        setChoice(event.target.value);
        onChange(event.target.value);
    };

    return (
        <div>
            <label>
                STUDENT
                <input
                    type="radio"
                    value="STUDENT"
                    checked={choice === 'STUDENT'}
                    onChange={handleChange}
                />
            </label>
            <label>
                EDUCATOR
                <input
                    type="radio"
                    value="EDUCATOR"
                    checked={choice === 'EDUCATOR'}
                    onChange={handleChange}
                />
            </label>
        </div>
    );
}

export default RadioButton;