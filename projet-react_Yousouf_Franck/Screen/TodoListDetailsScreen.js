import React, { useContext, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  Button,
  TextInput,
  Switch,
} from "react-native";
import { TodosContext } from "../Context/Context";

export default function TodoListDetailsScreen({ route }) {
  const { taskId } = route.params;
  const { todos, addNewSubTodo, updateSubTaskStatus, deleteSubTask } =
    useContext(TodosContext);
  const [newSubTodoText, setNewSubTodoText] = useState("");
  const task = todos.find((item) => item.id === taskId);
  const [refresh, setRefresh] = useState(false);

  if (!task) {
    return (
      <View style={styles.container}>
        <Text>Tâche non trouvée</Text>
      </View>
    );
  }

  const toggleSubTaskDone = (subTaskId, newStatus) => {
    updateSubTaskStatus(taskId, subTaskId, newStatus); // Met à jour la tâche en backend
    setRefresh(!refresh); // Change l'état pour forcer le re-rendu
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Détails de la Tâche</Text>
      <Text style={styles.taskContent}>
        Contenu: {task.content}, Tâche accomplie : {task.done ? "Oui" : "Non"}
      </Text>

      <Text style={styles.subTaskTitle}>Sous-Tâches :</Text>
      <FlatList
        data={task.todos}
        keyExtractor={(item) => item.id.toString()}
        renderItem={({ item }) => (
          <View style={styles.subTaskContainer}>
            <View style={styles.titleSwitch}>
              <Text>{item.content}</Text>
              <Switch
                value={item.done}
                onValueChange={(value) => toggleSubTaskDone(item.id, value)}
              />
            </View>
            <View style={styles.buttonContainer}>
              <Button
                title="Supprimer"
                onPress={() => {
                  deleteSubTask(taskId, item.id);
                  setRefresh(!refresh);
                }}
              />
            </View>
            {item.todos && item.todos.length > 0 && (
              <FlatList
                data={item.todos}
                keyExtractor={(subItem) => subItem.id.toString()}
                renderItem={({ item: subItem }) => (
                  <View style={styles.subTaskContainer}>
                    <Text style={{ paddingLeft: 10 }}>- {subItem.content}</Text>
                  </View>
                )}
              />
            )}
          </View>
        )}
      />

      <TextInput
        style={styles.textInput}
        placeholder="Ajouter une sous-tâche"
        onChangeText={(text) => {
          setNewSubTodoText(text);
        }}
        value={newSubTodoText}
      />
      <View style={styles.buttonContainer}>
        <Button
          title="Ajouter une sous-tâche"
          onPress={() => {
            addNewSubTodo(taskId, newSubTodoText);
            setRefresh(!refresh);
          }}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
    width: "90%",
  },
  titleSwitch: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: 10,
  },
  taskContent: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
  },
  subTaskTitle: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 10,
  },
  subTaskContainer: {
    marginBottom: 10,
    padding: 10,
    backgroundColor: "#fff",
    borderRadius: 5,
    borderWidth: 1,
    borderColor: "#ccc",
  },
  title: {
    fontSize: 20,
    marginBottom: 20,
    textAlign: "center",
  },
  textInput: {
    borderWidth: 1,
    borderColor: "#ccc",
    padding: 10,
    marginVertical: 10,
    borderRadius: 5,
    width: "100%",
  },
  buttonContainer: {
    marginVertical: 10,
    width: "100%",
  },
});
