import React from "react";
import {Button, Card} from 'antd';
import {Icon} from "antd";
import {generateRandom} from '../../../server/utils/methods';
import {GAME_STATE} from "../../containers/Game/game.action";

const GameActions = (props) => (
    <div>
        <h1 className={`text-center text-${props.textColor}-500 text-2xl`}>Game Status: <span>{props.status}</span></h1>
        <Button type={props.type} shape="round" icon={props.icon} size="large"
                className="mx-auto block mt-5" onClick={props.onClick}>{props.actionText}</Button>
    </div>
    );

class GameBoard extends React.Component {
    componentDidMount() {

    }

    fmtMSS = (s) => (s-(s%=60))/60+(9<s?':':':0')+s;

    renderButtons = () => {
        const {gameState} = this.props;
        switch (gameState) {
            case GAME_STATE.READY:
                return <GameActions status="READY" type="primary" textColor="green" icon="right-circle" onClick={this.props.handleStartGame} actionText="Start Game"/>;
            case GAME_STATE.IN_PROGRESS:
                return <GameActions status="IN PROGRESS" type="dashed" textColor="green" icon="stop" onClick={this.props.handlePauseGame} actionText="Pause"/>;
            case GAME_STATE.PAUSE:
                return <GameActions status="PAUSE" type="primary" textColor="orange" icon="play-circle" onClick={this.props.handleStartGame} actionText="Resume"/>;
            case GAME_STATE.FINISHED:
                return <div>
                    <h1 className="text-center text-green-500 text-2xl">Congrats! You made it :)</h1>
                    <Button type="primary" shape="round" icon="right-circle" size="large"
                            className="mx-auto block mt-5" onClick={this.props.handleBack}>Back to Menu</Button>
                </div>;
            case GAME_STATE.OVER:
                return <div>
                    <h1 className="text-center text-orange-500 text-2xl">Sorry you run out of time!</h1>
                    <Button type="primary" shape="round" icon="right-circle" size="large"
                            className="mx-auto block mt-5" onClick={this.props.handleBack}>Back to Menu</Button>
                </div>;
            default:
                return <></>;
        }
    };

    render() {
        const {currentLevel, timeLeft, children} = this.props;
        return (
            <>
                <div className="cell-header lg:flex lg:justify-between flex-wrap">
                    <h1 className="text-center text-2xl shadow-2xl p-4 bg-orange-100 lg:rounded-lg text-gray-600">{`Your Current Level: ${currentLevel + 1}`}</h1>
                    <h1 className="text-center text-2xl shadow-2xl p-4 bg-orange-100 lg:rounded-lg text-gray-600 flex items-center content-center lg:content-start justify-center">
                        <Icon type="clock-circle" className="mr-4"/> {`Time Left: ${this.fmtMSS(timeLeft)}`}</h1>
                </div>
                <div className="cell-action">
                    {this.renderButtons()}
                </div>
                <div className="cell-body lg:rounded-lg mt-4">
                    <div style={{maxWidth: (((currentLevel + 1) * 4) * 100) + 65}} className="flex flex-wrap bg-gray-400 py-2 lg:p-8 mx-auto">
                        {children}
                    </div>
                </div>
                <Button type="danger" shape="round" icon="close" size="large"
                        className="mx-auto block mt-5" onClick={this.props.handleExit}>Exit</Button>
            </>
        )
    }
}

export default GameBoard;
