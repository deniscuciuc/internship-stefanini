
import React from "react";
import {Link} from "react-router-dom";

function CategoryCard(props) {
    const url = "/books-by-category/" + props.id;
    return (
        <div className="card">
            <div className="card__name">
                <h3>{props.title}</h3>
            </div>
            <div className="card-body"></div>
            <div className="card__card-footer">
                <div className="card-footer__buttons">
                    <Link to={url}> Go to Books</Link>
                </div>
            </div>
        </div>
    );
}

export default CategoryCard;