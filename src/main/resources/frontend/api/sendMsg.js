function sendMsg(data) {
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        data
    })
}