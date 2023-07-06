import BookItem from "./BookItem";
import React from "react";
import NoItemsFoundErrorComponent from "../errors/NoItemsFoundError";

function BookList(props) {
    return (
        <div className="page__cards">
            {Array.isArray(props.books) ?
                props.books.map((book) => {
                    return (
                        <div key={book.id}>
                            <BookItem

                                id={book.id}
                                title={book.title}
                                description={book.description}
                                shelfNumber={book.shelfNumber}
                                status={book.status}
                            />

                        </div>
                    )
                })
                : <NoItemsFoundErrorComponent/>
            }
        </div>
    )
}

export default BookList;