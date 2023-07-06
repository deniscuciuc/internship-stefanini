import React, {useState} from "react";
import {giveTheBook, reserveTheBook, returnTheBook} from "../../redux/actions/book";
import {useDispatch, useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";
import {useNavigate} from "react-router-dom";
import {Modal} from "react-bootstrap";
import {searchClientData} from "../../redux/actions/client";
import {getClientData} from "../../redux/selectors/client";
import {setLastUserAction} from "../../redux/actions/login";


function BookItem(props) {
    const dispatch = useDispatch();
    const userInfo = useSelector(getUserData);
    const clientInfo = useSelector(getClientData);
    const [clientEmail, setClientEmail] = useState("")
    const userId = userInfo.id;
    const navigate = useNavigate();
    const [showpopup, setShowpopup] = useState(false);
    const [loaded, setLoaded] = useState(false);
    const [action, setAction] = useState("");

    const reserveBook = (bookId) => {
        if (userInfo.confirmedByEmail === true) {
            dispatch(reserveTheBook(bookId, userId));
            dispatch(setLastUserAction("Book was reserved"));
        } else {
            dispatch(setLastUserAction("You cant reserve books. Email is not confirmed"));
        }
    }

    const searchClient = (email) => {
        dispatch(searchClientData(email)).then(() => {
            setLoaded(true)
        })
        console.log(clientInfo.id);
    }
    const reserveBookForClient = (bookId, bookTitle) => {
        dispatch(reserveTheBook(bookId, clientInfo.id)).then(() => {
            setLoaded(false);
            dispatch(setLastUserAction(
                "Book: " + bookTitle + " was reserved for " + clientInfo.profile.firstName + " "
                + clientInfo.profile.lastName
            ));
        })
        console.log(bookId);
    }
    const giveBookToClient = (bookId, bookTitle) => {
        dispatch(giveTheBook(bookId, clientInfo.id)).then(() => {
            setLoaded(false);
            dispatch(setLastUserAction(
                "Book: " + bookTitle + " was given to " + clientInfo.profile.firstName + " "
                + clientInfo.profile.lastName
            ));
        })
        console.log(bookId);
    }
    const giveBook = (bookId) => {
        dispatch(giveTheBook(bookId, userId))
            .then(() => {
                dispatch(setLastUserAction("Book was given"));
            })
    }

    const returnBook = (bookId) => {
        dispatch(returnTheBook(bookId))
            .then(() => {
                dispatch(setLastUserAction("Book was returned"));
            })
    }
    const handlePopUp = (e) => {
        setShowpopup(!showpopup)
    };
    return (
        <>
            {
                userInfo?.roles?.includes("ADMIN") || userInfo?.roles?.includes("LIBRARIAN") ?
                    <div className="card">
                        <div className="card__name">
                            <h3>{props.title}</h3>
                            <h5><p>{props.description}</p></h5>
                        </div>
                        <div className="card__horizontal-line"></div>
                        <div className="card__body">
                            <p>shelfNumber: {props.shelfNumber}</p>
                            {
                                props.status === "BOOKED" ?
                                    <div>
                                        <p style={{display: 'inline'}}>status: </p>
                                        <p className={"book-status-color-booked"}>{props.status}</p>
                                    </div>
                                    : props.status === "TAKEN" ?
                                        <div>
                                            <p style={{display: 'inline'}}>status: </p>
                                            <p className={"book-status-color-taken"}>{props.status}</p>
                                        </div>
                                        :
                                        <div>
                                            <p style={{display: 'inline'}}>status: </p>
                                            <p className={"book-status-color-available"}>{props.status}</p>
                                        </div>
                            }
                        </div>
                        <div>
                            <div className="card-footer__buttons">
                                <button disabled={props.status === "BOOKED" || props.status === "TAKEN"}
                                        className="card-btn50__buttons"
                                        onClick={() => {
                                            setShowpopup(true);
                                            setAction("reserve")
                                        }}
                                        variant="primary">Reserve the book
                                </button>
                                <button
                                    className="card-btn50__buttons"
                                    onClick={() => props.status !== "TAKEN" ? (setShowpopup(true), setAction("give")) : returnBook(props.id)}
                                    variant="primary"> {props.status !== "TAKEN" ? "Give the book" : "Return the book"}</button>
                            </div>
                        </div>
                    </div>
                    :

                    <div className="card" style={{width: '18rem'}}>
                        <div className="card__name">
                            <h3>{props.title}</h3>
                            <h5>{props.description}</h5>
                        </div>
                        <div className="card__body">
                            <p>shelfNumber: {props.shelfNumber}</p>
                            {
                                props.status === "BOOKED" ?
                                    <div>
                                        <p style={{display: 'inline'}}>status: </p>
                                        <p className={"book-status-color-booked"}>{props.status}</p>
                                    </div>
                                    : props.status === "TAKEN" ?
                                        <div>
                                            <p style={{display: 'inline'}}>status: </p>
                                            <p className={"book-status-color-taken"}>{props.status}</p>
                                        </div>
                                        :
                                        <div>
                                            <p style={{display: 'inline'}}>status: </p>
                                            <p className={"book-status-color-available"}>{props.status}</p>
                                        </div>
                            }
                        </div>
                        <div>
                            <div>
                                <button disabled={props.status === "BOOKED" || props.status === "TAKEN"}
                                        className="card-btn100__buttons"
                                        onClick={() => userInfo?.roles ? reserveBook(props.id) : handlePopUp()}
                                >
                                    Reserve the book
                                </button>
                            </div>
                        </div>
                    </div>

            }

            <Modal show={showpopup} onHide={handlePopUp}>
                {
                    userInfo?.roles?.includes("ADMIN") || userInfo?.roles?.includes("LIBRARIAN") ?
                        <>
                            <Modal.Header closeButton>
                                <Modal.Title> Please introduce the email of client </Modal.Title>
                            </Modal.Header>
                            <div className="input-group">
                                <input type="search" className="form-control rounded" placeholder="Search"
                                       aria-label="Search"
                                       aria-describedby="search-addon" onChange={event => {
                                    setClientEmail(event.target.value);
                                }}/>
                                <button type="button" className="card-btn15__buttons" onClick={() => {
                                    setLoaded(false);
                                    searchClient(clientEmail)
                                }}>search
                                </button>
                            </div>
                            {
                                loaded ?
                                    (clientInfo.email ?
                                            <div> Client {clientInfo.profile.firstName} {clientInfo.profile.lastName} was
                                                found </div>
                                            : <div>Client not found, please check introduced email and try again</div>
                                    )
                                    :
                                    <div></div>
                            }
                            {
                                action === "reserve" ?
                                    <>
                                        <button className="card-btn100__buttons" onClick={() => {
                                            reserveBookForClient(props.id, props.title);
                                            handlePopUp()
                                        }}>Reserve
                                            book
                                        </button>
                                        <button onClick={() => {
                                            reserveBook(props.id);
                                            handlePopUp()
                                        }} className="card-btn100__buttons"><b>Reserve
                                            for me</b></button>
                                    </>
                                    :
                                    <>
                                        <button className="card-btn100__buttons" onClick={() => {
                                            giveBookToClient(props.id, props.title);
                                            handlePopUp()
                                        }}>Give
                                            the
                                            book
                                        </button>
                                        <button onClick={() => {
                                            giveBook(props.id);
                                            handlePopUp()
                                        }}
                                                className="card-btn100__buttons"><b>Take yourself</b>
                                        </button>
                                    </>
                            }
                        </>
                        :
                        <>
                            <Modal.Header closeButton>
                                <Modal.Title>For this action you should be authorized! </Modal.Title>
                            </Modal.Header>
                            <Modal.Body>Please login or register</Modal.Body>
                            <Modal.Footer>
                                <button className="card-btn100__buttons" onClick={() => navigate("/login")}><b>Login</b>
                                </button>
                                <button className="card-btn100__buttons"
                                        onClick={() => navigate("/registration")}>Register
                                </button>
                            </Modal.Footer>
                        </>
                }
            </Modal>
        </>

    )

}

export default BookItem;

