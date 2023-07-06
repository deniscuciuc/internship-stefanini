export default function validateUserAdminCreates(values) {
    const EMAIL_VALIDATOR_REGEX_CODE = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;


    let errors = {};


    if (values.firstName.trim() && values.lastName.trim() && values.email.trim() &&
        EMAIL_VALIDATOR_REGEX_CODE.test(values.email)) {
        return false;
    }

    if (!values.firstName.trim()) {
        errors.firstName = "First name required";
    }

    if (!values.lastName.trim()) {
        errors.lastName = "Last name required";
    }

    if (!values.email.trim()) {
        errors.email = "Email required";
    } else if (!EMAIL_VALIDATOR_REGEX_CODE.test(values.email)) {
        if (values.email.toString().charAt(0) === "@") {
            errors.email = "Email address must contain characters before '@'";
        } else if (values.email.toString().indexOf("@") > (values.email.toString().indexOf("."))) {
            errors.email = "Email address must contain '@' before '.'";
        } else if (values.email.toString() !== "@") {
            errors.email = "Email address must contain '@'";
        } else if (values.email.toString() !== ".") {
            errors.email = "Email address must contain '.'";
        }
    }
    if (values.phoneNumber) {
        if (!values.phoneNumber.trim()) {
            errors.phoneNumber = "Phone number required";
        }
    }


    return errors;
};

