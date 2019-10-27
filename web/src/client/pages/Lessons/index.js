import React, {Component} from 'react';
import NavBar from '../../components/NavBar';
import {Layout} from "antd";
import withLayout from "../../hocs/Layout";
import requireAuth from "../../hocs/requiresAuth";
const {Header, Content} = Layout;

class Lessons extends Component{
    
    render(){
        return (
            <div>
                <div style={{ background: '#fff', padding: 24, minHeight: 280 }}>test</div>
            </div>
        );
    }
}
export default requireAuth(withLayout(Lessons));