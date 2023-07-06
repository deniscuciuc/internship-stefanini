import {Provider} from 'react-redux';
import {store, persistor} from './store';
import { Routes, Route } from 'react-router-dom';
import {PersistGate} from 'redux-persist/integration/react';
import SessionFinished from './components/auth/SessionFinished';
import RequireAuth from './components/auth/RequireAuth';
import Layout from './components/layout/Layout';
import HomePage from './pages/Home';
import CategoriesPage from './pages/Categories';
import BooksPage from './pages/Books';
import AuthorPage from './pages/Author';
import LoginPage from './pages/Login';
import RegistrationPage from './pages/Registration';
import UnauthorizedPage from './pages/Unauthorized';
import ProfilePage from './pages/Profile';
import LogoutComponent from './components/logout/Logout';
import AdminComponent from './components/user/Admin';
import UsersComponent from './components/user/Users';
import PageNotFoundPage from './pages/PageNotFound';
import HistoryComponent from './components/profile/History';
import ProfileBooksComponent from './components/profile/ProfileBooks';
import ProfileAuthComponent from './components/profile/ProfileAuth';
import BookByCategoryComponent from "./components/books/BooksByCategory";
import BookByAuthorComponent from "./components/books/BooksByAuthor";
import BooksByCriteriaComponent from "./components/books/BooksByCriteria";
import ManageBooksComponent from "./components/books/BookAdminTable";
import ProfileInfoComponent from "./components/profile/ProfileInfo";
import WelcomePage from "./pages/Welcome";
import ResetPasswordComponent from "./components/login/ResetPassword";
import ForgotPasswordComponent from "./components/login/ForgotPassword";
import EmailConfirmationPage from "./pages/EmailConfirmation";


function App() {
    return (
        <Provider store={store}>
            <PersistGate persistor={persistor}>
                <SessionFinished>
                    <Routes>
                        <Route path="/" element={<Layout/>}>
                            <Route exact path="/" element={<HomePage/>}/>

                            <Route path="/categories" element={<CategoriesPage/>}/>
                            <Route path="/authors" element={<AuthorPage/>}/>
                            <Route path="/books" element={<BooksPage/>}/>

                            <Route path="/login" element={<LoginPage/>}/>
                            <Route path="/registration" element={<RegistrationPage/>}/>
                            <Route path="/unauthorized" element={<UnauthorizedPage/>}/>
                            <Route path="/forgot-password" element={<ForgotPasswordComponent/>}/>

                            <Route path="/books-by-category/:categoryId" element={<BookByCategoryComponent />}/>
                            <Route path="/books-by-author/:authorId" element={<BookByAuthorComponent />}/>
                            <Route path="/books/search_result/:criteria" element={<BooksByCriteriaComponent />}/>
                            <Route path="/email-confirmation/:token" element={<EmailConfirmationPage />}/>


                            <Route path="/resetPassword/:userId/:email" element={<ResetPasswordComponent />}/>

                            <Route element={<RequireAuth allowedRoles={['USER', 'LIBRARIAN', 'ADMIN']}/>}>
                                <Route exact path="/welcome" element={<WelcomePage/>}/>
                                <Route path="/profile" element={<ProfilePage renderComponent={<ProfileInfoComponent/>}/>}/>
                                <Route path="/logout" element={<LogoutComponent/>}/>
                                <Route path="/profile/myhistory" element={<ProfilePage renderComponent={<HistoryComponent/>}/>}/>
                                <Route path="/profile/mybooks" element={<ProfilePage renderComponent={<ProfileBooksComponent/>}/>}/>
                                <Route path="/profile/authorization" element={<ProfilePage renderComponent={<ProfileAuthComponent/>}/>}/>
                            </Route>

                            <Route element={<RequireAuth allowedRoles={['ADMIN', 'LIBRARIAN']}/>}>
                                <Route path="/profile/admin" element={<ProfilePage renderComponent={<AdminComponent/>}/>}/>
                                <Route path="/users" element={<UsersComponent/>}/>
                                <Route path="/manage-book" element={<ManageBooksComponent/>}/>
                            </Route>

                            <Route path="*" element={<PageNotFoundPage/>}/>
                        </Route>
                    </Routes>
                </SessionFinished>
            </PersistGate>
        </Provider>

    );

}

export default App;