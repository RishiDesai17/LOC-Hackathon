import React from 'react';
import Webcam from 'react-webcam';
import {Link} from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import CameraAltIcon from '@material-ui/icons/CameraAlt';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Navbar from './Navbar';
// import * as base64ToImage from 'base64-to-image';
// import * as fs from 'fs';

const Faceui = () => {
  const useStyles =  makeStyles(theme => ({
    CameraAltIcon:
    {
        marginLeft:"45%",
        fontSize:"150px",
        marginTop:"50px",

    },
    Info:
    {
        '& > *': {
            margin: theme.spacing(1),
            width: 450,
           marginLeft:"35%",

          },
    }
}));const classes = useStyles();
  //   const webcamRef = React.useRef(null);
 
  // const capture = React.useCallback(
  //   () => {
  //     const imageSrc = webcamRef.current.getScreenshot();
  //     console.log(imageSrc);
  //   },
  //   [webcamRef]
  // );const videoConstraints = {
  //   width: 1280,
  //   height: 720,
  //   facingMode: "user"
  // };
    return(
        <div>
        <Navbar />
        <h1>Welcome!</h1>
        <Link to="/camera"><CameraAltIcon className={classes.CameraAltIcon} edge="start"  color="inherit" aria-label="menu"></ CameraAltIcon>
        </Link>
            
            <form className={classes.Info} noValidate autoComplete="off">
                <TextField
                    id="name"
                    label="Name"
                    variant="outlined"
                    color="secondary"
                />
                 <Button variant="contained" color="primary">
                     Submit
      </Button>
            </form>
        {/* <Webcam
        audio={false}
        height={720}
        ref={webcamRef}
        screenshotFormat="image/jpeg"
        width={1280}
        videoConstraints={videoConstraints}
      /> */}
      <div>
        <li>
          <Link to="/photo">Photo Input</Link>
        </li>
        <li>
          <Link to="/camera">Video Camera</Link>
        </li>
      </div>
      {/* <button onClick={capture}>Capture photo</button> */}
      </div>
    )
}

export default Faceui;