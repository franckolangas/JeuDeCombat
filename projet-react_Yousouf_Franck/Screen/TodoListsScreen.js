import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import TodoList from './../compenents/TodoLists';
import todoData from '../Helpers/todoData'; // Importez les données de tâches

export default function TodoListsScreen() {
  const navigation = useNavigation();

  const handleNavigateToDetails = (taskId) => {
    navigation.navigate('Details', { taskId, todoData }); // Utilisez "Details" ici
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Liste des Tâches</Text>
      <TodoList handleNavigateToDetails={handleNavigateToDetails} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
});
