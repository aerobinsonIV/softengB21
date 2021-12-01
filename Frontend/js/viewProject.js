function handleViewProjectRequest(e) {
    var pathVars = location.pathname.split("/")
    var name = pathVars[pathVars.length - 1]

    if (name == '') // in case url path ends with '/'
        name = pathVars[pathVars.length - 2]

    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/Alpha/project/'

    var xhr = new XMLHttpRequest()
    xhr.open('GET', url, true)

    xhr.send()

    xhr.onloadend = function () {
        console.log('done')

        if (xhr.readyState == XMLHttpRequest.DONE) {
            alert('Got ' + xhr.responseText + ' from AWS')
        } else {
            alert('Something went wrong')
        }
    }
}
