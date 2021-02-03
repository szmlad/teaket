function matchManifestation(manif, searchData) {
    const matchName = (name, pat) => name.toLowerCase().includes(pat.toLowerCase())
    const matchLocation = (loc, pat) => loc.toLowerCase().includes(pat.toLowerCase())
    const matchType = (type, pat) => pat === '' ? true : type === pat
    const matchDates = (date, startDate, endDate) => {
        date = date.startOf('day')
        startDate = startDate ? moment(startDate).startOf('day') : moment(0)
        endDate = endDate ? moment(endDate).startOf('day') : moment('2200-01-01')

        return date.isSameOrAfter(startDate) && date.isSameOrBefore(endDate)
    }

    if (!matchName(manif.name, searchData.name)) return false

    const addr = manif.location.address
    const location = `${addr.street} ${addr.city} ${addr.zipCode}`
    if (!matchLocation(location, searchData.location)) return false

    if (!matchType(manif.type, searchData.type)) return false

    return matchDates(manif.time, searchData.startDate, searchData.endDate)
}

function comparisonCompose(...fns) {
    return (x, y) => {
        if (fns.length === 0) return 0

        const res = fns[0](x, y)
        if (res === 0) return comparisonCompose(fns.slice(1))
        else return res
    }
}

function manifestationSort(by, dir) {
    const compare = by => {
        switch (by) {
            case 'name': {
                return (m, n) => m.name < n.name ? -1 : m.name > n.name ? 1 : 0
            }
            case 'location': {
                return comparisonCompose(
                    (m, n) =>
                        m.location.address.zipCode - n.location.address.zipCode,
                    (m, n) =>
                        m.location.address.city < n.location.address.city ?
                            -1 :
                            m.location.address.city > n.location.address.city ?
                                1 :
                                0,
                    (m, n) =>
                        m.location.address.street < n.location.address.street ?
                            -1 :
                            m.location.address.street > n.location.address.street ?
                                1 :
                                0
                )
            }
            case 'date': {
                return (m, n) => m.time.isBefore(n.time) ? -1 : m.time.isAfter(n.time) ? 1 : 0
            }
            case 'rating': {
                return (m, n) => m.rating - n.rating
            }
            default: {
                console.log(`unknown option ${by}`)
                return (m, n) => 0
            }
        }
    }
    if (by === '' || dir === '') return xs => {}

    const sortingBy = compare(by)
    const sortingDir = dir === 'desc' ? -1 : 1
    return xs => xs.sort((m, n) => sortingDir * sortingBy(m, n))
}

const router = new VueRouter({
    mode: "hash",
    routes: [
        {
            path: '/',
            component: MainPage,
            props: true
        },
    ]
})

const eventBus = new Vue()

const app = new Vue({ router: router, el: '#app' })