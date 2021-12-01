function handleCreateProjectClick(e) {
    var name = document.getElementById("create-input").value

    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/Alpha/project/'

    var xhr = new XMLHttpRequest()
    xhr.open('POST', url + name, true)

    xhr.send()

    xhr.onloadend = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)
        } else {
            alert('Something went wrong')
        }
    }
}

function handleLoadProjectClick(e) {
    var name = document.getElementById("load-input").value

    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/Alpha/project/'

    var xhr = new XMLHttpRequest()
    xhr.open('GET', url + name, true)

    xhr.send()

    xhr.onloadend = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)
        } else {
            alert('Something went wrong')
        }
    }
}