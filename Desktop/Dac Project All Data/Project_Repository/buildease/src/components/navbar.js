// src/components/Navbar.js
import React from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import "./navbar.css";
import main_logo from "../assets/Logo/logo-no-background.png";

const Navbar = (props) => {
  return (
    <header className="navbar-container">
      <header data-thq="thq-navbar" className="navbar-navbar-interactive">
        <img
          alt={props.logoAlt}
          src={props.logoSrc}
          className="navbar-image1"
        />
        <div data-thq="thq-navbar-nav" className="navbar-desktop-menu">
          <nav className="navbar-links">
            <Link to="/">Home</Link>
            <Link to="/about">About</Link>
            <Link to="/contactus">Contact Us</Link>
            <Link to="/services">Services</Link>
            <Link to="/projects">Projects</Link>
          </nav>
          <div className="navbar-buttons">
            <Link to="/signin">
              <button className="navbar-action1 thq-button-animated thq-button-filled">
                <span className="thq-body-small">Sign-In</span>
              </button>
            </Link>
            <Link to="/signup">
              <button className="navbar-action2 thq-button-outline thq-button-animated">
                <span className="thq-body-small">Sign-Up</span>
              </button>
            </Link>
          </div>
        </div>
        <div data-thq="thq-burger-menu" className="navbar-burger-menu">
          <svg viewBox="0 0 1024 1024" className="navbar-icon">
            <path d="M128 554.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 298.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667zM128 810.667h768c23.552 0 42.667-19.115 42.667-42.667s-19.115-42.667-42.667-42.667h-768c-23.552 0-42.667 19.115-42.667 42.667s19.115 42.667 42.667 42.667z"></path>
          </svg>
        </div>
        <div data-thq="thq-mobile-menu" className="navbar-mobile-menu">
          <div className="navbar-nav">
            <div className="navbar-top">
              <img
                alt={props.logoAlt}
                src={props.logoSrc}
                className="navbar-logo"
              />
              <div data-thq="thq-close-menu" className="navbar-close-menu">
                <svg viewBox="0 0 1024 1024" className="navbar-icon2">
                  <path d="M810 274l-238 238 238 238-60 60-238-238-238 238-60-60 238-238-238-238 60-60 238 238 238-238z"></path>
                </svg>
              </div>
            </div>
            <nav className="navbar-links1">
              <Link to="/">Home</Link>
              <Link to="/signin">Sign-In</Link>
              <Link to="/signup">Sign-Up</Link>
            </nav>
          </div>
          <div className="navbar-buttons1">
            <Link to="/signin">
              <button className="thq-button-filled">Login</button>
            </Link>
            <Link to="/signup">
              <button className="thq-button-outline">Register</button>
            </Link>
          </div>
        </div>
      </header>
    </header>
  );
};

Navbar.defaultProps = {
  logoSrc: main_logo,
  logoAlt: "BUILDEASE",
};

Navbar.propTypes = {
  logoSrc: PropTypes.string,
  logoAlt: PropTypes.string,
};

export default Navbar;
