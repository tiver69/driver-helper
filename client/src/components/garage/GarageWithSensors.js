import React, { Component } from "react";
import OnlineStatusAnimation from "./OnlineStatusAnimation";
import OfflineStatusAnimation from "./OfflineStatusAnimation";
import { getSensorAlertMobile } from "./functions";

import Icon from "../../resources/webfonts/icomoon/Icon";

class GarageWithSensors extends Component {
  sensorData = () => {
    const { sensorDataNode } = this.props.sensorDataNode;
    return (
      <React.Fragment>
        <div
          className="row h-25 m-0 px-md-5 sensor-data"
          style={getSensorAlertMobile(sensorDataNode.front)}
        >
          <div className="col">
            <div className="row m-0">Front Side</div>
            <div className="row m-0">
              <div className="col">
                <Icon className="icon" icon="arrow-down2" />
              </div>
              <div className="col align-self-center">
                {sensorDataNode.front}
              </div>
            </div>
          </div>
        </div>

        <div className="row h-25 m-0"></div>

        <div className="row h-25 m-0 sensor-data">
          <div
            className="col-6"
            style={getSensorAlertMobile(sensorDataNode.leftSideAngle)}
          >
            <div className="row justify-content-center">Left Angle</div>
            <div className="row">
              <div className="col">
                <Icon className="icon" icon="arrow-down-right2" />
              </div>
              <div className="col align-self-center">
                {sensorDataNode.leftSideAngle}
              </div>
            </div>
          </div>
          <div
            className="col-6"
            style={getSensorAlertMobile(sensorDataNode.rightSideAngle)}
          >
            <div className="row justify-content-center">Right Angle</div>
            <div className="row">
              <div className="col align-self-center">
                {sensorDataNode.rightSideAngle}
              </div>
              <div className="col">
                <Icon className="icon" icon="arrow-down-left2" />
              </div>
            </div>
          </div>
        </div>

        <div
          className="row h-25 m-0 w-50 sensor-data"
          style={getSensorAlertMobile(sensorDataNode.leftSide)}
        >
          <div className="col">
            <div className="row justify-content-center">Left Side</div>
            <div className="row">
              <div className="col">
                <Icon className="icon" icon="arrow-right2" />
              </div>
              <div className="col align-self-center">
                {sensorDataNode.leftSide}
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  };

  render() {
    const { sensorDataNode } = this.props.sensorDataNode;

    return (
      <div
        className={
          this.props.mobile === true
            ? "col mobile-on justify-content-md-center align-self-center"
            : "col-4 mobile-off justify-content-md-center align-self-center"
        }
      >
        <div>
          <div
            id="background"
            style={{
              position: "relative",
              overflow: "unset",
              margin: "auto"
            }}
          >
            {this.props.mobile === true ? this.sensorData() : null}

            <div id="left-side-angle-sensor">
              {sensorDataNode.leftSideAngle === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation
                  distValue={sensorDataNode.leftSideAngle}
                />
              )}
            </div>
            <div id="left-side-sensor">
              {sensorDataNode.leftSide === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation distValue={sensorDataNode.leftSide} />
              )}
            </div>
            <div id="right-side-angle-sensor">
              {sensorDataNode.rightSideAngle === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation
                  distValue={sensorDataNode.rightSideAngle}
                />
              )}
            </div>
            <div id="front-sensor">
              {sensorDataNode.front === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation distValue={sensorDataNode.front} />
              )}
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default GarageWithSensors;
