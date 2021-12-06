function handleCreateProjectClick(e) {
    var name = document.getElementById("create-input").value

    if (name == '') {
        alert('Please input a name into the box below \'Create Project\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/G3/project/'

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
                    jsonResponse["isArchived"] = false // simulate actually returning a project object with an isArchived value
                    renderProject(jsonResponse)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function handleLoadProjectClick(e) {
    var name = document.getElementById("load-input").value

    if (name == '') {
        alert('Please input a name into the box below \'Load Project\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/G3/project/'

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
                    renderProject(jsonResponse)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function renderProject(response) {
    var name = response["name"]
    var isArchived = response["isArchived"]

    console.log("rendering project with name: " + name)

    document.getElementById("create-header").innerHTML = "Project " + name
    document.getElementById("load-header").innerHTML = (isArchived ? "" : "Not ") + "Archived"

    var hiddenElements = document.getElementsByClassName("hidden")
    var len = hiddenElements.length
    for (var index = 0; index < len; len--) {
        hiddenElements[index].setAttribute("class", "shown") // hiddenElements shrinks by one element every time this is called
    }

    document.getElementById("create-project-form").setAttribute("class", "hidden")
    document.getElementById("load-project-form").setAttribute("class", "hidden")
}

function renderLandingPage() {
    document.getElementById("create-header").innerHTML = "Create Project"
    document.getElementById("load-header").innerHTML = "Load Project"

    document.getElementById("create-project-form").setAttribute("class", "")
    document.getElementById("load-project-form").setAttribute("class", "")

    var shownElements = document.getElementsByClassName("shown")
    var len = shownElements.length
    for (var index = 0; index < len; len--) {
        shownElements[index].setAttribute("class", "hidden") // hiddenElements shrinks by one element every time this is called
    }

    document.getElementById("landing-btn").setAttribute("class", "hidden")
    document.getElementById("task-view").setAttribute("class", "hidden")
}