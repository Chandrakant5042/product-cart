import React, { useEffect } from "react";
import PropTypes from "prop-types";
import "./about.css";

import img1 from "../assets/image7.jpg";

const About = (props) => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="about-section">
      <div className="about-container">
        <div className="about-image-container">
          <img
            alt={props.imageAlt}
            src={props.imageSrc}
            className="about-image"
          />
        </div>
        <div className="about-content">
          <h2 className="about-heading">{props.title}</h2>
          <p className="about-description">{props.description}</p>
        </div>
      </div>
    </div>
  );
};

About.defaultProps = {
  imageSrc: img1, // Replace with your default image path
  imageAlt: "About Us Image",
  title: "About Our Company",
  description:
    "We are committed to delivering high-quality construction services...,  Gururaj Jayesh vallabh Mimansha Madhura  Gururaj Jayesh vallabh Mimansha Madhura  Gururaj Jayesh vallabh Mimansha Madhura  Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura  Gururaj Jayesh vallabh Mimansha Madhura  Gururaj Jayesh vallabh Mimansha Madhura  Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallabh Mimansha Madhura Gururaj Jayesh vallab",
};

About.propTypes = {
  imageAlt: PropTypes.string,
  imageSrc: PropTypes.string,
  title: PropTypes.string,
  description: PropTypes.string,
};

export default About;
