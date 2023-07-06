import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {Modal} from "react-bootstrap";
import {getShowModalState} from "../../redux/selectors/flagSelectors";
import {hideModalSessionFinished} from "../../redux/actions/flagActions";


const SessionFinished = ({children}) => {
    const showModal = useSelector(getShowModalState);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const handlePopUp = () => {
        dispatch(hideModalSessionFinished());
    };
    useEffect(() => {
        if (showModal) {
            navigate("/");
            // setShowpopup(true);
        }
    }, [showModal])

    return (
        <>
            <div>
                {children}
            </div>
            <Modal show={showModal} onHide={handlePopUp}>
                <Modal.Header closeButton>
                    <Modal.Title>Session finished </Modal.Title>
                </Modal.Header>
                <Modal.Body>Please login again</Modal.Body>
                <Modal.Footer>
                    <button className="card-btn100__buttons" onClick={() => {
                        navigate("/login");
                        handlePopUp()
                    }}>Login
                    </button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default SessionFinished;