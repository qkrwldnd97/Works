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
  const [search_txt, setSearchtxt] = useState('');
  const [to_search, setToSearch] = useState('');
  const [result, setResult] = useState('');

  const search_text = async (text) => {
    search(text);
    await AsyncStorage.getItem(text.toLowerCase())
    .then((asyncStorageRes) => {
      if(asyncStorageRes !== null){
        setResult(asyncStorageRes);
        // console.log(asyncStorageRes)
      }
    });
    // await AsyncStorage.getAllKeys().then((ab) => {      
    //     console.log(ab)
    // })
  };

  const render = (item) => {
    return (
      <TouchableOpacity
        style={styles.booklist}
        onPress={() => {
          AsyncStorage.getItem(item.item.toLowerCase()).then(
            (asyncStorageRes) => {
              setResult(asyncStorageRes);
            }
          );
        }}>
        <View
          style={{
            marginLeft: 20,
            marginRight: 20,
            borderBottomWidth: 1,
            borderColor: 'grey',
          }}>
          <Text style={{ fontSize: 25, marginLeft: 20, marginBottom: 5 }}>
            {item.item}
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
        <TextInput
          placeholder="Searching..."
          style={{ fontSize: 30, fontWeight: 'bold', color: 'grey' }}
          onChangeText={(text) => setSearchtxt(text)}
          blurOnSubmit={true}
        />
        <TouchableOpacity
          onPress={() =>
            recent_search.push(search_txt) &
            setToSearch(search_txt) &
            setSearchtxt('') &
            search_text(search_txt)
          }>
          <Entypo name="magnifying-glass" size={40} color="grey" />
        </TouchableOpacity>
      </View>
      {result === '' ? (
        <View style={styles.search}>
          <Text
            style={{
              fontSize: 25,
              marginLeft: 40,
              fontWeight: 'bold',
              marginTop: 20,
            }}>
            Recently searched...
          </Text>
          <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
            <FlatList
              vertical
              data={recent_search}
              renderItem={(item) => render(item)}
            />
          </ScrollView>
        </View>
      ) : (
        <TouchableOpacity style={styles.search} onPress={() => setResult('')}>
          <Text>{result.definition}</Text>
        </TouchableOpacity>
      )}
    </SafeAreaView>
  );
};

export default Search_voca;

const size = Dimensions.get('screen');
const top = size.height * (40 / 100);
const styles = StyleSheet.create({
  search: {
    flex: 1.5,
    marginTop: 5,
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
  booklist: {
    height: 60,
    justifyContent: 'center',
    borderColor: 'grey',
  },
});
