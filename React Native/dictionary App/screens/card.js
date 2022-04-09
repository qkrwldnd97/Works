import React, { useState } from 'react';
import {
  FlatList,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Dimensions,
  StatusBar,
  ScrollView,
  SafeAreaView,
  Button,
  Modal,
  TextInput,
  Alert,
} from 'react-native';
import Entypo from 'react-native-vector-icons/Entypo';
import { search } from './search';
import { AsyncStorage } from 'react-native';

var recent_search = [];

const Search_voca = () => {
  return (
    <SafeAreaView
      style={{
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'rgb(251,251,251)',
        alignContent: 'space-evenly',
      }}>
      <View
        style={{
          flexDirection: 'row',
          justifyContent: 'space-between',
          marginLeft: 15,
          marginRight: 15,
          marginTop: StatusBar.currentHeight || 35,
        }}>
        <Text
          style={{
            fontSize: 30,
            fontFamily: 'Roboto',
            fontWeight: 'bold',
            color: 'grey',
          }}>
          Deckname
        </Text>
      </View>
      <View style={styles.search}>
        <Text
          style={{ fontFamily: 'Roboto', fontSize: 24, fontWeight: 'bold' }}>
          the process of doing something, especially when dealing with a problem
          or difficulty:
        </Text>
      </View>
      <TouchableOpacity style={styles.option}>
        <Text
          style={{
            marginLeft: 10,
            fontFamily: 'Roboto',
            fontSize: 24,
            fontWeight: 'bold',
          }}>
          1. Sex
        </Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.option} onPress={() => Alert.alert('Correct!')}>
        <Text
          style={{
            marginLeft: 10,
            fontFamily: 'Roboto',
            fontSize: 24,
            fontWeight: 'bold',
          }}>
          2. Sex
        </Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.option}>
        <Text
          style={{
            marginLeft: 10,
            fontFamily: 'Roboto',
            fontSize: 24,
            fontWeight: 'bold',
          }}>
          3. Sex
        </Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.optionfinal}>
        <Text
          style={{
            marginLeft: 10,
            fontFamily: 'Roboto',
            fontSize: 24,
            fontWeight: 'bold',
          }}>
          4. Sex
        </Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
};

export default Search_voca;

const size = Dimensions.get('screen');
const top = size.height * (40 / 100);
const styles = StyleSheet.create({
  search: {
    flex: 1,
    marginTop: 5,
    marginLeft: 15,
    marginRight: 15,
    marginBottom: 15,
    borderTopRightRadius: 5,
    borderBottomRightRadius: 5,
    borderBottomLeftRadius: 5,
    borderTopLeftRadius: 5,
    backgroundColor: 'white',

    shadowColor: 'rgba(0,0,0, .4)', // IOS
    shadowOffset: { height: 3, width: 3 }, // IOS
    shadowOpacity: 5, // IOS
    shadowRadius: 5, //IOS
    elevation: 2, // Android
  },
  option: {
    justifyContent: 'center',
    flex: 0.2,
    marginTop: 20,
    marginLeft: 15,
    marginRight: 15,
    marginBottom: 10,
    borderTopRightRadius: 5,
    borderBottomRightRadius: 5,
    borderBottomLeftRadius: 5,
    borderTopLeftRadius: 5,
    backgroundColor: 'white',

    shadowColor: 'rgba(0,0,0, .4)', // IOS
    shadowOffset: { height: 3, width: 3 }, // IOS
    shadowOpacity: 5, // IOS
    shadowRadius: 5, //IOS
    elevation: 2, // Android
  },
  optionfinal: {
    justifyContent: 'center',
    flex: 0.2,
    marginTop: 30,
    marginLeft: 15,
    marginRight: 15,
    marginBottom: 65,
    borderTopRightRadius: 5,
    borderBottomRightRadius: 5,
    borderBottomLeftRadius: 5,
    borderTopLeftRadius: 5,
    backgroundColor: 'white',

    shadowColor: 'rgba(0,0,0, .4)', // IOS
    shadowOffset: { height: 3, width: 3 }, // IOS
    shadowOpacity: 5, // IOS
    shadowRadius: 5, //IOS
    elevation: 2, // Android
  },
});
