const RegisterForm = Vue.component('RegisterForm', {
    data: function () {
        return {
            username:  '',
            password:  '',
            firstName: '',
            lastName:  '',
            gender:    '',
            birthdate: 0,
            type:      'CUSTOMER',
        };
    },
    template: `
<div>
    <label for="register-username">Korisničko ime</label>
    <input type="text" id="register-username" v-model="username">
    <br>
    <label for="register-password">Lozinka</label>
    <input type="password" id="register-password" v-model="password">
    <br>
    <label for="register-first-name">Ime</label>
    <input type="text" id="register-first-name" v-model="firstName">
    <br>
    <label for="register-last-name">Prezime</label>
    <input type="text" id="register-last-name" v-model="lastName">
    <br>
    <label for="register-gender">Rod</label>
    <select id="register-gender" v-model="gender">
        <option name="Žensko" value="FEMALE">Žensko</option>    
        <option name="Muško" value="MALE">Muško</option>    
        <option name="Drugo" value="OTHER">Drugo</option>    
        <option name="" value="NA" selected></option>    
    </select>
    <br>
    <label for="register-birthdate">Datum rođenja</label>
    <input type="date" id="register-birthdate" v-model="birthdate">
    <br>
    <button type="submit" v-on:click="register">Registruj se</button>
</div>`,
    methods: {
        register() {
            store.dispatch('register', {
                username:  this.username,
                password:  this.password,
                firstName: this.firstName,
                lastName:  this.lastName,
                gender:    this.gender,
                birthdate: new Date(this.birthdate).valueOf(),
                type:      this.type,
            });
        }
    }
});