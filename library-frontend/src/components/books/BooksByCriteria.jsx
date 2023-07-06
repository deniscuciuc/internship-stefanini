import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {getBookList, getLastModifiedBook} from "../../redux/selectors/allBooks";
import {searchBooks} from "../../redux/actions/book";
import BookList from "./BookList";
import {useParams} from "react-router-dom";
import NavigationComponent from "../navigation/Navigation";
import {PulseLoader} from "react-spinners";
import UserLastActionMessageComponent from "../useraction/UserLastActionMessage";



const BooksByCriteriaComponent = () => {
    const [loaded, setLoaded] = useState(false)
    const dispatch = useDispatch();
    const books = useSelector(getBookList);
    const lastModified = useSelector(getLastModifiedBook)
    const params = useParams();

    useEffect(() => {
        setLoaded(false)
        dispatch(searchBooks(params.criteria)).then(() => {
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

export default BooksByCriteriaComponent;