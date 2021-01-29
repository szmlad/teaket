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
            showRegisterForm: false,
            loginUsername: '',
            loginPassword: '',
            registerFirstName: '',
            registerLastName: '',
            registerGender: '',
            registerBirthDate: '',
            registerUsername: '',
            registerPassword: '',
        }
    },
    template: `
<div>
    <div v-if="activeUser == null && !showLoginForm && !showRegisterForm" class="d-flex">
        <a v-on:click="toggleRegisterInfo" class="btn btn-dark ms" href="#" style="border-radius: 0; width: 50%;">Registracija</a>
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
    <div v-if="showRegisterForm" class="bg-dark">
        <div class="list-group list-group-flush">
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="register-first-name">Ime</label>
                <input v-model="registerFirstName" type="text" class="form-control from-control-sm" id="register-first-name">
            </div>
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="register-last-name">Prezime</label>
                <input v-model="registerLastName" type="text" class="form-control form-control-sm" id="register-last-name">
            </div>
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="register-gender">Rodno opredeljenje</label>
                <select v-model="registerGender" class="form-control form-control-sm" id="register-gender">
                    <option value="NA" selected>Ništa od navedenog</option>
                    <option value="FEMALE">Žensko</option>
                    <option value="MALE">Muško</option>
                    <option value="OTHER">Drugo</option>
                </select>
            </div>
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="register-birth-date">Datum rođenja</label>
                <vuejsDatepicker v-model="registerBirthDate" :calendar-button="true" :calendar-icon="'ui-icon-calendar'" :input-class="'calendar-input'" :bootstrap-styling="true" :format="dateFormatter"></vuejsDatepicker>
            </div>
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="register-username">Korisničko ime</label>
                <input v-model="registerUsername" type="text" class="form-control form-control-sm" id="register-username">
            </div>
            <div class="form-group p-2 pb-0">
                <label class="text-light" for="register-password">Lozinka</label>
                <input v-model="registerPassword" type="password" class="form-control form-control-sm" id="register-password">
            </div>
        </div>
        <div class="d-flex">
            <a v-on:click="register" class="btn btn-success p-2 mt-2" style="width: 50%; border-radius: 0;">Registruj se</a>
            <a v-on:click="toggleRegisterInfo" class="btn btn-light p-2 mt-2" style="width: 50%; border-radius: 0;">Nazad</a>
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
        toggleRegisterInfo: function (event) {
            this.showRegisterForm = !this.showRegisterForm
            this.registerFirstName = ''
            this.registerLastName = ''
            this.registerGender = 'NA'
            this.registerBirthDate = ''
            this.registerUsername = ''
            this.registerPassword = ''
            this.$nextTick(function () {
                if (this.showRegisterForm) document.getElementById('register-first-name').focus()
            })
        },
        register: function (event) {
            const registerData = {
                firstName: this.registerFirstName,
                lastName: this.registerLastName,
                gender: this.registerGender,
                birthDate: this.registerBirthDate,
                username: this.registerUsername,
                password: this.registerPassword
            }
            this.showRegisterForm = false
            eventBus.$emit('user-register', registerData)
        },
        dateFormatter: function (date) {
            return moment(date).format('DD.MM.yyyy')
        },
        showUsername: function () {
            if (this.activeUser == null) return ''
            return `${this.activeUser.username} (${this.activeUser.firstName} ${this.activeUser.lastName})`
        }
    },
    components: {
        vuejsDatepicker
    }
})