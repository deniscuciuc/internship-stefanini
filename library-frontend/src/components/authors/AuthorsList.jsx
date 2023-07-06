import React from "react";
import AuthorCard from "./AuthorCard";
import NoItemsFoundErrorComponent from "../errors/NoItemsFoundError";
import '../../assets/styles/authors.css';


function AuthorsList(props) {
    return (
        <div className="page__cards">
            {Array.isArray(props.authors) ?
                props.authors.map((author) => {
                    return (
                        <AuthorCard key={author.id}
                                    id={author.id}
                                    firstName={author.firstName}
                                    lastName={author.lastName}
                                    biography={author.biography}
                                    birthDate={author.birthDate}
                        />
                    )
                })
                : <NoItemsFoundErrorComponent/>
            }
        </div>
    );
}


export default AuthorsList;