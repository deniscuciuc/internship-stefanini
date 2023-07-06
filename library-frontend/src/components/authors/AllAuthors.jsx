import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchAuthorList, getNumberOfAuthors} from "../../redux/actions/author";
import {getAuthorList, getTotalNumberOfAuthors} from "../../redux/selectors/author";
import AuthorsList from "./AuthorsList";
import {PulseLoader} from "react-spinners";
import NavigationComponent from "../navigation/Navigation";

import sortIcon from '../../assets/images/icons/sorting-arrows.svg';
import nextPageIcon from '../../assets/images/icons/arrow-right-circle.svg';
import prevPageIcon from '../../assets/images/icons/arrow-left-circle.svg';

import '../../assets/styles/allbooks.css';
import '../../assets/styles/pagination-sorting.css';
import Form from "react-bootstrap/Form";

const AllAuthorsComponent = () => {
    const [loaded, setLoaded] = useState(false);
    const dispatch = useDispatch();

    /* Sorting */
    const pageSize = 6;
    const sortByTypes = ['Time Added', 'First name', 'Last name', 'Birth date'];
    const [pageCount, setPageCount] = useState(1);
    const [maxPages, setMaxPages] = useState(1);
    const [sortBy, setSortBy] = useState("Time Added");
    const [sortOrder, setSortOrder] = useState("asc");

    /* Authors */
    const authors = useSelector(getAuthorList);
    const numberOfAuthors = useSelector(getTotalNumberOfAuthors);

    const goToTheNextPage = () => {
        setLoaded(false);

        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        } else if (sortBy.toString() === sortByTypes.at(1).toString()) {
            sortByConverted = "firstName";
        } else if (sortBy.toString() === sortByTypes.at(2).toString()) {
            sortByConverted = "lastName";
        } else if (sortBy.toString() === sortByTypes.at(3).toString()) {
            sortByConverted = "birthDate";
        }

        dispatch(fetchAuthorList(pageCount + 1, pageSize, sortByConverted, sortOrder)).then(() => {
            setLoaded(true);
            setPageCount(pageCount + 1);
        })
    }

    const goToThePrevPage = () => {
        setLoaded(false);

        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        } else if (sortBy.toString() === sortByTypes.at(1).toString()) {
            sortByConverted = "firstName";
        } else if (sortBy.toString() === sortByTypes.at(2).toString()) {
            sortByConverted = "lastName";
        } else if (sortBy.toString() === sortByTypes.at(3).toString()) {
            sortByConverted = "birthDate";
        }

        dispatch(fetchAuthorList(pageCount - 1, pageSize, sortByConverted, sortOrder)).then(() => {
            setLoaded(true);
            setPageCount(pageCount - 1);
        })
    }

    const switchSortOrder = () => {
        if (sortOrder === 'asc') setSortOrder('desc');
        if (sortOrder === 'desc') setSortOrder('asc');
    }

    useEffect(() => {
        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        } else if (sortBy.toString() === sortByTypes.at(1).toString()) {
            sortByConverted = "firstName";
        } else if (sortBy.toString() === sortByTypes.at(2).toString()) {
            sortByConverted = "lastName";
        } else if (sortBy.toString() === sortByTypes.at(3).toString()) {
            sortByConverted = "birthDate";
        }

        dispatch(getNumberOfAuthors()).then(() => {

            if (numberOfAuthors % pageSize > 0) {
                setMaxPages(Math.floor(numberOfAuthors / pageSize + 1));
            } else {
                setMaxPages(Math.floor(numberOfAuthors / pageSize));
            }

            console.log("Max pages: " + maxPages);

            if (numberOfAuthors <= pageSize) {
                setMaxPages(1);
            }

            dispatch(fetchAuthorList(pageCount, pageSize, sortByConverted, sortOrder)).then(() => {
                setLoaded(true);
            })
        });
    }, [sortBy, sortOrder, maxPages, numberOfAuthors]);

    return (
        <>
            {
                loaded ?
                    <div>
                        <NavigationComponent/>
                        <div className="page">
                            <div className="all-books-page-header">
                                <div><h1>Authors</h1></div>
                                <div className="pagination-container__authors">
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
                            </div>
                            <div className="page__horizontal-line"></div>
                            <AuthorsList authors={authors}/>
                        </div>
                    </div>
                    :
                    <PulseLoader cssOverride={{
                        textAlign: "center",
                        paddingTop: "20%"
                    }} size={25}/>
            }
        </>
    );
};

export default AllAuthorsComponent;