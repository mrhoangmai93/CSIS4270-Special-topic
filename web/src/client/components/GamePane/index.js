import React from "react";
import {GAME_STATE} from "../../containers/Game/game.action";
import {Button, Modal, Skeleton} from 'antd';
import WrappedLoginForm from "../Login";
import GameBoard from "../GameBoard";
const VIEW_CALLBACK_ENUMS = {
    START: 'START',
    PAUSE: 'PAUSE',
    SINGLE_PLAYER: 'SINGLE_PLAYER',
    MULTI_PLAYERS: 'MULTI_PLAYERS',
    EXIT: 'EXIT',
    BACK: 'BACK',
};

class GamePane extends React.Component {
    componentDidMount() {

    }

    handleSinglePlayer = () => {
        this.props.callbackHandler(VIEW_CALLBACK_ENUMS.SINGLE_PLAYER, undefined);
    };

    handleMultiPlayers = () => {
        this.props.callbackHandler(VIEW_CALLBACK_ENUMS.MULTI_PLAYERS, undefined);
    };

    handleExit = () => {
        const {callbackHandler} = this.props;
        callbackHandler(VIEW_CALLBACK_ENUMS.EXIT, undefined);
    };
    handleBack = () => {
        const {callbackHandler} = this.props;
        callbackHandler(VIEW_CALLBACK_ENUMS.BACK, undefined);
    };
    handleStartGame = () => {
        this.props.callbackHandler(VIEW_CALLBACK_ENUMS.START, undefined);
    };
    handlePauseGame = () => {
        this.props.callbackHandler(VIEW_CALLBACK_ENUMS.PAUSE, undefined);
    };
    renderGameState = (state) => {
        switch (state) {
            case GAME_STATE.PENDING:
                return (
                    <>
                        <h1 className="text-3xl mb-5 text-center">Welcome to our Game Center</h1>
                        <div className="flex flex-wrap bg-gray-400 p-8 lg:rounded-lg">
                            <div className="w-full lg:w-1/2 h-auto mb-5 lg:mb-0">
                                <Button type="default" shape="round" icon="user" size="large"
                                        className="mx-auto block mb-5" onClick={this.handleSinglePlayer}>
                                    Practice
                                </Button>
                                <p className="text-center">Practice your English skills</p>
                            </div>
                            <div className="w-full lg:w-1/2 h-auto">
                                <Button type="default" shape="round" icon="team" size="large"
                                        className="mx-auto block mb-5" onClick={this.handleMultiPlayers}>
                                    Challenge
                                </Button>
                                <p className="text-center">Challenge your friends, who finish first win!</p>
                            </div>
                        </div>
                    </>
                );
            case GAME_STATE.WAITING:
                break;
            case GAME_STATE.ERROR:
                return (
                    <>
                        <div className="bg-gray-400 p-8 lg:rounded-lg">
                            <h1 className="text-xl text-center text-orange-500">{this.props.error}</h1>
                            <h1 className="text-xl text-center text-orange-500">Please restart the game!</h1>
                        </div>
                        <Button type="danger" shape="round" icon="close" size="large"
                                className="mx-auto block mt-5" onClick={this.handleBack}>
                            Exit
                        </Button>
                    </>
                );
            case GAME_STATE.READY:
            case GAME_STATE.IN_PROGRESS:
            case GAME_STATE.PAUSE:
            case GAME_STATE.FINISHED:
            case GAME_STATE.OVER:
                return <GameBoard currentLevel={this.props.currentLevel} timeLeft={this.props.timeLeft} handleExit={this.handleExit}
                                  handleStartGame={this.handleStartGame}
                                  handleBack={this.handleBack}
                                  handlePauseGame={this.handlePauseGame} gameState={state} >{this.props.renderBoardCells()}</GameBoard>;
            default:
                return <></>;
        }
    };

    render() {
        const {gameState, isLoading} = this.props;
        return <Skeleton loading={isLoading}><div className="mt-4">{this.renderGameState(gameState)}</div></Skeleton>;
    }
}

export default GamePane;

export {
    VIEW_CALLBACK_ENUMS as GAMEPANECALLBACK_ENUMS,
};