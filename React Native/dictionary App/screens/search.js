import React, { Component, useState } from "react";
import { SearchBar } from "react-native-elements";
// import { useState } from "react";
import { StyleSheet, View, FlatList, Text, Alert } from "react-native";
import { AsyncStorage } from 'react-native'
import {Vocab} from './voca_data'

export const search = (text) => {
  // const [data, setData] = useState(null);

  const DictAPI = (keyword) => {
    return fetch("https://api.dictionaryapi.dev/api/v2/entries/en/" + keyword)
      .then((response) => response.json())
      .then((json) => {
        // console.log(JSON.parse(JSON.stringify(json)));
        // return keywords;
        renderItem(JSON.parse(JSON.stringify(json)));
        // storeData(JSON.parse(JSON.stringify(json)))
        
        // renderItem()
      })
      .catch((error) => console.log(error));
  };
  const renderItem = async (item) => {
    item = item[0]
    const word = "Word: " + item.word + "\n";
    const phonetic = "Phonetic: " + item.phonetic + "\n";
    const origin = "Origin: " + item.origin + "\n";
    var partofsp = ""; 
    // var partofspeech = ""
    var meanings = "";

    Object.keys(item.meanings).map((key) => {
      partofsp +=
        item.meanings[key].partOfSpeech + "$";
      Object.keys(item.meanings[key].definitions).map((mean) => {
        meanings +=
          item.meanings[key].definitions[mean].definition +
          "$";
      });
    });
    
    const final = word + phonetic + origin+ meanings;
    await AsyncStorage.setItem(item.word, final);
    console.log(final)
    
    // AsyncStorage.getItem(item.word).then(asyncStorageRes => {
    //     console.log(asyncStorageRes)
    // });
   
    return final;
  };
  return (
    // DictAPI(text) & 
    DictAPI(text)
    // <View
    //   style={{
    //     flex: 1,
    //     flexDirection: "column",
    //     justifyContent: "flex-start"
    //   }}
    // >
    //   <View>
    //     <SearchBar
    //       lightTheme
    //       leftIcon
    //       placeholder="Type Here..."
    //       onChangeText={(val) => {
    //         setKeywords(val);
    //       }}
    //       onSubmitEditing={() => DictAPI(keywords) & setisModalvisible(true)}
    //       value={keywords}
    //       round="default"
    //       containerStyle={{ width: "100%", marginTop: "10%" }}
    //       inputContainerStyle={{ height: 10, width: "100%" }}
    //     />
      // </View>
      //  <FlatList
      //       data={response}
      //       renderItem={(item) => (
      //         // <Text style={{ fontFamily: "Roboto", fontSize: 20 }}>
      //           console.log(renderItem(item))
      //         // </Text>
      //       )}
      //       // keyExtractor={}
      //     /> 
    // </View>
  );
};
