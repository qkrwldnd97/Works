import React, { useEffect } from 'react';

import {
  Image,
  StyleSheet,
  View,
  TouchableOpacity,
  StatusBar,
  SafeAreaView,
  Text,
  Button,
  ScrollView,
  FlatList,
  RefreshControl,
  AppRegistry,
  Platform,
  NativeModules,
  ActivityIndicator,
} from 'react-native';
import ProgressCircle from 'react-native-progress-circle';
import DropShadow from 'react-native-drop-shadow';
import './book_list.js';
import Pdf from 'react-native-pdf';
import './book_list.js';


import {pushAllbook, getAllbook} from '../context/booklisting'

const wait = (timeout) => {
  return new Promise((resolve) => setTimeout(resolve, timeout));
};


const Home = ({ navigation }) => {
  const [refreshing, setRefreshing] = React.useState(true);

  // const onRefresh = React.useCallback(() => {
  //   setRefreshing(false);
  //   wait(2000).then(() => temp = global.user_data.book_list) & setRefreshing(true)
  // }, []);

  const render = (item) => {
    return (
      <TouchableOpacity style={styles.booklist}>
        <View
          style={{
            marginLeft: 20,
            marginRight: 20,
            borderBottomWidth: 1,
            borderColor: 'grey',
          }}>
          <Text style={{ fontSize: 25, marginLeft: 20, marginBottom: 5 }}>
            {item.item.name}
          </Text>
        </View>
      </TouchableOpacity>
    );
  };

  return (
    <SafeAreaView
      style={{
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'rgb(251,251,251)',
      }}>
      <Text style={styles.recent_font}>Recent</Text>
      <TouchableOpacity style={styles.recent}>
        <View>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
              alignItems: 'center',
            }}>
            <View style={{ margin: 15 }}>
              <Text>Catcher in the Rye</Text>
              <Text>Jordan B. Peterson</Text>
            </View>
            <View style={{ margin: 15 }}>
              <Text>2021/09/11</Text>
            </View>
          </View>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-around',
              alignItems: 'center',
            }}>
            <ProgressCircle
              percent={30}
              radius={70}
              borderWidth={8}
              color="#3399FF"
              shadowColor="#999"
              bgColor="#fff">
              <Text style={{ fontSize: 18 }}>{'30%'}</Text>
            </ProgressCircle>
            <View>
              <Text>
                Progress 72%{'\n'}Mastered 290{'\n'}Learned 29{'\n'}New added 84
              </Text>
            </View>
          </View>
        </View>
      </TouchableOpacity>
      <Text style={styles.deck_font}>Deck</Text>
      
          <View style={styles.deck}>
            <View style={{ flex: 1, backgroundColor: 'white' }}>
              <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
                <FlatList
                  vertical
                  data={global.user_data.book_list}
                  renderItem={(item) => render(item)}
                  keyExtractor={(item) => item.index}
                />
              </ScrollView>
            </View>
          </View>
    </SafeAreaView>
  );
};

export default Home;

const styles = StyleSheet.create({
  recent: {
    flex: 1,
    backgroundColor: 'white',
    marginLeft: 15,
    marginRight: 15,
    marginBottom: 5,
    marginTop: 1,
    borderTopRightRadius: 5,
    borderBottomRightRadius: 5,
    borderBottomLeftRadius: 5,
    borderTopLeftRadius: 5,
    borderColor: 'grey',

    shadowColor: 'rgba(0,0,0, .4)', // IOS
    shadowOffset: { height: 3, width: 3 }, // IOS
    shadowOpacity: 5, // IOS
    shadowRadius: 5, //IOS
    elevation: 2, // Android
  },
  recent_font: {
    margin: 15,
    marginTop: StatusBar.currentHeight || 35,
    marginBottom: 15,
    fontSize: 30,
    fontWeight: 'bold',
    color: 'grey',
    fontFamily: 'roboto',
  },

  deck: {
    flex: 1.5,
    marginLeft: 15,
    marginRight: 15,
    marginTop: 1,
    marginBottom: 55,
    borderTopRightRadius: 5,
    borderBottomRightRadius: 5,
    borderBottomLeftRadius: 5,
    borderTopLeftRadius: 5,
    borderColor: 'grey',

    shadowColor: 'rgba(0,0,0, .4)', // IOS
    shadowOffset: { height: 3, width: 3 }, // IOS
    shadowOpacity: 5, // IOS
    shadowRadius: 5, //IOS
    elevation: 2, // Android
  },
  deck_font: {
    margin: 15,
    marginBottom: 10,
    fontSize: 30,
    fontWeight: 'bold',
    color: 'grey',
    fontFamily: 'roboto',
  },
  booklist: {
    height: 90,
    justifyContent: 'center',
    borderColor: 'grey',
  },
});
