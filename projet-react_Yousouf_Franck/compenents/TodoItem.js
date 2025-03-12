import React, { useState, useEffect } from "react";
import {
  Image,
  View,
  Text,
  StyleSheet,
  Switch,
  TouchableOpacity,
} from "react-native";

export default function TodoItem(props) {
  const [done, setDone] = useState(props.item.done);

  useEffect(() => {
    setDone(props.item.done);
  }, [props.item.done]);

  // Fonction de rendu de chaque sous-tâche
  const renderSubTask = ({ item }) => (
    <View style={styles.subTaskContainer}>
      <Switch
        value={item.done}
        onValueChange={() => props.updateSubTask(props.item.id, item.id)}
      />
      <Text
        style={[
          styles.text_item,
          { textDecorationLine: item.done ? "line-through" : "none" },
        ]}
      >
        {item.content}
      </Text>
    </View>
  );

  return (
    <View style={styles.content}>
      {/* Switch principal pour la tâche */}
      <Switch
        value={done}
        onValueChange={(state) => {
          setDone(state);
          props.updateTodoStatus(props.item.id, state);
        }}
      />
      <Text
        style={[
          styles.text_item,
          { textDecorationLine: done ? "line-through" : "none" },
        ]}
      >
        {props.item.content}
      </Text>

      <TouchableOpacity onPress={() => props.deleteTodo(props.item.id)}>
        <Image
          source={require("../assets/trash-can-outline.png")}
          style={{ height: 24, width: 24 }}
        />
      </TouchableOpacity>

      {/* Affichage des sous-tâches
      {props.item.todos.length > 0 && (
        <FlatList
          data={props.item.todos}
          renderItem={renderSubTask}
          keyExtractor={subItem => subItem.id.toString()}
        />
      )} */}
    </View>
  );
}

const styles = StyleSheet.create({
  content: {
    flexDirection: "row",
    alignItems: "center", // Centre les éléments verticalement
    justifyContent: "center", // Centre les éléments horizontalement
    marginBottom: 10,
  },
  text_item: {
    marginLeft: 10,
    width: 150,
  },
  subTaskContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginLeft: 20,
  },
});
