import React from "react";
import ReactDOM from "react-dom";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
} from "react-router-dom";
import "./style.css";
import NotFound1 from "./views/NotFound1";
import Home from "./views/home";
import SignIn from "./pages/signin";
import SignUp from "./pages/signup";
import Navbar from "./components/navbar";
import About from "./pages/about";
import Contact from "./pages/contactus";
import Services from "./pages/services";
import Projects from "./pages/projects";
import Footer from "./components/footer";

const App = () => {
  return (
    <Router>
      <div>
        <Navbar />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/about" component={About}></Route>
          <Route path="/contactus" component={Contact}></Route>
          <Route path="/services" component={Services}></Route>
          <Route path="/projects" component={Projects}></Route>
          <Route path="/signin" component={SignIn} />
          <Route path="/signup" component={SignUp} />
          <Route path="/NotFound1" component={NotFound1} />
          <Route path="*" component={NotFound1} />
          <Redirect to="*" />
        </Switch>
        <Footer />
      </div>
    </Router>
  );
};

ReactDOM.render(<App />, document.getElementById("app"));
