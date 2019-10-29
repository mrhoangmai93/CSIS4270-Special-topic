import React from 'react';
import GameBoard, {GAMEBOARD_CALLBACK_ENUMS} from "../../components/GameBoard";
import {push} from "connected-react-router";
import {withRouter} from "react-router";
import {connect} from "react-redux";

class Game extends React.Component {

    componentDidMount() {

    }



    render() {
        return <div className="m-auto text-center"> <GameBoard> </GameBoard></div>
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
