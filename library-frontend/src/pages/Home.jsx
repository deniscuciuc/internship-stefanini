import '../assets/styles/home.css';
import NavigationComponent from "../components/navigation/Navigation";
import HomeContentComponent from '../components/home/HomeContent';
import UserLastActionMessageComponent from "../components/useraction/UserLastActionMessage";


const HomePage = () => {
    return (
        <>
            <NavigationComponent />
            <HomeContentComponent />
            <UserLastActionMessageComponent/>
        </>
    )
}

export default HomePage;