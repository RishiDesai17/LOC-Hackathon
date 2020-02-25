import React,{useState,useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Navbar from './Navbar';

export default function FIR(){
    const [firs,setFirs]=useState();
    const[flag,setFlag]=useState(false);
    const fn = async() => {
        const res=await fetch('http://localhost:8000/police/ViewFIR');
            const r=await res.json()
            console.log(r)
            setFirs(r);
            setFlag(true);
    }
    useEffect(()=>{
            fn();
        },[])
    return(
        <p>{flag&&firs.map((x)=>(
            
            <div>
                 <Navbar />
            <h1 style={{fontFamily:'Montserrat'}}>FIRs</h1>
            <div style={{border:"1px solid black ",borderRadius:"10px", width:'40%',marginLeft: '30%'}}>
            <h2 style={{fontFamily:'Montserrat'}}>Name: {x.Victim_Name}</h2>
            <h2 style={{fontFamily:'Montserrat'}}>Complaint: {x.Complaint}</h2>
            <h2 style={{fontFamily:'Montserrat'}}>Contactno.: {x.Contact_No}</h2>
            <h2 style={{fontFamily:'Montserrat'}}>Chowki: {x.Chowki}</h2>
            <a href={"http://localhost:8000/police/PoliceVerifyFIR/"+x.id} >To Verify</a>
            </div>
            </div>
        ))}</p>
    )
}