import React,{useContext} from "react";
import { Route, Redirect } from "react-router-dom";
import auth from './auth';
import {Context} from '../context/Context';

export const ProtectedRoute = ({
  component: Component,
  ...rest
}) => {
  const context = useContext(Context);
  return (
    <Route
      {...rest}
      render={props => {
        if (context.auth) {
          return <Component {...props} />;
        } else {
          console.log(context.auth)
          return (
            <Redirect
              to={{
                pathname: "/login",
                state: {
                  from: props.location
                }
              }}
            />
          );
        }
      }}
    />
  );
};