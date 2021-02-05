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
    <div class="row no-gutters">
        <div class="card-body">
            <div class="d-flex">
                <div><h5 class="card-title">{{ manifestation.name }}</h5></div>
            </div>
        </div>
    </div>
</div>
    `,
    mounted: function () {
        let component = this
        fetch(`/rest/manifestations/${this.ticket.manifestationId}`)
            .then(resp => resp.json())
            .then(resp => component.manifestation = resp)
            .catch(err => null)
    }
})