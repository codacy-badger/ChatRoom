$(function () {
    if (error !== "") {
        failNoty(error);
    }

    if (getChats !== undefined) {
        getChats();
    }
});

let failedNote;

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "top",
        timeout: 1000
    }).show();
}

function failNoty(text) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + text,
        type: "error",
        layout: "top"
    }).show();
}

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}