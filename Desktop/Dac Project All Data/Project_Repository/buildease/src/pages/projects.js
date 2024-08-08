import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import "./projects.css";

import img1 from "../assets/image1.jpg";
import img2 from "../assets/image2.jpg";
import img3 from "../assets/image3.jpg";
import img4 from "../assets/image4.jpg";
import img5 from "../assets/image5.jpg";
import img6 from "../assets/image6.jpg";
import img7 from "../assets/image7.jpg";
import img8 from "../assets/image8.jpg";
import img9 from "../assets/image9.jpg";
import img10 from "../assets/image10.jpg";

const Projects = (props) => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const [activeTab, setActiveTab] = useState(0);

  const features = [
    {
      imgSrc: props.feature1ImgSrc,
      imgAlt: props.feature1ImgAlt,
      title: props.feature1Title,
      description: props.feature1Description,
    },
    {
      imgSrc: props.feature2ImgSrc,
      imgAlt: props.feature2ImgAlt,
      title: props.feature2Title,
      description: props.feature2Description,
    },
    {
      imgSrc: props.feature3ImgSrc,
      imgAlt: props.feature3ImgAlt,
      title: props.feature3Title,
      description: props.feature3Description,
    },
    {
      imgSrc: props.feature4ImgSrc,
      imgAlt: props.feature4ImgAlt,
      title: props.feature4Title,
      description: props.feature4Description,
    },
    {
      imgSrc: props.feature5ImgSrc,
      imgAlt: props.feature5ImgAlt,
      title: props.feature5Title,
      description: props.feature5Description,
    },
    {
      imgSrc: props.feature6ImgSrc,
      imgAlt: props.feature6ImgAlt,
      title: props.feature6Title,
      description: props.feature6Description,
    },
    {
      imgSrc: props.feature7ImgSrc,
      imgAlt: props.feature7ImgAlt,
      title: props.feature7Title,
      description: props.feature7Description,
    },
    {
      imgSrc: props.feature8ImgSrc,
      imgAlt: props.feature8ImgAlt,
      title: props.feature8Title,
      description: props.feature8Description,
    },
    {
      imgSrc: props.feature9ImgSrc,
      imgAlt: props.feature9ImgAlt,
      title: props.feature9Title,
      description: props.feature9Description,
    },
    {
      imgSrc: props.feature10ImgSrc,
      imgAlt: props.feature10ImgAlt,
      title: props.feature10Title,
      description: props.feature10Description,
    },
  ];

  return (
    <div className="thq-section-padding">
      <div className="features1-container1 thq-section-max-width">
        <div className="features1-image-container">
          {features.map(
            (feature, index) =>
              activeTab === index && (
                <img
                  key={index}
                  alt={feature.imgAlt}
                  src={feature.imgSrc}
                  className="features1-image thq-img-ratio-16-9"
                />
              )
          )}
        </div>
        <div className="features1-tabs-menu">
          {features.map((feature, index) => (
            <div
              key={index}
              onClick={() => setActiveTab(index)}
              className="features1-tab-horizontal"
            >
              <div className="features1-divider-container">
                {activeTab === index && (
                  <div className="features1-divider"></div>
                )}
              </div>
              <div className="features1-content">
                <h2 className="thq-heading-2">{feature.title}</h2>
                <span className="thq-body-small">{feature.description}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

Projects.defaultProps = {
  feature1ImgSrc: img1,
  feature1ImgAlt: "Modern Building Design",
  feature1Title: "Quality Construction Services",
  feature1Description: "Expert Building Contractors",
  feature2ImgSrc: img2,
  feature2ImgAlt: "Creative Building Concepts",
  feature2Title: "Customized Project Planning",
  feature2Description: "Innovative Architectural Designs",
  feature3ImgSrc: img3,
  feature3Description: "Sustainable Construction Practices",
  feature3Title: "Green Building Solutions",
  feature3ImgAlt: "Eco-Friendly Construction",
  feature4ImgSrc: img4,
  feature4Description: "Project Management",
  feature4Title: "Efficient Project Management",
  feature4ImgAlt: "Project Management",
  feature5ImgSrc: img5,
  feature5Description: "Home Renovation",
  feature5Title: "Home Renovation",
  feature5ImgAlt: "Renovation Services",
  feature6ImgSrc: img6,
  feature6Description: "Commercial Construction",
  feature6Title: "Commercial Construction",
  feature6ImgAlt: "Commercial Construction",
  feature7ImgSrc: img7,
  feature7Description: "Interior Design",
  feature7Title: "Interior Design",
  feature7ImgAlt: "Interior Design",
  feature8ImgSrc: img8,
  feature8Description: "Landscaping",
  feature8Title: "Landscaping",
  feature8ImgAlt: "Landscaping Services",
  feature9ImgSrc: img9,
  feature9Description: "Plumbing Services",
  feature9Title: "Plumbing Services",
  feature9ImgAlt: "Plumbing Services",
  feature10ImgSrc: img10,
  feature10Description: "Electrical Services",
  feature10Title: "Electrical Services",
  feature10ImgAlt: "Electrical Services",
};

Projects.propTypes = {
  feature1ImgAlt: PropTypes.string,
  feature1ImgSrc: PropTypes.string,
  feature1Description: PropTypes.string,
  feature1Title: PropTypes.string,
  feature2Title: PropTypes.string,
  feature2Description: PropTypes.string,
  feature2ImgSrc: PropTypes.string,
  feature2ImgAlt: PropTypes.string,
  feature3Description: PropTypes.string,
  feature3Title: PropTypes.string,
  feature3ImgSrc: PropTypes.string,
  feature3ImgAlt: PropTypes.string,
  feature4Description: PropTypes.string,
  feature4Title: PropTypes.string,
  feature4ImgSrc: PropTypes.string,
  feature4ImgAlt: PropTypes.string,
  feature5Description: PropTypes.string,
  feature5Title: PropTypes.string,
  feature5ImgSrc: PropTypes.string,
  feature5ImgAlt: PropTypes.string,
  feature6Description: PropTypes.string,
  feature6Title: PropTypes.string,
  feature6ImgSrc: PropTypes.string,
  feature6ImgAlt: PropTypes.string,
  feature7Description: PropTypes.string,
  feature7Title: PropTypes.string,
  feature7ImgSrc: PropTypes.string,
  feature7ImgAlt: PropTypes.string,
  feature8Description: PropTypes.string,
  feature8Title: PropTypes.string,
  feature8ImgSrc: PropTypes.string,
  feature8ImgAlt: PropTypes.string,
  feature9Description: PropTypes.string,
  feature9Title: PropTypes.string,
  feature9ImgSrc: PropTypes.string,
  feature9ImgAlt: PropTypes.string,
  feature10Description: PropTypes.string,
  feature10Title: PropTypes.string,
  feature10ImgSrc: PropTypes.string,
  feature10ImgAlt: PropTypes.string,
};

export default Projects;
