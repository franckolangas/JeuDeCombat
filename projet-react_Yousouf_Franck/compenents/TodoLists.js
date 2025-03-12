import React, { useState, useContext } from "react";
import {
  StyleSheet,
  View,
  TextInput,
  Button,
  Text,
  FlatList,
  TouchableOpacity,
  Dimensions,
} from "react-native";

import { useNavigation } from "@react-navigation/native";
const { width, height } = Dimensions.get("window"); // Récupérer la largeur de l'écran

import TodoItem from "./TodoItem";

// Import des contextes
import { TodosContext } from "../Context/Context";

export default function TodoList() {
  // Utiliser les contextes
  const {
    todos,
    updateTodo,
    updateTodoStatus,
    updateSubTask,
    deleteTodo,
    filteredTodos,
    count,
    selectedTodoId,
    addNewTodo,
    addNewSubTodo,
    setSelectedTodoId,
    setFilter,
    checkAll,
    unCheckAll,
  } = useContext(TodosContext);

  const [newTodoText, setNewTodoText] = useState("");
  const [newSubTodoText, setNewSubTodoText] = useState("");
  const [editTodoId, setEditTodoId] = useState(null); // Pour garder track de la tâche en cours de modification
  const [editTodoText, setEditTodoText] = useState("");

  const navigation = useNavigation();

  // Total des tâches (y compris sous-tâches si présentes)
  const total = todos.length;

  // Calcul du pourcentage de tâches terminées
  const progress = (count / total) * 100;

  return (
    <View style={styles.container}>
      <Text style={styles.counter}>{count} tâches réalisées</Text>
      <div>
        <div style={styles.progressContainer}>
          <div
            style={{
              ...styles.progressBar,
              width: `${progress}%`, // Dynamique, ajusté selon le pourcentage
              backgroundColor: progress === 0 ? "#333" : "#4caf50", // Pas de vert quand progress est 0
            }}
          ></div>
        </div>
        <p>{`Tâches réalisées : ${count} / ${total}`}</p>
      </div>

      <View style={styles.textInputGroup}>
        <TextInput
          style={styles.textInput}
          onChangeText={setNewTodoText}
          placeholder="Saisir une nouvelle tâche"
          onSubmitEditing={() => addNewTodo(newTodoText)}
          value={newTodoText}
        />
        <TouchableOpacity
          style={styles.button}
          onPress={() => addNewTodo(newTodoText)}
        >
          <Text style={styles.buttonText}>Ajouter</Text>
        </TouchableOpacity>
      </View>

      {selectedTodoId !== null && (
        <View style={styles.textInputGroup}>
          <TextInput
            style={styles.textInput}
            onChangeText={setNewSubTodoText}
            placeholder="Saisir un sous-item"
            value={newSubTodoText}
          />
          <TouchableOpacity
            style={styles.button}
            onPress={() => addNewSubTodo(selectedTodoId, newSubTodoText)}
          >
            <Text style={styles.buttonText}>Ajouter Sous-Tâche</Text>
          </TouchableOpacity>
        </View>
      )}

      <View style={styles.listContainer}>
        <FlatList
          style={styles.list}
          data={filteredTodos}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <View>
              <TodoItem
                item={item}
                updateTodo={updateTodo}
                updateSubTask={updateSubTask}
                deleteTodo={deleteTodo}
                updateTodoStatus={updateTodoStatus}
              />

              {editTodoId === item.id ? (
                <View style={styles.textInputGroup}>
                  <TextInput
                    style={styles.textInput}
                    value={editTodoText}
                    onChangeText={setEditTodoText}
                    placeholder="Modifier la tâche"
                  />
                  <View style={styles.buttonGroup}>
                    <Button
                      title="Enregistrer"
                      onPress={() => updateTodo(editTodoId, editTodoText)}
                    />
                    <Button
                      title="Annuler"
                      onPress={() => setEditTodoId(null)}
                    />
                  </View>
                </View>
              ) : (
                <View style={styles.todoItem}>
                  <Button
                    title="Modifier"
                    onPress={() => {
                      setEditTodoId(item.id);
                      setEditTodoText(item.content);
                    }}
                  />
                </View>
              )}
              <TouchableOpacity
                style={styles.addSubTodoButton}
                onPress={() => setSelectedTodoId(item.id)}
              >
                <Text style={styles.addSubTodoButtonText}>
                  Ajouter un sous-item
                </Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.detailButton}
                onPress={() => {
                  setSelectedTodoId(item.id);

                  navigation.navigate("Details", {
                    taskId: item.id,
                  });
                }}
              >
                <Text style={styles.detailButtonText}>Voir les détails</Text>
              </TouchableOpacity>
            </View>
          )}
        />
      </View>

      <View style={styles.buttonGroup}>
        <TouchableOpacity style={styles.button} onPress={checkAll}>
          <Text style={styles.buttonText}>Tout Cocher</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={unCheckAll}>
          <Text style={styles.buttonText}>Tout Décocher</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setFilter("checked")}
        >
          <Text style={styles.buttonText}>Voir Coché</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setFilter("unchecked")}
        >
          <Text style={styles.buttonText}>Voir Non Coché</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.button}
          onPress={() => setFilter("all")}
        >
          <Text style={styles.buttonText}>Voir Tout</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: "100%", // Prend toute la largeur disponible
    alignItems: "center", // Centre le contenu horizontalement
    justifyContent: "center", // Centre le contenu verticalement
  },
  counter: {
    fontSize: 25,
    marginBottom: 10,
  },
  textInputGroup: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  textInput: {
    flex: 1,
    borderWidth: 1,
    borderColor: "#ccc",
    padding: 10,
    borderRadius: 5,
    marginRight: 10,
  },
  listContainer: {
    flex: 1,
    justifyContent: "center",
    width: "75%", // Ajustez la largeur à 80% de l'écran, ou une autre valeur souhaitée
    borderWidth: 1,
    borderColor: "#ddd",
    borderRadius: 5,
    padding: 10,
    marginBottom: 20,
    backgroundColor: "#fff",
    alignSelf: "center", // Centre le container horizontalement dans son parent
  },
  list: {
    marginBottom: 10,
  },
  buttonGroup: {
    flexDirection: "row",
    justifyContent: "space-between",
    flexWrap: "wrap",
  },
  button: {
    backgroundColor: "gray",
    justifyContent: "center",
    paddingVertical: 8,
    paddingHorizontal: 12,
    borderRadius: 5,
    marginVertical: 5,
    margin: 5,
    width: width * 0.2, // Ajuster la taille en fonction de l'écran
  },
  buttonText: {
    color: "white",
    textAlign: "center",
    fontSize: 14,
  },
  addSubTodoButton: {
    backgroundColor: "#4CAF50",
    padding: 10,
    marginVertical: 5,
    borderRadius: 5,
  },
  addSubTodoButtonText: {
    color: "white",
    textAlign: "center",
    fontSize: 14,
  },
  detailButton: {
    backgroundColor: "#1E90FF",
    padding: 10,
    marginVertical: 5,
    borderRadius: 5,
  },
  detailButtonText: {
    color: "white",
    textAlign: "center",
    fontSize: 14,
    fontWeight: "bold",
  },

  progressContainer: {
    width: "100%",
    backgroundColor: "#333",
    borderRadius: 5,
    height: 20,
    marginTop: 10,
  },

  progressBar: {
    height: 20,
    backgroundColor: "#4caf50",
    borderRadius: 5,
    transition: "width 0.5sc ease-in-out",
  },
});
