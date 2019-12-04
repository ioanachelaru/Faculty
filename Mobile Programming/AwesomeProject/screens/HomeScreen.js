import React from 'react';
import {ActivityIndicator,View ,Text, FlatList} from "react-native";
import "../auxiliars/Styles.js";
import MyPieChart from "../charts/piechart";

export default class Home extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            data1: [],
            filterData: [],
            ratingsForSeason1: []
        }
    };

    getRatingsBySeason(season) {
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season='+season)
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState(
                    {
                        filterData: responseJson['Episodes'],
                    }
                );
            })
            .catch((error) => {
                console.error(error);
            });
    }

    componentDidMount() {
        // fetch('http://www.omdbapi.com/?i=tt3896198&apikey=363ab14c')
        // fetch('http://www.omdbapi.com/?apikey=363ab14c&s=batman')
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season=1')
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState(
                    {
                        isLoading: false,
                        data1: responseJson['Episodes'],
                    }
                );
            })
            .catch((error) => {
                console.error(error);
            });

        this.getRatingsBySeason(2);
        this.setState(
            {
                ratingsForSeason1: this.state.filterData,
            }
        );
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
            <View style={styles.homeView}>
                <FlatList style={styles.list}
                          data={this.state.filterData}
                          renderItem={({item}) => <Text style={styles.text}>{item.Title}</Text>}
                          keyExtractor={item => item.id}
                />
                <View style={styles.container}>
                    <MyPieChart/>
                </View>

            </View>
        )

    }
}