import React, { Component } from "react";
import logo from "../../resources/images/logo.png";
import { Link } from "react-router-dom";

class Header extends Component {
  render() {
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
              <li className="nav-item">
                <Link to="/garage" className="nav-link page-scroll">
                  GARAGE
                </Link>
              </li>

              {/* <!-- Dropdown Menu -->           */}
              {/* <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle page-scroll"
                  href="#details"
                  id="navbarDropdown"
                  role="button"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  DETAILS
                </a>
                <div className="dropdown-menu" aria-labelledby="navbarDropdown">
                  <a className="dropdown-item" href="terms-conditions.html">
                    <span className="item-text">TERMS CONDITIONS</span>
                  </a>
                  <div className="dropdown-items-divide-hr"></div>
                  <a className="dropdown-item" href="privacy-policy.html">
                    <span className="item-text">PRIVACY POLICY</span>
                  </a>
                </div>
              </li> */}
              {/* <!-- end of dropdown menu --> */}
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

export default Header;
