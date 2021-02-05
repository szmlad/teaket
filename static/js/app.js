

const eventBus = new Vue()

const app = new Vue({
    el: '#app',
    router: new VueRouter({
        mode: "hash",
        routes: [
            { path: '/', component: MainPage },
            { path: '/customer/:username', component: CustomerPage },
            { path: '/salesperson/:username', component: SalespersonPage },
            { path: '/admin/:username', component: AdminPage },
        ]
    })
})