import React, { useState, useEffect } from "react";
import Navigation from "./Navigation/Navigation.js";
import AsyncStorage from "@react-native-async-storage/async-storage";

import { TodosContext, TokenContext, UsernameContext } from "./Context/Context";

import todoData from "./Helpers/todoData.js";

export default function App() {
  const [token, setToken] = useState(null);
  const [username, setUsername] = useState(null);

  const [todos, setTodos] = useState(todoData);
  const [selectedTodoId, setSelectedTodoId] = useState(null);
  const [count, setCount] = useState(todos.filter((item) => item.done).length);
  const [filter, setFilter] = useState("all");

  const addNewTodo = (label) => {
    if (label === "") return;

    const newTodo = {
      id: todos.length ? Math.max(...todos.map((item) => item.id)) + 1 : 1,
      content: label,
      done: false,
      todos: [],
    };
    updateTodos([...todos, newTodo]);
  };

  const updateTodos = (newTodos) => {
    setTodos(newTodos);
    saveTodos(newTodos);
    setCount(
      newTodos.filter(
        (item) =>
          item.done ||
          (item.todos.length && item.todos.every((subItem) => subItem.done))
      ).length
    );
  };

  const updateTodo = (id, label) => {
    const newTodos = todos.map((item) => {
      if (item.id === id) {
        item.content = label;
      }

      return item;
    });

    updateTodos(newTodos);
  };

  const deleteTodo = (id) => {
    const newTodos = todos.filter((item) => item.id !== id);
    updateTodos(newTodos);
  };

  const updateTodoStatus = (id, newStatus) => {
    const newTodos = todos.map((item) => {
      if (item.id === id) {
        item.done = newStatus;
        // Marquer toutes les sous-tâches comme "done" ou "not done" selon l'état de la tâche principale
        if (item.todos) {
          item.todos = item.todos.map((subItem) => ({
            ...subItem,
            done: item.done, // Synchroniser l'état des sous-tâches
          }));
        }
      }

      return item;
    });
    updateTodos(newTodos);
  };

  const addNewSubTodo = (id, newLabel) => {
    if (newLabel.trim() === "") return;

    const newTodos = todos.map((item) => {
      if (item.id === id) {
        const newSubTask = {
          id: item.todos.length
            ? Math.max(...item.todos.map((subItem) => subItem.id)) + 1
            : 1,
          content: newLabel,
          done: false, // La nouvelle sous-tâche est par défaut non terminée
        };

        item.todos.push(newSubTask);
      }
      return item;
    });

    updateTodos(newTodos);
  };

  const updateSubTask = (parentId, subTaskId, newLabel) => {
    const newTodos = todos.map((item) => {
      if (item.id === parentId) {
        item.todos = item.todos.map((subItem) =>
          subItem.id === subTaskId ? { ...subItem, content: newLabel } : subItem
        );
      }
      return item;
    });
    updateTodos(newTodos);
  };

  const updateSubTaskStatus = (parentId, subTaskId, newStatus) => {
    const newTodos = todos.map((item) => {
      if (item.id === parentId) {
        item.todos = item.todos.map((subItem) =>
          subItem.id === subTaskId ? { ...subItem, done: newStatus } : subItem
        );
        // Mettre à jour l'état de la tâche principale si toutes les sous-tâches sont complètes
        item.done = item.todos.every((subItem) => subItem.done);
      }
      return item;
    });
    updateTodos(newTodos);
  };

  // Fonction deleteTodo modifiée pour supprimer uniquement la sous-tâche
  const deleteSubTask = (taskId, subTaskId) => {
    const newTodos = todos.map((item) => {
      if (item.id === taskId) {
        // Supprimer uniquement la sous-tâche correspondant à subTaskId
        item.todos = item.todos.filter((subItem) => subItem.id !== subTaskId);
      }
      return item;
    });
    updateTodos(newTodos); // Mettre à jour l'état des tâches
  };

  // Fonction de chargement des tâches depuis AsyncStorage
  const loadTodos = async () => {
    try {
      const savedTodos = await AsyncStorage.getItem(`todos_user_${username}`);
      if (savedTodos) {
        const parsedTodos = JSON.parse(savedTodos);
        setTodos(parsedTodos);
        setCount(parsedTodos.filter((item) => item.done).length); // Mettre à jour le compteur
      } else {
        // Si aucune tâche n'est trouvée, initialisez avec todoData
        setTodos(todoData);
        setCount(todoData.filter((item) => item.done).length);
      }
    } catch (error) {
      console.error("Erreur lors du chargement des tâches:", error);
    }
  };

  // Sauvegarde des données sans useEffect
  const saveTodos = async (newTodos) => {
    try {
      await AsyncStorage.setItem(
        `todos_user_${username}`,
        JSON.stringify(newTodos)
      );
    } catch (error) {
      console.error("Erreur lors de la sauvegarde des tâches:", error);
    }
  };

  const checkAll = () => {
    const newTodos = todos.map((item) => ({
      ...item,
      done: true,
      todos: item.todos.map((subItem) => ({ ...subItem, done: true })),
    }));
    updateTodos(newTodos);
  };

  const unCheckAll = () => {
    const newTodos = todos.map((item) => ({
      ...item,
      done: false,
      todos: item.todos.map((subItem) => ({ ...subItem, done: false })),
    }));
    setCount(0);
    updateTodos(newTodos);
  };

  const filteredTodos = todos.filter((item) => {
    const allSubTasksDone =
      item.todos && item.todos.every((subItem) => subItem.done);
    if (filter === "checked") return item.done || allSubTasksDone;
    if (filter === "unchecked") return !item.done && !allSubTasksDone;
    return true;
  });

  // Appeler `loadTodos` au démarrage de l'application
  useEffect(() => {
    loadTodos(); // Charge les tâches une fois que le composant est monté
  }, []);

  const functions = {
    addNewSubTodo,
    addNewTodo,
    deleteSubTask,
    deleteTodo,
    checkAll,
    setCount,
    setFilter,
    setSelectedTodoId,
    setTodos,
    unCheckAll,
    updateSubTask,
    updateSubTaskStatus,
    updateTodo,
    updateTodos,
    updateTodoStatus,
  };

  return (
    <UsernameContext.Provider value={[username, setUsername]}>
      <TokenContext.Provider value={[token, setToken]}>
        <TodosContext.Provider
          value={{ todos, count, selectedTodoId, filteredTodos, ...functions }}
        >
          <Navigation />
        </TodosContext.Provider>
      </TokenContext.Provider>
    </UsernameContext.Provider>
  );
}
