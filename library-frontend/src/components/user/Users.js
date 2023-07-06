import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {createUser, deleteUser, getNumberOfUsers, searchUsers, updateUser, userList} from "../../redux/actions/user";
import {useDispatch, useSelector} from "react-redux";
import {getNewUserData, getTotalNumberOfUsers, getUserList} from "../../redux/selectors/user";
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Popover from 'react-bootstrap/Popover';
import Form from "react-bootstrap/Form";
import {PulseLoader} from "react-spinners";
import {Table} from "react-bootstrap";
import NavigationComponent from "../navigation/Navigation";
import searchIcon from '../../assets/images/icons/profile/search.svg';
import deleteIcon from '../../assets/images/icons/profile/trash.svg';
import updateIcon from '../../assets/images/icons/profile/pencil.svg';
import {getUserData} from "../../redux/selectors/login";
import UserLastActionMessageComponent from "../useraction/UserLastActionMessage";
import validateUserAdminCreates from "../../util/validateUserAdminCreates";
import {setLastUserAction} from "../../redux/actions/login";
import {fetchBookList} from "../../redux/actions/book";
import prevPageIcon from "../../assets/images/icons/arrow-left-circle.svg";
import nextPageIcon from "../../assets/images/icons/arrow-right-circle.svg";
import sortIcon from "../../assets/images/icons/sorting-arrows.svg";


const UsersComponent = () => {
    const dispatch = useDispatch();

    /* Users */
    const users = useSelector(getUserList);
    const userInfo = useSelector(getUserData);
    const numberOfUsers = useSelector(getTotalNumberOfUsers);

    /* Logged user */
    const userData = useSelector(getUserData);
    const [isAdmin, setIsAdmin] = useState(false);
    const allowedRoleToDeleteAndUpdateUsers = 'ADMIN';

    /* Create | Update | Delete */
    const [phoneNumber, setPhoneNumber] = useState('');
    const [role, setRole] = useState('USER');
    const [emailTakenError, setEmailTakenError] = useState('');
    const [errors, setErrors] = useState({})
    const [loaded, setLoaded] = useState(false)
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [saved, setSaved] = useState(false);
    const newUserData = useSelector(getNewUserData);
    const roles = [
        {id: 0, role: 'Choose a role'},
        {id: 1, role: "USER"},
        {id: 2, role: "ADMIN"},
        {id: 3, role: "LIBRARIAN"}
    ];

    /* Search */
    const [criteria, setCriteria] = useState('');

    /* Sorting and Pagination */
    const pageSize = 10;
    const sortByTypes = ['Time Added', 'Email'];
    const [pageCount, setPageCount] = useState(1);
    const [maxPages, setMaxPages] = useState(1);
    const [sortBy, setSortBy] = useState("Time Added");
    const [sortOrder, setSortOrder] = useState("asc");

    const goToTheNextPage = () => {
        setLoaded(false);

        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        }

        dispatch(fetchBookList(pageCount + 1, pageSize, sortByConverted.toLowerCase(), sortOrder)).then(() => {
            setLoaded(true);
            setPageCount(pageCount + 1);
        })
    }

    const goToThePrevPage = () => {
        setLoaded(false);

        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        }

        dispatch(fetchBookList(pageCount - 1, pageSize, sortByConverted.toLowerCase(), sortOrder)).then(() => {
            setLoaded(true);
            setPageCount(pageCount - 1);
        })
    }

    const switchSortOrder = () => {
        if (sortOrder === 'asc') setSortOrder('desc');
        if (sortOrder === 'desc') setSortOrder('asc');
    }

    const deleteUserById = (id) => {
        dispatch(deleteUser(id)).then(() => {
            setLoaded(false)
        });
    }

    const updateUserFields = (id) => {

        const userDetails = {
            "email": email,
            "profile": {
                "firstName": firstName,
                "lastName": lastName
            }
        };

        dispatch(updateUser(id, userDetails)).then(() => {
            setLoaded(false)
        });
    }

    const getUserMainRole = (array) => {
        let userRole = "USER";

        if (array?.find(userRole => userRole === 'LIBRARIAN')) {
            userRole = "LIBRARIAN";
        } else if (array?.find(userRole => userRole === 'ADMIN')) {
            userRole = "ADMIN";
        }

        return userRole;
    }

    const handleSaved = () => {
        setSaved(false)
    };
    const createNewUser = () => {

        if (!errors) {
            const userDetails = {
                "email": email,
                "password": "",
                "profile": {
                    "firstName": firstName,
                    "lastName": lastName,
                    "phoneNumber": phoneNumber,
                },
                "roles": [
                    role
                ]
            };

            dispatch(createUser(userDetails)).then(() => {
                setSaved(true)
            })
        }
    }

    const handleOnChangeValidating = () => {
        setErrors(validateUserAdminCreates({firstName, lastName, email, phoneNumber}));
    }

    useEffect(() => {
        if (userData?.roles?.find(role => allowedRoleToDeleteAndUpdateUsers?.includes(role))) {
            setIsAdmin(true);
        }

        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        } else if (sortBy.toString() === sortByTypes.at(1).toString()) {
            sortByConverted = "email";
        }

        dispatch(getNumberOfUsers()).then(() => {

            if (numberOfUsers % pageSize > 0) {
                setMaxPages(Math.floor(numberOfUsers / pageSize + 1));
            } else {
                setMaxPages(Math.floor(numberOfUsers / pageSize));
            }

            if (numberOfUsers <= pageSize) {
                setMaxPages(1);
            }

            dispatch(userList(pageCount, pageSize, sortByConverted, sortOrder)).then(() => {
                setLoaded(true);
            })
        });

    }, [loaded, sortBy, sortOrder, maxPages, numberOfUsers, newUserData, pageSize]);

    useEffect(() => {
        handleOnChangeValidating();
    }, [email, firstName, lastName, phoneNumber, emailTakenError])

    return (
        <>
            <NavigationComponent/>
            <div className="users-page">
                <UserLastActionMessageComponent/>
                <div className="page__header">
                    <h1>Users</h1>
                    <div className="pagination-container">
                        <div className="pagination-container__prev-btn">
                            {
                                pageCount === 1 ?
                                    <></>
                                    :
                                    <img onClick={goToThePrevPage} src={prevPageIcon} alt="Prev Icon"/>
                            }
                        </div>
                        <div className="pagination_container__page-number">
                            <p>{pageCount}</p>
                        </div>
                        <div className="pagination-container__next-btn">
                            {
                                pageCount === maxPages ?
                                    <></>
                                    :
                                    <img onClick={goToTheNextPage} src={nextPageIcon} alt="Next Icon"/>
                            }
                        </div>
                    </div>
                    <div className="sorting-container">
                        <div className="sorting-container__sort-by-selector">
                            <Form.Group>
                                <Form.Select name="sort-type"
                                             onChange={e => setSortBy(e.currentTarget.value)}>
                                    {
                                        sortByTypes.map(sortType =>
                                            <option key={sortType} value={sortType}>
                                                Sort by {sortType}
                                            </option>
                                        )
                                    }
                                </Form.Select>
                            </Form.Group>
                        </div>
                        <div className="sorting-container__sort-arrows">
                            <img src={sortIcon} alt="Sort Arrows" onClick={switchSortOrder}/>
                        </div>
                    </div>
                    <div className="users-page__header__buttons">
                        <OverlayTrigger
                            trigger="click"
                            key='right'
                            placement='left'
                            rootClose={true}
                            onExited={handleSaved}
                            overlay={
                                <Popover>
                                    <Popover.Header as="h3">{`Create new user`}</Popover.Header>
                                    <Popover.Body>
                                        <Form.Group className="mb-3" controlId="formEmail">
                                            <Form.Label><b>Email</b></Form.Label>
                                            <Form.Control type="text" placeholder="Email"
                                                          onChange={e => {
                                                              setEmail(e.target.value);
                                                              handleOnChangeValidating();
                                                          }}
                                                          onFocus={handleOnChangeValidating}/>
                                            {emailTakenError &&
                                                <p className="error-message">{emailTakenError}<i>*</i></p>}
                                            {errors.email && <p className="error-message">{errors.email}<i>*</i></p>}
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="formFirstName">
                                            <Form.Label><b>FirstName</b></Form.Label>
                                            <Form.Control type="text" placeholder="FirstName"
                                                          onChange={e => {
                                                              setFirstName(e.target.value);
                                                              handleOnChangeValidating();
                                                          }}
                                                          onFocus={handleOnChangeValidating}
                                            />
                                            {errors.firstName &&
                                                <p className="error-message">{errors.firstName}<i>*</i></p>}
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="formLastName">
                                            <Form.Label><b>LastName</b></Form.Label>
                                            <Form.Control type="text" placeholder="LastName"
                                                          onChange={e => {
                                                              setLastName(e.target.value);
                                                              handleOnChangeValidating();
                                                          }}
                                                          onFocus={handleOnChangeValidating}
                                            />
                                            {errors.lastName &&
                                                <p className="error-message">{errors.lastName}<i>*</i></p>}
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="formPhoneNumber">
                                            <Form.Label><b>PhoneNumber</b></Form.Label>
                                            <Form.Control type="text" placeholder="PhoneNumber"
                                                          onChange={e => {
                                                              setPhoneNumber(e.target.value);
                                                              handleOnChangeValidating();
                                                          }}
                                                          onFocus={handleOnChangeValidating}
                                            />
                                            {errors.phoneNumber &&
                                                <p className="error-message">{errors.phoneNumber}<i>*</i></p>}
                                        </Form.Group>

                                        <section>
                                            {userInfo?.roles?.includes("ADMIN") ?
                                                <>
                                                    <Form.Label><b>Choose role</b></Form.Label>
                                                    <Form.Group>
                                                        <Form.Select name="roles"
                                                                     id="select-roles"
                                                                     onChange={e => {
                                                                         setRole(e.target.value)
                                                                     }}>
                                                            {roles.map((role, index) => (

                                                                <option key={index} value={role.role}>
                                                                    {role.role}
                                                                </option>
                                                            ))}
                                                        </Form.Select>
                                                    </Form.Group>
                                                </>
                                                : <> </>}

                                        </section>
                                        <div className="sign-up-btn">
                                            <button type="submit" disabled={errors}
                                                    onClick={() => createNewUser()}>
                                                Save
                                            </button>
                                            {saved ? (
                                                newUserData.email ?
                                                    <div> New
                                                        user {newUserData.profile.firstName} {newUserData.profile.lastName} was
                                                        created! A temporary password was sent to {newUserData.email}</div>
                                                    : <div>An error occurred while saving new user</div>
                                            ) : <div></div>
                                            }
                                        </div>
                                    </Popover.Body>
                                </Popover>
                            }
                        >
                            <button className="card-btn100__buttons">
                                Add user
                            </button>

                        </OverlayTrigger>
                    </div>
                    <div className="input-group">
                        <input type="search" className="form-control rounded" placeholder="Search by email" aria-label="Search"
                               aria-describedby="search-addon" onChange={e => setCriteria(e.target.value)}/>
                        <img src={searchIcon} alt="Search Icon" onClick={() => {
                            dispatch(searchUsers(criteria)).then(() => {
                                console.log(criteria)
                                setLoaded(true)
                            })
                        }}/>
                    </div>
                </div>
                <div className="page__horizontal-line"></div>
                {
                    loaded ?
                        <Table>
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Email</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Role</th>
                                {
                                    isAdmin ?
                                        <>
                                            <th>Delete</th>
                                            <th>Update</th>
                                        </>
                                        : <></>}
                            </tr>
                            </thead>
                            {
                                Array.isArray(users) && users.length >= 1 ?
                                    users.map(result => {
                                        return (
                                            <tbody key={result.id}>
                                            <tr>
                                                <td>{result.id}</td>
                                                <td>{result.email}</td>
                                                <td>{result.profile.firstName}</td>
                                                <td>{result.profile.lastName}</td>
                                                <td>{getUserMainRole(result.roles)}</td>
                                                {
                                                    isAdmin ?
                                                        <>
                                                            <td>
                                                                {result.email !== userData.email ?
                                                                    <img src={deleteIcon}
                                                                         alt="Delete Icon"
                                                                         onClick={() => deleteUserById(result.id)}/>
                                                                    : <></>}
                                                            </td>
                                                            <td>
                                                                <OverlayTrigger
                                                                    trigger="click"
                                                                    key='editUser'
                                                                    placement='right'
                                                                    rootClose={true}
                                                                    overlay={
                                                                        <Popover>
                                                                            <Popover.Header
                                                                                as="h3">{`Edit user`}</Popover.Header>
                                                                            <Popover.Body>
                                                                                <Form.Group
                                                                                    className="mb-3"
                                                                                    controlId="formEmail">
                                                                                    <Form.Label>Email</Form.Label>
                                                                                    <Form.Control
                                                                                        type="text"
                                                                                        placeholder="Email"
                                                                                        onChange={e => {
                                                                                            setEmail(e.target.value);
                                                                                            handleOnChangeValidating();
                                                                                        }}
                                                                                        onFocus={handleOnChangeValidating}/>
                                                                                    {emailTakenError &&
                                                                                        <p className="error-message">{emailTakenError}<i>*</i>
                                                                                        </p>}
                                                                                    {errors.email &&
                                                                                        <p className="error-message">{errors.email}<i>*</i>
                                                                                        </p>}
                                                                                </Form.Group>
                                                                                <Form.Group
                                                                                    className="mb-3"
                                                                                    controlId="formFirstName">
                                                                                    <Form.Label>First
                                                                                        Name</Form.Label>
                                                                                    <Form.Control
                                                                                        type="text"
                                                                                        placeholder="First name"
                                                                                        onChange={e => {
                                                                                            setFirstName(e.target.value);
                                                                                            handleOnChangeValidating();
                                                                                        }}
                                                                                        onFocus={handleOnChangeValidating}/>
                                                                                    {errors.firstName &&
                                                                                        <p className="error-message">{errors.firstName}<i>*</i>
                                                                                        </p>}
                                                                                </Form.Group>

                                                                                <Form.Group
                                                                                    className="mb-3"
                                                                                    controlId="formLastName">
                                                                                    <Form.Label>Last
                                                                                        Name</Form.Label>
                                                                                    <Form.Control
                                                                                        type="text"
                                                                                        placeholder="Last name"
                                                                                        onChange={e => {
                                                                                            setLastName(e.target.value);
                                                                                            handleOnChangeValidating();
                                                                                        }}
                                                                                        onFocus={handleOnChangeValidating}/>
                                                                                    {errors.lastName &&
                                                                                        <p className="error-message">{errors.lastName}<i>*</i>
                                                                                        </p>}
                                                                                </Form.Group>

                                                                                <button
                                                                                    className="card-btn100__buttons"
                                                                                    type="submit"
                                                                                    disabled={errors}
                                                                                    onClick={() => updateUserFields(result.id)}>
                                                                                    Save
                                                                                </button>
                                                                            </Popover.Body>
                                                                        </Popover>
                                                                    }
                                                                >
                                                                    <img src={updateIcon}
                                                                         alt="Update icon"/>

                                                                </OverlayTrigger>
                                                            </td>
                                                        </>
                                                        :
                                                        <></>
                                                }
                                            </tr>
                                            </tbody>

                                        )
                                    })
                                    :
                                    <></>
                            }
                        </Table>

                        :
                        <PulseLoader cssOverride={{
                            textAlign: "center",
                            paddingTop: "20%"
                        }} size={25}/>
                }
            </div>
        </>
    );


};

export default UsersComponent;