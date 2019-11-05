import React, {Component} from 'react';
import LessonCard from '../LessonCard';
import './index.scss';

class CarouselCards extends Component {
  render(){
    const {lessons} = this.props;
    const children = lessons.map((lesson, i) => {
      return (
        <div key={i.toString()}>
          <div className="flip-card img-wrapper">
            <div className="flip-card-inner">
              <div className="flip-card-front">
                <img src={lesson.image} alt="Avatar" style={{width: "250px", height: "300px"}} />
              </div>
              <div className="flip-card-back">{lesson.word}</div>
            </div>
          </div>
        </div>      
      )
    });
    return (
      <div className="carousel-demo-wrapper">
        <LessonCard className="carousel-demo" childMaxLength={6}>
          {children}
        </LessonCard>
      </div>
    );
  }
}

export default CarouselCards;