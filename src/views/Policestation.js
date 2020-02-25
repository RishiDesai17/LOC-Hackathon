import React,{useState} from 'react';
//import Home from './Home';
import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import { makeStyles } from '@material-ui/core/styles';
import Navbar from './Navbar';
import AOS from 'aos';
import Button from '@material-ui/core/Button';
import 'aos/dist/aos.css';
import TextField from '@material-ui/core/TextField'

const useStyles = makeStyles({
    root: {
    width:'250px',

     
     
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
    
    form:
    {
        textAlign:"center",
        padding:"5px",
    },
  
    
  });

export default function Policestation()
{    const classes = useStyles();
  AOS.init({
    duration : 1000
  })
  const [name,setName] = useState();
  const [complaint,setComplaint] = useState();
  const [aadhar,setAadhar] = useState();
  const [no,setNo] = useState();
  const [email,setEmail] = useState();
  const [chowki,setChowki] = useState();
  const sendFir = async() => {
      try{
        const res = await fetch('http://localhost:8000/police/CreateFIR',{
            method:'POST',
            headers:{
                'Content-Type': 'application/json',
                'X-CSRftoken':'8Ubg2mZSNO0Ms0Mo5zn6EWk9dX05Jr3lrLI65aSW9nZmjLlrmib1CHieM3g7lPEv'
            },body:JSON.stringify({
                Victim_Name: name,
                Complaint: complaint,
                Aaadhar_No: parseInt(aadhar),
                Contact_No: parseInt(no),
                chowki: chowki,
                email: email
            })
        })
        const r =await res.json()
        console.log(r)
      }catch(err){
          console.log(err);
      }
  }
    return(
        <div className={classes.main}>
        <Navbar />
        <Typography component="div">
            <Box textAlign="center" fontWeight="fontWeightBold" fontSize="25px" fontFamily=" Helvetica" padding="10px">
                Know Police Stations around you!
            </Box>
      </Typography>
      {/* <iframe className={classes.map} src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d942.4994801090171!2d72.83743607483443!3d19.107747162267845!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x75f29a4205098f99!2sDwarkadas+J+Sanghvi+College+of+Engineering!5e0!3m2!1sen!2sus!4v1397913464271"></iframe> */}
      <iframe  data-aos='fade-up' src="https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d15079.240136442573!2d72.839731396761!3d19.1159879351994!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1spolice%20station%20near%20me!5e0!3m2!1sen!2sin!4v1582364923501!5m2!1sen!2sin" className={classes.map}></iframe>
      <div className={classes.firform}>
        <Typography component="div">  <Box fontSize="h3.fontSize" m={1}>
        First Information Report(F.I.R)
      </Box></Typography>
      <form className={classes.form} noValidate autoComplete="off">
      <TextField onChange={(event)=>{setName(event.target.value)}} id="Victim_Name" label="Victim_Name" variant="outlined" style={{marginBottom:"10px",marginRight:"10px"}} />
      <TextField onChange={(event)=>{setComplaint(event.target.value)}} id="Complaint" label="Complaint" variant="outlined"style={{marginBottom:"10px",marginRight:"10px"}} />
      <TextField onChange={(event)=>{setAadhar(event.target.value)}} id="Aaadhar_No" label="Aaadhar_No" variant="outlined" style={{marginBottom:"10px",marginRight:"10px"}}/><br />
      
      <TextField onChange={(event)=>{setNo(event.target.value)}} id="Contact_No" label="Contact_No" variant="outlined" style={{marginBottom:"10px",marginRight:"10px"}} />
      <TextField onChange={(event)=>{setEmail(event.target.value)}} id="email" label="email" variant="outlined" style={{marginBottom:"10px",marginRight:"10px"}}/><br />
      <TextField onChange={(event)=>{setChowki(event.target.value)}} id="Chowki" label="Chowki" variant="outlined" style={{marginBottom:"10px",marginRight:"10px"}} />
        
    </form>
    <Button onClick={()=>sendFir()} color="primary" variant="outlined">SEND FIR</Button>
      </div>
      </div>
    );
}