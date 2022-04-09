import React from 'react';
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
  Image,
  TouchableWithoutFeedback,
  Alert,
  Animated,
  Button,
  TextInput,
} from 'react-native';
// import SQLite from 'react-native-sqlite-storage';
// import * as SQLite from 'expo-sqlite';
import realm from 'realm/realm'

import Logo from '../images/logo.js';
import AuthStack from '../AuthStack';
import { AuthContext } from '../context/context';

// const db = SQLite.openDatabase('db')

// const createTable = () => {
//   db.transaction((tx) => {
//     tx.executeSql(
//       "CREATE TABLE IF NOT EXISTS "
//       + "Users "
//       + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT);"
//     )
//   })
// }

// const newUser = (email, password) => {
//       db.transaction(tx => {
//       tx.executeSql(
//       `insert into Users (email, password) values (?, ?);`,
//       [email, password]
//     )});
//     console.log('Succeed')
//   }

// const login = (email, password) => {
//     db.transaction(tx => {
//       tx.executeSql(
//         'select email, password from Users;'
//       )
//     })
// }

const Cover = () => {
  var value = new Animated.Value(0);
  var position = new Animated.ValueXY({ x: 0, y: 0 });
  const [email, setEmail] = React.useState(null);
  const [password, setPassword] = React.useState(null);
  const { signIn, signUp } = React.useContext(AuthContext);

  function fadein() {
    Animated.timing(value, {
      toValue: 1,
      duration: 1500,
      //easing : Easing.bounce,
      delay: 3000,
    }).start();
  }

  function _moveY() {
    Animated.timing(position, {
      toValue: { x: 0, y: -100 },
      duration: 1500,
      delay: 3000,
    }).start();
  }

  function componentDidMount() {
    // createTable();
    _moveY();
    fadein();
  }

  componentDidMount();

  

  return (
    <SafeAreaView style={styles.logo}>
      <Animated.View
        style={[{marginTop: '50%'},{
          transform: [{ translateY: position.y }],
        }]}>
        <Logo />
      </Animated.View>
      <Animated.View
        style={{
          opacity: value,
          alignItems: 'center',
          position: 'absolute',
          bottom: '40%',
        }}>
        <TextInput
          style={styles.input}
          placeholder="ID or Email"
          onChangeText={(text) => setEmail(text)}
        />
        <TextInput
          style={styles.input}
          placeholder="Password"
          onChangeText={(text) => setPassword(text)}
          secureTextEntry={true}
        />
        <View style={{alignSelf: "flex-end", justifyContent: 'row'}}>
          <Text style={{fontSize: 12, fontFamily: "Roboto", color: 'gainsboro'}}>Forgot 
            <Text style={{fontSize: 12, fontFamily: 'Roboto', color: 'blue'}} onPress={() => alert('Hello')}> Password</Text>
           ? / <Text style={{fontSize: 12, fontFamily: 'Roboto', color: 'blue'}} onPress={()=> alert("Hello")}> Register</Text>
          </Text> 
        </View>
        <View style={{ alignItems: 'center', marginTop: 20 }}>
          <TouchableOpacity onPress={() => signIn()}>
            <Text style={{fontSize: 18, fontWeight: 'bold', color: 'blue', fontFamily: 'Roboto'}}>Confirm</Text>
          </TouchableOpacity>
       
        </View>
      </Animated.View>
    </SafeAreaView>
  );
};

export default Cover;

const size = Dimensions.get('screen');
const top = size.height * (40 / 100);
const styles = StyleSheet.create({
  logo: {
    flex:1,
    alignItems: 'center',
    backgroundColor: 'white'
  },
  input: {
    width: 300,
    height: 50,
    backgroundColor: 'gainsboro',
    paddingVertical: 10,
    paddingHorizontal: 15,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 5,
    fontSize: 20,
    marginBottom: 15,
    fontWeight: 'bold'
  },
});
