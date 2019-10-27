import React, {Component} from 'react';
import {Menu} from "antd";
import {Link} from 'react-router-dom';
class NavBar extends Component {
    render() {
        return(
            <div>
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['1']}
                    style={{ lineHeight: '64px' }}
                >
                    <Menu.Item key="1"><Link to="/dashboard/lessons">Lessons</Link></Menu.Item>
                    <Menu.Item key="2">Translation</Menu.Item>
                    <Menu.Item key="3">Game</Menu.Item>
                    <Menu.Item key="4">Logout</Menu.Item>
                </Menu>
            </div>
        )
    }
}

export default NavBar;
