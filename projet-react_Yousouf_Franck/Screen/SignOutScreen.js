import React, { useContext } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { TokenContext } from './../Context/Context';

export default function SignOutScreen({ navigation }) {
    const [token, setToken] = useContext(TokenContext); // Récupérer le token et la fonction setToken

    const handleSignOut = () => {
        setToken(null); // Réinitialisation le token
        navigation.navigate('SignIn'); // Redirection vers l'écran de connexion
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Déconnexion</Text>
            <Button title="Se déconnecter" onPress={handleSignOut} />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    title: {
        fontSize: 24,
        marginBottom: 20,
    },
});
