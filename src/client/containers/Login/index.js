import React from 'react';
import {Form, Icon, Input, Button, Checkbox} from 'antd';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import LoginForm from "../../components/Login";

class Login extends React.Component {

    render() {
        return (<>
            <LoginForm/>
        </>);
    }
}


const mapStateToProps = state => ({
    login: state.authentication.login
});

const mapDispatchToProps = {};


export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Login)
);