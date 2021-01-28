function matchManifestation(manif, searchData) {
    const matchName = (name, pat) => name.toLowerCase().includes(pat.toLowerCase())
    const matchLocation = (loc, pat) => loc.toLowerCase().includes(pat.toLowerCase())
    const matchType = (type, pat) => pat === '' ? true : type === pat
    const matchDates = (date, startDate, endDate) => {
        date = moment(date).startOf('day')
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

const eventBus = new Vue()

let app = new Vue({
    el: '#app',
    data: {
        manifestations: {}
    },
    mounted: function () {
        fetch('/rest/manifestations')
            .then(resp => resp.json())
            .then(data => {
                this.manifestations = data
                Object.values(this.manifestations).forEach(m => {
                    m.time = new Date(Date.parse(m.time))
                    m.isHidden = false
                })

                let vm = this
                eventBus.$on('search-manifs', function (data) {
                    for (let [id, m] of Object.entries(vm.manifestations)) {
                        Vue.set(vm.manifestations[id], 'isHidden', !matchManifestation(m, data))
                    }
                    vm.manifestations = Object.assign({}, vm.manifestations)
                })

                const allTypes = [...new Set(Object.values(this.manifestations).map(m => m.type))]
                eventBus.$emit('send-manif-types', allTypes)
            })
    },
    components: {
        vuejsDatepicker
    }
})

Vue.component('star-rating', VueStarRating.default)