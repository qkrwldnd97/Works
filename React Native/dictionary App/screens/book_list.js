import React, { useState, useEffect, createContext } from 'react';
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
  Animated,
  Platform,
} from 'react-native';
import * as DocumentPicker from 'expo-document-picker';

import Filter from '../images/filter_icon';
import Search from '../images/square_glass_icon';
import Plus from '../images/plus_icon';
import { Book } from './book_data';
import AppContext from '../context/context';
import { search } from './search';
import { AsyncStorage } from 'react-native';

import { Variable } from '../context/booklisting';

import { pushAllbook, getAllbook } from '../context/booklisting';

global.user_data = {
  book_list: [],
  total: 0,
};

const book_list = ({ navigation }) => {
  const [value, onChangeText] = useState('');
  const [book_title, setBooktitle] = useState('No name');
  // const [context, setContext] = useState('');

  const [modalVisible, setmodalVisible] = useState(false);
  const [actionTrigger, setactionTrigger] = useState(null);
  const [userchoice, setUserchoice] = useState(false);
  const [title, setTitle] = useState('Library');
  const [current, setCurrent] = useState(null);

  const [plus, setPlus] = useState(false);

  const UploadFile = async () => {
    let result = await DocumentPicker.getDocumentAsync({});
    console.log(result.uri);
    console.log(result);
  };

  function split_text(text, separator) {
    return text.split(separator);
  }

  function search_text(text) {
    search(text);
    AsyncStorage.getItem(text.toLowerCase()).then((asyncres) => {
      // console.log(asyncres)
      Alert.alert(asyncres);
    });
    AsyncStorage.getAllKeys().then((abc) => console.log(abc));
  }

  const render = (item) => {
    return (
      <TouchableOpacity
        style={styles.booklist}
        onPress={() =>
          setCurrent(
            new Book(item.item.name, item.item.author, item.item.context)
          ) &
          setTitle(item.item.name) &
          setUserchoice(true)
        }>
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
        // backgroundColor: 'dodgerblue',
      }}>
      <Modal
        animationType={'fade'}
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          console.log('Modal has been closed.');
        }}>
        {/*All views of Modal*/}
        <View style={styles.modal}>
          <Button
            title="Click To Close Modal"
            onPress={() => setmodalVisible(!modalVisible)}
          />
          <TextInput
            style={{
              flex: 1,
              borderColor: 'gray',
              borderWidth: 1,
              width: '90%',
              marginBottom: 5,
            }}
            onChangeText={(text) => onChangeText(text)}
            value={value}
            multiline="true"
            placeholder="Paste text here..."
            selectTextOnFocus={true}
            blurOnSubmit={true}
          />
          <Button
            title="Enter"
            onPress={() => setmodalVisible(!modalVisible)}
          />
        </View>
      </Modal>
      <View
        style={{
          flexDirection: 'row',
          justifyContent: 'space-between',
          marginLeft: 15,
          marginRight: 15,
          marginTop: StatusBar.currentHeight || 35,
          zIndex: 1,
          position: 'absolute',
          top: '5%',
        }}>
        <Text
          style={{
            fontSize: 30,
            fontWeight: 'bold',
            color: 'grey',
          }}>
          {title}
        </Text>
        <View
          style={{ flexDirection: 'row', flex: 1, justifyContent: 'flex-end' }}>
          {!userchoice ? (
            <View
              style={
                plus
                  ? {
                      backgroundColor: 'yellow',
                      height: 120,
                      width: 250,
                      justifyContent: 'flex-end',
                      alignItems: 'flex-end',
                      marginRight: 15,
                      flexDirection: 'column',
                    }
                  : {
                      backgroundColor: 'red',
                      height: 30,
                      width: 30,
                      justifyContent: 'center',
                      alignItems: 'center',
                      marginRight: 15,
                      flexDirection: 'column',
                    }
              }>
              <TouchableOpacity
                style={{ margin: 5 }}
                onPress={() => setPlus(!plus)}>
                <Plus />
              </TouchableOpacity>
              {plus ? (
                <ScrollView
                  style={{
                    width: '100%',
                    height: '100%',
                    flexGrow: 1,
                    alignItems: 'center',
                    marginTop: -10,
                  }}>
                  <View style={styles.plus_format}>
                    <TextInput
                      placeholder="Type folder name"
                      clearButtonMode="always"
                      style={{
                        marginBottom: 5,
                        fontSize: 22,
                        color: '#AAAAAA',
                        fontFamily: 'roboto',
                        fontWeight: 'bold',
                      }}
                      onChangeText={(text) => setBooktitle(text)}
                    />
                  </View>
                  <View style={styles.plus_format}>
                    <TouchableOpacity
                      onPress={() => setmodalVisible(!modalVisible)}>
                      {value === '' ? (
                        <Text
                          style={{
                            fontSize: 15,
                            fontFamily: 'roboto',
                            color: '#CCCCCC',
                            marginBottom: 5,
                          }}>
                          + Add text
                        </Text>
                      ) : (
                        <Text
                          style={{
                            fontSize: 15,
                            fontFamily: 'roboto',
                            color: 'Blue',
                            marginBottom: 5,
                          }}>
                          👍 Added text
                        </Text>
                      )}
                    </TouchableOpacity>
                  </View>
                  <View style={styles.plus_format}>
                    <TouchableOpacity onPress={UploadFile}>
                      <Text
                        style={{
                          fontSize: 15,
                          fontFamily: 'roboto',
                          color: '#CCCCCC',
                          marginBottom: 5,
                        }}>
                        + Import file
                      </Text>
                    </TouchableOpacity>
                  </View>
                  <Button
                    title="Submit"
                    onPress={() =>
                      global.user_data.book_list.push(
                        new Book(book_title, 'ERic', value)
                      ) &
                      onChangeText('') &
                      setBooktitle('') &
                      setPlus(false)
                    }
                  />
                </ScrollView>
              ) : null}
            </View>
          ) : (
            <View
              style={{
                backgroundColor: 'white',
                height: 30,
                width: 30,
                justifyContent: 'center',
                alignItems: 'center',
                marginRight: 15,
              }}>
              <TouchableOpacity
                onPress={() => setUserchoice(false) & setTitle('Library')}>
                <Text>Back</Text>
              </TouchableOpacity>
            </View>
          )}
          <View
            style={{
              backgroundColor: 'white',
              height: 30,
              width: 30,
              justifyContent: 'center',
              alignItems: 'center',
              marginRight: 15,
            }}>
            <TouchableOpacity>
              <Search />
            </TouchableOpacity>
          </View>
          <View
            style={{
              backgroundColor: 'white',
              height: 30,
              width: 30,
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <TouchableOpacity>
              <Filter />
            </TouchableOpacity>
          </View>
        </View>
      </View>
      <View style={styles.library}>
        {userchoice === false ? (
          <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
            <FlatList
              vertical
              data={global.user_data.book_list}
              renderItem={(item) => render(item)}
              keyExtractor={(item) => item.index}
            />
          </ScrollView>
        ) : (
          <View style={{ flexDirection: 'row' }}>
            {split_text(current.context, ' ').map((word) => (
              <TouchableOpacity
                onPress={() => search_text(word.replace(/\W|_/g, ''))}>
                <Text>{word + ' '}</Text>
              </TouchableOpacity>
            ))}
          </View>
        )}
      </View>
    </SafeAreaView>
  );
};

export default book_list;

const size = Dimensions.get('screen');
const top = size.height * (40 / 100);
const styles = StyleSheet.create({
  booklist: {
    height: 60,
    justifyContent: 'center',
    borderColor: 'grey',
  },
  library: {
    flex: 1.5,
    marginLeft: 15,
    marginRight: 15,
    marginTop: 70,
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

  plus_format: {
    width: 200,
    marginBottom: 10,
    borderBottomWidth: 1,
    borderColor: 'grey',
  },
  modal: {
    // justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#00BCD4',
    height: '80%',
    width: '80%',
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#fff',
    marginTop: 80,
    marginLeft: 40,
  },
});
