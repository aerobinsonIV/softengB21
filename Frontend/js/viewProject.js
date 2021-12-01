function handleCreateProjectClick(e) {
    var form = document.createProjectForm
    var name = form.name.value

    // var url = "https://99.99.99.99:80/project/" + name + "/"
    var url =
        'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/Alpha/teammate/Test/Jimmy'

    var xhr = new XMLHttpRequest()
    xhr.open('POST', url, true)

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
