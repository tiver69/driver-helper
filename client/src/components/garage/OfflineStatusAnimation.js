import React, { Component } from "react";

class OfflineStatusAnimation extends Component {
  render() {
    return (
      <React.Fragment>
        <div
          className="sensor-point"
          style={{
            backgroundColor: "grey"
          }}
        ></div>
      </React.Fragment>
    );
  }
}
export default OfflineStatusAnimation;
