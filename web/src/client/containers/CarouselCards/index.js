import React, {Component} from 'react';
import LessonCard from '../../components/LessonCard';

const imgWrapper = [
    'https://langurupic.s3.amazonaws.com/pic/school/backpack.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/book.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/compass.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/eraser.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/marker.png',
    'https://langurupic.s3.amazonaws.com/pic/school/notebook.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/pen.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/pencil_sharpener.jpeg',
    'https://langurupic.s3.amazonaws.com/pic/school/pencil.png',
    'https://langurupic.s3.amazonaws.com/pic/school/ruler.jpg',
];

class CarouselCards extends Component {
    render(){
        const children = imgWrapper.map((src, i) => (
            <div
              key={i.toString()}
              className="img-wrapper"
              style={{
                backgroundImage: `url(${src})`,
              }}
            />
          ));
          return (
            <div className="carousel-demo-wrapper">
              <LessonCard className="carousel-demo" childMaxLength={6}>
                {children}
              </LessonCard>
            </div>);
    }
}

export default CarouselCards;