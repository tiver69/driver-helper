import React, { Component } from "react";
import logo from "../../resources/images/logo.png";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import { getAllCars } from "../../actions/SettingsActions";
import PropTypes from "prop-types";

class Header extends Component {
  componentDidMount() {
    this.props.getAllCars();
  }

  render() {
    const { allCars } = this.props.settings;
    var outputCarsSlides = allCars.map(car => (
      <React.Fragment key={car.id}>
        {" "}
        <Link to={"/garage" + car.id} className="dropdown-item">
          <span className="item-text">
            {car.brand} {car.model}
          </span>
        </Link>
        <div className="dropdown-items-divide-hr"></div>
      </React.Fragment>
    ));

    return (
      <React.Fragment>
        <nav className="navbar navbar-expand-md navbar-dark navbar-custom fixed-top">
          <a className="navbar-brand logo-image" href="/">
            <img src={logo} alt="alternative" />
            <span style={{ paddingLeft: "10px" }}>DRIVER HELPER</span>
          </a>

          {/* <!-- Mobile Menu --> */}
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-awesome fas fa-bars"></span>
            <span className="navbar-toggler-awesome fas fa-times"></span>
          </button>
          {/* <!-- end of mobile menu --> */}

          <div className="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <a className="nav-link page-scroll" href="/">
                  HOME <span className="sr-only">(current)</span>
                </a>
              </li>
              <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle page-scroll"
                  href="#details"
                  id="navbarDropdown"
                  role="button"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  GARAGE
                </a>
                <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                  {outputCarsSlides}
                </div>
              </li>
            </ul>

            {/* <span className="nav-item social-icons">
              <span className="fa-stack">
                <a href="#your-link">
                  <i className="fas fa-circle fa-stack-2x"></i>
                  <i className="fab fa-facebook-f fa-stack-1x"></i>
                </a>
              </span>
              <span className="fa-stack">
                <a href="#your-link">
                  <i className="fas fa-circle fa-stack-2x"></i>
                  <i className="fab fa-twitter fa-stack-1x"></i>
                </a>
              </span> 
            </span>*/}
          </div>
        </nav>
      </React.Fragment>
    );
  }
}

Header.propTypes = {
  settings: PropTypes.object.isRequired,
  getAllCars: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  settings: state.settings
});
export default connect(mapStateToProps, { getAllCars })(Header);
