import '../../assets/styles/noitems.css';

const NoItemsFoundErrorComponent = (itemName) => {
    return (
        <div className="no-items-found-page">
            <h1>No {itemName}s found!</h1>
            <p>Maybe there is a problem with the connection to the server or new {itemName}s have not been added</p>
        </div>
    );
}

export default NoItemsFoundErrorComponent;