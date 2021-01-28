Vue.component('manif-list', {
    props: {
        manifestations: {
            type: Array,
            required: true,
        }
    },
    template: `
<div id="list-manifestations">
    <div v-for="(m, i) in manifestations" class="list-manifs">
        <manif-card v-bind:manifestation="m" v-bind:isHidden="m.isHidden"></manif-card>
    </div>
</div>
    `,
})