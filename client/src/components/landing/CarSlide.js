import React, { Component } from "react";
import { Link } from "react-router-dom";

class CarSlide extends Component {
  render() {
    const imageBasePath = "/images/";
    const imageFileFormat = ".jpg";
    return (
      <div className="swiper-slide">
        <Link to={`/garage${this.props.id}`} className="car-slide">
          <img
            className="img-fluid"
            src={imageBasePath + this.props.src + imageFileFormat}
            alt="your car"
          />

          <div className="card-body">
            <p className="testimonial-text">
              {this.props.brand} {this.props.model}
            </p>
            {/* <p className="testimonial-author"></p> */}
          </div>
        </Link>
      </div>
    );
  }
}
export default CarSlide;
