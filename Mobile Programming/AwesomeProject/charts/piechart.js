import React, { Component } from 'react';
import {StyleSheet, ScrollView , StatusBar, Text, View } from 'react-native';
import PieChart from 'react-native-pie-chart';

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center'
    },
    title: {
        fontSize: 24,
        margin: 10
    }
});

export default class MyPieChart extends Component {
    render() {
        const chart_wh = 250;
        const series = [123, 321, 123, 789, 537];
        const sliceColor = ['#F44336','#2196F3','#FFEB3B', '#4CAF50', '#FF9800'];

        return (
            <ScrollView style={{flex: 1}}>
                <View style={styles.container}>
                    <StatusBar
                        hidden={true}
                    />
                    <PieChart
                        chart_wh={chart_wh}
                        series={series}
                        sliceColor={sliceColor}
                    />
                </View>
            </ScrollView>
        );
    }
}