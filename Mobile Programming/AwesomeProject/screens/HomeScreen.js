import DropdownMenu from 'react-native-dropdown-menu';
import {View, ScrollView, Text, FlatList, ActivityIndicator} from "react-native";
import React from "react";
import "../auxiliars/Styles.js";
import MyPieChart from "../charts/piechart";

export default class Home extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            text: '',
            dataForList: []
        };
    };

    getSeason(season) {
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season='+season)
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState(
                    {
                        isLoading: false,
                        dataForList: responseJson['Episodes']
                    }
                );
            })
            .catch((error) => {
                console.error(error);
            });
    }

    componentDidMount() {
        this.getSeason("1")
    }

    render() {

        if (this.state.isLoading) {
            return (
                <View style={styles.homeView}>
                    <ActivityIndicator size="large"/>
                </View>
            )
        }

        var data = [["Season 1", "Season 2", "Season 3", "Season 4","Season 5","Season 6","Season 7","Season 8"]];
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
            </ScrollView>
        );
    }

}