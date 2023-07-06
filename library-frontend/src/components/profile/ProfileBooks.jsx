import {useDispatch, useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";
import {getBookList, getLastModifiedBook} from "../../redux/selectors/allBooks";
import {PulseLoader} from "react-spinners";
import React, {useEffect, useState} from "react";
import {getUserBooks} from "../../redux/actions/book";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";


const ProfileBooksComponent = () => {
    const userData = useSelector(getUserData)
    const [loaded, setLoaded] = useState(false)
    const dispatch = useDispatch();
    const books = useSelector(getBookList);
    const lastModified = useSelector(getLastModifiedBook)


    const showAuthors = (authors) => {
        return (
            <div>
                {
                    Array.isArray(authors) ?
                        authors.map(result => {
                            return (
                                <div key={result.id}>
                                    <div>{result.firstName} {result.lastName}</div>
                                </div>
                            )
                        })
                        :
                        <></>
                }
            </div>
        )
    }


    const showCategory = (categories) => {
        return (
            <div>
                {
                    Array.isArray(categories) ?
                        categories.map(result => {
                            return (
                                <div key={result.id}>
                                    <div>{result.title}</div>
                                </div>
                            )
                        })
                        :
                        <></>
                }
            </div>)
    }


    useEffect(() => {
        setLoaded(false)
        dispatch(getUserBooks(userData.id)).then(() => {
            setLoaded(true)
        })
    }, [lastModified]);

    return (
        <>
            {
                loaded ?
                    <div className="my-books-page">
                        {
                            Array.isArray(books) && books.length >= 1 ?

                                    <Table>
                                        <thead>
                                        <tr>
                                            <th>Title</th>
                                            <th>Description</th>
                                            <th>Author</th>
                                            <th>Category</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>
                                        {
                                            books.map(result => {
                                            return (
                                                <tbody key={result.status}>
                                                <tr>
                                                    <td>{result.title}</td>
                                                    <td>{result.description}</td>
                                                    <td>{showAuthors(result.authors)}</td>
                                                    <td>{showCategory(result.categories)}</td>
                                                    <td>{result.status}</td>
                                                </tr>
                                                </tbody>
                                            )
                                        })}
                                    </Table>

                                :
                                <>
                                    <div className="no-books-error-message">
                                        <h3>You have no books for now</h3>
                                        <p>Go <Link to={"/books"}>here</Link> if you want to reserve any book <br/>
                                            <small>If you know you have a book and it is not visible here, contact the
                                                librarian
                                            </small>
                                        </p>
                                    </div>
                                </>
                        }
                    </div>
                    :
                    <PulseLoader cssOverride={{
                        textAlign: "center",
                        paddingTop: "20%"
                    }} size={25}/>
            }
        </>
    )
}

export default ProfileBooksComponent;
