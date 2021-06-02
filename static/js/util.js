async function get(url) {
    return fetch(url);
}

async function post(url, payload) {
    return fetch(url, {
       method: 'POST',
       body: JSON.stringify(payload),
       headers: {
           'Content-Type': 'application/json'
       }
    });
}