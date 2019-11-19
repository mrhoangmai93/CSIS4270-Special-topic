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
                <div className="bannerHome">
                    <h1>Welcome to Languru!</h1>
                    <p>
                        A new way of learning English by combining practical lessons and real-world examples.<br/> 
                        You can relax, have fun and challenge their English skill with other people through our memory flip card game
                    </p>
                </div>
                <div className="contentHome mx-auto">
                    {/* <h2 className="text-2xl">What we have</h2> */}
                    <div className="flex mb-4">
                        <div className="w-1/3 feature">
                            <img className="picFeature" src="/book2.png" alt="" />
                            <b>Lessons by topics</b>
                            <p>Multiple topics including: greeting, furniture, animals, school and music with vocabularies related to each topic.</p>
                        </div>
                        <div className="w-1/3 feature">
                            <img className="picFeature" src="/translation.png" alt="" />
                            <b>Translation</b>
                            <p>Help to translate any language that users input into English.</p>
                        </div>
                        <div className="w-1/3 feature">
                            <img className="picFeature" src="/card.png" alt="" />
                            <b>Memory game</b>
                            <p>Flip card game to test your memory of vocabularies you have just learned. You can also challenge your friends simulatenously.</p>
                        </div>
                    </div>

                </div>
                <div>
                    
                </div>
            </div>
        );
    }
}

export default withLayout(Index);
