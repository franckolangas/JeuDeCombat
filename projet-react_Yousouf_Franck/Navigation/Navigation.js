import React, { useContext } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { TokenContext } from './../Context/Context';
import SignInScreen from '../Screen/SignInScreen';
import SignUpScreen from '../Screen/SignUpScreen';
import HomeScreen from '../Screen/HomeScreen';
import TodoListsScreen from '../Screen/TodoListsScreen';
import SignOutScreen from '../Screen/SignOutScreen';
import TodoListDetailsScreen from '../Screen/TodoListDetailsScreen';

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

// export default function Navigation() {
//   const [token] = useContext(TokenContext);
//   return (
//     <NavigationContainer>
//       {token == null ? (
//         // Navigation pour les utilisateurs non connectés
//         <Tab.Navigator>
//           <Tab.Screen name='SignIn' component={SignInScreen} />
//           <Tab.Screen name='SignUp' component={SignUpScreen} />
//         </Tab.Navigator>
//       ) : (
//         // Navigation pour les utilisateurs connectés
//         <Tab.Navigator>
//           <Tab.Screen name='Home' component={HomeScreen} />
//           <Tab.Screen name='TodoLists' component={NavigationTodo} />
//           <Tab.Screen name='SignOut' component={SignOutScreen} />
//         </Tab.Navigator>
//       )}
//     </NavigationContainer>
//   );
// }

export default function Navigation () {
  return (
    <TokenContext.Consumer>
      {([_, setToken]) => {
        const token = 'oajzajzkaj'
        return (<NavigationContainer>
          {token == null ? (
            <Tab.Navigator>
              <Tab.Screen name='SignIn' component={SignInScreen} />
              <Tab.Screen name='SignUp' component={SignUpScreen} />
            </Tab.Navigator>
          ) : (
            <Tab.Navigator>
              <Tab.Screen name='Home' component={HomeScreen} />
              <Tab.Screen name='TodoLists' component={NavigationTodo} />
              <Tab.Screen name='SignOut' component={SignOutScreen} />
              
            </Tab.Navigator>
          )}
        </NavigationContainer>)
      }}
    </TokenContext.Consumer>
  )
}

function NavigationTodo() {
  return (
    <Stack.Navigator initialRouteName='List'>
      <Stack.Screen name='List' component={TodoListsScreen} />
      <Stack.Screen name='Details' component={TodoListDetailsScreen} />
    </Stack.Navigator>
  );
}
