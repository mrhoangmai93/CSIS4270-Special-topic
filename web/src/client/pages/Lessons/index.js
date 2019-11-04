import React, {Component} from 'react';
import withLayout from "../../hocs/Layout";
import CarouselCards from '../../components/CarouselCards';
import Lessons from '../../containers/Lessons';

// class Lessons extends Component{
//     render(){
//         const topic = this.props.match.params.topic;
//         return (
//             <div>
//                 <div style={{ background: '#fff', padding: 24, minHeight: 280 }}>
//                     <CarouselCards topic={topic}/>
//                 </div>
//             </div>
//         );
//     }
// }
export default withLayout(Lessons);
// export default requireAuth(withLayout(Lessons));
