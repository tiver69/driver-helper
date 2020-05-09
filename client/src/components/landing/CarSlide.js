import React, { Component } from "react";
import { Link } from "react-router-dom";

class CarSlide extends Component {
  render() {
    return (
      <div className="swiper-slide">
        <Link to={`/garage${this.props.id}`} className="car-slide">
          <img className="img-fluid" src={this.props.src} alt="your car" />

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
