import React from "react";
import {Link} from "react-router-dom";

function AuthorCard(props) {
    const url = "/books-by-author/" + props.id;
    const birthDate = new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    }).format(props.birthDate);

    return (

        <div className="card-group">
        <div className="card">
            <div className="card__name">
                <h3>{props.firstName} {props.lastName}</h3>
            </div>
            <div className="card__horizontal-line"></div>
            <div className="card-body ">
                <p className="card-text">
                    About: {props.biography}<br/>
                    Birth date: {birthDate}
                </p>
            </div>
            <div className="card__card-footer">
                    <div className="card-footer__buttons">
                        <Link to={url} className="btn">Books</Link>
                    </div>
            </div>
        </div>
        </div>
    );
}

export default AuthorCard;