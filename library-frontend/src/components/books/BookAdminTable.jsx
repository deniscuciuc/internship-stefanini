import React, {useEffect, useState} from "react";
import {Table} from "react-bootstrap";
import Popover from "react-bootstrap/Popover";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";

import {useDispatch, useSelector} from "react-redux";
import {getBookData, getBookList, getTotalNumberOfBooks} from "../../redux/selectors/allBooks";
import {
    deleteBook,
    fetchBookList,
    getNumberOfBooks,
    insertBookWithExistingCategoryAndAuthor, searchBooks
} from "../../redux/actions/book";
import {PulseLoader} from "react-spinners";
import NavigationComponent from "../navigation/Navigation";

import {fetchCategoryList, insertCategory} from "../../redux/actions/category";
import {fetchAllAuthors, fetchAuthorList, getNumberOfAuthors, insertAuthor} from "../../redux/actions/author";
import {getCategoryData, getCategoryList} from "../../redux/selectors/category";
import {getAuthorData, getAuthorList} from "../../redux/selectors/author";
import searchIcon from "../../assets/images/icons/profile/search.svg";
import {useNavigate} from "react-router-dom";

import prevPageIcon from "../../assets/images/icons/arrow-left-circle.svg";
import nextPageIcon from "../../assets/images/icons/arrow-right-circle.svg";
import sortIcon from "../../assets/images/icons/sorting-arrows.svg";
import deleteIcon from '../../assets/images/icons/profile/trash.svg';
import '../../assets/styles/bookadmin.css';

const ManageBooksComponent = () => {
    const dispatch = useDispatch();
    const [loaded, setLoaded] = useState(false);

    /* Search */
    const [criteria, setCriteria] = useState('');

    /* Create book */
    const newBookData = useSelector(getBookData);
    const [saved, setSaved] = useState(false);
    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('');
    const [shelfNumber, setShelfNumber] = useState('');
    const [firstName, setFirstName] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [lastName, setLastName] = useState('');
    const [biography, setBiography] = useState('');
    const [categoryTitle, setCategoryTitle] = useState('');
    const [category, setCategory] = useState('');
    const [author, setAuthor] = useState('');

    /* Sorting */
    const pageSize = 10;
    const sortByTypes = ['Time Added', 'Status', 'Title'];
    const [pageCount, setPageCount] = useState(1);
    const [maxPages, setMaxPages] = useState(1);
    const [sortBy, setSortBy] = useState("Time Added");
    const [sortOrder, setSortOrder] = useState("asc");

    /* Books, Authors and Categories */
    const books = useSelector(getBookList);
    const numberOfBooks = useSelector(getTotalNumberOfBooks);
    const categories = useSelector(getCategoryList);
    const authors = useSelector(getAuthorList);
    const newCategory = useSelector(getCategoryData);
    const newAuthor = useSelector(getAuthorData);


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

    const handleSaved = () => {
        setSaved(false)
    };

    const addCategory = (e) => {
        e.preventDefault()

        const categoryData = {
            "title": categoryTitle
        }

        dispatch(insertCategory(categoryData)).then(() => {
            setSaved(true)
        });
    }

    const addAuthor = (e) => {
        e.preventDefault()

        const authorData = {
            "firstName": firstName,
            "lastName": lastName,
            "birthDate": birthDate,
            "biography": biography
        }

        dispatch(insertAuthor(authorData)).then(() => {
            setSaved(true)
        });
    }

    const insertBook = (e) => {
        e.preventDefault()

        const bookData = {
            "title": title,
            "description": description,
            "shelfNumber": shelfNumber
        }

        dispatch(insertBookWithExistingCategoryAndAuthor(bookData, category, author)).then(() => {
            setSaved(true)
            window.location.reload();
        })
    }

    const deleteBookById = (id) => {
        dispatch(deleteBook(id)).then(() => {
            setLoaded(false)
        });
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

            if (numberOfBooks <= pageSize) {
                setMaxPages(1);
            }

            dispatch(fetchBookList(pageCount, pageSize, sortByConverted.toLowerCase(), sortOrder)).then(() => {
                setLoaded(true);
            })
        });
    }, [loaded, sortBy, sortOrder, maxPages, numberOfBooks, newBookData, pageSize]);

    useEffect(() => {
        dispatch(fetchCategoryList());
    }, [newCategory]);

    useEffect(() => {
        dispatch(fetchAllAuthors());
    }, [newAuthor]);

    return (
        <>
            <NavigationComponent/>
            <div className="book-admin-page">
                <div className="page__header">
                    <h1>Books</h1>
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
                    <div className="manage-button">


                        <div className="manage-button__item">
                            <OverlayTrigger
                                trigger="click"
                                key='new book'
                                placement='left'
                                rootClose={true}
                                onExited={handleSaved}
                                overlay={
                                    <Popover>
                                        <Popover.Header
                                            as="h3">{`New Book`}</Popover.Header>
                                        <Popover.Body>
                                            <Form.Group className="mb-3" controlId="formBook">
                                                <Form.Label>Title</Form.Label>
                                                <Form.Control type="text" placeholder="Title"
                                                              onChange={e => setTitle(e.target.value)}/>
                                            </Form.Group>
                                            <Form.Group className="mb-3" controlId="formBook">
                                                <Form.Label>Description</Form.Label>
                                                <Form.Control type="text" placeholder="Description"
                                                              onChange={e => setDescription(e.target.value)}/>
                                            </Form.Group>
                                            <Form.Group className="mb-3" controlId="formBook">
                                                <Form.Label>ShelfNumber</Form.Label>
                                                <Form.Control type="text" placeholder="ShelfNumber"
                                                              onChange={e => setShelfNumber(e.target.value)}/>
                                            </Form.Group>
                                            <Form.Label>Choose Category</Form.Label>
                                            <Form.Group>
                                                <Form.Select name="category"
                                                             onChange={e => setCategory(e.currentTarget.value)}>
                                                    {Array.isArray(categories)
                                                        ? categories.map(category =>
                                                            <option key={category.id} value={category.id}>
                                                                {category.title}
                                                            </option>)
                                                        : <> </>}
                                                </Form.Select>
                                            </Form.Group>

                                            <Form.Label>Choose Author</Form.Label>
                                            <Form.Group>
                                                <Form.Select name="authors"
                                                             onChange={e => setAuthor(e.currentTarget.value)}>
                                                    {
                                                        Array.isArray(authors)
                                                            ? authors.map(author =>
                                                                <option key={author.id} value={author.id}>
                                                                    {author.fullName}
                                                                </option>
                                                            )
                                                            : <> </>
                                                    }
                                                </Form.Select>
                                            </Form.Group>
                                            <Form.Label></Form.Label>
                                            <Form.Group>

                                                <Button className="card-btn100__buttons" type="submit"
                                                        onClick={insertBook}>
                                                    Save
                                                </Button>
                                            </Form.Group>
                                            {saved ? (
                                                newBookData.title ?
                                                    <div> New book {newBookData.title} was
                                                        added to library </div>
                                                    : <div> an error occurred </div>
                                            ) : <div></div>
                                            }

                                        </Popover.Body>
                                    </Popover>
                                }
                            >

                                <button className="card-btn100__buttons">
                                    Add Book
                                </button>

                            </OverlayTrigger>
                        </div>

                        <div className="manage-button__item">
                            <OverlayTrigger
                                trigger="click"
                                key='new author'
                                placement='right'
                                rootClose={true}
                                onExited={handleSaved}
                                overlay={
                                    <Popover>
                                        <Popover.Header
                                            as="h3">{`New Author`}</Popover.Header>
                                        <Popover.Body>
                                            <Form.Group className="mb-3" controlId="formBook">
                                                <Form.Label>Author Details: </Form.Label>
                                                <Form.Control type="text" placeholder="firstName"
                                                              onChange={e => setFirstName(e.target.value)}/>
                                                <Form.Control type="text" placeholder="lastName"
                                                              onChange={e => setLastName(e.target.value)}/>
                                                <Form.Control type="text" placeholder="birthDate(yyyy-mm-dd)"
                                                              onChange={e => setBirthDate(e.target.value)}/>
                                                <Form.Control type="text" placeholder="biography"
                                                              onChange={e => setBiography(e.target.value)}/>
                                            </Form.Group>
                                            <Form.Label></Form.Label>
                                            <Form.Group>
                                                <Button className="card-btn100__buttons"
                                                        onClick={addAuthor}>
                                                    Save
                                                </Button>
                                            </Form.Group>
                                            {saved ? (
                                                newAuthor.fullName ?
                                                    <div> New author {newAuthor.fullName} was
                                                        added to library </div>
                                                    : <div> an error occurred </div>
                                            ) : <div></div>
                                            }
                                        </Popover.Body>
                                    </Popover>
                                }
                            >
                                <button className="card-btn100__buttons"> Add Author</button>

                            </OverlayTrigger>
                        </div>

                        <div className="manage-button__item">
                            <OverlayTrigger
                                trigger="click"
                                key='new category'
                                placement='left'
                                rootClose={true}
                                onExited={handleSaved}
                                overlay={
                                    <Popover>
                                        <Popover.Header
                                            as="h3">{`New Category`}</Popover.Header>
                                        <Popover.Body>
                                            <Form.Label>Create Category</Form.Label>
                                            <Form.Group className="mb-3" controlId="formBook">

                                                <Form.Control type="text" placeholder="title"
                                                              onChange={e => setCategoryTitle(e.target.value)}/>

                                            </Form.Group>
                                            <Form.Label></Form.Label>
                                            <Form.Group>
                                                <Button className="card-btn100__buttons"
                                                        onClick={addCategory}>
                                                    Save
                                                </Button>
                                            </Form.Group>
                                            {saved ? (
                                                newCategory.title ?
                                                    <div> New category {newCategory.title} was
                                                        added to library </div>
                                                    : <div> an error occurred </div>
                                            ) : <div></div>
                                            }
                                        </Popover.Body>
                                    </Popover>
                                }
                            >
                                <button className="card-btn100__buttons"> Add Category</button>

                            </OverlayTrigger>
                        </div>

                        <div className="input-group">
                            <input type="search" className="form-control rounded" placeholder="Search"
                                   aria-label="Search"
                                   aria-describedby="search-addon" onChange={e => setCriteria(e.target.value)}/>
                            <img src={searchIcon} alt="Search Icon" onClick={() => {
                                dispatch(searchBooks(criteria)).then(() => {
                                    setLoaded(true)
                                })
                            }}/>
                        </div>

                    </div>
                </div>
                <div className="page__horizontal-line"></div>
                {
                    loaded ?
                        <Table>
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Shelf number</th>
                                <th>Status</th>
                                <th>Delete Book</th>
                            </tr>
                            </thead>
                            {
                                Array.isArray(books) && books.length >= 1 ?
                                    books.map(result => {
                                        return (
                                            <tbody key={result.id}>
                                            <tr>
                                                <td>{result.id}</td>
                                                <td>{result.title}</td>
                                                <td>{result.description}</td>
                                                <td>{result.shelfNumber}</td>
                                                <td>{result.status}</td>
                                                <td>
                                                    <img src={deleteIcon} alt="Delete Icon"
                                                         onClick={() => deleteBookById(result.id)}/>
                                                </td>
                                            </tr>
                                            </tbody>
                                        )
                                    })
                                    : <></>
                            }
                        </Table>

                        : <PulseLoader cssOverride={{
                            textAlign: "center",
                            paddingTop: "20%"
                        }} size={25}/>
                }
            </div>
        </>
    );

};
export default ManageBooksComponent;