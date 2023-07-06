import NavigationComponent from "./components/navigation/Navigation";
import {render as rtlRender, screen} from "@testing-library/react";
import {store} from "./store";
import {Provider} from "react-redux";
import {BrowserRouter} from "react-router-dom";
import LoginFormComponent from "./components/login/LoginForm";
import userEvent from "@testing-library/user-event";


export function render(component) {
    return (
        rtlRender(
            <BrowserRouter>
                <Provider store={store}>
                    {component}
                </Provider>
            </BrowserRouter>))
}

test('Matches Navigation Snapshot', async () => {
    const {baseElement} = render(<NavigationComponent/>);
    expect(baseElement).toMatchSnapshot();
})

test('Home is in the Nav component', async () => {
    render(<NavigationComponent/>);
    expect(screen.getByText(/home/i)).toBeInTheDocument()
})

describe('Login component tests', ()=>{

    test('Login component', async () => {
        render(<LoginFormComponent/>);

        const emailInput = screen.getByPlaceholderText('Email address')
        userEvent.type(emailInput, 'admin@gmail.com')
        expect(emailInput).toHaveValue('admin@gmail.com')

        const passwordInput = screen.getByPlaceholderText('Password')
        userEvent.type(passwordInput, '123')
        expect(passwordInput).toHaveValue('123')

        });
    })

