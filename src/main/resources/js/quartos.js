let usuarioLogado = sessionStorage.getItem("usuarioLogado");


if (!usuarioLogado) {
    window.location.href = "login.html"; // impede acesso sem login
}

const API_URL = "http://localhost:8080/quartos";

// Listar todos os quartos
function listarQuartos() {
  fetch(API_URL)
    .then(res => res.json())
    .then(data => {
      const tabela = document.getElementById("quartosTabela");
      tabela.innerHTML = "";
      data.forEach(q => {
        const row = `
          <tr>
            <td>${q.id}</td>
            <td>${q.numero}</td>
            <td>${q.tipo}</td>
            <td>${q.quantidadeCamas}</td>
            <td>${q.tipoCamas}</td>
            <td>${q.tv ? "Sim" : "Não"}</td>
            <td>${q.ar ? "Sim" : "Não"}</td>
            <td>${q.ventilador ? "Sim" : "Não"}</td>
            <td>${q.frigobar ? "Sim" : "Não"}</td>
            <td>${q.banheiroPrivativo ? "Sim" : "Não"}</td>
            
            <td>
              <button onclick="editarQuarto(${q.id})">Editar</button>
              <button onclick="excluirQuarto(${q.id})">Excluir</button>
            </td>
          </tr>
        `;
        tabela.innerHTML += row;
      });
    })
    .catch(err => console.error("Erro ao carregar quartos:", err));
}

// Salvar ou atualizar quarto
document.getElementById("formQuarto").addEventListener("submit", e => {
  e.preventDefault();
  const id = document.getElementById("quartoId").value;
  const quarto = {
    numero: document.getElementById("numero").value,
    tipo: document.getElementById("tipo").value,
    quantidadeCamas: document.getElementById("quantidadeCamas").value,
    tipoCamas: document.getElementById("tipoCamas").value,
    tv: document.getElementById("tv").checked,
    ar: document.getElementById("ar").checked,
    ventilador: document.getElementById("ventilador").checked,
    frigobar: document.getElementById("frigobar").checked,
    banheiroPrivativo: document.getElementById("banheiroPrivativo").checked,
    status: "Livre"
   
  };

  const metodo = id ? "PUT" : "POST";
  const url = id ? `${API_URL}/${id}` : API_URL;

  fetch(url, {
    method: metodo,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(quarto)
  })
    .then(res => {
      if (res.ok) {
        listarQuartos();
        e.target.reset();
        document.getElementById("quartoId").value = "";
      } else {
        alert("Erro ao salvar quarto.");
      }
    })
    .catch(err => console.error("Erro ao salvar quarto:", err));
});

// Excluir quarto
function excluirQuarto(id) {
  fetch(`${API_URL}/${id}`, { method: "DELETE" })
    .then(res => {
      if (res.ok) listarQuartos();
    })
    .catch(err => console.error("Erro ao excluir quarto:", err));
}

// Editar quarto
function editarQuarto(id) {
  fetch(`${API_URL}/${id}`)
    .then(res => res.json())
    .then(q => {
      document.getElementById("quartoId").value = q.id;
      document.getElementById("numero").value = q.numero;
      document.getElementById("tipo").value = q.tipo;
      document.getElementById("quantidadeCamas").value = q.quantidadeCamas;
      document.getElementById("tipoCamas").value = q.tipoCamas;
      document.getElementById("tv").checked = q.tv;
      document.getElementById("ar").checked = q.ar;
      document.getElementById("ventilador").checked = q.ventilador;
      document.getElementById("frigobar").checked = q.frigobar;
      document.getElementById("banheiroPrivativo").checked = q.banheiroPrivativo;
     
    })
    .catch(err => console.error("Erro ao carregar quarto para edição:", err));
}

// Inicializa a lista
listarQuartos();
