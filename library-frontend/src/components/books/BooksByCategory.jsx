import React, {useEffect, useState} from "react";
import BookList from "./BookList";
import {useDispatch, useSelector} from "react-redux";
import {getBookList, getLastModifiedBook} from "../../redux/selectors/allBooks";
import {getBooksByCategory} from "../../redux/actions/book";
import {useParams} from "react-router-dom";
import NavigationComponent from "../navigation/Navigation";
import {PulseLoader} from "react-spinners";
import UserLastActionMessageComponent from "../useraction/UserLastActionMessage";

function BookByCategoryComponent() {
    const [loaded, setLoaded] = useState(false)
    const lastModified = useSelector(getLastModifiedBook)
    const books = useSelector(getBookList);
    const dispatch = useDispatch();
    const params = useParams();

    useEffect(() => {
        dispatch(getBooksByCategory(params.categoryId)).then(() => {
            setLoaded(true)
        })
    }, [lastModified]);

    return (
        <>
            {loaded?
                <div>
                    <NavigationComponent/>
                    <UserLastActionMessageComponent/>
                    <div className="page">
                        <div className="all-books-page-header">
                            <div><h1>Books</h1></div>
                        </div>
                        <div className="page__horizontal-line"></div>
                        <BookList books={books}/>
                    </div>
                </div>
                :  <PulseLoader cssOverride={{
                    textAlign: "center",
                    paddingTop: "20%"
                }} size={25} />
            }

        </>
    );

};
export default BookByCategoryComponent;