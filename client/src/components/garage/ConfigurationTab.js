import React, { Component } from "react";
import OfflineStatusAnimation from "./OfflineStatusAnimation";
import classnames from "classnames";
import {
  getGarageSettings,
  updateSettings
} from "../../actions/SettingsActions";
import { connect } from "react-redux";
import PropTypes from "prop-types";

class ConfigurationTab extends Component {
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

    this.onChange = this.onChange.bind(this);
    this.onRestoreClick = this.onRestoreClick.bind(this);
    this.onSubmitClick = this.onSubmitClick.bind(this);
  }

  componentDidMount() {
    this.props.getGarageSettings();
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

  onRestoreClick(e) {
    e.preventDefault();
    const settings = {
      garageWidth: 300,
      garageHeight: 600,
      frontSensor: 150,
      leftAngleSensor: 300,
      rightAngleSensor: 300,
      leftSensor: 530
    };

    console.log(settings);
    this.props.updateSettings(settings);
  }

  onSubmitClick(e) {
    e.preventDefault();
    const settings = {
      garageWidth: this.state.garageWidth,
      garageHeight: this.state.garageHeight,
      frontSensor: this.state.frontSensor,
      leftAngleSensor: this.state.leftAngleSensor,
      rightAngleSensor: this.state.rightAngleSensor,
      leftSensor: this.state.leftSensor
    };
    console.log(settings);
    this.props.updateSettings(settings);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  propertyForm = isMobile => {
    return (
      <React.Fragment>
        <div
          className={classnames("align-self-center", {
            col: isMobile === true,
            "col-6": isMobile === false
          })}
        >
          <div className="form">
            <div
              className="row"
              style={
                this.state.garageHeight !== "" && this.state.garageHeight >= 500
                  ? { minHeight: this.state.garageHeight + "px" }
                  : null
              }
            >
              <div
                className={classnames("align-self-center offset-lg-3", {
                  col: isMobile === true,
                  "col-lg-6": isMobile === false
                })}
              >
                <form data-focus="false" onSubmit={this.onSubmitClick}>
                  <div className="form-group">
                    <input
                      type="number"
                      min="300"
                      className={classnames("form-control-input", {
                        notEmpty: this.state.garageWidth !== ""
                      })}
                      value={this.state.garageWidth}
                      onChange={this.onChange}
                      name="garageWidth"
                      required
                    />
                    <label className="label-control">Garage With</label>
                    {/* <div className="help-block with-errors"></div> */}
                  </div>

                  <div className="form-group">
                    <input
                      type="number"
                      min="500"
                      className={classnames("form-control-input", {
                        notEmpty: this.state.garageHeight !== ""
                      })}
                      value={this.state.garageHeight}
                      onChange={this.onChange}
                      name="garageHeight"
                      required
                    />
                    <label className="label-control">Garage Height</label>
                    {/* <div className="help-block with-errors"></div> */}
                  </div>

                  <div className="form-group">
                    <input
                      type="number"
                      min="0"
                      max={this.state.garageWidth - 1}
                      className={classnames("form-control-input", {
                        notEmpty: this.state.frontSensor !== ""
                      })}
                      value={this.state.frontSensor}
                      onChange={this.onChange}
                      name="frontSensor"
                      required
                    />
                    <label className="label-control">
                      Front Sensor Position
                    </label>
                    {/* <div className="help-block with-errors"></div> */}
                  </div>

                  <div className="form-group">
                    <input
                      type="number"
                      min="0"
                      max={this.state.leftSensor - 1}
                      className={classnames("form-control-input", {
                        notEmpty: this.state.leftAngleSensor !== ""
                      })}
                      value={this.state.leftAngleSensor}
                      onChange={this.onChange}
                      name="leftAngleSensor"
                      required
                    />
                    <label className="label-control">
                      Left Angle Sensor Position
                    </label>
                    {/* <div className="help-block with-errors"></div> */}
                  </div>

                  <div className="form-group">
                    <input
                      type="number"
                      min="0"
                      max={this.state.garageHeight - 1}
                      className={classnames("form-control-input", {
                        notEmpty: this.state.rightAngleSensor !== ""
                      })}
                      value={this.state.rightAngleSensor}
                      name="rightAngleSensor"
                      onChange={this.onChange}
                      required
                    />
                    <label className="label-control">
                      Right Angle Sensor Position
                    </label>
                    {/* <div className="help-block with-errors"></div> */}
                  </div>

                  <div className="form-group">
                    <input
                      type="number"
                      min="0"
                      max={this.state.garageHeight - 1}
                      className={classnames("form-control-input", {
                        notEmpty: this.state.leftSensor !== ""
                      })}
                      value={this.state.leftSensor}
                      name="leftSensor"
                      onChange={this.onChange}
                      required
                    />
                    <label className="label-control">
                      Left Sensor Position
                    </label>
                    {/* <div className="help-block with-errors"></div> */}
                  </div>
                  <div className="row">
                    <div className="col-6 form-group">
                      <button
                        // {validateGarageConfig(this.state)? disabled : null}
                        className="form-control-submit-button"
                      >
                        SUBMIT
                      </button>
                    </div>

                    <div className="col-6 form-group">
                      <button
                        onClick={this.onRestoreClick}
                        // {validateGarageConfig(this.state)? disabled : null}
                        className="form-control-submit-button"
                      >
                        RESTORE DEFAULT
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  };

  mobileForm = () => {
    return (
      <React.Fragment>
        <div
          className="row mobile-on align-self-center"
          style={{ minHeight: "calc(100vh - 94.74px - 19.976px - 24px)" }}
        >
          <div
            id="background"
            style={
              this.state.garageWidth !== "" &&
              this.state.garageWidth >= 300 &&
              this.state.garageHeight !== "" &&
              this.state.garageHeight >= 500
                ? {
                    position: "relative",
                    overflow: "unset",
                    margin: "auto",
                    width: this.state.garageWidth + "px",
                    height: this.state.garageHeight + "px"
                  }
                : this.state.garageHeight !== "" &&
                  this.state.garageHeight >= 500
                ? {
                    position: "relative",
                    overflow: "unset",
                    margin: "auto",
                    height: this.state.garageHeight + "px"
                  }
                : this.state.garageWidth !== "" && this.state.garageWidth >= 300
                ? {
                    position: "relative",
                    overflow: "unset",
                    margin: "auto",
                    width: this.state.garageWidth + "px"
                  }
                : {
                    position: "relative",
                    overflow: "unset",
                    margin: "auto"
                  }
            }
          >
            {this.propertyForm(true)}
            <div
              id="left-side-angle-sensor"
              style={
                this.state.leftAngleSensor !== "" &&
                this.state.leftAngleSensor < this.state.leftSensor
                  ? {
                      top: this.state.leftAngleSensor - 35 + "px"
                    }
                  : null
              }
            >
              <OfflineStatusAnimation />
            </div>
            <div
              id="left-side-sensor"
              style={
                this.state.leftSensor !== "" &&
                this.state.leftSensor < this.state.garageHeight
                  ? {
                      top: this.state.leftSensor - 35 + "px"
                    }
                  : null
              }
            >
              <OfflineStatusAnimation />
            </div>
            <div
              id="right-side-angle-sensor"
              style={
                this.state.rightAngleSensor !== "" &&
                this.state.rightAngleSensor < this.state.garageHeight
                  ? {
                      top: this.state.rightAngleSensor - 35 + "px"
                    }
                  : null
              }
            >
              <OfflineStatusAnimation />
            </div>
            <div
              id="front-sensor"
              style={
                this.state.frontSensor !== "" &&
                this.state.frontSensor < this.state.garageWidth
                  ? {
                      left: this.state.frontSensor - 35 + "px"
                    }
                  : null
              }
            >
              <OfflineStatusAnimation />
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  };

  render() {
    return (
      <div
        className="tab-pane fade"
        id="tab-1"
        role="tabpanel"
        aria-labelledby="tab-1"
      >
        <div className="container">
          {this.mobileForm()}

          <div
            className="row mobile-off"
            style={{ minHeight: "calc(100vh - 94.74px - 19.976px - 24px)" }}
          >
            {this.propertyForm(false)}

            <div className="col-6 justify-content-md-center align-self-center">
              <div>
                <div
                  id="background"
                  style={
                    this.state.garageWidth !== "" &&
                    this.state.garageWidth >= 300 &&
                    this.state.garageHeight !== "" &&
                    this.state.garageHeight >= 500
                      ? {
                          position: "relative",
                          overflow: "unset",
                          margin: "auto",
                          width: this.state.garageWidth + "px",
                          height: this.state.garageHeight + "px"
                        }
                      : this.state.garageHeight !== "" &&
                        this.state.garageHeight >= 500
                      ? {
                          position: "relative",
                          overflow: "unset",
                          margin: "auto",
                          height: this.state.garageHeight + "px"
                        }
                      : this.state.garageWidth !== "" &&
                        this.state.garageWidth >= 300
                      ? {
                          position: "relative",
                          overflow: "unset",
                          margin: "auto",
                          width: this.state.garageWidth + "px"
                        }
                      : {
                          position: "relative",
                          overflow: "unset",
                          margin: "auto"
                        }
                  }
                >
                  {this.props.mobile === true ? this.sensorData() : null}

                  <div
                    id="left-side-angle-sensor"
                    style={
                      this.state.leftAngleSensor !== "" &&
                      this.state.leftAngleSensor < this.state.leftSensor
                        ? {
                            top: this.state.leftAngleSensor - 35 + "px"
                          }
                        : null
                    }
                  >
                    <OfflineStatusAnimation />
                  </div>
                  <div
                    id="left-side-sensor"
                    style={
                      this.state.leftSensor !== "" &&
                      this.state.leftSensor < this.state.garageHeight
                        ? {
                            top: this.state.leftSensor - 35 + "px"
                          }
                        : null
                    }
                  >
                    <OfflineStatusAnimation />
                  </div>
                  <div
                    id="right-side-angle-sensor"
                    style={
                      this.state.rightAngleSensor !== "" &&
                      this.state.rightAngleSensor < this.state.garageHeight
                        ? {
                            top: this.state.rightAngleSensor - 35 + "px"
                          }
                        : null
                    }
                  >
                    <OfflineStatusAnimation />
                  </div>
                  <div
                    id="front-sensor"
                    style={
                      this.state.frontSensor !== "" &&
                      this.state.frontSensor < this.state.garageWidth
                        ? {
                            left: this.state.frontSensor - 35 + "px"
                          }
                        : null
                    }
                  >
                    <OfflineStatusAnimation />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ConfigurationTab.propTypes = {
  updateSettings: PropTypes.func.isRequired,
  getGarageSettings: PropTypes.func.isRequired,
  settings: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  settings: state.settings,
  errors: state.errors
});

export default connect(mapStateToProps, {
  updateSettings,
  getGarageSettings
})(ConfigurationTab);
