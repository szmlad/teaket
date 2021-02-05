const CustomerPage = Vue.component('customer-page', {
    data: function () {
        return {
            tickets: [],
            activeUser: null
        }
    },
    template: `
<span class="d-flex">
    <div class="col-md-auto">
        <customer-sidebar :active-user="activeUser"></customer-sidebar>
    </div>
    <div class="col-md">
        <ticket-list :active-user="activeUser" :tickets="tickets"></ticket-list>
    </div>
</span>
    `,
    mounted: function () {
        let vm = this

        fetch('/auth/get')
            .then(resp => resp.json())
            .then(resp => vm.activeUser = resp)
            .catch(err => null)

        fetch(`/rest/tickets?username=${vm.$route.params.username}`)
            .then(resp => resp.json())
            .then(resp => {
                vm.tickets = Object.values(resp)
            })
            .catch(err => null)

        eventBus.$on('user-login', async function (data) {
            if (data.username === '') return

            let user = await fetch('/auth/login', {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(resp => resp.json()).catch(err => null)

            vm.activeUser = user

            if (vm.activeUser)
                vm.$notify('alert', `<h5>Uspešno ste se ulogovali kao ${user.username}</h5>`, 'success')
            else
                vm.$notify('alert', `<h5>Pogrešno korisničko ime ili lozinka</h5>`, 'error')
        })

        eventBus.$on('user-logout', async function (data) {
            let success = await fetch('/auth/logout').then(resp => vm.activeUser = null).catch(err => null)
        })

        eventBus.$on('user-register', async function (data) {
            data.birthDate = moment(data.birthDate).format('yyyy-MM-DD')
            let user = await fetch('/rest/customers', {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(resp => resp.json())
                .then(data => Object.assign(data, { 'type': 'customer' })).catch(err => null)

            if (!user) {
                vm.$notify('alert', `<h5>Neuspešna registracija!</h5>`, 'error')
                return
            }
            vm.activeUser = user
            vm.$notify('alert', `<h5>Uspešno ste se registrovali kao ${user.username}</h5>`, 'success')
        })

        eventBus.$on('cancel-ticket', async function (data) {
            let canceled = await fetch(`/rest/tickets/${data}`, { method: 'DELETE' })
                .then(resp => resp.json())
                .catch(err => null)

            if (canceled != null) {
                vm.tickets = vm.tickets.filter(t => t.id !== canceled.id)
                vm.$notify('alert', `Rezervacija uspešno otkazana!`, 'success')
            } else {
                vm.$notify('alert', `Neuspešno otkazivanje rezervacije!`, 'error')
            }
        })
    }
})