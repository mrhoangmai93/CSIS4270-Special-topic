import React from 'react';
import {Icon} from 'antd';
import './index.scss';

export default (props) => (
    <>
        <div style={{margin: "0px 0px 5px 0px", }}>
            <Icon type="facebook" theme="filled" className="iconFooter"/>
            <Icon type="twitter" className="iconFooter"/>
            <Icon type="youtube" theme="filled" className="iconFooter"/>
            <Icon type="linkedin" theme="filled" className="iconFooter"/>
        </div>
        <div>&copy;2019 Languru. All rights reserved. </div>
    </>
);