import React from 'react';
import {Button, Checkbox, Form, Icon, Input} from "antd";
import {Link} from 'react-router-dom';

class Login extends React.Component {

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
                        <div className="block">
                        {getFieldDecorator('remember', {
                            valuePropName: 'checked',
                            initialValue: true,
                        })(<Checkbox>Remember me</Checkbox>)}
                        </div>
                        <Button type="primary" htmlType="submit" className="w-full block">
                            Log in
                        </Button> 
                        Or <Link to="/register" className="text-green-700">Register now!</Link>
                    </Form.Item>
                </Form>
            </div>
        );
    }
}

const WrappedLoginForm = Form.create({name: 'login'})(Login);

export default WrappedLoginForm;