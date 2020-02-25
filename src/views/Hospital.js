import React from 'react';
import Home from './Home';
import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Navbar from './Navbar';
import AOS from 'aos';
import 'aos/dist/aos.css';

const useStyles = makeStyles({
    root: {
    width:'250px',
     margin:'20px',
     
     
    },
    title: {
      fontSize: 14,
    },
    pos: {
      marginBottom: 12,
    },
    map: {
        minHeight:'500px',
        minWidth:'500px',
      
        boxShadow:'10px',
        border:'1px solid black',
        borderRadius:'10px'

    },
   
  });

export default function Hospital()
{    const classes = useStyles();
   
  AOS.init({
    duration : 1000
  })
    return(
      <div className={classes.main}>
        <Navbar />
        <Typography component="div">
            <Box textAlign="center" fontWeight="fontWeightBold" fontSize="25px" m={5}>
                Know Hospitals around you!
            </Box>
      </Typography>
      {/* <iframe className={classes.map} src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d942.4994801090171!2d72.83743607483443!3d19.107747162267845!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x75f29a4205098f99!2sDwarkadas+J+Sanghvi+College+of+Engineering!5e0!3m2!1sen!2sus!4v1397913464271"></iframe> */}
      <iframe  data-aos='fade-up' src="https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d15079.240136442573!2d72.839731396761!3d19.1159879351994!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1shospital%20near%20me!5e0!3m2!1sen!2sin!4v1582365875242!5m2!1sen!2sin" className={classes.map}></iframe>
     
      </div>
    );
}
