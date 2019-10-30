import React from 'react';
import GameBoard, {GAMEBOARD_CALLBACK_ENUMS} from "../../components/GameBoard";
import {push} from "connected-react-router";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import GameCell from '../../components/GameCell';
class Game extends React.Component {

    componentDidMount() {

    }



    render() {
        return <div className="m-auto text-center"> <GameBoard> <GameCell /> </GameBoard></div>
    }
}
const mapStateToProps = state => ({
    gameState: state.game
});

const mapDispatchToProps = {
};

export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Game)
);
