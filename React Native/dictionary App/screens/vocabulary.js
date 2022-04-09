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
} from 'react-native';
import { CheckBox } from 'react-native-elements';

let user_data = {
  voca_list: [],
  total: 0,
};

import Filter from '../images/filter_icon';
import Search from '../images/square_glass_icon';
import Plus from '../images/plus_icon';
import { Vocab } from './voca_data';

user_data.voca_list.push(new Vocab('Hello', 'abc', 'adverb',"", "", 2, '', false));
user_data.voca_list.push(new Vocab('Sex', 'abc', 'adjective',"", "", 3, '', false));
user_data.voca_list.push(new Vocab('Kid', 'abc', 'pronoun',"", "", 3, '', false));
user_data.voca_list.push(
  new Vocab('children', 'abc', 'noun', 2, 'Catcher in the Rye', false)
);
user_data.voca_list.push(new Vocab('Asap', 'abc', 'abc',"", "", 1, '', false));
user_data.voca_list.push(new Vocab('Fuck', 'abc', 'aa',"", "", 4, '', false));
const voca = () => {
  const [filter_h, setFilter_h] = useState(30);
  const [filter_w, setFilter_w] = useState(30);
  const [filter, setFilter] = useState(false);
  const [justifyContent, setJustifyContent] = useState('center');
  const [filter_word, setFilter_word] = useState('');

  const filter_part_of_speech = () => {
    var newArrayList = [];
    user_data.voca_list.map((item) =>
      newArrayList.length === 0
        ? newArrayList.push(item)
        : newArrayList.some((item2) =>
            item === item2 ? null : newArrayList.push(item)
          )
    );

    return newArrayList;
  };

  const render2 = (item) => {
    return (
      <View>
        {item.item.source === '' ? (
          <TouchableOpacity
            onPress={() => setFilter_word(item.item.partofspeech)}>
            <Text>{item.item.partofspeech}</Text>
          </TouchableOpacity>
        ) : (
          <View>
            <TouchableOpacity
              onPress={() => setFilter_word(item.item.partofspeech)}>
              <Text>{item.item.partofspeech}</Text>
            </TouchableOpacity>
            <TouchableOpacity
              onPress={() => setFilter_word(item.item.partofspeech)}>
              <Text>{item.item.source}</Text>
            </TouchableOpacity>
          </View>
        )}
      </View>
    );
  };
  const filter_word_list = (item) => {
    if (
      filter_word === '' ||
      filter_word === item.item.partofspeech ||
      filter_word === item.item.source
    ) {
      return render(item);
    } else {
      return null;
    }
  };
  const render = (item) => {
    return (
      <TouchableOpacity style={styles.vocalist}>
        <View
          style={{
            marginLeft: 20,
            marginRight: 20,
            borderBottomWidth: 1,
            borderColor: 'grey',
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}>
          <Text style={{ fontSize: 25, marginLeft: 20, marginBottom: 5 }}>
            {item.item.word}
          </Text>
          <View style={{ flexDirection: 'row' }}>
            <View
              style={{
                height: 10,
                width: 10,
                borderRadius: 30,
                borderWidth: 1,
                margin: 5,
                backgroundColor: item.item.test1,
              }}
            />
            <View
              style={{
                height: 10,
                width: 10,
                borderRadius: 30,
                borderWidth: 1,
                margin: 5,
                backgroundColor: item.item.test2,
              }}
            />
            <View
              style={{
                height: 10,
                width: 10,
                borderRadius: 30,
                borderWidth: 1,
                margin: 5,
                backgroundColor: item.item.test3,
              }}
            />
            <View
              style={{
                height: 10,
                width: 10,
                borderRadius: 30,
                borderWidth: 1,
                margin: 5,
                backgroundColor: item.item.test4,
              }}
            />
          </View>
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
        alignContent: 'space-evenly',
      }}>
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
          Vocabulary
        </Text>
        <View
          style={{ flexDirection: 'row', flex: 1, justifyContent: 'flex-end' }}>
          <View
            style={{
              backgroundColor: 'white',
              height: 30,
              width: 30,
              justifyContent: 'space-between',
              alignItems: 'center',
              marginRight: 15,
              flexDirection: 'column',
            }}>
            <TouchableOpacity style={{ margin: 5 }}>
              <Plus />
            </TouchableOpacity>
          </View>
          <View
            style={{
              backgroundColor: 'white',
              height: 30,
              width: 30,
              justifyContent: 'center',
              alignItems: 'center',
              marginRight: 15,
            }}>
            <TouchableOpacity style={{ margin: 5 }}>
              <Search />
            </TouchableOpacity>
          </View>
          <View
            style={{
              backgroundColor: 'white',
              height: filter_h,
              width: filter_w,
              justifyContent: 'space-between',
              alignItems: 'center',
              flexDirection: 'column',
              // padding: pad,
            }}>
            <TouchableOpacity
              style={{ margin: 5, alignSelf: justifyContent }}
              onPress={() =>
                setFilter(!filter) &
                (!filter
                  ? setFilter_h(400) &
                    setFilter_w(200) &
                    setJustifyContent('flex-end')
                  : setFilter_h(30) &
                    setFilter_w(30) &
                    setJustifyContent('center'))
              }>
              <Filter />
            </TouchableOpacity>
            {filter ? (
              <ScrollView
                stlye={{
                  width: '100%',
                  height: '100%',
                  flexGrow: 1,
                  alignItems: 'center',
                  marginTop: -10,
                }}>
                <TouchableOpacity onPress={() => setFilter_word('')}>
                  <Text>None</Text>
                </TouchableOpacity>
                <FlatList
                  vertical
                  data={filter_part_of_speech()}
                  renderItem={(item) => render2(item)}
                  keyExtractor={(item) => item.index}
                />
              </ScrollView>
            ) : null}
          </View>
        </View>
      </View>
      <View style={styles.voca}>
        <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
          <FlatList
            vertical
            data={user_data.voca_list}
            renderItem={(item) => filter_word_list(item)}
            keyExtractor={(item) => item.index}
          />
        </ScrollView>
      </View>
    </SafeAreaView>
  );
};

export default voca;

const size = Dimensions.get('screen');
const top = size.height * (40 / 100);
const styles = StyleSheet.create({
  vocalist: {
    height: 60,
    justifyContent: 'center',
  },
  voca: {
    flex: 1.5,
    marginTop: 70,
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
