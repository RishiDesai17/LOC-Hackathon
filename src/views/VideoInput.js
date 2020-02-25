import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import Webcam from 'react-webcam';
import { loadModels, getFullFaceDescription, createMatcher } from '../api/face';
import Button from '@material-ui/core/Button';
import Navbar from './Navbar';

const WIDTH = 420;
const HEIGHT = 420;
const inputSize = 160;

class VideoInput extends Component {
  constructor(props) {
    super(props);
    this.webcam = React.createRef();
    this.state = {
      flag: false,
      fullDesc: null,
      detections: null,
      descriptors: null,
      faceMatcher: null,
      match: null,
      facingMode: null,
      name: null,
      data: [],
      latitude: null,
      longitude: null,
      criminalHist: [],
      image: null
      //descriptors: null
    };
  }
  getMyLocation() {
    const location = window.navigator && window.navigator.geolocation
    
    if (location) {
      location.getCurrentPosition((position) => {
        this.setState({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        })
      }, (error) => {
        this.setState({ latitude: 'err-latitude', longitude: 'err-longitude' })
      })
    }

  }
  componentDidMount(){
    this.getMyLocation()
  }
  componentWillMount = async () => {
    const res = await fetch("http://localhost:3009/criminals")
    const r = await res.json()
    console.log(r.docs);
    this.setState({data: r.docs})
    
    // for(var i = 0;i<r.docs.length;i++){
    //    var obj = {i.toString(): r.docs[i]}
    // }
    await loadModels(); 
    this.setState({ faceMatcher: await createMatcher( { 
     
       Wpf: r.docs[0],Wff: r.docs[1] } ) })   
  };

  cameraHandler = () => {
    this.setInputDevice();
  }

  getData = async() => {
    
    let arr = this.state.data.filter((x)=>{
      if(this.state.match.length===0){
        return 0;
      }
      return x.name===this.state.match[0]._label
    })
    if(this.state.match!==[]){
      this.setState({criminalHist: arr})
      this.setState({flag: true})
    }
  }

  setInputDevice = () => {
    navigator.mediaDevices.enumerateDevices().then(async devices => {
      let inputDevice = await devices.filter(
        device => device.kind === 'videoinput'
      );
      if (inputDevice.length < 2) {
        await this.setState({
          facingMode: 'user'
        });
      } else {
        await this.setState({
          facingMode: { exact: 'environment' }
        });
      }
      this.startCapture();
    });
  };

  startCapture = () => {
    this.interval = setInterval(() => {
      this.capture();

    }, 1500);
  };

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  email = async() =>{
    try{
      const res = await fetch('http://localhost:8000/police/sendmail',{
        method:'POST',
        headers:{
          'Content-Type': 'application/json',
          'X-CSRftoken':'8Ubg2mZSNO0Ms0Mo5zn6EWk9dX05Jr3lrLI65aSW9nZmjLlrmib1CHieM3g7lPEv'
        },
        body:JSON.stringify({
          Name: this.state.match[0]._label.toString(),
          Location: this.state.latitude.toString()+"  "+this.state.longitude.toString(),
          Timestamp: new Date().toISOString()
        })
      })
      const r = await res.json();
      console.log(r)
    }catch(err){
      console.log(err);
    }
  }

  update = async(img) => {
    try{
      const res = await fetch('http://localhost:3009/criminals/'+this.state.match[0]._label,{
        method:'PATCH',
        headers:{
          'Content-Type': 'application/json'
        },
        body:JSON.stringify({
          image: img
        })
      })
      const r = await res.json();
      console.log(r);
    }catch(err){
      console.log(err);
    }
  }
  

  send = async() => {
    try{
      const r = await fetch("http://localhost:3009/criminals/",{
        method: 'POST',
        headers:{
          'Content-Type':'application/json'
        },
        body:JSON.stringify({
          name: this.state.name,
          descriptors:[this.state.descriptors[0]]
        })
      })
      const res = await r.json();
      console.log(res);
      //this.setState({data: res})
    }catch(err){
      console.log(err);
    }
  }

  capture = async () => {
    if (!!this.webcam.current) {
      await getFullFaceDescription(
        this.webcam.current.getScreenshot(),
        inputSize
      ).then(fullDesc => {
        if (!!fullDesc) {
          this.setState({
            detections: fullDesc.map(fd => fd.detection),
            descriptors: fullDesc.map(fd => fd.descriptor)
          });
          console.log(this.state.detections);
          console.log(this.state.descriptors);
        }
      });

      if (!!this.state.descriptors && !!this.state.faceMatcher) {
        let match = await this.state.descriptors.map(descriptor =>
          this.state.faceMatcher.findBestMatch(descriptor)
        );
        this.setState({ match });
        let x = "unknown"
        if(this.state.match===[]){
          x = this.state.match[0]._label
        }
        if(this.state.descriptors!==[]&&x!=="unknown"){
          this.email();
        }
        // if(this.state.match[0]._label!=undefined){
        //   //console.log(this.state.match[0]._label)
        // }
      }
    }
  };

  render() {
    const { detections, match, facingMode } = this.state;
    let videoConstraints = null;
    let camera = '';
    if (!!facingMode) {
      videoConstraints = {
        width: WIDTH,
        height: HEIGHT,
        facingMode: facingMode
      };
      if (facingMode === 'user') {
        camera = 'Front';
      } else {
        camera = 'Back';
      }
    }

    let drawBox = null;
    if (!!detections) {
      drawBox = detections.map((detection, i) => {
        let _H = detection.box.height;
        let _W = detection.box.width;
        let _X = detection.box._x;
        let _Y = detection.box._y;
        return (
          <div key={i}>
            <div
              style={{
                position: 'absolute',
                border: 'solid',
                borderColor: 'blue',
                height: _H,
                width: _W,
                transform: `translate(${_X}px,${_Y}px)`
              }}
            >
              {!!match && !!match[i] ? (
                <p
                  style={{
                    backgroundColor: 'blue',
                    border: 'solid',
                    borderColor: 'blue',
                    width: _W,
                    marginTop: 0,
                    color: '#fff',
                    transform: `translate(-3px,${_H}px)`
                  }}
                >
                  {match[i]._label}
                </p>
              ) : null}
            </div>
          </div>
        );
      });
    }

    return (
      <div>
        <Navbar />
      <div
        className="Camera"
        style={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          fontFamily:'Montserrat'
        }}
      >
        <p>Camera: {camera}</p>
        <div
          // style={{
          //   width: WIDTH,
          //   height: HEIGHT
          // }}
        ><Button color="primary" variant="outlined" onClick={()=>this.cameraHandler()} >Capture</Button>
          <div style={{ position: 'relative', width: WIDTH,marginTop:"10px" }}>
            {!!videoConstraints ? (
              <div style={{ position: 'absolute' }}>
                <Webcam
                  audio={false}
                  width={WIDTH}
                  height={HEIGHT}
                  ref={this.webcam}
                  screenshotFormat="image/jpeg"
                  videoConstraints={videoConstraints}
                />
              </div>
            ) : null}
            {!!drawBox ? drawBox : null}
          </div>
        </div>
        {/* <button style={{marginTop: 50}} onClick={()=>{this.send()}} >POST</button> */}
       
        {this.state.descriptors!==[]&&this.state.descriptors?
        <Button style={{marginTop:450}} color="primary" variant="outlined" onClick={()=>{this.update(this.webcam.current.getScreenshot());
            this.getData();
        }} >GET DETAILS</Button>:null}
        {/* <p>{JSON.stringify(this.state.criminalHist)}</p> */}
        {this.state.flag&&this.state.criminalHist.map((x)=>(
          <div style={{fontFamily:"Montserrat"}}>
          <h1>Name:{x.name}</h1>
          <h2>Crimes:{x.crimes.map((y)=>(<h3>{y}</h3>))}</h2>
          {/* <h1>{x.name}</h1> */}
          </div>
        ))}
         </div></div>
    );
  }
}

export default withRouter(VideoInput);
