Vue.component('main-sidebar', {
    data: function () {
        return {
            startTime: new Date(),
            endTime: new Date()
        }
    },
    template: `
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="bg-dark text-light d-flex justify-content-center pb-3 pt-3">
            <h1>Teaket</h1>
        </div>
        <div class="d-flex">
            <a class="btn btn-dark ms" href="#" style="border-radius: 0; width: 50%;">Registracija</a>
            <a class="btn btn-dark m-0" href="#" style="border-radius: 0; width: 50%;">Prijava</a>       
        </div>
        <manif-search></manif-search>
    </div>`
})