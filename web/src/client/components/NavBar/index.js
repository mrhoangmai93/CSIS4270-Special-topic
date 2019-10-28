import React, {Component} from 'react';
import {Menu} from "antd";
import {Link} from 'react-router-dom';
const VIEW_CALLBACK_ENUMS = {
    LOGOUT: 'LOGOUT'
};

const RenderMenu = (props) => {
    const {isAuthenticated, key, logout} = props;
    if (isAuthenticated) {
        return <Menu
            theme="dark"
            mode="horizontal"
            style={{lineHeight: '64px', textAlign: 'right'}}
        >
            <Menu.Item key="1"><Link to="/dashboard/lessons">Lessons</Link></Menu.Item>
            <Menu.Item key="2"><Link to="/dashboard/translation">Translation</Link></Menu.Item>
            <Menu.Item key="3"><Link to="/dashboard/game">Game</Link></Menu.Item>
            <Menu.Item key="4" onClick={logout}>Logout</Menu.Item>
        </Menu>;
    }
    return <Menu
        theme="dark"
        mode="horizontal"
        style={{lineHeight: '64px', textAlign: 'right'}}
    >
        <Menu.Item key="1"><Link to="/login">Login</Link></Menu.Item>
        <Menu.Item key="2"><Link to="/register">Register</Link></Menu.Item>
    </Menu>;
};

class NavBar extends Component {
    state = {
        key: undefined,
    };

    componentDidMount() {

    }

    handleLogout = () => {
      this.props.callbackHandler(VIEW_CALLBACK_ENUMS.LOGOUT, {});
    };

    render() {
        const {isAuthenticated} = this.props;

        return <RenderMenu isAuthenticated={isAuthenticated} key={this.state.key} logout={this.handleLogout}/>;

    }
}

export default NavBar;

export {
    VIEW_CALLBACK_ENUMS as NAVBAR_CALLBACK_ENUMS,
};
