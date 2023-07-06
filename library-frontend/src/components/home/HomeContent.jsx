import React from "react";
import {useDispatch} from "react-redux";
import ChangePasswordNotification from "../auth/ChangePasswordNotification";
import UserLastActionMessage from "../useraction/UserLastActionMessage";
import {useNavigate} from "react-router-dom";
import LogoutComponent from "../logout/Logout";


const HomeContentComponent = () => {
    useDispatch();
    const navigate = useNavigate();


    return (
        <>
            <div className="background-image">
                <div className="home-page">
                    <div className="home-page__content-container">
                        <div className="home-page__main-info">
                            <div className="main-info__title">
                                <h1>Stefanini Library</h1>
                            </div>
                            <div className="main-info__description">
                                <p>
                                    The <strong>Stefanini Library</strong> is situated in the center of Chisinau,
                                    established in
                                    2022. It was founded on the initiative of 3 interns : Daria, Denis and Elena, who
                                    believed
                                    that
                                    reading books is the best way to spend time. Membership is open to all, without any
                                    payment.
                                    Today's libraries are more than just books. From teaching critical literacy skills
                                    to
                                    promoting entrepreneurship and small business development to preserving and
                                    facilitating
                                    our community stories, an easier question might be what don't libraries do!
                                </p>
                            </div>
                        </div>
                        <div className="home-page__explore-books-button">
                            <button onClick={() => navigate("/books")}>
                                Explore books
                            </button>
                        </div>
                    </div>
                </div>
                <ChangePasswordNotification/>
            </div>
        </>
    );
}

export default HomeContentComponent;