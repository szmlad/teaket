Vue.component('manif-list', {
    props: {
        manifestations: {
            type: Array,
            required: true,
        },
        activeUser: {
            type: Object,
            default: null
        }
    },
    template: `
<div id="list-manifestations">
    <div v-for="(m, i) in manifestations" class="list-manifs">
        <manif-card v-bind:manifestation="m" v-bind:isHidden="m.isHidden" v-bind:active-user="activeUser"></manif-card>
    </div>
</div>
    `,
})