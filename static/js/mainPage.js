function matchManifestation(manif, searchData) {
    const matchName = (name, pat) => name.toLowerCase().includes(pat.toLowerCase())
    const matchLocation = (loc, pat) => loc.toLowerCase().includes(pat.toLowerCase())
    const matchType = (type, pat) => pat === '' ? true : type === pat
    const matchDates = (date, startDate, endDate) => {
        date = date.startOf('day')
        startDate = startDate ? moment(startDate).startOf('day') : moment(0)
        endDate = endDate ? moment(endDate).startOf('day') : moment('2200-01-01')

        return date.isSameOrAfter(startDate) && date.isSameOrBefore(endDate)
    }

    if (!matchName(manif.name, searchData.name)) return false

    const addr = manif.location.address
    const location = `${addr.street} ${addr.city} ${addr.zipCode}`
    if (!matchLocation(location, searchData.location)) return false

    if (!matchType(manif.type, searchData.type)) return false

    return matchDates(manif.time, searchData.startDate, searchData.endDate)
}

function comparisonCompose(...fns) {
    return (x, y) => {
        if (fns.length === 0) return 0

        const res = fns[0](x, y)
        if (res === 0) return comparisonCompose(fns.slice(1))
        else return res
    }
}

function manifestationSort(by, dir) {
    const compare = by => {
        switch (by) {
            case 'name': {
                return (m, n) => m.name < n.name ? -1 : m.name > n.name ? 1 : 0
            }
            case 'location': {
                return comparisonCompose(
                    (m, n) =>
                        m.location.address.zipCode - n.location.address.zipCode,
                    (m, n) =>
                        m.location.address.city < n.location.address.city ?
                            -1 :
                            m.location.address.city > n.location.address.city ?
                                1 :
                                0,
                    (m, n) =>
                        m.location.address.street < n.location.address.street ?
                            -1 :
                            m.location.address.street > n.location.address.street ?
                                1 :
                                0
                )
            }
            case 'date': {
                return (m, n) => m.time.isBefore(n.time) ? -1 : m.time.isAfter(n.time) ? 1 : 0
            }
            case 'rating': {
                return (m, n) => m.rating - n.rating
            }
            default: {
                console.log(`unknown option ${by}`)
                return (m, n) => 0
            }
        }
    }
    if (by === '' || dir === '') return xs => {}

    const sortingBy = compare(by)
    const sortingDir = dir === 'desc' ? -1 : 1
    return xs => xs.sort((m, n) => sortingDir * sortingBy(m, n))
}

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
                .catch(err => console.log(err))

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
            } else {
                vm.$notify('alert', `Uspešno ste rezervisali kartu!`, 'success')
            }
        })
    },
    components: {
        vuejsDatepicker
    }
})