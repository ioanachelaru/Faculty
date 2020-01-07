import React from 'react';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator} from 'react-navigation-stack';
import LoginScreen from '../screens/LoginScreen';
import HomeScreen from "../screens/HomeScreen";
import MapScreen from "../screens/Harta";

const AppStack = createStackNavigator(
    {
        Login: {
            screen: LoginScreen,
            navigationOptions: {
                header: null
            }
        },

        Home: {
             screen: HomeScreen,
             navigationOptions: {
                 title: 'Dashboard',
                 headerTintColor: '#3C1070',
                 headerStyle:{
                     backgroundColor: '#AD5389'
                 }
              }
        },
        Harta:{
            screen: MapScreen,
            navigationOptions: {
                title: 'Map',
                headerTintColor: '#3C1070',
                headerStyle:{
                    backgroundColor: '#AD5389'
                }
            }
        }
    }
);

const AppContainer = createAppContainer(AppStack);
export default AppContainer;