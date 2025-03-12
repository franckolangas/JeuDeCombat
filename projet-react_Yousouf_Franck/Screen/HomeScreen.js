import React, { useContext } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { TokenContext, UsernameContext } from './../Context/Context';

export default function HomeScreen() {
  const [username] = useContext(UsernameContext);
  const [token] = useContext(TokenContext); // Si tu souhaites également utiliser le token

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>Bienvenue !</Text>
      <Text style={styles.username}>Vous êtes connecté en tant que : {username}</Text>
      {/* Affiche le token si nécessaire */}
      {/* <Text>Token : {token}</Text> */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  welcome: {
    fontSize: 24,
    marginBottom: 20,
  },
  username: {
    fontSize: 18,
  },
});
