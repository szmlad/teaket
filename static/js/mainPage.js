const MainPage = Vue.component('main-page', {
    data: function () {
        return {
            manifestations: [],
            activeUser: null
        }
    },
    template: `
<span class="d-flex">
    <div class="col-md-auto">
        <main-sidebar :active-user="activeUser"></main-sidebar>
    </div>
    <div class="col-md">
        <manif-list :active-user="activeUser" :manifestations="manifestations"></manif-list>
    </div>
</span>
    `,
    mounted: function () {
        let vm = this

        fetch('/auth/get')
            .then(resp => resp.json())
            .then(resp => vm.activeUser = resp)
            .catch(err => null)
        fetch('/rest/manifestations')
            .then(resp => resp.json())
            .then(data => {
                this.manifestations = Object.values(data)
                this.manifestations.forEach(m => {
                    m.time = moment(Date.parse(m.time))
                    m.isHidden = false
                })

                const allTypes = [...new Set(Object.values(this.manifestations).map(m => m.type))]
                eventBus.$emit('send-manif-types', allTypes)
            })

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

        eventBus.$on('search-manifs', function (data) {
            const sort = manifestationSort(data.sortBy, data.sortDirection)
            sort(vm.manifestations)
            vm.manifestations.forEach(m => m.isHidden = !matchManifestation(m, data))
            vm.manifestations = vm.manifestations.slice(0)
        })

        eventBus.$on('purchase-ticket', async function (data) {
            const price = data.price
            delete data.price
            const ticketData = await fetch('/rest/tickets', {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(resp => resp.json())
                .catch(err => null)

            if (ticketData == null) {
                vm.$notify('alert', `Neuspešna kupovina!`, 'error')
                return
            }

            vm.activeUser.tickets.push(ticketData.id)

            const updatedUser = await fetch(`/rest/customers/${vm.activeUser.username}`, {
                method: 'PUT',
                body: JSON.stringify(vm.activeUser),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(resp => resp.json())
                .catch(err => null)

            if (updatedUser == null) {
                vm.$notify('alert', 'Neuspešna rezervacija!', 'error')
                return
            }

            vm.$notify('alert', `Uspešno ste rezervisali kartu!`, 'success')
        })
    },
    components: {
        vuejsDatepicker
    }
})