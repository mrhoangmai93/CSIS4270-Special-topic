import React, {Component} from 'react';
import Login from '../../containers/Login';
import withLayout from "../../hocs/Layout";
import './index.scss';


class Index extends Component{

    render(){
        return (
            <div>
                <div className="bgHome" style={{ background: '#fff', paddingTop: 24 }}>
                    <img src="/bg1.jpg" alt=""/>
                </div>
                <div className="contentHome">
                    <div className="titleHome"><h1>Welcome to Languru!</h1></div>

                </div>
            </div>
        );
    }
}

export default withLayout(Index);
