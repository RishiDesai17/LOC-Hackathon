import React,{useState,useEffect} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Navbar from './Navbar';
export default function Criminal(){
    const [criminal,setCriminal]=useState();
    const[flag,setFlag]=useState(false);
    const fn = async() => {
        const res=await fetch('http://localhost:3009/criminals');
            const r=await res.json()
            console.log(r.docs)
            setCriminal(r.docs);
            setFlag(true);
    }
    useEffect(()=>{
        fn();
    },[])
    return(
        <div>
            {flag&&criminal.map((x)=>(
                <div>
                <h2>{x.name}</h2>
                <img src={x.image} style={{border:"1px solid black",borderRadius:"20px"}} />
                {x.crimes.map((y)=>(
                    <p>{y}</p>
                ))}
                </div>
            ))}
        </div>
    )
}