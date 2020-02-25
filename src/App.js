import React, { Component } from 'react';
import { Route, BrowserRouter } from 'react-router-dom';
import createHistory from 'history/createBrowserHistory';
import './App.css';
import Login from './views/Login';
import Faceui from './views/faceui';
import VideoInput from './views/VideoInput';
import PoliceStation from './views/Policestation';
import Fir from './views/fir';
import Home from './views/Home';
import Hospital from './views/Hospital';
import Law from './views/Lawyer';
import Criminal from './views/Criminaldata';
import Blacklist from './views/Blacklisted';
//import Laywer from './views/Lawyer';

class App extends Component {
  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <div className="route">
            <Route exact path="/login" component={Login} />
            <Route exact path="/faceui" component={Faceui} />
            <Route exact path="/camera" component={VideoInput} />
            <Route exact path="/policestation" component={PoliceStation} />
            <Route exact path="/fir" component={Fir} />
            <Route exact path="/criminal" component={Criminal} />
            <Route exact path="/law" component={Law} />
            <Route exact path="/hospital" component={Hospital} />
            <Route exact path="/blacklisted" component={Blacklist} />
            <Route exact path="/home" component={Home} />
            {/* <Route exact path="/lawyer" component={Lawyer} /> */}
          </div>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
