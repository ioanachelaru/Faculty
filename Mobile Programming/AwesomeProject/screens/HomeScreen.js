import React from 'react';
import {ActivityIndicator, ScrollView, FlatList, Text, View} from "react-native";
import "../auxiliars/Styles.js";
import MyPieChart from "../charts/piechart";

export default class Home extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            dataForList: [],
        }
    };

    getSeason(season) {
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season='+season)
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState(
                    {
                        isLoading: false,
                        dataForList: this.state.dataForList.concat(responseJson['Episodes']),
                    }
                );
            })
            .catch((error) => {
                console.error(error);
            });
    }

    componentDidMount() {
        this.getSeason(1);
        this.getSeason(2);
        this.getSeason(3);
        this.getSeason(4);
        this.getSeason(5);
        this.getSeason(6);
        this.getSeason(7);
        this.getSeason(8);
    }

    render() {
        if (this.state.isLoading) {
            return (
                <View style={styles.homeView}>
                    <ActivityIndicator size="large"/>
                </View>
            )
        }
        return (
            <ScrollView style={{flex: 1}}>
                <View style={styles.homeView}>
                    <FlatList style={styles.list}
                              data={this.state.dataForList}
                              renderItem={({item}) => <Text style={styles.text}>{item.Title}</Text>}
                              keyExtractor={item => item.id}
                    />
                    <View style={styles.container}>
                        <MyPieChart />
                    </View>

                </View>
            </ScrollView>
        )

    }
}