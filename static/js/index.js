Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        activeUser: null,
    },
    mutations: {
        userSet: (state, user) => Vue.set(state, 'activeUser', user),
        userClear: state => Vue.set(state, 'activeUser', null),
    },
    actions: {
        async initUser({ commit }) {
            get('/auth')
                .then(resp => resp.json())
                .then(json => commit('userSet', json))
                .catch(_ => null);
        },
        async login({ commit }, creds) {
            post('/auth/login', creds)
                .then(resp => resp.json())
                .then(json => commit('userSet', json))
                .catch(_ => null);
        },
        async logout({ commit }) {
            get('/auth/logout')
                .then(_ => commit('userClear'))
                .catch(_ => null);
        },
        async register({ commit }, creds) {
            post('/auth/register', creds)
                .then(resp => resp.json())
                .then(json => commit('userSet', json))
                .catch(_ => null);
        }
    },
    getters: {
        activeUser: state => state.activeUser,
    },
});

new Vue({
    el: '#app',
    store: store,
    template: '<App/>',
    components: { App },
});