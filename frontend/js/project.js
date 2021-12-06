function addTask() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substr(projectHeader.indexOf(" ") + 1)
    var taskName = document.getElementById("add-task-input").value

    if (taskName == '') {
        alert('Please input a name into the box below \'Add Task\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/G3/task/' + projectName + "/"

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url + taskName, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)
                var jsonResponse = JSON.parse(xhr.responseText)

                if (jsonResponse["name"] == undefined) {
                    alert('Project with name ' + projectName + ' does not exist.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function addTeammate() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substr(projectHeader.indexOf(" ") + 1)
    var teammateName = document.getElementById("add-teammate-input").value

    if (teammateName == '') {
        alert('Please input a name into the box below \'Add Teammate\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/G3/teammate/' + projectName + "/"

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url + teammateName, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Teammate with name ' + teammateName + ' already exists.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}