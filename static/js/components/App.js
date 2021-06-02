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
    {{ activeUser.username }}
    ({{ activeUser.firstName }} 
     {{ activeUser.lastName }})
</span>
</p>
</div>
`,
    components: { AuthForm },
});