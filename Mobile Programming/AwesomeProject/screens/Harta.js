import React, { Component } from 'react';
import { Text, View, StyleSheet } from 'react-native';
import MapView from 'react-native-maps';

class MapScreen extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mapRegion: null,
            lastLat: null,
            lastLong:null
        }
    }

    componentDidMount() {
        this.watchID = navigator.geolocation.watchPosition((position) => {
            let region = {
                latitude:       position.coords.latitude,
                longitude:      position.coords.longitude,
                latitudeDelta:  0.00922*1.5,
                longitudeDelta: 0.00421*1.5
            };
            this.onRegionChange(region, region.latitude, region.longitude);
        });
    }
    componentWillUnmount() {
        navigator.geolocation.clearWatch(this.watchID);
    }

    onMapPress(e) {
        let region = {
            latitude:       e.nativeEvent.coordinate.latitude,
            longitude:      e.nativeEvent.coordinate.longitude,
            latitudeDelta:  0.00922*1.5,
            longitudeDelta: 0.00421*1.5
        };
        this.onRegionChange(region, region.latitude, region.longitude);
    }

    onRegionChange(region, lastLat, lastLong) {
        this.setState({
            mapRegion: region,
            // If there are no new values set use the the current ones
            lastLat: lastLat || this.state.lastLat,
            lastLong: lastLong || this.state.lastLong
        });
    }

    renderScreen = () => {
        return (
            <View style={styles.container}>
                <MapView
                    style={styles.map}
                    region={this.state.mapRegion}
                    zoomEnabled={true}
                    zoomControlEnabled={true}
                    showsUserLocation={true}
                    followUserLocation={true}
                    onRegionChange={this.onRegionChange.bind(this)}
                    onPress={this.onMapPress.bind(this)}>
                    <MapView.Marker
                        coordinate={{
                            latitude: (this.state.lastLat + 0.00050) || -36.82339,
                            longitude: (this.state.lastLong + 0.00050) || -73.03569,
                        }}>
                        <View>
                            <Text style={{color: '#000'}}>
                                { this.state.lastLong } / { this.state.lastLat }
                            </Text>
                        </View>
                    </MapView.Marker>

                </MapView>
            </View>
        );
    };

    render() {
        return (
            this.renderScreen()
        );
    }
}

const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        justifyContent: 'flex-end',
        alignItems: 'center',
    },
    map: {
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
    },
});

export default MapScreen;