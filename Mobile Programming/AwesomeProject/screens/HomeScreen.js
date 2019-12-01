import React from 'react';
import { Text, View, ActivityIndicator, FlatList } from 'react-native';
import styles from '../auxiliars/Styles'

export default class HomeScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            data: []
        }
    };

    componentDidMount() {
        fetch('http://www.omdbapi.com/?i=tt3896198&apikey=363ab14c')
            .then((response) => response.json())
            .then((responseJson) => {
                this.setState(
                    {
                        isLoading: false,
                        data: responseJson
                    }
                );
            })
            .catch((error) => {
                console.error(error);
            });
    }


    Item({ title }) {
        return (
            <View style={styles.item}>
                <Text>{title}</Text>
            </View>
        );
    }

    render() {
        if (this.state.isLoading) {
            return(
                <View style={styles.container}>
                    <ActivityIndicator size="large" />
                </View>
            )}

        return (
            <View style={styles.homeView}>
                <FlatList
                          data={this.state.data}
                          renderItem={({ item }) => <Item title={item['Title']} />}

                          //keyExtractor={item => item.id}
                />
            </View>
        );
    }
}