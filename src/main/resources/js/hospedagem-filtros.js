const form = document.getElementById("filtrosForm");
const tabela = document.getElementById("tabelaResultados");

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const dataInicio = document.getElementById("dataInicio").value;
  const dataFim = document.getElementById("dataFim").value;
  const status = document.getElementById("status").value;
  const cliente = document.getElementById("cliente").value;
  const quarto = document.getElementById("quarto").value;

  try {
    const response = await fetch("http://localhost:8080/hospedagens");
    let hospedagens = await response.json();

    // Filtros
    if (dataInicio) {
      const inicio = new Date(dataInicio);
      inicio.setHours(0,0,0,0);
      hospedagens = hospedagens.filter(h => {
        const entrada = new Date(h.dataEntrada);
        entrada.setHours(0,0,0,0);
        return entrada >= inicio;
      });
    }

    if (dataFim) {
      const fim = new Date(dataFim);
      fim.setHours(23,59,59,999);
      hospedagens = hospedagens.filter(h => {
        if (!h.dataSaida) return false;
        const saida = new Date(h.dataSaida);
        return saida <= fim;
      });
    }

    if (status) {
      hospedagens = hospedagens.filter(h => h.status?.toLowerCase() === status.toLowerCase());
    }

    if (cliente) {
      hospedagens = hospedagens.filter(h => h.hospede?.nome?.toLowerCase().includes(cliente.toLowerCase()));
    }

    if (quarto) {
      hospedagens = hospedagens.filter(h => h.quarto?.numero == Number(quarto));
    }

    // Montar tabela
    let rows = "";
    hospedagens.forEach(h => {
      let bgColor = "";
      if (h.status === "Pendente") bgColor = "red";
      else if (h.status === "Paga") bgColor = "green";
      else if (h.status === "Finalizada") bgColor = "blue";

      rows += `
        <tr>
          <td>${h.id}</td>
          <td>${h.hospede?.nome || "-"}</td>
          <td>${h.quarto?.numero || "-"}</td>
          <td>${h.dataEntrada ? new Date(h.dataEntrada).toLocaleDateString("pt-BR") : "-"}</td>
          <td>${h.dataSaida ? new Date(h.dataSaida).toLocaleDateString("pt-BR") : "-"}</td>
          <td style="background-color:${bgColor}; color:white;">${h.status}</td>
          <td>R$ ${h.valorTotal ? h.valorTotal.toFixed(2) : "0,00"}</td>
        </tr>
      `;
    });

    tabela.innerHTML = rows;

  } catch (error) {
    console.error("Erro ao buscar hospedagens:", error);
  }
});
