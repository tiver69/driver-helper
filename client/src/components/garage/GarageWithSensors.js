import React, { Component } from "react";
import OnlineStatusAnimation from "./OnlineStatusAnimation";
import OfflineStatusAnimation from "./OfflineStatusAnimation";
import { getSensorAlertMobile } from "./functions";
import { connect } from "react-redux";
import PropTypes from "prop-types";

import Icon from "../../resources/webfonts/icomoon/Icon";

class GarageWithSensors extends Component {
  constructor() {
    super();
    this.state = {
      garageWidth: 300,
      garageHeight: 600,
      frontSensor: 150,
      leftAngleSensor: 300,
      rightAngleSensor: 300,
      leftSensor: 530
    };
  }

  componentWillReceiveProps(nextProps) {
    const { garageConfig } = nextProps.settings;
    if (garageConfig.garageWidth !== undefined)
      this.setState({
        garageWidth: garageConfig.garageWidth,
        garageHeight: garageConfig.garageHeight,
        frontSensor: garageConfig.frontSensor,
        leftAngleSensor: garageConfig.leftAngleSensor,
        rightAngleSensor: garageConfig.rightAngleSensor,
        leftSensor: garageConfig.leftSensor
      });
  }

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

    const dynamicStyleBackgroung = {
      position: "relative",
      overflow: "unset",
      margin: "auto",
      width: this.state.garageWidth + "px",
      height: this.state.garageHeight + "px"
    };

    return (
      <div
        className={
          this.props.mobile === true
            ? "col mobile-on justify-content-md-center align-self-center"
            : "col-4 mobile-off justify-content-md-center align-self-center"
        }
      >
        <div>
          <div id="background" style={dynamicStyleBackgroung}>
            {this.props.mobile === true ? this.sensorData() : null}

            <div
              id="left-side-angle-sensor"
              style={{
                top: this.state.leftAngleSensor - 35 + "px"
              }}
            >
              {sensorDataNode.leftSideAngle === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation
                  distValue={sensorDataNode.leftSideAngle}
                />
              )}
            </div>
            <div
              id="left-side-sensor"
              style={{
                top: this.state.leftSensor - 35 + "px"
              }}
            >
              {sensorDataNode.leftSide === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation distValue={sensorDataNode.leftSide} />
              )}
            </div>
            <div
              id="right-side-angle-sensor"
              style={{
                top: this.state.rightAngleSensor - 35 + "px"
              }}
            >
              {sensorDataNode.rightSideAngle === 0 ? (
                <OfflineStatusAnimation />
              ) : (
                <OnlineStatusAnimation
                  distValue={sensorDataNode.rightSideAngle}
                />
              )}
            </div>
            <div
              id="front-sensor"
              style={{
                left: this.state.frontSensor - 35 + "px"
              }}
            >
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

GarageWithSensors.propTypes = {
  settings: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  settings: state.settings
});
export default connect(mapStateToProps, {})(GarageWithSensors);
