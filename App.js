import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Button,
  DeviceEventEmitter,
  Alert,
  BackHandler
} from 'react-native';
import { CheckBox } from 'react-native-elements'
import { Router, Scene, Actions, ActionConst, Drawer } from 'react-native-router-flux';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import LiScannerRouting from './liscannerrouting';
import LiScannerMenu from "./src/liscannermenu"
import ItemMenu from "./src/itemmenu"
import ItemReady from "./src/itemready"
import LiScannerBundleConnection from "./liscannerbundleconnection"
import ScanResult from "./src/scanresult"
import LiListView from "./src/lilistview"
import ItemReadyQuantity from './src/itemreadyquantity'
import ItemBroken from './src/itembroken'
import ScanExample from './src/nativeconnector/scanconnector';
// import allReducers from './src/liscannerreducer/index';

// const store = createStore(allReducers);
type Props = {};
export default class App extends Component<Props> {

    componentWillMount() {
        DeviceEventEmitter.addListener('showResult', function (e: Event) {
          Actions.ScanResult({barcode:e.rackId,result:e.result});
        });
        DeviceEventEmitter.addListener('onBackPressed', function (e: Event) { 
          const scene = Actions.currentScene;
            if (scene === 'LiScannerMenu') {
                BackHandler.exitApp();
                return true;
            }
          if(scene == 'ItemMenu')
            ScanExample.setTitle("LiScanner");
          if(scene == 'ItemReadyQuantity' || scene === 'ItemReady' || scene === 'ItemBroken')
            ScanExample.setTitle("Li.Scanner - Item");
          Actions.pop();
          return true;
        });
      }

    componentWillUnmount() {
      DeviceEventEmitter.removeEventListener('onBackPressed');
    }

      constructor(props){
        super(props)
      }
      render() {
        return (          
          // <LiScannerBundleConnection/>
          <LiScannerRouting/>

          // <View>
          //   <Provider store={store}>
          //     <LiScannerRouting/>
          //   </Provider>
          // </View>    
        );
      }
  }

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
