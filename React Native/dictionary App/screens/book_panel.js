import React, { useState, useContext } from 'react';
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
} from 'react-native';
import AppContext from '../context/context'
import './book_list.js';

const book_list = ({ navigation }) => {
  return (
    <SafeAreaView
      style={{
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'rgb(251,251,251)',
      }}>
      <View
        style={{
          flexDirection: 'row',
          justifyContent: 'space-between',
          marginLeft: 15,
          marginRight: 15,
          marginTop: StatusBar.currentHeight || 35,
          marginBottom: 15,
        }}>
        <Text
          style={{
            fontSize: 30,
            fontWeight: 'bold',
            color: 'grey',
          }}>
          {global.user_book.recent[global.user_book.total]}
        </Text>
      </View>
      <View style={styles.library}>
      <TouchableOpacity>
        <Text>Context</Text>
      </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

export default book_list;

const size = Dimensions.get('screen');
const top = size.height * (40 / 100);
const styles = StyleSheet.create({
  library: {
    flex: 1,
    marginLeft: 15,
    marginRight: 15,
    marginBottom: 55,
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
