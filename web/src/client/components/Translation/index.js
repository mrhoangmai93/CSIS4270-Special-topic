import React, {Component} from 'react';
import {Icon} from 'antd'
import './index.scss';

class Translation extends Component {
  render(){
    return <>
      <div className="flex mb-4 flex-wrap mb-5">
        <div className="w-full lg:w-1/2 p-4">
          <div className="border-dotted border-4 border-gray-600 p-3">
            <p>Some Text</p>
          </div>
        </div>
        <div className="w-full lg:w-1/2 p-4">
          <div className="border-dotted border-4 border-gray-600 p-3">
            <h1 style={{fontSize: "20px", fontWeight: "bold"}}>English</h1>
            <p>Some Text</p>
          </div>
        </div>
      </div>
    </>
  }
}

export default Translation;