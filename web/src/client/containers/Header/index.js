import React from 'react';
import NavBar from '../../components/NavBar';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux"

class Header extends React.Component {
    render() {
        const {pathname} = this.props.location;
        return (
            <>
            <NavBar location={pathname} isAuthenticated={this.props.isAuthenticated}/>
            </>
        )
    }
}
const mapStateToProps = state => ({
    isAuthenticated: state.authentication.login.get("isAuthenticated")
});

const mapDispatchToProps = {};

export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Header)
);