const App = Vue.component('App', {
    computed: {
        activeUser() {
            return store.state.activeUser;
        },
    },
    mounted: function () {
        store.dispatch('initUser');
    },
    template: `
<div>
<AuthForm/>
<p>
Logged in as:
<span v-if="activeUser != null">
    {{ activeUser.credentials.username }}
    ({{ activeUser.credentials.firstName }} 
     {{ activeUser.credentials.lastName }})
</span>
</p>
</div>
`,
    components: { AuthForm },
});