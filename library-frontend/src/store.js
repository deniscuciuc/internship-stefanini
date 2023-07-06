import {createStore, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';
import {createLogger} from 'redux-logger';
import {persistStore} from 'redux-persist';
import rootReducer from './redux/reducers/rootReducer';

const middleware = [thunk, createLogger()];

export const store = createStore(rootReducer, compose(applyMiddleware(...middleware)));

export const persistor = persistStore(store);

export default { store, persistor };
