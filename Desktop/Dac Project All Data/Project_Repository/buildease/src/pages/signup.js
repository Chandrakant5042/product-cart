import React from "react";
import "./signup.css";
import { Link, useHistory } from "react-router-dom/cjs/react-router-dom.min";

const SignUp = () => {
  const history = useHistory();

  return (
    <div id="signUpWrapper">
      <div id="signUpContainer">
        {/* <Link to="/"> */}
        <button
          type="submit"
          className="back  hoverEffect"
          onClick={() => history.goBack()}
        >
          Back
        </button>
        {/* </Link> */}
        <form action="/signUp">
          <h2 id="title">Sign Up</h2>
          <div id="signUpInputs">
            <div className="formRow">
              <div className="formGroup halfWidth">
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  placeholder="First Name"
                  required
                />
              </div>
              <div className="formGroup halfWidth">
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  placeholder="Last Name"
                  required
                />
              </div>
            </div>
            <div className="formRow">
              <div className="formGroup halfWidth">
                <input
                  type="tel"
                  id="contactNumber"
                  name="contactNumber"
                  placeholder="Contact Number"
                  required
                />
              </div>
              <div className="formGroup halfWidth">
                <input
                  type="date"
                  id="dateOfBirth"
                  name="dateOfBirth"
                  placeholder="Date of Birth"
                  required
                />
              </div>
            </div>
            <div className="formRow">
              <div className="formGroup">
                <div id="gender">
                  <label htmlFor="male">Male</label>
                  <input type="radio" id="male" name="gender" required />
                  <label htmlFor="female">Female</label>
                  <input type="radio" id="female" name="gender" required />
                </div>
              </div>
            </div>
            <div className="formRow">
              <div className="formGroup halfWidth">
                <input
                  type="email"
                  id="signUpEmail"
                  name="signUpEmail"
                  placeholder="Email"
                  required
                />
              </div>
              <div className="formGroup halfWidth">
                <input
                  type="password"
                  id="signUpPassword"
                  name="signUpPassword"
                  placeholder="Password"
                  required
                />
              </div>
            </div>
            <div className="formRow">
              <div className="formGroup fullWidth">
                <input
                  type="password"
                  id="signUpPasswordConfirm"
                  name="signUpPasswordConfirm"
                  placeholder="Confirm Password"
                  required
                />
              </div>
            </div>
            <button type="submit" className="button hoverEffect shake">
              SIGN UP
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
