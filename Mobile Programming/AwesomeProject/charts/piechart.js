import React, { Component } from 'react';
import { Animated, Easing , StatusBar, } from 'react-native';
import PieChart from 'react-native-pie-chart';

export default class MyPieChart extends Component {
    constructor(props) {
        super(props);
        this.spinValue = new Animated.Value(0);
        this.state = {
            series:[],
            test:[1,1]
        }
    };


    getEpisodesOfSeason(season, episode) {
        fetch('http://www.omdbapi.com/?apikey=363ab14c&t=Game of Thrones&Season='+season)
            .then((response) => response.json())
            .then((responseJson) => {
                let l = [];
                l[0] = parseInt(responseJson['Episodes'][episode]["imdbRating"]);

                this.setState((state)=>({
                    series: state.series.concat(l)
                }));
            })
            .catch((error) => {
                console.error(error);
            });
    }

    componentDidMount() {
        this.getEpisodesOfSeason(1,1);
        this.getEpisodesOfSeason(1,2);
        this.getEpisodesOfSeason(1,3);
        this.getEpisodesOfSeason(1,4);
        this.getEpisodesOfSeason(1,5);
        this.getEpisodesOfSeason(1,6);
        this.getEpisodesOfSeason(1,7);
        this.getEpisodesOfSeason(1,8);

        this.spin();
    }

    spin () {
        this.spinValue.setValue(0);
        Animated.timing(
            this.spinValue,
            {
                toValue: 1,
                duration: 4000,
                easing: Easing.linear()
            }
        ).start(() => this.spin())
    }

    render() {

        const spin = this.spinValue.interpolate({
            inputRange: [0, 1],
            outputRange: ['0deg', '360deg']
        });

        const chart_wh = 250;
        const sliceColor = ['#F44336','#2196F3','#FFEB3B', '#4CAF50', '#FF9800',"#7746c2","#3294a8","#32a852"];

        return (
            <Animated.View style={{
                flex: 1,
                alignItems: 'center',
                transform: [{rotate: spin}]
            }}>
                <StatusBar
                    hidden={false}
                />
                <PieChart
                    chart_wh={chart_wh}
                    series={this.state.series}
                    sliceColor={sliceColor}
                />
            </Animated.View>
        );
    }
}