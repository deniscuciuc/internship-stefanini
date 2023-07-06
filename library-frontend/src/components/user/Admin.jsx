import {useNavigate} from "react-router-dom";
import '../../assets/styles/profile/admin.css';
import React from "react";


const AdminComponent = () => {
    const navigate = useNavigate();

    return (
        <div className="admin-page">
            <h3>Admin Panel</h3>
            <div className="page__cards">
                <div className="card__body">
                    <button className="card-btn100__buttons" onClick={() => navigate("/users")}>Manage Users</button>
                </div>
                <div className="card__body">
                    <button className="card-btn100__buttons" onClick={() => navigate("/manage-book")}>Manage Books
                    </button>
                </div>
            </div>
        </div>

    )
}

export default AdminComponent;