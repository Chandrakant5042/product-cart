import React from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";

import "./cta.css";

const CTA = (props) => {
  return (
    <div className="thq-section-padding">
      <div className="thq-section-max-width">
        <div className="cta-accent2-bg">
          <div className="cta-accent1-bg">
            <div className="cta-container1">
              <div className="cta-content">
                <span className="thq-heading-2">{props.heading1}</span>
                <p className="thq-body-large">{props.content1}</p>
              </div>
              <div className="cta-actions">
                <Link to="/contactus">
                  <button
                    type="button"
                    className="thq-button-filled cta-button"
                  >
                    {props.action1}
                  </button>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

CTA.defaultProps = {
  heading1: "Ready to start your next project?",
  content1: "Contact us today to get a free consultation and quote.",
  action1: "Get in touch",
};

CTA.propTypes = {
  heading1: PropTypes.string,
  content1: PropTypes.string,
  action1: PropTypes.string,
};

export default CTA;
