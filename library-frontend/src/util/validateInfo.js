export default function validateInfo(values) {
    const EMAIL_VALIDATOR_REGEX_CODE = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const PASSWORD_VALIDATOR_REGEX_CODE = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/;
    const PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_LOWERCASE_CHARACTER_REGEX = /(?=.*[a-z])/;
    const PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_UPPERCASE_CHARACTER_REGEX = /(?=.*[A-Z])/;
    const PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMERIC_CHARACTER_REGEX = /(?=.*[0-9])/;
    const PASSWORD_MUST_CONTAIN_AT_LEAST_EIGHT_CHARACTERS_REGEX = /(?=.{8,})/;
    const PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_SPECIAL_CHARACTER_REGEX = /(?=.*[!@#$%^&*])/;

    let errors = {};
    let isPasswordMatch = values.password === values.confirmedPassword;

    if (values.firstName.trim() && values.lastName.trim() && values.email.trim() &&
        EMAIL_VALIDATOR_REGEX_CODE.test(values.email) && values.password.trim() &&
        PASSWORD_VALIDATOR_REGEX_CODE.test(values.password) && isPasswordMatch) {
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
    }  else if (!EMAIL_VALIDATOR_REGEX_CODE.test(values.email)) {
        if (values.email.toString().charAt(0) === "@") {
            errors.email = "Email address must contain characters before '@'";
        }else if (values.email.toString().indexOf( "@" ) > (values.email.toString().indexOf( "." ) )) {
            errors.email = "Email address must contain '@' before '.'";
        }else if (values.email.toString() !== "@") {
            errors.email = "Email address must contain '@'";
        }else if (values.email.toString() !== ".") {
            errors.email = "Email address must contain '.'";
        }
    }

    if (!values.phoneNumber.trim()) {
        errors.phoneNumber = "Phone number required";
    }


    if (!values.password.trim()) {
        errors.password = "Password required";
    } else if (!PASSWORD_VALIDATOR_REGEX_CODE.test(values.password)) {

        if (!PASSWORD_MUST_CONTAIN_AT_LEAST_EIGHT_CHARACTERS_REGEX.test(values.password)) {
            errors.password = "Password must be eight characters or longer";
        } else if (!PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_LOWERCASE_CHARACTER_REGEX.test(values.password)) {
            errors.password = "Password must contain at least 1 lowercase alphabetical character";
        } else if (!PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_UPPERCASE_CHARACTER_REGEX.test(values.password)) {
            errors.password = "Password must contain at least 1 uppercase alphabetical character";
        } else if (!PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMERIC_CHARACTER_REGEX.test(values.password)) {
            errors.password = "Password must contain at least 1 numeric character";
        } else if (!PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_SPECIAL_CHARACTER_REGEX.test(values.password)) {
            errors.password = "Password must contain at least one special character";
        }
    }

    if (!isPasswordMatch) {
        errors.confirmedPassword = "Password does not match";
    }
    return errors;
};

