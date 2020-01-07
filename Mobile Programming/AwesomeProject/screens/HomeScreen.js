import DropdownMenu from 'react-native-dropdown-menu';
import { View, ActivityIndicator, ScrollView, Text, FlatList, NetInfo, Platform, AsyncStorage, Button } from "react-native";
import React from "react";
import "../auxiliars/Styles.js";
import MyPieChart from "../charts/piechart";

export default class Demo extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            dataForList: [],
            CurrentUserName: '',
            token:''
        };

        this._bootstrapAsync();
    }

    getSeason(season) {
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season='+season)
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState(
                    {
                        isLoading:false,
                        dataForList: responseJson['Episodes'],
                    }
                );
            })
            .catch((error) => {
                console.error(error);
            });
    }

    componentDidMount() {
        if(this.CheckConnectivity()){
            return (
                <View style={styles.homeView}>
                    <Text>There is no internet!!!</Text>
                </View>
            )
        }else{
            this.getSeason("1");
            AsyncStorage.getItem('UserName').then((value) => this.setState({ CurrentUserName: value }));
            AsyncStorage.getItem('token').then((value) => this.setState({ token:value, }))
        }
    }

    _bootstrapAsync =  () => {

        const userToken =  AsyncStorage.getItem('token');
        this.setState({
            token:userToken,
        });
    };

    CheckConnectivity = () => {
        // For Android devices
        if (Platform.OS === "android") {
            NetInfo.isConnected.fetch().then(isConnected => {
                if (isConnected) {
                    // Alert.alert("You are online!");
                } else {
                    // Alert.alert("You are offline!");
                }
            });
        } else {
            // For iOS devices
            NetInfo.isConnected.addEventListener(
                "connectionChange",
                this.handleFirstConnectivityChange
            );
        }
    };

    handleFirstConnectivityChange = isConnected => {
        NetInfo.isConnected.removeEventListener(
            "connectionChange",
            this.handleFirstConnectivityChange
        );

        if (isConnected === false) {
            // Alert.alert("You are offline!");
        } else {
            // Alert.alert("You are online!");
        }
    };


    render() {
        if(this.state.token != null){
            var data = [["Season 1", "Season 2", "Season 3", "Season 4","Season 5","Season 6","Season 7","Season 8"]];
            // this.CheckConnectivity()

            if (this.state.isLoading) {
                return (
                    <View style={styles.homeView}>
                        <ActivityIndicator size="large"/>
                    </View>
                )
            }else

                return (
                    <ScrollView style={{flex: 1}}>
                        <DropdownMenu
                            style={{flex: 1}}
                            bgColor={'white'}
                            tintColor={'#666666'}
                            activityTintColor={'#3C1070'}
                            optionTextStyle={{color: '#333333'}}
                            titleStyle={{color: '#333333'}}
                            handler={(selection, row) => this.getSeason(data[selection][row].split(" ")[1])}
                            data={data}
                        >

                            <View style={{flex: 1}}>
                                <FlatList style={styles.list}
                                          data={this.state.dataForList}
                                          renderItem={({item}) => <Text style={styles.text}>{item.Title}</Text>}
                                          keyExtractor={item => item.id}
                                />
                            </View>
                            <View style={styles.container}>
                                <MyPieChart lista={this.state.dataForList} />
                            </View>
                        </DropdownMenu>
                        <View>
                            <Text>
                                Current User: {this.state.CurrentUserName}
                            </Text>

                            <Text>
                                Token: {this.state.token}
                            </Text>
                        </View>
                        <View style={styles.button}>
                            <Button
                                color = {'#3C1053'}
                                title={'Go To Map'}
                                onPress={()=>{
                                    this.props.navigation.navigate("Harta");
                                }}
                            />
                        </View>
                    </ScrollView>
                );
        }else {
            return(
                <View>
                    <Text>
                        Token wasn't found!
                    </Text>
                </View>
            );
        }
    }

}