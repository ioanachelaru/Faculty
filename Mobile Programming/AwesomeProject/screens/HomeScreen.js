import DropdownMenu from 'react-native-dropdown-menu';
import { View, Alert, ActivityIndicator, ScrollView, Text, FlatList, NetInfo, Platform, AsyncStorage, Button } from "react-native";
import React from "react";
import "../auxiliars/Styles.js";
import MyPieChart from "../charts/piechart";

// var connected = false;
export default class Demo extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            dataForList: [],
            CurrentUserName: '',
            token:'',
            connected: false
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

    componentWillMount() {
        this.CheckConnectivity();
    }

    componentDidMount() {
        this.createLocalStorage();
        this.getSeason("1");
        AsyncStorage.getItem('UserName').then((value) => this.setState({ CurrentUserName: value }));
        AsyncStorage.getItem('token').then((value) => this.setState({ token:value, }))
    }

    getEpisodesForLocalStorage(season){
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season='+season)
            .then((response) => response.json())
            .then(async (responseJson) => {
                await AsyncStorage.setItem(season, JSON.stringify(responseJson['Episodes']));
            })
            .catch((error) => {
                console.error(error);
            });
    }
    createLocalStorage() {
        this.getEpisodesForLocalStorage("1");
        this.getEpisodesForLocalStorage("2");
        this.getEpisodesForLocalStorage("3");
        this.getEpisodesForLocalStorage("4");
        this.getEpisodesForLocalStorage("5");
        this.getEpisodesForLocalStorage("6");
        this.getEpisodesForLocalStorage("7");
        this.getEpisodesForLocalStorage("8");
    }

    async getEpisodesFromLocal(season) {
        await AsyncStorage.getItem(season).then((value) => {
            this.setState({dataForList: value,})
        })
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
                    this.setState({
                        connected: true,
                    });
                    // connected = true;
                } else {
                    this.setState({
                        connected: false,
                    });
                    // connected = false;
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

        if (isConnected) {
            this.setState({
                connected: true,
            });
            // connected = true;
        } else {
            this.setState({
                connected: false,
            });
            // connected = false;
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
                            handler={(selection, row) => {
                                if(this.state.connected){
                                    this.getSeason(data[selection][row].split(" ")[1])
                                }else {
                                    this.getEpisodesFromLocal(data[selection][row].split(" ")[1])
                                }
                            }}
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
                                title={'Go To Map'}
                                color = {'#3C1053'}
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