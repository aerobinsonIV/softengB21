function handleCreateProjectClick(e) {
    var name = document.getElementById("create-input").value

    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/Alpha/project/'

    var xhr = new XMLHttpRequest()
    xhr.open('POST', url + name, true)

    xhr.send()

    xhr.onloadend = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)
            var jsonResponse = JSON.parse(xhr.responseText)

            if (jsonResponse["error"] == "Failed to create project.") {
                alert('Project with name ' + jsonResponse["name"] + ' already exists.')
            } else {
                renderProject(jsonResponse["name"])
            }
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
            var jsonResponse = JSON.parse(xhr.responseText)

            if (jsonResponse["name"] == "Failed") {
                alert('Project with name ' + name + ' does not exist.')
            } else {
                renderProject(jsonResponse["name"])
            }
        } else {
            alert('Something went wrong')
        }
    }
}

function renderProject(name) {
    console.log("rendering project with name: " + name)

    document.getElementById("create-header").innerHTML = "Project " + name

    document.getElementById("create-project-form").setAttribute("class", "hidden")
    document.getElementById("load-header").setAttribute("class", "hidden")
    document.getElementById("load-project-form").setAttribute("class", "hidden")

    document.getElementById("new-task-btn").setAttribute("class", "padded")
    document.getElementById("task-view").setAttribute("class", "padded")
}