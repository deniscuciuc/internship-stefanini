import NoItemsFoundErrorComponent from "../errors/NoItemsFoundError";
import CategoryCard from "./CategoryCard";
import React from "react";


function CategoriesList(props) {
    return (
        <>
            <div className="page">
                {
                    Array.isArray(props.categories) && props.categories.length >= 1 ?
                        <>
                            <div className="page__title">
                                <h1>Categories</h1>
                            </div>
                            <div className="page__horizontal-line"></div>
                            <div className="page__cards">
                                {props.categories.map((category) => {
                                    return (
                                        <CategoryCard key={category.id}
                                                      id={category.id}
                                                      title={category.title}
                                        />
                                    )
                                })}
                            </div>
                        </>
                        : <NoItemsFoundErrorComponent itemName={"category"}/>
                }
            </div>
        </>
    );
}


export default CategoriesList;