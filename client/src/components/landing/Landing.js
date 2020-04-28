import React, { Component } from "react";
import CarSlide from "./CarSlide";

class Landing2 extends Component {
  render() {
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
            <div className="row">
              <div className="col-lg-12">
                <div className="slider-container">
                  <div className="swiper-container image-slider">
                    <div className="swiper-wrapper">
                      <CarSlide
                        src="./images/aveo.jpg"
                        brand="Chevroet"
                        model="AVEO"
                      />
                      <CarSlide
                        src="./images/vitara.jpg"
                        brand="Suzuki"
                        model="VITARA"
                      />
                      <CarSlide
                        src="./images/mini.jpg"
                        brand="Mini"
                        model="COOPER"
                      />
                      <CarSlide
                        src="./images/aveo.jpg"
                        brand="Chevroet"
                        model="AVEO"
                      />
                      <CarSlide
                        src="./images/vitara.jpg"
                        brand="Suzuki"
                        model="VITARA"
                      />
                      <CarSlide
                        src="./images/mini.jpg"
                        brand="Mini"
                        model="COOPER"
                      />
                      <CarSlide
                        src="./images/aveo.jpg"
                        brand="Chevroet"
                        model="AVEO"
                      />
                      <CarSlide
                        src="./images/vitara.jpg"
                        brand="Suzuki"
                        model="VITARA"
                      />
                      <CarSlide
                        src="./images/mini.jpg"
                        brand="Mini"
                        model="COOPER"
                      />
                    </div>
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

export default Landing2;
