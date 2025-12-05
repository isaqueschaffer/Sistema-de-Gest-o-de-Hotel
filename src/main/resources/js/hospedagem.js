const API_URL = "http://localhost:8080/hospedagens";
const API_HOSPEDES = "http://localhost:8080/hospedes";
const API_QUARTOS = "http://localhost:8080/quartos";

// =========================
// Funções utilitárias
// =========================

// Carregar selects de hóspedes e quartos
function carregarSelects() {
  fetch(API_HOSPEDES)
    .then(res => res.json())
    .then(data => {
      const hospedeSelect = document.getElementById("hospedeId");
      hospedeSelect.innerHTML = '<option value="">Selecione</option>';
      data.forEach(h => {
        hospedeSelect.innerHTML += `<option value="${h.id}">${h.nome}</option>`;
      });
    });

  fetch(API_QUARTOS)
    .then(res => res.json())
    .then(data => {
      const quartoSelect = document.getElementById("quartoId");
      quartoSelect.innerHTML = '<option value="">Selecione</option>';
      data.forEach(q => {
        quartoSelect.innerHTML += `<option value="${q.id}">${q.numero} - ${q.tipo} (${q.status})</option>`;
      });
    });
}

// Listar todas as hospedagens
function listarHospedagens() {
  fetch(API_URL)
    .then(res => res.json())
    .then(data => {
      const tabela = document.getElementById("hospedagensTabela");
      tabela.innerHTML = ""; // limpa tabela
      
      data.forEach(h => {
        // Cria um <tr> vazio
        
if(h.status==="Pendente" || h.status==="Paga"){ 
  const tr = document.createElement("tr");
        // Preenche o <tr> com as células
        tr.innerHTML = `
          <td>${h.id}</td>
          <td>${h.hospede ? h.hospede.nome : ''}</td>
          <td>${h.quarto ? h.quarto.numero : ''}</td>
          <td>${h.dataEntrada ? h.dataEntrada.replace('T', ' ') : ''}</td>
          <td>${h.dataSaida ? h.dataSaida.replace('T', ' ') : ''}</td>
          <td>${h.tipoHospedagem}</td>
          <td>${h.valorTotal},00 R$</td>
          <td>${h.quarto ? h.quarto.status : ''}</td>
          <td class="stthosp">${h.status ? h.status : ''}</td>
          <td>
            <button onclick="editarHospedagem(${h.id})">Editar</button>
            <button onclick="excluirHospedagem(${h.id})">Excluir</button>
          </td>
        `;

        // Seleciona o <td> do status dentro dessa linha
        const statusTd = tr.querySelector(".stthosp");

        if (h.status === "Pendente") {
          statusTd.style.backgroundColor = "#f700044d";
          statusTd.style.color = "white";
        } else if (h.status === "Paga") {
          statusTd.style.backgroundColor = "#98f7004d";
          statusTd.style.color = "black";
        } else {
          statusTd.style.backgroundColor = ""; // cor padrão
        }

        // Adiciona a linha na tabela
        tabela.appendChild(tr);
      }
        
      });
    });
}


// =========================
// CRUD
// =========================

// Criar hospedagem (POST)
function criarHospedagem(hospedagem, status, quartoId) {
  return fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(hospedagem)
  })
    .then(res => {
      if (!res.ok) throw new Error("Erro ao criar hospedagem");
      // Atualiza status do quarto
      return fetch(API_QUARTOS + "/" + quartoId + "/status", {method: "PATCH", headers: { "Content-Type": "application/json" },body: JSON.stringify({ status: status })});
    });
}

// Atualizar hospedagem (PUT)
function atualizarHospedagem(id, hospedagem, status, quartoId) {
  return fetch(API_URL + "/" + id, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(hospedagem)
  })
    .then(res => {
      if (!res.ok) throw new Error("Erro ao atualizar hospedagem");
      // Atualiza status do quarto
      return fetch(API_QUARTOS + "/" + quartoId + "/status", {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status: status })
      });
    });
}

// Excluir hospedagem
function excluirHospedagem(id) {
  fetch(API_URL + "/" + id, { method: "DELETE" })
    .then(res => { if (res.ok) listarHospedagens(); })
    .catch(err => console.error(err));
}

// Editar hospedagem
function editarHospedagem(id) {
  fetch(API_URL + "/" + id)
    .then(res => res.json())
    .then(h => {
      document.getElementById("hospedagemId").value = h.id || "";
      document.getElementById("hospedeId").value = h.hospede.id;
      document.getElementById("quartoId").value = h.quarto.id;
      document.getElementById("dataEntrada").value = h.dataEntrada;
      document.getElementById("dataSaida").value = h.dataSaida;
      document.getElementById("tipoHospedagem").value = h.tipoHospedagem;
      document.getElementById("valorTotal").value = h.valorTotal;
      document.getElementById("status").value = h.quarto.status;
      document.getElementById("btnAtualizar").disabled = false;
      document.getElementById("Statushospedagem").value= h.status; 
    });
    document.getElementById("btnAtualizar").style.display = "inline-block"; // ou "block"
    document.getElementById("btnSalvar").style.display = "none";

}

// =========================
// Formulário - Botões
// =========================

// === Botão SALVAR ===
function salvarHospedagem() {
  const quartoSelect = document.getElementById("quartoId");
  const quartoTexto = quartoSelect.options[quartoSelect.selectedIndex].text;
  const status = document.getElementById("status").value;
  const quartoId = quartoSelect.value;

  // Validação: não permitir hospedar quarto ocupado
  if (quartoTexto.includes("Ocupado")) {
    alert("Este quarto já está ocupado! Escolha outro quarto.");
    return;
  }

  const hospedagem = {
    hospede: { id: document.getElementById("hospedeId").value },
    quarto: { id: quartoId },
    dataEntrada: document.getElementById("dataEntrada").value,
    dataSaida: document.getElementById("dataSaida").value,
    tipoHospedagem: document.getElementById("tipoHospedagem").value,
    valorTotal: parseFloat(document.getElementById("valorTotal").value),
    status: document.getElementById("Statushospedagem").value
  };

  criarHospedagem(hospedagem, status, quartoId)
    .then(res => {
      if (res.ok) {
        listarHospedagens();
        carregarSelects();
        document.getElementById("formHospedagem").reset();
      }
    })
    .catch(err => console.error(err));
}

// === Botão ATUALIZAR ===
function atualizarDadosHospedagem() {
  const id = document.getElementById("hospedagemId").value;
  if (!id) {
    alert("Selecione uma hospedagem para atualizar.");
    return;
  }

  const quartoId = document.getElementById("quartoId").value;
  const status = document.getElementById("status").value;
  const statushospedagem=document.getElementById("Statushospedagem").value;

  const valorTotal = document.getElementById("valorTotal").value;

if (valorTotal === "" || valorTotal === null) {
    alert("Preencha o campo Valor Total.");
    return;
}


if(status==="Ocupado" && statushospedagem ==="Finalizada" ){
 alert("Precisa deixar o status do quarto para 'Livre' ao finalizar a hospedagem!");

}else{
   const hospedagem = {
    hospede: { id: document.getElementById("hospedeId").value },
    quarto: { id: quartoId },
    dataEntrada: document.getElementById("dataEntrada").value,
    dataSaida: document.getElementById("dataSaida").value,
    tipoHospedagem: document.getElementById("tipoHospedagem").value,
    valorTotal: parseFloat(document.getElementById("valorTotal").value)
    ,status: document.getElementById("Statushospedagem").value
  };

  atualizarHospedagem(id, hospedagem, status, quartoId)
  
    .then(res => {
      if (res.ok) {
        listarHospedagens();
        carregarSelects();
        document.getElementById("formHospedagem").reset();
        document.getElementById("hospedagemId").value = "";
        document.getElementById("btnAtualizar").disabled = true;
      }
    })
    .catch(err => console.error(err));
     alert("hospedagem atualizada com sucesso!");
    document.getElementById("btnAtualizar").style.display = "none";
    document.getElementById("btnSalvar").style.display = "inline-block";

}



 
}

// =========================
// Inicializar
// =========================
function iniciarPagina() {
  carregarSelects();
  listarHospedagens();
  document.getElementById("btnAtualizar").disabled = true;

  document.getElementById("btnSalvar").addEventListener("click", salvarHospedagem);
  document.getElementById("btnAtualizar").addEventListener("click", atualizarDadosHospedagem);
}

document.addEventListener("DOMContentLoaded", iniciarPagina);
