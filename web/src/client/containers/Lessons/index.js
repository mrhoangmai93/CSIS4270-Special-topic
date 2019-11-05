import React, {Component} from 'react';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import CarouselCards from '../../components/CarouselCards';
import {loadLessons} from '../Lessons/lessons.action';

class Lessons extends Component{
    componentDidMount() {
        const topic = this.props.match.params.topic;
        this.props.loadLessons(topic);
    }
    componentDidUpdate(prevProps, prevState) {
        if(prevProps.match.params.topic !== this.props.match.params.topic){
            this.props.loadLessons(this.props.match.params.topic);
        }
    }
    render(){
        return (
            <div>
                <div style={{ background: '#fff', padding: 24, minHeight: 280 }}>
                    <CarouselCards lessons={this.props.lessons}/>
                </div>
            </div>
        );
    }
}
const mapStateToProps = state => ({
    lessons: state.lessons.get("lessons")
});

const mapDispatchToProps = {
    loadLessons
};

export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Lessons)
);