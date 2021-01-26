const app = new Vue({
    el: '#app',
    data: {
        manifestations: null
    },
    mounted: function() {
        fetch('/rest/manifestations')
            .then(resp => resp.json())
            .then(data => {
                this.manifestations = Object.values(data)
                for (let m of this.manifestations) {
                    m.time = new Date(Date.parse(m.time))
                }
            })
    },
})

Vue.component('star-rating', VueStarRating.default)