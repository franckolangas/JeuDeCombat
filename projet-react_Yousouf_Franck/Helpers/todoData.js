// Helpers/todoData.js

const todoData = [
    {
        id: 1,
        content: "faire ses devoirs",
        done: true,
        todos: [
            {
                id: 1,
                content: 'Choisir la matière',
                done: true
            }
        ]
    },
    {
        id: 2,
        content: "ranger sa chambre",
        done: false,
        todos: [
            {
                id: 2,
                content: 'Fraire le trie',
                done: false
            }
        ]
    },
    {
        id: 3,
        content: "essuyer la vaisselle",
        done: false,
        todos: [
            {
                id: 3,
                content: 'vider les ordures',
                done: false
            }
        ]
    }
];

export default todoData