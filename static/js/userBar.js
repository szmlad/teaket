Vue.component('user-bar', {
    props: {
        activeUser: {
            type: Object,
            default: null
        }
    },
    data: function () {
        return {
            showLoginForm: false,
            loginUsername: '',
            loginPassword: '',
        }
    },
    template: `
<div>
    <div v-if="activeUser == null && !showLoginForm" class="d-flex">
        <a class="btn btn-dark ms" href="#" style="border-radius: 0; width: 50%;">Registracija</a>
        <a v-on:click="toggleLoginInfo" type="button" class="btn btn-dark m-0" data-toggle="modal" data-target="#loginModal" style="border-radius: 0; width: 50%;">Prijava</a>
    </div>
    <div v-if="showLoginForm" class="bg-dark">
        <div class="list-group list-group-flush">
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="login-username">Korisničko ime</label>
                <input v-model="loginUsername" type="text" class="form-control form-control-sm" id="login-username">
            </div>
            <div class="form-group p-2">
                <label class="text-light" for="login-password">Lozinka</label>
                <input v-model="loginPassword" type="password" class="form-control form-control-sm" id="login-password">            
            </div>
        </div>
        <div class="d-flex">
            <a v-on:click="logIn" class="btn btn-success p-2 mt-2" style="width: 50%; border-radius: 0;">Prijavi se</a>
            <a v-on:click="toggleLoginInfo" class="btn btn-light p-2 mt-2" style="width: 50%; border-radius: 0;">Nazad</a>
        </div>
    </div>
    <div v-if="activeUser != null" class="bg-dark">
        <h5 class="text-light m-auto w-75">{{ showUsername() }}</h5>
        <div class="p-2 mt-2">
            <a v-on:click="logOut" class="btn btn-danger" style="width: 100%;">Odjavi se</a>
        </div>
    </div>
</div>
    `,
    methods: {
        toggleLoginInfo: function (event) {
            this.showLoginForm = !this.showLoginForm
            this.loginUsername = ''
            this.loginPassword = ''
            this.$nextTick(function () {
                if (this.showLoginForm) document.getElementById('login-username').focus()
            })
        },
        logIn: function (event) {
            const loginData = {
                username: this.loginUsername,
                password: this.loginPassword
            }
            this.showLoginForm = false
            eventBus.$emit('user-login', loginData)
        },
        logOut: function (event) {
            eventBus.$emit('user-logout')
        },
        showUsername: function () {
            if (this.activeUser == null) return ''
            return `${this.activeUser.username} (${this.activeUser.firstName} ${this.activeUser.lastName})`
        }
    }
})