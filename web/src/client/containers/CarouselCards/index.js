import React, {Component} from 'react';
import LessonCard from '../../components/LessonCard';
import './index.scss';

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

const wordsArr = ['backpack', 'book', 'compass', 'eraser', 'maker', 'notebook', 'pen', 'pencil sharpener', 'pencil', 'ruler'];

class CarouselCards extends Component {
  render(){
    const children = imgWrapper.map((imgSrc, i) => {
      return (

        <div
          key={i.toString()}
          // className="img-wrapper"
          // style={{
          //   backgroundImage: `url(${src})`,
          // }}
        >
          <div className="flip-card img-wrapper">
            <div className="flip-card-inner">
              <div className="flip-card-front">
                <img src={imgSrc} alt="Avatar" style={{width: "250px", height: "300px"}} />
              </div>
              <div className="flip-card-back">{wordsArr[i]}</div>
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