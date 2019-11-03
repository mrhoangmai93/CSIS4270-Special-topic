import React from 'react';
import NavBar, {NAVBAR_CALLBACK_ENUMS} from '../../components/NavBar';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux"
import {LOGIN_CALLBACK_ENUMS} from "../../components/Login";
import {getTopics} from '../Header/header.action';
import {logout} from '../Login/login.action';
class Header extends React.Component {
    componentDidMount() {
        this.props.getTopics();
    }
    callbackHandler = (type, data) => {
        switch (type) {
            case NAVBAR_CALLBACK_ENUMS.LOGOUT:
                this.props.logout();
                break;
            default:
                break;
        }
    };
    render() {
        const {pathname} = this.props.location;
        return (
            <>
            <NavBar 
                location={pathname} 
                isAuthenticated={this.props.isAuthenticated} 
                callbackHandler={this.callbackHandler}
                topics={this.props.topics}/>
            </>
        )
    }
}
const mapStateToProps = state => ({
    isAuthenticated: state.authentication.login.get("isAuthenticated"),
    topics: state.header.get("topics")
});

const mapDispatchToProps = {
    getTopics,
    logout
};

export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Header)
);