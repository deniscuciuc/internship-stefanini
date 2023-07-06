import Popup from "reactjs-popup";
import 'reactjs-popup/dist/index.css';
import button from "bootstrap/js/src/button";
import {useNavigate} from "react-router-dom";

const Modal1 = (showPopUp) => {
const navigate = useNavigate()
    return(
        <div>
            <Popup open ={showPopUp}
                   position="right center"
                   closeOnDocumentClick>
                <div>For this action you should be authorized! Please login or register</div>
                <button onClick={() => navigate("/login")}> Login </button>
                <button onClick={() => navigate("/registration")}> Register </button>
            </Popup>
        </div>
    )

}
export default Modal1