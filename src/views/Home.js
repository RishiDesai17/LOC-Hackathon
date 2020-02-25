import React,{useState,useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import "./Background.css";
import Paper from '@material-ui/core/Paper';
import Navbar from './Navbar';
import {Link} from 'react-router-dom';
let i=0;
const useStyles = makeStyles(theme => ({
  paper:{
    display: 'flex',
    flexWrap: 'wrap',
    marginLeft:'21%',   
    
    
    '& > *': {
      margin: theme.spacing(1),
      width: theme.spacing(16),
      height: theme.spacing(16),
     
      width:"250px",
      height:"300px",
      marginTop:"100px",
      marginLeft:"40px",
      
      
  }
}
 

  
}));

export default function Home() {
  const classes = useStyles();
  const [news,setNews]=useState();
  const [flag,setFlag]=useState(false);
  useEffect(()=>{
    setInterval(async()=>{
      const res = await fetch('http://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=eff73011dc944f4a8d5b9cf50640eaa3')
      const r = await res.json()
      console.log(r.articles);
      if(i+3===9){
        i=0;
      }
      setNews(r.articles.slice(i,i+3))
      i+=3;
      if(r.articles){
        setFlag(true);
      }
      
    },2000)
  },[])
  return(
      <body className={classes.home} >
      <Navbar />
      <div id="Home">
        <div className={classes.paper}>
          {/* <p>{JSON.stringify(news)}</p> */}
          {flag&&news.map((news)=>(
            <div>
            {/* <h3>{news.title}</h3> */}
            <a href={news.url} style={{textDecoration:'none',color:'black'}}>
            <div className="card">
  <img src={news.urlToImage}  style={{height: 150,width: 250, overflow:'hidden',borderRadius:"10px" }} href={news.url} />
  <div className="container">
          <h4><b>{news.title}</b></h4>
  </div>
</div></a>
            </div>
          ))}
        
      </div> 
      </div>
      </body> 
  );
}

