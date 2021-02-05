const CustomerPage = Vue.component('customer-page', {
    data: function () {
        return {
            tickets: []
        }
    },
    props: {
        activeUser: {
            type: Object,
            default: null
        }
    },
    template: `
<span class="d-flex">
    <div class="col-md-auto">
        <main-sidebar :active-user="activeUser"></main-sidebar>
    </div>
    <div class="col-md">
        <ticket-list :active-user="activeUser" :tickets="tickets"></ticket-list>
    </div>
</span>
    `,
    mounted: function () {
        let vm = this
        fetch(`/rest/tickets?username=${vm.$route.params.username}`)
            .then(resp => resp.json())
            .then(resp => {
                vm.tickets = Object.values(resp)
            })
            .catch(err => null)
    }
})