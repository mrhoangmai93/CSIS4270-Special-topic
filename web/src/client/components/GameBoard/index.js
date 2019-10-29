import React from "react";
import {Card} from 'antd';

const VIEW_CALLBACK_ENUMS = {};

class GameBoard extends React.Component {
    componentDidMount() {

    }

    render() {
        const {playerName, children} = this.props;
        return (
            <Card title={playerName}>
                {children}
            </Card>
        )
    }
}

export default GameBoard;

export {
    VIEW_CALLBACK_ENUMS as GAMEBOARD_CALLBACK_ENUMS,
};
