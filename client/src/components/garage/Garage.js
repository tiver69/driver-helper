import React, { Component } from "react";
import ConfigurationTab from "./ConfigurationTab";
import TrackingTab from "./TrackingTab";
import MonitoringTab from "./MonitoringTab";
// import IcoMoon from "react-icomoon";

class Garage extends Component {
  render() {
    return (
      <React.Fragment>
        <header id="header" className="header">
          <div className="header-content">
            <div className="container">
              <div className="row">
                <div className="col">
                  <div className="text-container">
                    <div className="p-large tabs">
                      <div className="container">
                        <div className="row">
                          <ul
                            className="nav nav-tabs"
                            id="lenoTabs"
                            role="tablist"
                          >
                            <li className="nav-item col-4">
                              <a
                                className="nav-link"
                                id="nav-tab-1"
                                data-toggle="tab"
                                href="#tab-1"
                                role="tab"
                                aria-controls="tab-1"
                                aria-selected="true"
                              >
                                <i className="fas fa-cog"></i>CONFIGURING
                              </a>
                            </li>
                            <li className="nav-item col-4">
                              <a
                                className="nav-link active"
                                id="nav-tab-2"
                                data-toggle="tab"
                                href="#tab-2"
                                role="tab"
                                aria-controls="tab-2"
                                aria-selected="false"
                              >
                                <i className="fas fa-binoculars"></i>TRACKING
                              </a>
                            </li>
                            <li className="nav-item col-4">
                              <a
                                className="nav-link"
                                id="nav-tab-3"
                                data-toggle="tab"
                                href="#tab-3"
                                role="tab"
                                aria-controls="tab-3"
                                aria-selected="false"
                              >
                                <i className="fas fa-search"></i>MONITORING
                              </a>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </header>

        <div id="features" className="tabs-content">
          <div className="container">
            <div className="row">
              <div className="tab-content" id="lenoTabsContent">
                <ConfigurationTab />
                <TrackingTab />
                <MonitoringTab />
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default Garage;
