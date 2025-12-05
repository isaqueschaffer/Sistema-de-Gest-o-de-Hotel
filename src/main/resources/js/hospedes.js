

const API_URL = "http://localhost:8080/hospedes";


function listarHospedes() {
  fetch(API_URL)
    .then(res => res.json())
    .then(data => {
      const tabela = document.getElementById("hospedesTabela");
      tabela.innerHTML = "";
      data.forEach(hospede => {
        const row = `
    <tr>
        <td>${hospede.id}</td>
        <td>${hospede.nome}</td>
        <td>${hospede.cpfCnpj}</td>
        <td>${hospede.telefone}</td>
        <td>${hospede.endereco}</td>
        <td>
            <button onclick="editarHospede(${hospede.id})">Editar</button>
            <button onclick="excluirHospede(${hospede.id})">Excluir</button>
        </td>
    </tr>
`;

        tabela.innerHTML += row;
      });
    })
    .catch(err => console.error("Erro ao carregar hóspedes:", err));
}

document.getElementById("formHospede").addEventListener("submit", e => {
  e.preventDefault();
 const hospede = {
  nome: document.getElementById("nome").value,
  cpfCnpj: document.getElementById("cpfCnpj").value,
  telefone: document.getElementById("telefone").value,
  endereco: document.getElementById("endereco").value
};


  fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(hospede),
  }).then(res => {
    if (res.ok) {
      listarHospedes();
      e.target.reset();
    } else {
      alert("Erro ao salvar hóspede.");
    }
  });
});

function excluirHospede(id) {
  fetch(`${API_URL}/${id}`, { method: "DELETE" })
    .then(res => {
      if (res.ok) listarHospedes();
    })
    .catch(err => console.error("Erro ao excluir hóspede:", err));
}

function editarHospede(id) {
  fetch(`${API_URL}/${id}`)
    .then(res => res.json())
    .then(h => {

      document.getElementById("editId").value = h.id;
      document.getElementById("editNome").value = h.nome;
      document.getElementById("editCpfCnpj").value = h.cpfCnpj;
      document.getElementById("editTelefone").value = h.telefone;
      document.getElementById("editEndereco").value = h.endereco;

      document.getElementById("modal").style.display = "flex";
    });
}

function fecharModal() {
  document.getElementById("modal").style.display = "none";
}

function salvarEdicao() {

  const hospede = {
    id: document.getElementById("editId").value,
    nome: document.getElementById("editNome").value,
    cpfCnpj: document.getElementById("editCpfCnpj").value,
    telefone: document.getElementById("editTelefone").value,
    endereco: document.getElementById("editEndereco").value
  };

  fetch(`${API_URL}/${hospede.id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(hospede)
  })
    .then(res => {
      if (res.ok) {
        fecharModal();
        listarHospedes();
      } else {
        alert("Erro ao editar hóspede.");
      }
    });
}


listarHospedes();
