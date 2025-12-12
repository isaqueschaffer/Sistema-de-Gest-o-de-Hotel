const API_QUARTOS = "http://localhost:8080/quartos";
    const API_HOSPEDAGENS = "http://localhost:8080/hospedagens";

    function carregarQuartos() {
      fetch(API_QUARTOS)
        .then(res => res.json())
        .then(quartos => {
          const livres = document.getElementById("quartosLivres");
          const ocupados = document.getElementById("quartosOcupados");
          livres.innerHTML = "";
          ocupados.innerHTML = "";

          quartos.forEach(q => {
            const div = document.createElement("div");
            div.classList.add("quarto");
            div.textContent = `${q.numero} - ${q.tipo}`;

            if (q.status.toLowerCase() === "livre") {
              div.classList.add("livre");
              livres.appendChild(div);
            } else {
              div.classList.add("ocupado");
              div.addEventListener("click", () => abrirHospedagem(q.id));
              ocupados.appendChild(div);
            }
          });
        })
        .catch(err => console.error("Erro ao carregar quartos:", err));
    }

    let hospedeIdGlobal = null;

function abrirHospedagem(quartoId) {
  fetch(`${API_HOSPEDAGENS}/quarto/${quartoId}`)
    .then(res => res.json())
    .then(h => {

      hospedeIdGlobal = h.hospede.id; // ← SALVA O ID AQUI
      Statusglob = h.status;

      document.getElementById("hospedagemId").value = h.id;
      document.getElementById("quartoId").value = h.quarto.id;
      document.getElementById("hospedeNome").value = h.hospede.nome;
      document.getElementById("dataEntrada").value = h.dataEntrada;
      document.getElementById("dataSaida").value = h.dataSaida;
      document.getElementById("tipoHospedagem").value = h.tipoHospedagem;
      document.getElementById("valorTotal").value = h.valorTotal;

      document.getElementById("modalHospedagem").style.display = "flex";
    })
    .catch(err => alert("Não foi possível carregar hospedagem deste quarto."));
}


    function salvarAtualizacao() {
  const id = document.getElementById("hospedagemId").value;
  const quartoId = document.getElementById("quartoId").value;

  const hospedagem = {
    id: id,
    quarto: { id: quartoId },
    hospede: { id: hospedeIdGlobal }, // ← você precisa guardar esse ID
    dataEntrada: document.getElementById("dataEntrada").value,
    dataSaida: document.getElementById("dataSaida").value,
    tipoHospedagem: document.getElementById("tipoHospedagem").value,
    valorTotal: parseFloat(document.getElementById("valorTotal").value),
    status: Statusglob
  };

  fetch(`${API_HOSPEDAGENS}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(hospedagem)
  })
  .then(res => {
    if (!res.ok) throw new Error("Erro ao atualizar hospedagem");
    fecharModal();
    carregarQuartos();
  })
  .catch(err => alert("Erro ao salvar: " + err.message));
}


    function fecharModal() {
      document.getElementById("modalHospedagem").style.display = "none";
    }

    document.addEventListener("DOMContentLoaded", carregarQuartos);