import React, { useContext, useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { TokenContext, UsernameContext } from './../Context/Context';
import { signIn } from '../compenents/SignIn'; // Mettez à jour ce chemin avec celui où se trouvent vos fonctions

export default function SignInScreen({ navigation }) {
  const [usernameInput, setUsernameInput] = useState('');
  const [passwordInput, setPasswordInput] = useState('');

  // Utilisation des contextes
  const [, setToken] = useContext(TokenContext);
  const [, setUsername] = useContext(UsernameContext);

  const handleSignIn = async () => {
    try {
      const token = await signIn(usernameInput, passwordInput); // Appel à la fonction signIn
      setToken(token); // Définit le token pour l'utilisateur connecté
      setUsername(usernameInput); // Enregistre le nom d’utilisateur dans le contexte
      Alert.alert('Connexion réussie', `Bienvenue, ${usernameInput}!`);
      navigation.navigate('Home'); // Navigue vers l'écran d'accueil
    } catch (error) {
      Alert.alert('Erreur', error.message); // Affiche l'erreur reçue
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Connexion</Text>
      <TextInput
        style={styles.input}
        placeholder="Nom d'utilisateur"
        value={usernameInput}
        onChangeText={setUsernameInput}
      />
      <TextInput
        style={styles.input}
        placeholder="Mot de passe"
        secureTextEntry
        value={passwordInput}
        onChangeText={setPasswordInput}
      />
      <Button title="Se connecter" onPress={handleSignIn} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 2,
    justifyContent: 'center',
    padding: 20,
    width: 300,
    margin: 'auto',
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 20,
    paddingHorizontal: 10,
  },
});
