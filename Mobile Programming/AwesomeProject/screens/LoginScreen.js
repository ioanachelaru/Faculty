import React from 'react';
import { View, Image, TextInput, Button, Alert } from 'react-native';
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
                            style={styles.input}
                        />
                    </View>
                    <View style={styles.SectionStyle}>
                        <Image source={require('../assets/ic_password.png')} style={styles.icon} />
                        <TextInput
                            value={this.state.password}
                            onChangeText={(password) => this.setState({ password })}
                            placeholder={'Password'}
                            secureTextEntry={true}
                            style={styles.input}
                        />
                    </View >
                    <View style={styles.button}>
                        <Button
                            title={'Login'}
                            color='#3C1053'
                            onPress={()=>{
                                // if(this.state.username.toString() === "Ioana")
                                //     if(this.state.password.toString() === "Parola")
                                //         this.props.navigation.navigate("Home");
                                //     else{
                                //         Alert.alert("Warning","Your password is incorrect");
                                //     }
                                // else {
                                //     Alert.alert("Warning","This is not a valid account");
                                // }
                                this.props.navigation.navigate("Home");
                            }}
                        />
                    </View>
                </View>
            </LinearGradient>
        );
    }
}