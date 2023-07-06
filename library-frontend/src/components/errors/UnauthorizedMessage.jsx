import { Link } from "react-router-dom";

const UnauthorizedMessageComponent = () => {
    return (
        <article style={{ padding: "100px" }}>
            <h1>Oops!</h1>
            <p>Unauthorized</p>
            <div className="flexGrow">
                <Link to="/">Visit Our Homepage</Link>
            </div>
        </article>
    )
}


export default UnauthorizedMessageComponent;