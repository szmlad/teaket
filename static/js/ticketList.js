Vue.component('ticket-list', {
    props: {
        tickets: {
            type: Array,
            required: true,
        },
        activeUser: {
            type: Object,
            default: null
        }
    },
    template: `
<div id="list-tickets">
    <div class="list-ticks">
        <ticket-card v-for="(t, i) in tickets" v-bind:key="i" v-bind:ticket="t" v-bind:active-user="activeUser"></ticket-card>
    </div>
</div>
    `
})