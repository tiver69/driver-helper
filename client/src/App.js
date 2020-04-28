import React from "react";
import "./App.css";
import "./resources/css/swiper.css";
import "./resources/css/magnific-popup.css";
import "./resources/css/bootstrap.css";
import "./resources/css/fontawesome-all.css";
import "./resources/css/style.css";
import Landing from "./components/landing/Landing";
import Garage from "./components/garage/Garage";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./store";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <Header />
        <Route exact path="/" component={Landing} />
        <Route exact path="/garage" component={Garage} />
        <Footer />
      </Router>
    </Provider>
  );
}

export default App;
