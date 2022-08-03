let btnSelectCheckBoxes = document.getElementById("all_checkbox")
let btnUndoCheckBoxes = document.getElementById("undo_all_checkbox")
let table = document.getElementsByTagName("table");
let checkBoxes = document.querySelectorAll(`[id^="box"]`);
let btnBlock = document.getElementById("block")
let btnDelete = document.getElementById("delete")
let btnUnblock = document.getElementById("unblock")

let selectAll = (event) => {
    for (const checkBox of checkBoxes) {
        checkBox.checked = true;
    }
}

let undoSelect = (event) => {
    for (const checkBox of checkBoxes) {
        checkBox.checked = false;
    }
}

let blockUsers = (event) => {
    let req = new XMLHttpRequest();
    req.open('POST', "/blockUsers");
    req.setRequestHeader('Content-Type', 'application/json');
    req.send(JSON.stringify(selectedBoxes()))
    req.onload = function() {
        document.location.reload()
    }
    undoSelect();
}

let unblockUsers = (event) => {
    let req = new XMLHttpRequest();
    req.open('POST', "/unblockUsers");
    req.setRequestHeader('Content-Type', 'application/json');
    req.send(JSON.stringify(selectedBoxes()))
    req.onload = function() {
        document.location.reload()
    }
    undoSelect();
}

let deleteUsers = (event) => {
    let req = new XMLHttpRequest();
    req.open('POST', "/deleteUsers");
    req.setRequestHeader('Content-Type', 'application/json');
    req.send(JSON.stringify(selectedBoxes()))
    req.onload = function() {
        document.location.reload()
    }
    undoSelect();
}

let selectedBoxes = () => {
    let arr = [];
    for (const checkBox of checkBoxes) {
        if (checkBox.checked) {
            let index = parseInt(checkBox.id.slice(3, checkBox.id.length));
            let email = table[0].rows[index + 1].firstChild.nextSibling.nextSibling.nextSibling.textContent;
            arr.push(email);
        }
    }
    return arr
}


btnSelectCheckBoxes.addEventListener("click", selectAll, false);
btnUndoCheckBoxes.addEventListener("click", undoSelect, false);
btnBlock.addEventListener("click", blockUsers, false)
btnDelete.addEventListener("click", deleteUsers, false)
btnUnblock.addEventListener("click", unblockUsers, false)