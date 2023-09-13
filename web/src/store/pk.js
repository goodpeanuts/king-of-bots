//import $ from 'jquery'

export default {
  state: {
            status: "matching", //matching 表示正在匹配中，playing 表示正在游戏中
            socket: null,
            opponent_username: "",
            opponent_photo: "",  
  },
  getters: {
  },
  mutations: {
    updateSocket(state, socket) {
        state.socket = socket;
    },

    updateOpponent(state, opponent) {
        state.opponent_username = opponent.username;
        state.opponent_photo = opponent.photo;

    },

    updateStatus (state, status) {
        state.status = status; 
    }
  },
  actions: {
  },
  modules: {
  }
}