import React, { useEffect } from "react";
import { useHistory } from "react-router-dom";
import "./services.css";

export default function Services() {
  const history = useHistory();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="servicesWrapper">
      <div className="servicesContainer">
        <button className="backButton" onClick={() => history.goBack()}>
          Back
        </button>
        <h2 className="title">Our Services</h2>
        <div className="servicesList">
          <div
            className="serviceItem"
            onClick={() => history.push("/admin-portfolio")}
          >
            <h3>Admin Portfolio</h3>
            <p>Manage and view all administrative portfolios.</p>
          </div>
          <div
            className="serviceItem"
            onClick={() => history.push("/builder-portfolio")}
          >
            <h3>Builder Portfolio</h3>
            <p>Access builder portfolios and project details.</p>
          </div>
          <div
            className="serviceItem"
            onClick={() => history.push("/user-portfolio")}
          >
            <h3>User Portfolio</h3>
            <p>View and manage user portfolios and related information.</p>
          </div>
        </div>
      </div>
    </div>
  );
}
