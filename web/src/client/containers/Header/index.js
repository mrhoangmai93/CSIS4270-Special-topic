import React from 'react';
import NavBar from '../../components/NavBar';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux"

class Header extends React.Component {
    render() {
        return (
            <>
            <NavBar />
            </>
        )
    }
}
const mapStateToProps = state => ({

});

const mapDispatchToProps = {};

export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Header)
);