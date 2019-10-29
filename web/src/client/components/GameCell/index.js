import React from "react";
import {Card} from 'antd';

const { Meta } = Card;


const VIEW_CALLBACK_ENUMS = {};

class GameCell extends React.Component {
    state = {
        wordVisible: false,
        loading: true,
    };

    componentDidMount() {

    }
    renderWordVisible = () => {
        if(this.state.wordVisible === false) return 'none';
        return 'block';
    };
    render() {
        return (
                <Card style={{ width: 300, height: 300 }} className="p-2" loading={this.state.loading}>
                    <Meta style={{display: this.renderWordVisible()}} title="Europe Street beat" />
                </Card>
        )
    }
}

export default GameCell;


export {
    VIEW_CALLBACK_ENUMS as GAMECELL_CALLBACK_ENUMS,
};
