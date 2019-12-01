import React, {StyleSheet} from 'react-native'

export default StyleSheet.create({

    gradient: { flex: 1 },

    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },

    image: {
        resizeMode: 'contain',
        width: 170,
        height: 170
    },
    input: {
        width: 300,
        height: 44,
        padding: 10,
        borderWidth: 1,
        borderColor: 'transparent',
        backgroundColor: '#3C1053',
        borderRadius: 25,
        color: 'grey'
    },
    button: {
        width: 300,
        borderRadius: 25,
        overflow: 'hidden',
    },
    icon: {
        padding: 10,
        margin: 5,
        height: 25,
        width: 25,
        resizeMode : 'stretch',
        alignItems: 'center'
    },
    SectionStyle: {
        flexDirection: 'row',
        alignItems: 'center',
        width: 300,
        marginRight: 50,
        height: 44,
        padding: 10,
        borderWidth: 1,
        borderColor: 'transparent',
        backgroundColor: '#3C1053',
        borderRadius: 25,
        marginBottom: 10
    },

    homeView: {
        backgroundColor: '#9A4885',
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    }
});