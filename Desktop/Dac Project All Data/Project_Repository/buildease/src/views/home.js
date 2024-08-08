import React, { useEffect } from "react";
import { Helmet } from "react-helmet";
import Hero from "../components/hero";
import Features1 from "../components/features1";
import CTA from "../components/cta";
import Features2 from "../components/features2";
import Pricing from "../components/pricing";
import Steps from "../components/steps";
import Testimonial from "../components/testimonial";
import Contact from "../components/contact";
import Footer from "../components/footer";
import "./home.css";

const Home = (props) => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="home-container">
      <Helmet>
        <title>BUILDEASE</title>
      </Helmet>
      <Hero />
      <Features1 />
      <CTA />
      <Features2 />
      <Pricing />
      <Steps />
      <Testimonial />
      <Contact />
      {/* <Footer /> */}
    </div>
  );
};

export default Home;
