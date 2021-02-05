Vue.component('manif-card', {
    props: {
        manifestation: {
            type: Object,
            required: true
        },
        activeUser: {
            type: Object,
            default: null
        },
        isHidden: {
            type: Boolean,
            default: false
        }
    },
    template: `
<div v-if="!isHidden" class="bg-light card man-card" style="margin: auto; width: 90%; margin-top: 20px;">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img class="card-img" v-bind:src="manifestation.image" style="width: 100%; height: 250px; object-fit: cover;">
        </div>
        <div class="col-md">
            <div class="card-body">
                <div class="d-flex">
                    <div><h5 class="card-title">{{ manifestation.name }}</h5></div>
                    <div class="ms-auto card-text"><span class="badge alert-secondary">{{ manifestation.type }}</span></div>
                </div>
                <p class="card-text">
                    <span class="text-muted">{{ manifestation.time | formatDate }}</span>
                </p>
                <p class="card-text">
                    <small class="text-muted">{{ manifestation.seatCount }} mesta</small>
                </p>
                <p class="card-text">
                    <small class="text-muted">
                        <div>{{ manifestation.location.address.street }}</div>
                        <div>{{ manifestation.location.address.zipCode }} {{ manifestation.location.address.city }}</div>
                        <div>{{ manifestation.location.latitude | formatNumber }}, {{ manifestation.location.longitude | formatNumber }}</div>
                    </small>
                </p>   
                <div class="d-flex">
                    <div>
                        <!-- <star-rating :star-size="20" :rating="3.20" :round-start-rating="false" :show-rating="false"></star-rating>        -->     
                    </div>
                    <div class="ms-auto card-text">
                        <button v-if="canPurchase" v-on:click="purchaseRegular" class="btn btn-success" :disabled="activeUser == null || activeUser.type != 'customer'">
                        Regularna: {{ manifestation.ticketPrice | regularPrice | formatPrice }}
                        </button>
                        <button v-if="canPurchase" v-on:click="purchaseVip" class="btn btn-success" :disabled="activeUser == null || activeUser.type != 'customer'">
                        VIP: {{ manifestation.ticketPrice | vipPrice | formatPrice }}
                        </button>
                        <button v-if="canPurchase" v-on:click="purchaseFanpit" class="btn btn-success" :disabled="activeUser == null || activeUser.type != 'customer'">
                        Fan pit: {{ manifestation.ticketPrice | fanpitPrice | formatPrice }}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    `,
    methods: {
        purchaseTicket: function (event, type) {
            const purchaseData = {
                manifestationId: this.manifestation.id,
                buyer: this.activeUser.username,
                type: type,
                status: 'RESERVED',
                price: this.manifestation.ticketPrice
            }
            eventBus.$emit('purchase-ticket', purchaseData)
        },
        purchaseRegular: function (event) {
            this.purchaseTicket(event, 'REGULAR')
        },
        purchaseVip: function (event) {
            this.purchaseTicket(event, 'VIP')
        },
        purchaseFanpit: function (event) {
            this.purchaseTicket(event, 'FAN_PIT')
        }
    },
    filters: {
        formatDate: function (value) {
            return moment(value).format('DD.MM.YYYY, HH:mm')
        },
        formatNumber: function (num) {
            return num.toFixed(2)
        },
        formatPrice: function (price) {
            return price.toFixed(2)
        },
        regularPrice: function (price) {
            return price
        },
        vipPrice: function (price) {
            return price * 1.1
        },
        fanpitPrice: function (price) {
            return price * 1.2
        }
    },
    computed: {
        canPurchase: function () {
            return moment(this.manifestation.time) - moment() >= 0
        }
    }
})