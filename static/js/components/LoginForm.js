const LoginForm = Vue.component('AuthForm', {
    data: function () {
        return {
            username: '',
            password: '',
        };
    },
    template: `
<div>
    <label for="login-username">Korisničko ime</label>
    <input type="text" id="login-username" v-model="username">
    
    <label for="login-password">Lozinka</label>
    <input type="password" id="login-password" v-model="password">
    
    <button type="submit" v-on:click="login">Prijavi se</button>
    <button type="button" v-on:click="logout">Odjavi se</button>
</div>`,
    methods: {
        login() {
            store.dispatch('login', {
                username: this.username,
                password: this.password
            });
        },
        logout() {
            store.dispatch('logout');
        }
    }
});