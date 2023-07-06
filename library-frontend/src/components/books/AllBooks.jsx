import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {getBookList, getLastModifiedBook, getTotalNumberOfBooks} from "../../redux/selectors/allBooks";
import {fetchBookList, getNumberOfBooks, searchBooks} from "../../redux/actions/book";
import BookList from "./BookList";
import {PulseLoader} from "react-spinners";
import {useNavigate} from "react-router-dom";
import NavigationComponent from "../navigation/Navigation";
import searchIcon from '../../assets/images/icons/profile/search.svg';
import sortIcon from '../../assets/images/icons/sorting-arrows.svg';
import nextPageIcon from '../../assets/images/icons/arrow-right-circle.svg';
import prevPageIcon from '../../assets/images/icons/arrow-left-circle.svg';
import '../../assets/styles/allbooks.css';
import '../../assets/styles/pagination-sorting.css';
import Form from "react-bootstrap/Form";
import UserLastActionMessageComponent from "../useraction/UserLastActionMessage";

const AllBooksComponent = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [loaded, setLoaded] = useState(false);

    /* Search by criteria */
    const [criteria, setCriteria] = useState('');
    const url = "/books/search_result/" + criteria;

    /* Sorting */
    const pageSize = 6;
    const sortByTypes = ['Time Added', 'Status', 'Title'];
    const [pageCount, setPageCount] = useState(1);
    const [maxPages, setMaxPages] = useState(1);
    const [sortBy, setSortBy] = useState("status");
    const [sortOrder, setSortOrder] = useState("asc");

    /* Books */
    const books = useSelector(getBookList);
    const numberOfBooks = useSelector(getTotalNumberOfBooks);
    const lastModified = useSelector(getLastModifiedBook);


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

    useEffect(() => {
        let sortByConverted = sortBy;
        if (sortBy.toString() === sortByTypes.at(0).toString()) {
            sortByConverted = "id";
        }

        dispatch(getNumberOfBooks()).then(() => {
            if (numberOfBooks % pageSize > 0) {
                setMaxPages(Math.floor(numberOfBooks / pageSize + 1));
            } else {
                setMaxPages(Math.floor(numberOfBooks / pageSize));
            }

            console.log("Max pages: " + maxPages);

            if (numberOfBooks <= pageSize) {
                setMaxPages(1);
            }

            dispatch(fetchBookList(pageCount, pageSize, sortByConverted.toLowerCase(), sortOrder)).then(() => {
                setLoaded(true);
            })
        });
    }, [lastModified, sortBy, sortOrder, maxPages, numberOfBooks]);

    return (
        <>
            {
                loaded ?
                    <div>
                        <NavigationComponent/>
                        <UserLastActionMessageComponent/>
                        <div className="page">
                            <div className="all-books-page-header">
                                <div><h1>Books</h1></div>
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
                                <div className="input-group">
                                    <input type="search" className="form-control rounded" placeholder="Search"
                                           aria-label="Search"
                                           aria-describedby="search-addon" onChange={e => setCriteria(e.target.value)}/>
                                    <img src={searchIcon} alt="Search Icon" onClick={() => {
                                        dispatch(searchBooks(criteria))
                                        navigate(url)
                                    }}/>
                                </div>
                            </div>
                            <div className="page__horizontal-line"></div>
                            <BookList books={books}/>
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

export default AllBooksComponent;