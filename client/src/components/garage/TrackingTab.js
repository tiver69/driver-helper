import React, { Component } from "react";
import { connect } from "react-redux";
import { getDataNode, postResetData } from "../../actions/DataActions";
import { setUpActiveCar } from "../../actions/SettingsActions";
import PropTypes from "prop-types";

class TrackingTab extends Component {
  constructor() {
    super();
    this.state = {
      i: 0,
      render: false,
      garageWidth: 300,
      garageHeight: 600
    };
    this.handleClick = this.handleClick.bind(this);
    this.handleResetClick = this.handleResetClick.bind(this);
  }

  handleClick(e) {
    e.preventDefault();
    this.props.getDataNode();
    console.log(this.props.dataNode);
  }

  handleResetClick(e) {
    e.preventDefault();
    this.props.postResetData();
  }

  componentDidMount() {
    this.props.setUpActiveCar(this.props.carId);
    this.props.postResetData();
    this.doStartResivingData();
    setInterval(this.doStartResivingData.bind(this), 1000);
  }

  async doStartResivingData() {
    if (this.state.i === 29) {
      console.log("reset");
      this.props.postResetData();
      this.setState({ i: 0 });
    } else {
      this.props.getDataNode();
      let newI = this.state.i + 1;
      console.log(newI);
      this.setState({ i: newI });
    }
  }

  componentWillReceiveProps(nextProps) {
    const { garageConfig } = nextProps.settings;
    if (garageConfig.garageWidth !== undefined)
      this.setState({
        garageWidth: garageConfig.garageWidth,
        garageHeight: garageConfig.garageHeight
      });
  }

  render() {
    const { dataNode } = this.props.dataNode;

    const dynamicStyle0 = {
      width: dataNode.rotatedWidth + "px",
      height: dataNode.rotatedHeight + "px",
      top: dataNode.positionY + "px",
      left: dataNode.positionX + "px"
    };

    const { currentCar } = this.props.settings;
    const imageWidth =
      currentCar.imageDimensions !== undefined
        ? currentCar.imageDimensions.width
        : 0;
    const imageHeight =
      currentCar.imageDimensions !== undefined
        ? currentCar.imageDimensions.height
        : 0;
    const imageBasePath = "/images/";
    const imageFileFormat = ".png";

    const dynamicStyleBefore = {
      transform: "rotate(" + dataNode.degree + "deg)",
      top: dataNode.shiftedWidth + "px",
      left: dataNode.shiftedHeight + "px",
      backgroundImage:
        "url(" +
        imageBasePath +
        currentCar.imageFileName +
        imageFileFormat +
        ")",
      width: imageWidth,
      height: imageHeight
    };

    const dynamicStyleBackgroung = {
      width: this.state.garageWidth + "px",
      height: this.state.garageHeight + "px"
    };

    return (
      <div
        className="tab-pane fade show active"
        id="tab-2"
        role="tabpanel"
        aria-labelledby="tab-2"
      >
        <div className="container">
          <div
            className="row"
            style={{ minHeight: "calc(100vh - 94.74px - 19.976px - 24px)" }}
          >
            {/* <button onClick={this.handleClick}>
                        DONT PUSH ME!!!
                      </button>
                      <button onClick={this.handleResetClick}>RESET ALL</button> */}
            <div className="col-12  justify-content-md-center align-self-center">
              <div id="background" style={dynamicStyleBackgroung}>
                <div id="car" style={dynamicStyle0}>
                  <div id="before" style={dynamicStyleBefore}></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

TrackingTab.propTypes = {
  dataNode: PropTypes.object.isRequired,
  getDataNode: PropTypes.func.isRequired,
  setUpActiveCar: PropTypes.func.isRequired,
  postResetData: PropTypes.func.isRequired,
  settings: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  settings: state.settings,
  dataNode: state.dataNode
});
export default connect(mapStateToProps, {
  getDataNode,
  postResetData,
  setUpActiveCar
})(TrackingTab);
