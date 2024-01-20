function List() {

    const fruits =
        [{id: 1, name: 'apple', calories: 95},
        {id: 2, name: 'banana', calories: 105},
        {id: 3, name: 'orange', calories: 45},
        {id: 4, name: 'pear', calories: 100},
        {id: 5, name: 'pineapple', calories: 452}];


    const listItems = fruits.map((fruit) =>
        <li key={fruit.id}>
            {fruit.name}: &nbsp;
            <b>{fruit.calories}</b>
        </li>);

    return (
        <ul>
            {listItems}
        </ul>
    );
}

export default List;