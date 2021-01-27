Vue.component('manif-search', {
    data: function () {
        return {
            allTypes: [],
            name: '',
            location: '',
            type: '',
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
            <input v-model="startDate" type="date" id="search-manif-start-time">
        </div>
        <div class="form-group p-2 pb-0">
            <label for="search-manif-end-time">Do</label>
            <input v-model="endDate" type="date" id="search-manif-end-time">
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
        <div class="p-2 mt-2">
            <a v-on:click="searchManifestations" class="btn btn-dark" href="#" style="width: 100%;">Pretraga</a>
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
                startDate: this.startDate,
                endDate: this.endDate
            }
            eventBus.$emit('search-manifs', searchData)
        }
    }
})