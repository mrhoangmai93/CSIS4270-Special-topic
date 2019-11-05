import React, {Component} from 'react';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import CarouselCards from '../../components/CarouselCards';
import {loadLessons, setProgress} from '../Lessons/lessons.action';
import ProgressBar from '../../components/ProgressBar';

class Lessons extends Component{
    state = {
        progress: 0,
    }
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
            <div className="lessonContainer">
                <ProgressBar progress={this.state.progress}/>
                <div style={{ background: '#fff', padding: 24, minHeight: 280 }}>
                    {this.props.lessons && this.props.lessons.list &&
                        <CarouselCards lessons={this.props.lessons.list}/>
                    }
                </div>
            </div>
        );
    }
}
const mapStateToProps = state => ({
    lessons: state.lessons.get("lessons"),
    progress: state.lessons.get("progress")
});

const mapDispatchToProps = {
    loadLessons,
    setProgress,
};

export default withRouter(
    connect(
        mapStateToProps,
        mapDispatchToProps
    )(Lessons)
);