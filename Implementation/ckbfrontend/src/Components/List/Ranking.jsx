import React from 'react';
import style from "./Styles/Ranking.module.css";
import propTypes from 'prop-types';

function Ranking(props) {
    const itemList = props.items;

    // Sort itemList by score in descending order
    const sortedItemList = itemList.sort((a, b) => b.score - a.score);

    const listItems = sortedItemList.map(item => (
        <li key={item.id}>
            {item.score}: &nbsp;
        </li>
    ));

    return (
        <div>
            <h3 className={style.category}>RANKING:</h3>
            <ol>{listItems}</ol>
        </div>
    );
}

Ranking.propTypes = {
    items: propTypes.arrayOf(propTypes.shape({
        id: propTypes.string.isRequired,
        score: propTypes.number.isRequired,
    })),
}

Ranking.defaultProps = {
    items: [],
}

export default Ranking;
