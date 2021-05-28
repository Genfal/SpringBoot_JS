$(async function () {
    await fillTables();
    await fillNewUserRoles();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-type': 'application/json',
        'Referer': null
    },

    getAllUsers: async () => await fetch('/api/').then(res => res.json()),
    getUser: (id) => fetch('/api/' + id).then(res => res.json()),
    addUser: (user) => fetch('/api/', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }).then(res => res.json()),
    editUser: (id, user) => fetch('/api/' + id, {
        method: 'PATCH',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    deleteUser: (id) => fetch('/api/' + id, {method: 'DELETE', headers: userFetchService.head}),
    getCurrentUser: async () => await fetch('/api/getCurrentUser').then(res => res.json()),
    getRoles: async () => await fetch('/api/getRoles').then(res => res.json()),
    getRoleByName: (role) => fetch('/api/getRoleByRoleName/' + role).then(res => res.json())
}

let roles;

async function fillNewUserRoles() {
    roles = await userFetchService.getRoles()
    for (let role of roles) {
        $('#newUserRole').append('<option value="' + role.role + '">' + role.name + '</option>')
    }
}

async function fillTables() {
    const UserNameHeader = $('#currentUserName')
    const UserRolesHeader = $('#currentUserRole')
    const adminButton = $('#adminButton')
    const userTable = $('#userTableBody')
    const currentUser = await userFetchService.getCurrentUser()
    let currentUserRoles = ""
    let roles = currentUser.roles

    for (let role of roles) {
        currentUserRoles = currentUserRoles + " " + role.name
    }

    UserNameHeader.text(currentUser.email)
    UserRolesHeader.text(currentUserRoles)

    for (let role of roles) {
        if (role.role === "ROLE_ADMIN") {
            adminButton.show()
            fillAdminTable()
            break
        } else {
            adminButton.hide()
        }
    }

    userTable.append('<tr>')
    userTable.append('<td>' + currentUser.id + '</td>')
    userTable.append('<td>' + currentUser.name + '</td>')
    userTable.append('<td>' + currentUser.lastName + '</td>')
    userTable.append('<td>' + currentUser.age + '</td>')
    userTable.append('<td>' + currentUser.email + '</td>')
    userTable.append('<td>' + currentUserRoles + '</td>')
    userTable.append('</tr>')
}

function fillAdminTable() {
    const adminTable = $('#adminTableBody')
    userFetchService
        .getAllUsers()
        .then(users => users.forEach(user => {
            appendOneUser(adminTable, user)
        }))
}

function appendOneUser(table, user) {

    let userRoles = ""
    for (let role of user.roles) {
        userRoles = userRoles + role.name + " "
    }
    table.append('<tr>')
    table.append('<td>' + user.id + '</td>')
    table.append('<td>' + user.name + '</td>')
    table.append('<td>' + user.lastName + '</td>')
    table.append('<td>' + user.age + '</td>')
    table.append('<td>' + user.email + '</td>')
    table.append('<td>' + userRoles + '</td>')
    table.append('<td> <button class="btn btn-info" data-id="' + user.id + '" onclick="editButton(this)" id="editButton">Edit</button> </td>')
    table.append('<td> <button class="btn btn-danger" data-id="' + user.id + '" onclick="deleteButton(this)" id="deleteButton">Delete</button> </td>')
    table.append('</tr>')
}

const modalEdit = $('#modalEdit')
const modalDelete = $('#modalDelete')

function editButton(el) {
    let id = el.dataset.id
    userFetchService
        .getUser(id)
        .then(user => fillEditModal(user))

    modalEdit.modal('show')
}

let userID;

function fillEditModal(user) {
    const editRole = $('#editRole')
    userID = user.id
    $('#editID').val(user.id)
    $('#editName').val(user.name)
    $('#editLastName').val(user.lastName)
    $('#editAge').val(user.age)
    $('#editEmail').val(user.email)
    editRole.empty()
    editRole.append('<option selected value="ROLE_USER">Пользователь</option>')
    for (let role of roles) {
        editRole.append('<option value="' + role.role + '">' + role.name + '</option>')
    }
}

async function editUser() {
    let roles = await userFetchService.getRoleByName($('#editRole').val())
    let id = $('#editID').val()
    let name = $('#editName').val()
    let lastName = $('#editLastName').val()
    let age = $('#editAge').val()
    let email = $('#editEmail').val()
    let password = $('#editPassword').val()

    let editUser = {
        name: name,
        lastName: lastName,
        age: age,
        email: email,
        password: password,
        roles: roles
    }

    console.log(editUser)

    await userFetchService.editUser(id, editUser)
    $('#adminTableBody').empty()
    fillAdminTable()
    modalEdit.modal('hide')
}

function deleteButton(el) {
    let id = el.dataset.id
    userFetchService
        .getUser(id)
        .then(user => fillDeleteModal(user))

    modalDelete.modal('show')
}

function fillDeleteModal(user) {
    userID = user.id
    $('#deleteID').val(user.id)
    $('#deleteName').val(user.name)
    $('#deleteLastName').val(user.lastName)
    $('#deleteAge').val(user.age)
    $('#deleteEmail').val(user.email)
}

function deleteUser() {
    userFetchService
        .deleteUser(userID)
        .then(modalDelete.modal('hide'))
        .then(() => $('#adminTableBody').empty())
        .then(() => fillAdminTable())
}


async function addUser() {
    let roles = await userFetchService.getRoleByName($('#newUserRole').val())
    let name = $('#newUserName').val()
    let lastName = $('#newUserLastName').val()
    let age = $('#newUserAge').val()
    let email = $('#newUserEmail').val()
    let password = $('#newUserPassword').val()

    let addUser = {
        name: name,
        lastName: lastName,
        age: age,
        email: email,
        password: password,
        roles: roles
    }

    let user = await userFetchService.addUser(addUser)
    appendOneUser($('#adminTableBody'), user)
    ($('#tableNav').tab('show'))
}
