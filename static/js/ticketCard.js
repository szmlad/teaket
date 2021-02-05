Vue.component('ticket-card', {
    props: {
        ticket: {
            type: Object,
            required: true
        },
        activeUser: {
            type: Object,
            default: null
        }
    },
    data: function () {
        return {
            manifestation: null
        }
    },
    template: `
<div class="bg-light card man-card" style="margin: auto; width: 90%; margin-top: 20px;">
    <div class="card-body">
        <div class="d-flex">
            <div>
                <div class="card-title"><h5 class="card-title">{{ manifestation.name }}</h5></div>
                <div class="card-text">{{ manifestation.time | formatDate }}</div>
                <div>
                    <small class="text-muted">
                        <div>{{ manifestation.location.address.street }}</div>
                        <div>{{ manifestation.location.address.zipCode }} {{ manifestation.location.address.city }}</div>
                        <div class="">{{ manifestation.location.latitude | formatNumber }}, {{ manifestation.location.longitude | formatNumber }}</div>
                    </small>
                </div>
            </div>
            <div class="ms-auto card-text"><span class="badge alert-secondary">{{ ticket.type | prettifyType }}</span></div>
        </div>
        <div class="d-flex">
            <div></div>
            <div class="ms-auto">
                <button v-on:click="cancelReservation" v-if="canCancel" class="btn btn-danger">Otkaži rezervaciju</button>
            </div>
        </div>
    </div>
</div>
    `,
    created: function () {
        let vm = this
        fetch(`/rest/manifestations/${this.ticket.manifestationId}`)
            .then(resp => resp.json())
            .then(resp => vm.manifestation = resp)
            .catch(err => null)
    },
    methods: {
        cancelReservation: function (event) {
            eventBus.$emit('cancel-ticket', this.ticket.id)
        }
    },
    filters: {
        prettifyType: function (type) {
            if (type === 'REGULAR') return 'Regularna karta'
            else if (type === 'VIP') return 'VIP karta'
            else return 'Fan Pit karta'
        },
        formatNumber: function (num) {
            return num.toFixed(2)
        },
        formatDate: function (value) {
            return moment(value).format('DD.MM.YYYY, HH:mm')
        },
    },
    computed: {
        canCancel: function () {
            if (this.activeUser === null) return false
            if (this.$route.params.username !== this.activeUser.username) return false
            if (moment(this.manifestation.time) - moment() < 0) return false
            return true
        }
    }
})