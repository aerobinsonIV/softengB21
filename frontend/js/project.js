function checkTaskID(id) {
    for (var taskIndex in tasksCache) {
        if (tasksCache[taskIndex].id == id) {
            return true;
        }
    }

    return false;
}

function addTask() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var taskName = document.getElementById("add-task-input").value

    if (taskName == '') {
        alert('Please input a name into the box below \'Add Task\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/task/' + projectName + "/"

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url + taskName, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)
                var jsonResponse = JSON.parse(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Project with name ' + projectName + ' does not exist. Make sure the project is not archived.')
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
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var teammateName = document.getElementById("add-teammate-input").value

    if (teammateName == '') {
        alert('Please input a name into the box below \'Add Teammate To Project\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/teammate/' + projectName + "/"

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url + teammateName, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Teammate with name ' + teammateName + ' already exists. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function deleteTeammate() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var teammateName = document.getElementById("remove-teammate-input").value

    if (teammateName == '') {
        alert('Please input a name into the box below \'Remove Teammate From Project\'')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/teammate/delete/' + projectName + "/"

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url + teammateName, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Teammate with name ' + teammateName + ' does not exists. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function assignTeammate() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var taskID = document.getElementById("assign-taskid-input").value
    var teammateName = document.getElementById("assign-teammate-input").value

    if (taskID == '' || teammateName == '') {
        alert('Please input a task ID and a teammate name into the box below \'Assign Teammate To Task\'')
    } else if (!checkTaskID(taskID)) {
        alert('The task with that id does not exist.')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/task/assign/' + projectName + "/" + taskID + "/" + teammateName

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Desired Task and/or Teammate do not exists. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function unassignTeammate() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var taskID = document.getElementById("unassign-taskid-input").value
    var teammateName = document.getElementById("unassign-teammate-input").value

    if (taskID == '' || teammateName == '') {
        alert('Please input a task ID and a teammate name into the box below \'Assign Teammate To Task\'')
    } else if (!checkTaskID(taskID)) {
        alert('The task with that id does not exist.')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/task/unassign/' + projectName + "/" + taskID + "/" + teammateName

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Desired Task and/or Teammate do not exists. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function markTask() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var taskID = document.getElementById("mark-taskid-input").value
    var markStatus = document.getElementById("mark-status-input").value

    if (taskID == '' || markStatus == '') {
        alert('Please input a task ID and a teammate name into the box below \'Mark Task\'')
    } else if (!checkTaskID(taskID)) {
        alert('The task with that id does not exist.')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/task/mark/' + projectName + "/" + taskID + "/" + markStatus

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                var jsonResponse = JSON.parse(xhr.responseText)

                if (jsonResponse["statusCode"] == 400) {
                    alert('Desired Task does not exist or status was invalid. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function renameTask() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var taskID = document.getElementById("rename-taskid-input").value
    var newName = document.getElementById("rename-name-input").value

    if (taskID == '' || newName == '') {
        alert('Please input a task ID and a teammate name into the box below \'Mark Task\'')
    } else if (!checkTaskID(taskID)) {
        alert('The task with that id does not exist.')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/task/rename/' + projectName + "/" + taskID + "/" + newName

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                if (xhr.responseText == null || xhr.responseText == "null") {
                    alert('Desired Task does not exist or status was invalid. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}

function decomposeTask() {
    var projectHeader = document.getElementById("create-header").innerHTML
    var projectName = projectHeader.substring(projectHeader.indexOf(" ") + 1)
    var parentID = document.getElementById("add-taskid-input").value
    var childName = document.getElementById("add-childname-input").value

    if (parentID == '' || childName == '') {
        alert('Please input a parent task ID and a teammate name into the box below \'Add Subtask To Task\'')
    } else if (!checkTaskID(parentID)) {
        alert('The task with that id does not exist.')
    } else {
        var url = 'https://5odsqadon5.execute-api.us-east-1.amazonaws.com/GFinal/task/decompose/' + parentID + "/" + childName

        var xhr = new XMLHttpRequest()
        xhr.open('POST', url, true)

        xhr.send()

        xhr.onloadend = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                console.log(xhr.responseText)

                var jsonResponse = JSON.parse(xhr.responseText)

                if (jsonResponse["statusCode"] == 400) {
                    alert('Desired Task does not exist. Make sure the project is not archived.')
                } else {
                    loadProjectWithName(projectName)
                }
            } else {
                alert('Something went wrong')
            }
        }
    }
}
