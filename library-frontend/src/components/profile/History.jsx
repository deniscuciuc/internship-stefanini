import {useDispatch, useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";
import React, {useEffect, useState} from "react";
import {getUserHistory} from "../../redux/actions/history";
import {getLastModifiedBook} from "../../redux/selectors/allBooks";
import {getHistoryList} from "../../redux/selectors/history";
import {Table} from "react-bootstrap";

const HistoryComponent = () => {
    const userData = useSelector(getUserData)
    const [loaded, setLoaded] = useState(false)
    const dispatch = useDispatch();
    const history = useSelector(getHistoryList);
    const lastModified = useSelector(getLastModifiedBook);

    useEffect(() => {
        setLoaded(false)

        dispatch(getUserHistory(userData.id)).then(() => {
            setLoaded(true)
        })
    }, [lastModified]);

    return (
        <div className="history-page">
            {
                Array.isArray(history) && history.length >= 1 ?

                    <Table>
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Action</th>
                        </tr>
                        </thead>

                        {
                            history.map((action) => {
                                return (<tbody key={action}>
                                    <tr>
                                        <td>{new Intl.DateTimeFormat('en-US', {
                                            year: 'numeric',
                                            month: '2-digit',
                                            day: '2-digit'
                                        }).format(action.date)}</td>
                                        <td>{action.actionName}</td>
                                    </tr>
                                    </tbody>
                                );
                            })
                        }

                    </Table>

                    :

                    <div className="no-history-error-message">
                        <h3>You have no history for now</h3>
                        <p>Do some activity to take new actions in your history<br/>
                            <small>If you know you have a activity and it is not visible here, contact the
                                librarian
                            </small>
                        </p>
                    </div>

            }
        </div>
    );
}

export default HistoryComponent;