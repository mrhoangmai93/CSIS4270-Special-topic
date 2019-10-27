import React from 'react';
import {Form, Icon, Input, Button, Checkbox} from 'antd';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import RegisterForm from "../../components/Register";

class Register extends React.Component {
    
    render() {
        return (<>
            <RegisterForm/>
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
    )(Register)
);