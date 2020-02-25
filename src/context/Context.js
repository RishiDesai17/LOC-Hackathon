import React,{useState,createContext} from 'react';

export const Context = createContext({
    auth: false
})

const ContextProvider = (props) => {
    const [auth,setAuth] = useState(false);
    const authenticate = () => {
        setAuth(true);
    }
    return(
        <Context.Provider value={{auth: auth,authenticate: authenticate}}>
            {props.children}
        </Context.Provider>
    )
}

export default ContextProvider;