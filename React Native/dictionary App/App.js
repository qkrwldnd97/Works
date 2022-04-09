import React, { useEffect, useState } from 'react';
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
  SafeAreaView,
  Button,
} from 'react-native';

import Home from './screens/home.js';
import Search from './screens/search.js';
import Book_list from './screens/book_list.js';
import Vocabulary from './screens/vocabulary.js';
import Cover from './screens/cover.js';
import AppStack from './AppStack';
import AuthStack from './AuthStack';
import Search_page from './screens/search_page';
import Cardpage from './screens/card';
import Note_pad from './screens/note';

import { AuthContext } from './context/context';

import Splash from './images/logo.js';
import RoundGlass from './images/round_glass_icon';
import Menu from './images/menu_icon';
import Menu_toggled from './images/menu_toggled';
import Card from './images/Card';
import Book from './images/book';
import Voca from './images/voca';
import Note_icon from './images/note';
import Search_icon from './images/search';

import { navigationRef } from './RootNavigation';
import * as RootNavigation from './RootNavigation.js';

const Stack = createStackNavigator();
const Tab = createMaterialTopTabNavigator();
var active = false;

const actionbutton = () => {
  return (
    <ActionButton
      buttonColor="background:rgba(255,255,255, 0.9)"
      active={false}
      activeOpacity={0.2}
      position="right"
      degrees="360"
      offsetY={70}
      onPress={() => (active = !active)}
      renderIcon={() => (active ? <Menu_toggled /> : <Menu />)}>
      <ActionButton.Item
        buttonColor="#FBFBFB"
        title="Search"
        activeOpacity={0.2}
        onPress={() => RootNavigation.navigate('Search') & (active = !active)}>
        <Search_icon />
      </ActionButton.Item>
      <ActionButton.Item
        buttonColor="#FBFBFB"
        title="Card"
        activeOpacity={0.2}
        onPress={() =>
          RootNavigation.navigate('Cardpage') & (active = !active)
        }>
        <Card />
      </ActionButton.Item>
      <ActionButton.Item
        buttonColor="#FBFBFB"
        title="Book"
        activeOpacity={0.2}
        onPress={() =>
          RootNavigation.navigate('Book_list') & (active = !active)
        }>
        <Book />
      </ActionButton.Item>
      <ActionButton.Item
        buttonColor="#FBFBFB"
        title="Voca"
        activeOpacity={0.2}
        onPress={() =>
          RootNavigation.navigate('Vocabulary') & (active = !active)
        }>
        <Voca />
      </ActionButton.Item>
      <ActionButton.Item
        buttonColor="#FBFBFB"
        title="Note"
        activeOpacity={0.2}
        onPress={() => RootNavigation.navigate('Note') & (active = !active)}>
        <Note_icon />
      </ActionButton.Item>
    </ActionButton>
  );
};



const App = () => {
  const [isLoading, setisLoading] = React.useState(true);
  const [userToken, setUserToken] = React.useState(null);
  const authContext = React.useMemo(() => ({
    signIn: () => {
      setUserToken('JIWOONG');
      setisLoading(false);
    },
    signOut: () => {
      setUserToken(null);
      setisLoading(false);
    },
    signUp: (userID) => {
      setUserToken(userID);
      setisLoading(false);
    },
  }));

  React.useEffect(() => {
    setTimeout(() => {
      setisLoading(false);
    }, 2000);
  }, []);

  if (isLoading) {
    return (
      <SafeAreaView style={styles.logo}>
        <Splash />
      </SafeAreaView>
    );
  }

  return (
    <AuthContext.Provider value={authContext}>
      <NavigationContainer ref={navigationRef}>
        {userToken != null ? <AppStack /> : <AuthStack />}
        {userToken != null ? actionbutton() : null}
      </NavigationContainer>
    </AuthContext.Provider>
  );
};

export default App;

const styles = StyleSheet.create({
  logo: {
    flex: 1,
    alignItems: 'center',
    marginTop: '70%',
  },
});
