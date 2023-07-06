import {useDispatch, useSelector} from "react-redux";
import {getUserProfileData} from "../../redux/selectors/profile";
import Popover from "react-bootstrap/Popover";
import Form from "react-bootstrap/Form";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import React, {useState} from "react";
import {updateUser} from "../../redux/actions/user";
import {getUserData} from "../../redux/selectors/login";
import {getUpdatedUserData} from "../../redux/selectors/user";
import {ClipLoader} from "react-spinners";
import {setLastUserAction} from "../../redux/actions/login";


const ProfileInfoComponent = () => {
    const profileData = useSelector(getUserProfileData);
    const userData = useSelector(getUserData)
    const dispatch = useDispatch();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [loaded, setLoaded] = useState(false);
    const updatedData = useSelector(getUpdatedUserData)

    const updateUserFields = (id) => {
        const userDetails = {
            "email": userData.email,
            "profile": {
                "firstName": firstName,
                "lastName": lastName,
                "phoneNumber": phoneNumber
            }
        };

        dispatch(updateUser(id, userDetails)).then(() => {
            setLoaded(true);

            setTimeout(() => {
                window.location.reload();
            }, 500)
        });
    }


    return (
        <div className="profile-side-content__user-info" key={profileData.id}>
            <div className="profile-side-content">
                <div className="profile-side-content__name">
                    <h5>First name: {profileData.firstName}</h5>
                    <h5>Last name: {profileData.lastName}</h5>
                </div>
                <div className="user-info__phone-number">
                    <h5>Phone number: {profileData.phoneNumber}</h5>
                </div>
                <div className="user-info__update-info">
                    <OverlayTrigger
                        trigger="click"
                        key='right'
                        placement='right'
                        rootClose={true}
                        overlay={
                            <Popover>
                                <Popover.Header
                                    as="h3">{`Edit profile info`}</Popover.Header>
                                <Popover.Body>

                                    <Form.Group className="mb-3" controlId="formFirstName">
                                        <Form.Label>First Name</Form.Label>
                                        <Form.Control type="text" placeholder="First name"
                                                      onChange={e => setFirstName(e.target.value)}/>
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="formLastName">
                                        <Form.Label>Last Mame</Form.Label>
                                        <Form.Control type="text" placeholder="Last name"
                                                      onChange={e => setLastName(e.target.value)}/>
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formLastName">
                                        <Form.Label>Phone Number</Form.Label>
                                        <Form.Control type="text" placeholder="Phone number"
                                                      onChange={e => setPhoneNumber(e.target.value)}/>
                                    </Form.Group>

                                    <button className="card-btn100__buttons"
                                            onClick={() => updateUserFields(profileData.id)}>
                                        {
                                            loaded ?
                                                <ClipLoader
                                                    color="black"
                                                    size={25}
                                                    speedMultiplier={0.6}
                                                />
                                                :
                                                <>Save</>
                                        }
                                    </button>
                                    {
                                        loaded ?
                                            (
                                                updatedData.email ?
                                                    <div className="error-message">
                                                        <></>
                                                    </div>
                                                    : <div>Failed to update</div>
                                            )
                                            : <></>
                                    }
                                </Popover.Body>
                            </Popover>
                        }
                    >
                        <button className="card-btn-updateProfile__buttons">
                            Edit
                        </button>
                    </OverlayTrigger>
                </div>
            </div>

        </div>
    )
}

export default ProfileInfoComponent;
