import {useEffect, useState} from "react";


const ServerNotRespondingErrorComponent = () => {
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {

        setTimeout(() => {
            setLoading(false);
        }, 2500)

    })

    return (
        <>
            {
                isLoading ?
                    <></>
                    :
                    <div>
                        Server not responding!
                    </div>
            }
        </>
    );
}

export default ServerNotRespondingErrorComponent;