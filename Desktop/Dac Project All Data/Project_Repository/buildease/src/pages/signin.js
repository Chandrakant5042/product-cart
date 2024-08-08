import React from "react";
import "./signin.css";
// import { Link } from "react-router-dom";
import { useHistory, Link } from "react-router-dom";

const SignIn = () => {
  const handleSignUpClick = () => {
    // Logic for handling Sign Up button click can be added here
    console.log("Sign Up button clicked");
  };
  const history = useHistory();

  return (
    <div id="signInWrapper">
      <div id="signInContainer">
        {/* <Link to="/"> */}
        <button
          type="submit"
          className="back  hoverEffect"
          onClick={() => history.goBack()}
        >
          Back
        </button>
        {/* </Link> */}
        <form action="/signIn" method="post">
          <h2 id="title">Login</h2>
          <div id="accountCards">
            <div className="cardElement hoverEffect">
              <a href="#">
                <i className="fa-brands fa-google-plus-g"></i>
              </a>
              <span className="hidden">Google</span>
            </div>
            <div className="cardElement hoverEffect">
              <a href="#">
                <i className="fa-brands fa-facebook-f"></i>
              </a>
              <span className="hidden">Facebook</span>
            </div>
            <div className="cardElement hoverEffect">
              <a href="#">
                <i className="fa-brands fa-github"></i>
              </a>
              <span className="hidden">GitHub</span>
            </div>
            <div className="cardElement hoverEffect">
              <a href="#">
                <i className="fa-brands fa-linkedin-in"></i>
              </a>
              <span className="hidden">LinkedIn</span>
            </div>
            <div className="cardElement hoverEffect">
              <a href="#">
                <i className="fa-solid fa-mobile"></i>
              </a>
              <span className="hidden">Otp</span>
            </div>
          </div>
          <p id="alternativeSignIn">or use your email & password</p>
          <input
            type="email"
            required
            placeholder="Email"
            name="signInEmail"
            id="signInEmail"
          />
          <input
            type="password"
            required
            placeholder="Password"
            name="signInPassword"
            id="signInPassword"
          />
          <p id="forgotPassword">Forgot Your Password?</p>
          <div id="signInContainerButtons">
            <button type="submit" className="button hoverEffect">
              SIGN IN
            </button>
            <Link to="/signup">
              <button
                type="button"
                className="button hoverEffect shake"
                id="signUpButton"
                onClick={handleSignUpClick}
              >
                SIGN UP
              </button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignIn;
