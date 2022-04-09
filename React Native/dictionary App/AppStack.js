import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { NavigationContainer, useNavigation } from '@react-navigation/native';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import 'react-native-gesture-handler';
import ActionButton from 'react-native-action-button';
import Icon from 'react-native-vector-icons/Ionicons';
import {
  Image,
  StyleSheet,
  View,
  TouchableOpacity,
  StatusBar,
  Text,
} from 'react-native';

import Home from './screens/home.js';
import Search from './screens/search.js';
import Book_list from './screens/book_list.js';
import Vocabulary from './screens/vocabulary.js';
import Cover from './screens/cover.js';
import RoundGlass from './images/round_glass_icon';
import Book from './screens/book_panel.js';
import Search_page from './screens/search_page';
import Cardpage from './screens/card';
import Note from './screens/note';

import Splash from './images/logo.js';


const Stack = createStackNavigator();
const Tab = createMaterialTopTabNavigator();




function Hhome() {
  return (
    <Tab.Navigator
      tabBarOptions={{ showLabel: false }}
      tabBarPosition={'bottom'}>
      <Tab.Screen name="Home" component={Home} />
      <Tab.Screen name="Book_list" component={Book_list} />
      <Tab.Screen name="Vocabulary" component={Vocabulary} />
    </Tab.Navigator>
  );
}

export default class AppStack extends React.Component{
  render(){
  return (
    
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        <Stack.Screen name="Home" component={Hhome} />
        <Stack.Screen name="Book" component={Book} />
        <Stack.Screen name="Search" component={Search_page} />
        <Stack.Screen name="Cardpage" component={Cardpage} />
        <Stack.Screen name="Note" component={Note} />
      </Stack.Navigator>
  );
  }
}

