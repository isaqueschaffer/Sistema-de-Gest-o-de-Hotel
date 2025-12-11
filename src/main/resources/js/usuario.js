const API = "http://localhost:8080/usuarios";

/* LISTAR TODOS */
async function carregarUsuarios() {
    const tbody = document.querySelector("tbody");

    const res = await fetch(API);
    const dados = await res.json();

    tbody.innerHTML = "";

    dados.forEach(u => {
        tbody.innerHTML += `
            <tr>
                <td>${u.id}</td>
                <td>${u.nome}</td>
                <td>${u.login}</td>
                <td>${u.perfil}</td>
                <td>${u.turno}</td>
                <td>
                    <a href="editar.html?id=${u.id}" class="btn">Editar</a>
                    <button class="btn-danger" onclick="deletar(${u.id})">Excluir</button>
                </td>
            </tr>
        `;
    });
}

/* CADASTRAR */
async function criarUsuario(event) {
    event.preventDefault();

    const data = {
        nome: document.querySelector("#nome").value,
        login: document.querySelector("#login").value,
        senha: document.querySelector("#senha").value,
        perfil: document.querySelector("#perfil").value,
        cpf: document.querySelector("#cpf").value,
        turno: document.querySelector("#turno").value
    };

    const res = await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        alert("Usuário criado com sucesso!");
        window.location.href = "index.html";
    }
}

/* BUSCAR POR ID */
async function carregarEdicao() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    const res = await fetch(`${API}/${id}`);
    const u = await res.json();

    document.querySelector("#id").value = u.id;
    document.querySelector("#nome").value = u.nome;
    document.querySelector("#login").value = u.login;
    document.querySelector("#perfil").value = u.perfil;
    document.querySelector("#cpf").value = u.cpf;
    document.querySelector("#turno").value = u.turno;
}

/* ATUALIZAR */
async function atualizarUsuario(event) {
    event.preventDefault();

    const id = document.querySelector("#id").value;

    const data = {
        nome: document.querySelector("#nome").value,
        login: document.querySelector("#login").value,
        perfil: document.querySelector("#perfil").value,
        cpf: document.querySelector("#cpf").value,
        turno: document.querySelector("#turno").value
    };

    const res = await fetch(`${API}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        alert("Usuário atualizado!");
        window.location.href = "index.html";
    }
}

/* DELETAR */
async function deletar(id) {
    if (!confirm("Deseja realmente excluir este usuário?")) return;

    const res = await fetch(`${API}/${id}`, {
        method: "DELETE"
    });

    if (res.ok) {
        alert("Usuário removido!");
        carregarUsuarios();
    }
}
