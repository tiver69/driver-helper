import React, { Component } from "react";
import { getSensorAlertPointAnimation, getSensorAlertPoint } from "./functions";

class OnlineStatusAnimation extends Component {
  render() {
    return (
      <React.Fragment>
        <div
          className="sensor-point"
          style={getSensorAlertPoint(this.props.distValue)}
        ></div>
        <div className="lds-ripple">
          <div style={getSensorAlertPointAnimation(this.props.distValue)}></div>
          <div style={getSensorAlertPointAnimation(this.props.distValue)}></div>
        </div>
      </React.Fragment>
    );
  }
}
export default OnlineStatusAnimation;
