import React,{useEffect,useState} from 'react';
import Navbar from './Navbar';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

import AOS from 'aos';
import 'aos/dist/aos.css';
const useStyles = makeStyles(theme=>({
    root: {
    width:'250px',
     margin:'20px',
    },
    paper: {
      padding: theme.spacing(2),
      textAlign: 'center',
      color: theme.palette.text.secondary,
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

    },root1: {
      minWidth: 275,
    },
    bullet: {
      display: 'inline-block',
      margin: '0 2px',
      transform: 'scale(0.8)',
    },
    pos1: {
      marginBottom: 12,
    },
  }));

export default function Lawyer()
{    const classes = useStyles();
  const bull = <span className={classes.bullet}>•</span>;
  AOS.init({
    duration : 1000
  })
  const [laws,setLaws]=useState()
  const [flag,setFlag]=useState(false)
  const fn = async() => {
    try{
      const res = await fetch('http://localhost:8000/Law/ViewLaws')
      const r = await res.json()
      console.log(r);
      setLaws(r);
      setFlag(true);
    }catch(err){
      console.log(err)
    }
  }
  useEffect(()=>{
    fn()
  },[])
    return(
        <div className={classes.main}>
        <Navbar />
        <Typography component="div">
            <Box textAlign="center" fontWeight="fontWeightBold" fontSize="25px" m={5}>
                Know Lawyers around you!
            </Box>
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={6}>
      {/* <iframe className={classes.map} src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d942.4994801090171!2d72.83743607483443!3d19.107747162267845!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x75f29a4205098f99!2sDwarkadas+J+Sanghvi+College+of+Engineering!5e0!3m2!1sen!2sus!4v1397913464271"></iframe> */}
      <iframe data-aos='fade-up'
   src="https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d12679.817240751296!2d72.8404099478507!3d19.119399897406335!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1slawyers%20near%20me!5e0!3m2!1sen!2sin!4v1582366088998!5m2!1sen!2sin" className={classes.map}></iframe></Grid>
     <Grid item xs={6}>
          <Paper className={classes.paper}>
         {flag&&laws.map((x)=>(
        <div style={{border:'1 px solid black'}}>
          <Card className={classes.root1}>
      <CardContent>
        <Typography className={classes.title} color="textSecondary" gutterBottom>
          
        </Typography>
        <Typography variant="h5" component="h2">
          Section: {x.Section_No}
        </Typography>
        <Typography className={classes.pos1} color="textSecondary">
          Chapter: {x.chapterNo}
        </Typography>
        <Typography variant="body2" component="p">
          {x.Description}
        </Typography>
      </CardContent>
      <CardActions>
      </CardActions>
    </Card>
    </div>
    ))}
    </Paper>
        </Grid>
        </Grid>
        </div>
      

    );
}
