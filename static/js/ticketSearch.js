Vue.component('ticket-search', {
    data: function () {
        return {
            allTypes: [],
            name: '',
            location: '',
            type: '',
            manifType: '',
            sortBy: '',
            sortDirection: '',
            startDate: null,
            endDate: null
        }
    },
    template: `
<div>
    <div class="list-group list-group-flush">
        <div class="form-group p-2 pb-0">
            <label for="search-ticket-name">Naziv manifestacije</label>
            <input v-model="name" type="text" class="form-control form-control-sm" id="search-ticket-name">
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-ticket-loc">Lokacija</label>
            <input v-model="location" type="text" class="form-control form-control-sm" id="search-ticket-loc">
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-ticket-start-time">Od</label>
            <vuejsDatepicker id="search-ticket-start-time" :calendar-button="true" :calendar-icon="'ui-icon-calendar'" :input-class="'calendar-input'" :bootstrap-styling="true" :format="dateFormatter" v-model="startDate"></vuejsDatepicker>
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-ticket-end-time">Do</label>
            <vuejsDatepicker id="search-ticket-end-time" :calendar-button="true" :calendar-icon="'fa fa-calendar'" :input-class="'calendar-input'" :bootstrap-styling="true" :format="dateFormatter" v-model="endDate"></vuejsDatepicker>
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-ticket-manif-type">Tip manifestacije</label>
            <select v-model="manifType" class="form-control" id="search-ticket-manif-type">
                <option value="" selected>Sve</option>
                <option v-for="t in allTypes" :key="t" :value="t">
                    {{ t }}
                </option>
            </select>
        </div>
        <div class="form-group p-2 pb-o">
            <label for="search-ticket-type">Tip karte</label>
            <select v-model="type" class="form-control" id="search-ticket-type">
                <option value="REGULAR" selected>Regularna karta</option>
                <option value="VIP">VIP karta</option>
                <option value="FAN_PIT">Fan pit karta</option>
            </select>
        </div>
        <div class="form-group p-2 pb-0">
            <div class="pb-2">Sortiraj po</div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-ticket-sort-by" id="search-ticket-sort-by-name" value="name" checked>
                <label class="form-check-label" for="search-ticket-sort-by-name">Nazivu</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-ticket-sort-by" id="search-ticket-sort-by-location" value="location">
                <label class="form-check-label" for="search-ticket-sort-by-location">Lokaciji</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-ticket-sort-by" id="search-ticket-sort-by-date" value="date">
                <label class="form-check-label" for="search-ticket-sort-by-date">Datumu održavanja</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-ticket-sort-by" id="search-ticket-sort-by-rating" value="rating">
                <label class="form-check-label" for="search-ticket-sort-by-rating">Oceni</label>
            </div>
        </div>
        <hr class="align-self-center m-1" style="width: 90%;">
        <div class="form-group p-2 pb-0 pt-0">
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortDirection" class="form-check-input" type="radio" name="search-ticket-sort-direction" id="search-ticket-sort-direction-descending" value="desc" checked>
                <label class="form-check-label" for="search-ticket-sort-direction-descending">Opadajuće</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortDirection" class="form-check-input" type="radio" name="search-ticket-sort-direction" id="search-ticket-sort-direction-ascending" value="asc" checked>
                <label class="form-check-label" for="search-ticket-sort-direction-ascending">Rastuće</label>
            </div>
        </div>
        <div class="p-2 mt-2">
            <a v-on:click="searchTickets" class="btn btn-dark" href="#" style="width: 100%;">Pretraga</a>
        </div>
        <div class="p-2 mt-0">
            <a v-on:click="clearSearchFields" class="btn btn-light" href="#" style="width: 100%;">Očisti</a>
        </div>
    </div>
</div>    
    `,
    mounted: function () {
        eventBus.$on('send-manif-types', function (data) {
            this.allTypes = data
        }.bind(this))
    },
    methods: {
        searchTickets: function (event) {
            const searchData = {
                name: this.name,
                location: this.location,
                type: this.type,
                ticketType: this.ticketType,
                sortBy: this.sortBy,
                sortDirection: this.sortDirection,
                startDate: this.startDate,
                endDate: this.endDate
            }
            eventBus.$emit('search-tickets', searchData)
        },
        clearSearchFields: function (event) {
            let vm = this
            Vue.set(vm, 'name', '')
            Vue.set(vm, 'location', '')
            Vue.set(vm, 'type', '')
            Vue.set(vm, 'manifType', '')
            Vue.set(vm, 'sortBy', '')
            Vue.set(vm, 'sortDirection', '')
            Vue.set(vm, 'startDate', null)
            Vue.set(vm, 'endDate', null)
            this.searchManifestations(null)
        },
        dateFormatter: function (date) {
            return moment(date).format('DD.MM.yyyy')
        },
        requireSortParams: function (event) {
            if (this.sortBy === '') this.sortBy = 'name'
            if (this.sortDirection === '') this.sortDirection = 'desc'
        }
    },
    components: {
        vuejsDatepicker
    }
})