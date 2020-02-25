import React from 'react';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import SvgIcon from '@material-ui/core/SvgIcon';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import {Link} from 'react-router-dom';

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
      },
      menuButton: {
        marginRight: theme.spacing(2),
      },
      title: {
        flexGrow: 1,
        fontFamily:	'Montserrat',
      },  
    list: {
    width: 250,
  },
}));



export default function Navbar() {
    const classes = useStyles();
    const [state, setState] = React.useState({
     
      left: false,
      chat:false,
     
    });
   /* function HomeIcon(props) {
      return (
        <SvgIcon {...props}>
          <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z" />
        </SvgIcon>
      );
    }*/
    const chatPopUp = () => {
      var x = document.getElementById('iframe')
      if(x.style.display==="block"){
        x.style.display='none'
      }
      else{
        x.style.display='block'
      }
    }
    const toggleDrawer = (side, open) => event => {
      if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
        return;
      }
  
      setState({ ...state, [side]: open });
    };
  
    const sideList = side => (
      <div
        className={classes.list}
        role="presentation"
        onClick={toggleDrawer(side, false)}
        onKeyDown={toggleDrawer(side, false)}
      >
        <List>
          {/* {[ 'Police Station','Hospital','Lawyers','BlackListed Sites'].map((text, index) => (
            <ListItem button key={text}>
              <ListItemText primary={text} />
            </ListItem>
          ))} */}
           <Link style={{textDecoration:'none',color:'black'}} to="/home">
          <ListItem button >
              <ListItemText primary={'Home'} />
            </ListItem>
            </Link>
          <Link style={{textDecoration:'none',color:'black'}} to="/policestation">
          <ListItem button >
              <ListItemText primary={'Police station'} />
            </ListItem>
            </Link>
            <Link style={{textDecoration:'none',color:'black'}} to="/hospital">
            <ListItem button >
              <ListItemText primary={'Hospital'} />
            </ListItem>
            </Link>
            <Link style={{textDecoration:'none',color:'black'}} to="/law">
            <ListItem button >
              <ListItemText primary={'Lawyers'} />
            </ListItem>
            </Link>
            <Link style={{textDecoration:'none',color:'black'}} to="/blacklisted">
            <ListItem button >
              <ListItemText primary={'Blacklisted'} />
            </ListItem>
            </Link>
            <Link style={{textDecoration:'none',color:'black'}} to="/fir">
            <ListItem button >
              <ListItemText primary={'FIR Details'} />
            </ListItem>
            </Link>
            <Link style={{textDecoration:'none',color:'black'}} to="/camera">
            <ListItem button >
              <ListItemText primary={'Criminal Face Detection'} />
            </ListItem>
            </Link>
        </List>
        {/* <List>
          {[ 'Police Station','Hospital','Lawyers','BlackListed Sites',].map((text, index) => (
            <ListItem button key={text}>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List> */}
      </div>
    );
  
    return (
      <body className={classes.root} >
       
        <AppBar position="static" style={{ backgroundColor: '#802b00',boxShadow: 'none'}}>
          <Toolbar>
            <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
              <Button onClick={toggleDrawer('left', true)}><MenuIcon style={{color: 'white'}} fontSize="large" /></Button>
                  <Drawer open={state.left} onClose={toggleDrawer('left', false)}>
                      {sideList('left')}
                   </Drawer>
            </IconButton>
            <Typography variant="h4" className={classes.title}>
              LINES  OF  SAFETY
            </Typography>
            <Button onClick={()=>{chatPopUp()}} color="inherit">Chat</Button>
            <Link to="/login" style={{textDecoration:'none',color:'white'}}>
              <Button color="inherit">Login</Button>
            </Link>
            <Button color="inherit">SignUp</Button>
          </Toolbar>
        </AppBar>
        <div>
        <iframe id="iframe" style={{float:"right",display:'none',border:"1px solid black",borderRadius:"10px",marginRight:"10px"}}
    allow="microphone;"
    width="350"
    height="430"
    src="https://console.dialogflow.com/api-client/demo/embedded/18b38abb-ce92-43c7-aec2-a68e131b8ffe">
</iframe>
        </div>
</body>
);
}