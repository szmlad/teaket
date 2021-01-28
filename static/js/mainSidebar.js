Vue.component('main-sidebar', {
    props: {
        'active-user': {
            type: Object,
            default: null
        }
    },
    template: `
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="bg-dark text-light d-flex justify-content-center pb-3 pt-3">
            <h1>Teaket</h1>
        </div>
        <user-bar :active-user="activeUser"></user-bar>
        <manif-search></manif-search>
    </div>`
})