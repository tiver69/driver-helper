import React, { Component } from "react";
import CarSlide from "./CarSlide";
import { connect } from "react-redux";
import { getAllCars } from "../../actions/CarActions";
import PropTypes from "prop-types";

class Landing2 extends Component {
  componentDidMount() {
    this.props.getAllCars();
  }

  render() {
    const { allCars } = this.props.car;
    console.log(allCars);
    var outputCarsSlides = allCars.map(car => (
      <CarSlide
        key={car.id}
        src={car.imageSrc}
        brand={car.brand}
        model={car.model}
      />
    ));

    return (
      <React.Fragment>
        <header id="header" className="header">
          <div className="header-content" style={{ paddingTop: "1.5rem" }}>
            <div className="container">
              <div className="row">
                <div className="col-lg-6">
                  <div className="text-container">
                    <p className="p-large">
                      Your personal pocket helper in sticky situations.
                      <br /> Stay safe and take care of your car!
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </header>

        <div className="slider-2">
          <div className="container">
            <div
              className="row"
              style={{
                minHeight:
                  "calc(100vh - 139.635px - 19.976px - 24px - 4.875rem)"
              }}
            >
              <div className="col-lg-12  align-self-center">
                <div className="slider-container">
                  <div className="swiper-container image-slider">
                    <div className="swiper-wrapper">{outputCarsSlides}</div>
                    <div className="swiper-button-next"></div>
                    <div className="swiper-button-prev"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

Landing2.propTypes = {
  car: PropTypes.object.isRequired,
  getAllCars: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  car: state.car
});
export default connect(mapStateToProps, { getAllCars })(Landing2);
