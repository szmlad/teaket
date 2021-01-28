Vue.component('manif-search', {
    data: function () {
        return {
            allTypes: [],
            name: '',
            location: '',
            type: '',
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
            <label for="search-manif-name">Naziv</label>
            <input v-model="name" type="text" class="form-control form-control-sm" id="search-manif-name">
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-manif-loc">Lokacija</label>
            <input v-model="location" type="text" class="form-control form-control-sm" id="search-manif-loc">
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-manif-start-time">Od</label>
            <vuejsDatepicker :calendar-button="true" :calendar-icon="'fa fa-calendar'" :bootstrap-styling="true" :format="dateFormatter" v-model="startDate"></vuejsDatepicker>
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-manif-end-time">Do</label>
            <vuejsDatepicker :calendar-button="true" :calendar-icon="'fa fa-calendar'" :bootstrap-styling="true" :format="dateFormatter" v-model="endDate"></vuejsDatepicker>
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-manif-type">Tip manifestacije</label>
            <select v-model="type" class="form-control" id="search-manif-type">
                <option value="" selected>Sve</option>
                <option v-for="t in allTypes" :key="t" :value="t">
                    {{ t }}
                </option>
            </select>
        </div>
        <div class="form-group p-2 pb-0">
            <div class="pb-2">Sortiraj po</div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-manif-sort-by" id="search-manif-sort-by-name" value="name" checked>
                <label class="form-check-label" for="search-manif-sort-by-name">Nazivu</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-manif-sort-by" id="search-manif-sort-by-location" value="location">
                <label class="form-check-label" for="search-manif-sort-by-location">Lokaciji</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-manif-sort-by" id="search-manif-sort-by-date" value="date">
                <label class="form-check-label" for="search-manif-sort-by-date">Datumu održavanja</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortBy" class="form-check-input" type="radio" name="search-manif-sort-by" id="search-manif-sort-by-rating" value="rating">
                <label class="form-check-label" for="search-manif-sort-by-rating">Oceni</label>
            </div>
        </div>
        <hr class="align-self-center m-1" style="width: 90%;">
        <div class="form-group p-2 pb-0 pt-0">
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortDirection" class="form-check-input" type="radio" name="search-manif-sort-direction" id="search-manif-sort-direction-descending" value="desc" checked>
                <label class="form-check-label" for="search-manif-sort-direction-descending">Opadajuće</label>
            </div>
            <div class="form-check">
                <input v-on:click="requireSortParams" v-model="sortDirection" class="form-check-input" type="radio" name="search-manif-sort-direction" id="search-manif-sort-direction-ascending" value="asc" checked>
                <label class="form-check-label" for="search-manif-sort-direction-ascending">Rastuće</label>
            </div>
        </div>
        <div class="p-2 mt-2">
            <a v-on:click="searchManifestations" class="btn btn-dark" href="#" style="width: 100%;">Pretraga</a>
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
        searchManifestations: function (event) {
            const searchData = {
                name: this.name,
                location: this.location,
                type: this.type,
                sortBy: this.sortBy,
                sortDirection: this.sortDirection,
                startDate: this.startDate,
                endDate: this.endDate
            }
            eventBus.$emit('search-manifs', searchData)
        },
        clearSearchFields: function (event) {
            let vm = this
            Vue.set(vm, 'name', '')
            Vue.set(vm, 'location', '')
            Vue.set(vm, 'type', '')
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