function loadProjects() {
    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/project/'

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

        var totalComplete = 0
        var totalTasks = 0

        for (var taskIndex in projects[index].tasks) {
            if (projects[index].tasks[taskIndex].markStatus == "COMPLETE") {
                totalComplete++;
            }
            totalTasks++;
        }

        var percent = (100 * totalComplete / totalTasks).toFixed(1) 
        if (totalTasks == 0) {
            percent = 100
        }

        innerHTMLStr += "<br>" + percent +"% Complete"
        innerHTMLStr += "</div>"
    }

    document.getElementById("projects-list").innerHTML = innerHTMLStr
}

function deleteProject() {
    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/project/delete/'

    var name = document.getElementById("delete-input").value

    var xhr = new XMLHttpRequest()
    xhr.open('POST', url + name, true)

    xhr.send()

    xhr.onloadend = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)
            var jsonResponse = JSON.parse(xhr.responseText)

            if (xhr.responseText == null || xhr.responseText == "null") {
                alert('Could not delete project ' + name + '.')
            } else {
                loadProjects()
            }
        } else {
            alert('Something went wrong.')
        }
    }
}

function archiveProject() {
    var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/project/archive/'

    var projectName = document.getElementById("archive-input").value

    var xhr = new XMLHttpRequest()
    xhr.open("POST", url + projectName, true)

    xhr.send()

    xhr.onloadend = () => {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText)

            var jsonResponse = JSON.parse(xhr.responseText)

            if (xhr.responseText == null || xhr.responseText == "null") {
                alert("Could not archive project " + projectName + ".")
            } else {
                loadProjects()
            }
        } else {
            alert("Something went wrong.")
        }
    }
}