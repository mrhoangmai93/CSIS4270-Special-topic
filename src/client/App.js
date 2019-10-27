import React from 'react';
import { Provider } from "react-redux";
import { Switch } from 'react-router' // react-router v4/v5
import { ConnectedRouter } from "connected-react-router";
import Index from './pages/Index';
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
