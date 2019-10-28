import React from 'react';
import { Provider } from "react-redux";
import { Switch } from 'react-router' // react-router v4/v5
import { ConnectedRouter } from "connected-react-router";
import Index from './pages/Index';
import Login from './pages/Login';
import Lessons from './pages/Lessons';
import Register from './pages/Register';
import FancyRoute from './components/FancyRoute';
import configureStore, {
  history
} from "./states/configureStore";
const store = configureStore({});

const routes = [
  {
    title: 'Home',
    path: '/',
    exact: true,
    component: Index,
  },
  {
    title: 'Login',
    path: '/login',
    exact: true,
    component: Login,
  },
  {
    title: 'Register',
    path: '/register',
    exact: true,
    component: Register,
  },
  {
    title: 'Lessons',
    path: '/dashboard/lessons',
    exact: true,
    component: Lessons,
  },
];

function App() {
  return (
      <Provider store={store}>
          <ConnectedRouter history={history}>
            <Switch>
                {/* eslint-disable-next-line react/no-array-index-key,react/jsx-props-no-spreading */}
                {routes.map((route, i) => (
                    <FancyRoute key={i} {...route} />
                ))}
            </Switch>
          </ConnectedRouter>
      </Provider>
  );
}

export default App;
