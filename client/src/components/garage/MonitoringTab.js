import React, { Component } from "react";
import { getSensorDataNode } from "../../actions/DataActions";
import { getSensorAlert } from "./functions";
import GarageWithSensors from "./GarageWithSensors";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import Icon from "../../resources/webfonts/icomoon/Icon";

class MonitoringTab extends Component {
  componentDidMount() {
    this.props.getSensorDataNode();
    this.doStartResivingData();
    setInterval(this.doStartResivingData.bind(this), 1000);
  }

  async doStartResivingData() {
    this.props.getSensorDataNode();
  }

  render() {
    const { sensorDataNode } = this.props.dataNode;

    return (
      <div
        className="tab-pane fade"
        id="tab-3"
        role="tabpanel"
        aria-labelledby="tab-3"
      >
        <div className="container">
          <div
            className="row justify-content-center"
            style={{ minHeight: "calc(100vh - 94.74px - 19.976px - 24px)" }}
          >
            <div className="col-4 mobile-off">
              <div
                className="row h-50 align-bottom sensor-data"
                style={getSensorAlert(sensorDataNode.leftSideAngle)}
              >
                <div className="col align-self-end">
                  <div className="row">Left Side Angle</div>
                  <div className="row">
                    <Icon className="icon" icon="arrow-down-right2" />
                  </div>
                </div>
                <div className="col align-self-end">
                  {sensorDataNode.leftSideAngle}
                </div>
              </div>
              <div
                className="row h-50 sensor-data"
                style={getSensorAlert(sensorDataNode.leftSide)}
              >
                <div className="col align-self-center">
                  <div className="row">Left Side</div>
                  <div className="row">
                    <Icon className="icon" icon="arrow-right2" />
                  </div>
                </div>
                <div className="col align-self-center">
                  {sensorDataNode.leftSide}
                </div>
              </div>
            </div>

            <GarageWithSensors
              mobile={false}
              sensorDataNode={this.props.dataNode}
            />

            <div className="col-4 mobile-off">
              <div
                className="row h-50 align-bottom sensor-data"
                style={getSensorAlert(sensorDataNode.front)}
              >
                <div className="col align-self-center">
                  {sensorDataNode.front}
                </div>
                <div className="col align-self-center">
                  <div className="row">Front</div>
                  <div className="row">
                    <Icon className="icon" icon="arrow-down2" />
                  </div>
                </div>
              </div>
              <div
                className="row h-50 sensor-data"
                style={getSensorAlert(sensorDataNode.rightSideAngle)}
              >
                <div className="col">{sensorDataNode.rightSideAngle}</div>
                <div className="col">
                  <div className="row">Right Side Angle</div>
                  <div className="row">
                    <Icon className="icon" icon="arrow-down-left2" />
                  </div>
                </div>
              </div>
            </div>

            <GarageWithSensors
              mobile={true}
              sensorDataNode={this.props.dataNode}
            />
          </div>
        </div>
      </div>
    );
  }
}

MonitoringTab.propTypes = {
  dataNode: PropTypes.object.isRequired,
  getSensorDataNode: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  dataNode: state.dataNode
});
export default connect(mapStateToProps, { getSensorDataNode })(MonitoringTab);
