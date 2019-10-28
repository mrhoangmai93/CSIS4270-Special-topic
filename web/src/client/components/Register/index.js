import React from 'react';
import {Button, Checkbox, Form, Icon, Input} from "antd";
import {Link} from 'react-router-dom';

class Register extends React.Component {

    componentDidMount() {

    }

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
            }
        });
    };

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <div className="lg:w-1/3 mx-auto p-2 mt-8 mb-8">
                <Form onSubmit={this.handleSubmit} className="login-form">
                    <Form.Item>
                        {getFieldDecorator('username', {
                            rules: [{required: true, message: 'Please input your username!'}],
                        })(
                            <Input
                                prefix={<Icon type="user" style={{color: 'rgba(0,0,0,.25)'}}/>}
                                placeholder="Username"
                            />,
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('password', {
                            rules: [{required: true, message: 'Please input your Password!'}],
                        })(
                            <Input
                                prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>}
                                type="password"
                                placeholder="Password"
                            />,
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('Confirm password', {
                            rules: [{required: true, message: 'Please retype your Password!'}],
                        })(
                            <Input
                                prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>}
                                type="password"
                                placeholder="Confirm Password"
                            />,
                        )}
                    </Form.Item>
                    <Form.Item>
                        <div className="block">
                        </div>
                        <Button type="primary" htmlType="submit" className="w-full block">
                            Register
                        </Button>
                        Already has an account?
                        <Link to="/login" className="text-green-700"> Login here</Link>
                    </Form.Item>
                </Form>
            </div>
        );
    }
}

const WrappedRegisterForm = Form.create({name: 'register'})(Register);

export default WrappedRegisterForm;