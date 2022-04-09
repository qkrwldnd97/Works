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

const Stack = createStackNavigator();
const Tab = createMaterialTopTabNavigator();


export default class AuthStack extends React.Component {
  render(){
    return (
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        <Stack.Screen
          name="Cover"
          component={Cover}
          options={{ gestureEnabled: false }}
        />
      </Stack.Navigator>
    );
  }
};

