function matchManifestation(manif, searchData) {
    if (!manif.name.toLowerCase().includes(searchData.name.toLowerCase())) return false

    const fullLocation = `${manif.location.address.street} ${manif.location.address.city} ${manif.location.address.zipCode}`.toLowerCase()
    if (!fullLocation.includes(searchData.location.toLowerCase())) return false

    if (searchData.type !== '' && manif.type !== searchData.type) return false

    return true
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
    }
})

Vue.component('star-rating', VueStarRating.default)