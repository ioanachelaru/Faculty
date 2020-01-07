import React from 'react';
import { View, AsyncStorage,Image, TextInput, Button } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient'
import styles from '../auxiliars/Styles'

export default class LoginScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
        };
    }

    saveUserName = async UserName => {
        try {
            await AsyncStorage.setItem('UserName', UserName);
        } catch (error) {
            // Error retrieving data
            console.log(error.message);
        }
    };

    _signInAsync = async token => {
        fetch('https://demo7402239.mockable.io/login')
            .then((response) => response.json())
            .then(async (responseJson) => {
                await AsyncStorage.setItem('token', responseJson['token']);
            })
            .catch((error) => {
                console.error(error);
            });

    };

    render() {
        return (
            <LinearGradient
                colors={['#ad5389', '#3c1053']}
                style={styles.gradient}
            >
                <View
                    style={styles.container}>
                    <Image
                        source={require('../assets/logo.png')}
                        style={styles.image}
                    />
                    <View style={styles.SectionStyle}>
                        <Image source={require('../assets/ic_user.png')} style={styles.icon} />
                        <TextInput
                            value={this.state.username}
                            onChangeText={(username) => this.setState({ username })}
                            placeholder={'Username'}
                            placeholderTextColor={'#fff'}
                            style={styles.input}
                        />
                    </View>
                    <View style={styles.SectionStyle}>
                        <Image source={require('../assets/ic_password.png')} style={styles.icon} />
                        <TextInput
                            value={this.state.password}
                            onChangeText={(password) => this.setState({ password })}
                            placeholder={'Password'}
                            placeholderTextColor={'#fff'}
                            secureTextEntry={true}
                            style={styles.input}
                        />
                    </View >
                    <View style={styles.button}>
                        <Button
                            color = {'#3C1053'}
                            title={'LOGIN'}
                            onPress={()=>{
                                this._signInAsync();
                                this.props.navigation.navigate("Home");
                                this.saveUserName(this.state.username)
                            }}
                        />
                    </View>
                </View>
            </LinearGradient>
        );
    }
}