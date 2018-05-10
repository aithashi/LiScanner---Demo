import React, { Component } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Image
} from 'react-native';
import { Actions, ActionConst } from 'react-native-router-flux';

export default class LiListView extends Component {
 constructor(props){
   super(props)
 }
  alertItemName = (item) => {
     Actions.Item_ready({barcode:item});
  }
  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity style = {styles.header}>
          <Text style = {styles.headerText}>
             Orders
          </Text>
        </TouchableOpacity>
          {  this.props.result.item.map((item, index) => (
                  <TouchableOpacity
                    key = {index}
                    style = {styles.items}
                    onPress = {() => this.alertItemName(item)}>

                    <Text style = {styles.text}>
                        {item.orderNo}
                    </Text>
                    <Image style={styles.ImageIconStyle}
                    source={require('./image/right-arrow-angle.jpg')}
                    />
                  </TouchableOpacity>
                ))
            }
      </View>
    );
  }
}

const styles = StyleSheet.create ({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
   items: {
    flexDirection:'row',
    padding:10,
    borderTopWidth:0.5,
    borderBottomWidth:0.5,
   },
   header: {
    backgroundColor: '#dfcee7',
    padding:10,

   },
   ImageIconStyle:{
    alignItems: 'flex-end',
    justifyContent: 'center',
    width:15,
    height:25,
    alignItems: 'stretch',
    resizeMode: 'stretch',

   },
   text: {
    color: '#4f603c',
    fontSize: 20,
    alignItems: 'flex-start',
    width:375,
         // backgroundColor: 'wheat',
   },
   headerText: {
    color:'#660033',
    fontSize: 30,
    textAlign: 'center',
    fontWeight: 'bold',
    fontFamily: 'Cochin',
    paddingBottom:10
   }
})
