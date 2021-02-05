Vue.component('main-sidebar', {
    props: {
        'active-user': {
            type: Object,
            default: null
        }
    },
    template: `
    <div class="bg-light border-right sidebar-wrapper">
        <div class="bg-dark text-light d-flex justify-content-center pb-3 pt-3">
            <h1><a class="text-light" href="#/" style="text-decoration: none;">Teaket</a></h1>
        </div>
        <user-bar :active-user="activeUser"></user-bar>
        <manif-search></manif-search>
    </div>`
})