import React, { useState } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    TextInput,
    View
} from 'react-native';

const PizzaTranslator = () => {
    const [text, setText] = useState('test');
    return (
        <View style={{padding: 10}}>
            <TextInput
                style={{height: 40}}
                placeholder="Type here to translate!"
                onChangeText={text => setText(text)}
                defaultValue={text}
            />
            <Text style={{padding: 10, fontSize: 42}}>
                {text.split(' ').map((word) => word && '🍕').join(' ')}
            </Text>
        </View>
    );
}

const HelloWorld = () => {
    return (
        <View style={styles.container}>
            <Text style={styles.hello}>Hello, World22233344566677</Text>
            <TextInput style={styles.input} defaultValue="Name me!"/>
            <PizzaTranslator/>
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