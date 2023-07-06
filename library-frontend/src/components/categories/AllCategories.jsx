import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import { PulseLoader } from "react-spinners";
import {fetchCategoryList} from "../../redux/actions/category";
import {getCategoryList} from "../../redux/selectors/category";
import CategoriesList from "./CategoriesList";
import NavigationComponent from "../navigation/Navigation";
import UserLastActionMessageComponent from "../useraction/UserLastActionMessage";


const AllCategoriesComponent = () => {
    const [loaded, setLoaded] = useState(false);
    
    const dispatch = useDispatch();
    const categories = useSelector(getCategoryList);

    useEffect(() => {
        setLoaded(false);

        dispatch(fetchCategoryList()).then(() => {
            setLoaded(true)
        })
        
    }, [dispatch]);


    return (
        <>
            {
                loaded ?
                    <>
                        <NavigationComponent/>
                        <UserLastActionMessageComponent/>
                        <CategoriesList categories={categories}/>
                    </>
                    :
                    <PulseLoader cssOverride={{
                        textAlign: "center",
                        paddingTop: "20%"
                    }} size={25} />
            }
        </>
    );
};

export default AllCategoriesComponent;