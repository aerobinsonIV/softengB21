function loadProjects() {
    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/G3/project/'

    var xhr = new XMLHttpRequest()
    xhr.open('GET', url, true)

    xhr.send()

    xhr.onloadend = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)
            var jsonResponse = JSON.parse(xhr.responseText)

            if (jsonResponse["message"] != undefined) { // message is from the ErrorResponse schema
                alert('Could not load projects: ' + jsonResponse["message"])
            } else {
                renderProjects(jsonResponse["projects"])
            }
        } else {
            alert('Something went wrong.')
        }
    }
}

function renderProjects(projects) {
    var innerHTMLStr = "";

    for (var index in projects) {
        innerHTMLStr += "<div id=" + projects[index].name + " class=\"padded\">"
        innerHTMLStr += projects[index].name + " : " + (projects[index].isArchived ? "" : "Not ") + "Archived"
        innerHTMLStr += "</div>"
    }

    document.getElementById("projects-list").innerHTML = innerHTMLStr
}

function deleteProject() {
    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/G3/project/delete/'

    var name = document.getElementById("delete-input").value

    var xhr = new XMLHttpRequest()
    xhr.open('POST', url + name, true)

    xhr.send()

    xhr.onloadend = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)
            var jsonResponse = JSON.parse(xhr.responseText)

            if (jsonResponse["message"] != undefined) { // message is from the ErrorResponse schema
                alert('Could not delete project ' + name + ': ' + jsonResponse["message"])
            } else {
                loadProjects()
            }
        } else {
            alert('Something went wrong.')
        }
    }
}