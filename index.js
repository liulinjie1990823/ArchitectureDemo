import React from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    TextInput,
    View
} from 'react-native';

const HelloWorld = () => {
    return (
        <View style={styles.container}>
            <Text style={styles.hello}>Hello, World342424</Text>
            <TextInput style={styles.input} defaultValue="Name me!"/>
        </View>
    );
}

// class HelloWorld extends React.Component {
//   render() {
//     return (
//       <View style={styles.container}>
//         <Text style={styles.hello}>Hello, World111</Text>
//       </View>
//     );
//   }
// }
let styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center'
    },
    hello: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10
    },
    input: {
        marginEnd :20,
        marginStart:20,
        height: 40,
        borderColor: 'gray',
        borderWidth: 1
    }

});

AppRegistry.registerComponent(
    'MyReactNativeApp',
    () => HelloWorld
);