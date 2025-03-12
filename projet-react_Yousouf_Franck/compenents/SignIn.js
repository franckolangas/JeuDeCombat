import fetch from "node-fetch";

const API_URL = 'http://graphql.unicaen.fr:4000';

const SIGN_IN = `
mutation SignIn($username: String!, $password: String!) {
  signIn(username: $username, password: $password)
}
`;

const SIGN_UP = `
mutation SignUp($username: String!, $password: String!) {
  signUp(username: $username, password: $password)
}`;

export async function signIn(username, password) {
  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        query: SIGN_IN,
        variables: { username, password }
      })
    });

    const jsonResponse = await response.json();

    if (jsonResponse.errors) {
      throw new Error(jsonResponse.errors[0].message);
    }

    return jsonResponse.data.signIn; // Retourne le token reçu
  } catch (error) {
    throw new Error(`Erreur lors de la connexion : ${error.message}`);
  }
}

export async function signUp(username, password) {
  try {
    console.log('Tentative d\'inscription avec:', username, password); // Pour le débogage
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        query: SIGN_UP,
        variables: { username, password }
      })
    });

    const jsonResponse = await response.json();
    console.log('Réponse de l\'API:', jsonResponse); // Pour le débogage

    if (jsonResponse.errors) {
      throw new Error(jsonResponse.errors[0].message);
    }

    return jsonResponse.data.signUp;
  } catch (error) {
    throw new Error(`Erreur lors de l'inscription : ${error.message}`);
  }
}

