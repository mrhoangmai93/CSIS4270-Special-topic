import React, {Component} from 'react';
import NavBar from '../../components/NavBar';
import {Layout} from "antd";
import withLayout from "../../hocs/Layout";
import requireAuth from "../../hocs/requiresAuth";
import CarouselCards from '../../components/CarouselCards';
const {Header, Content} = Layout;

class Lessons extends Component{
    render(){
        const topic = this.props.match.params.topic;
        return (
            <div>
                <div style={{ background: '#fff', padding: 24, minHeight: 280 }}>
                    <CarouselCards topic={topic}/>
                </div>
            </div>
        );
    }
}
export default withLayout(Lessons);
// export default requireAuth(withLayout(Lessons));