import React, { useState, useContext } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { TokenContext } from './../Context/Context'; // Assurez-vous que ce chemin est correct
import { signUp } from '../compenents/SignIn'; // Mettez à jour ce chemin avec celui où se trouvent vos fonctions

export default function SignUpScreen({ navigation }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [, setToken] = useContext(TokenContext); // Récupération de setToken du contexte

  const handleSignUp = async () => {
    if (!username || !password) {
      Alert.alert('Erreur', 'Veuillez remplir tous les champs.');
      return;
    }

    try {
      const token = await signUp(username, password);
      setToken(token); // Mettre à jour le token dans le contexte
      Alert.alert('Inscription réussie', `Bienvenue, ${username}!`);
      navigation.navigate('Home'); // Naviguer vers l'écran d'accueil
    } catch (error) {
      if (error.message.includes('already exists')) {
        Alert.alert('Erreur', 'Ce nom d\'utilisateur est déjà pris. Veuillez en choisir un autre.');
        navigation.navigate('SignIn'); // Naviguer vers l'écran de connexion si l'utilisateur existe déjà
      } else {
        Alert.alert('Erreur', error.message); // Afficher les autres erreurs
      }
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Inscription</Text>
      <TextInput
        style={styles.input}
        placeholder="Nom d'utilisateur"
        value={username}
        onChangeText={setUsername}
      />
      <TextInput
        style={styles.input}
        placeholder="Mot de passe"
        secureTextEntry
        value={password}
        onChangeText={setPassword}
      />
      <Button title="S'inscrire" onPress={handleSignUp} />
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
