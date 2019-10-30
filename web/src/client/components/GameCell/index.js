import React from "react";
import {Card} from 'antd';
import ReactCardFlip from 'react-card-flip';
import {ReactComponent as Background} from '../../images/cell.svg';
import {Skeleton} from 'antd';

import './index.scss';

const {Meta} = Card;

const VIEW_CALLBACK_ENUMS = {};

class GameCell extends React.Component {
    state = {
        width: 150,
        height: 150,
        loading: true,
        isFlipped: false,
        isFlipping: false,
    };

    componentDidMount() {

    }

    renderWordVisible = () => {
        if (this.state.isFlipped === false) return 'none';
        return 'block';
    };

    handleCardClick = () => {
        if (!this.state.isFlipping) {
            this.setState(prevState => ({isFlipped: !prevState.isFlipped, isFlipping: true}));
            setTimeout(
                function () {
                    this.setState(prevState => ({isFlipped: !prevState.isFlipped, isFlipping: false}));
                }
                    .bind(this),
                1500
            );
        }
    };

    render() {
        return (
            <>
                <div style={{width: this.state.width, height: this.state.height}}>
                    <ReactCardFlip isFlipped={this.state.isFlipped} flipDirection="horizontal">
                        <div style={{width: this.state.width, height: this.state.height}}
                             className="cursor-pointer shadow p-1 select-none flex justify-center items-center"
                             onClick={this.handleCardClick} key="front">
                            <div className="cover w-full h-full"/>
                        </div>

                        <div style={{width: this.state.width, height: this.state.height}}
                             className="cursor-pointer shadow p-1 select-none flex justify-center items-center"
                             onClick={this.handleCardClick} key="back">
                            <h3>Europe Street beat</h3>
                        </div>
                    </ReactCardFlip>
                </div>
            </>
        )
    }
}

export default GameCell;


export {
    VIEW_CALLBACK_ENUMS as GAMECELL_CALLBACK_ENUMS,
};
