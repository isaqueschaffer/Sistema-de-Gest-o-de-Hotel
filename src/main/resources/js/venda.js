const API_PRODUTOS = "http://localhost:8080/produtos";
const API_VENDAS = "http://localhost:8080/vendas";

const usuarioLogado = "isaque"; // teste — ideal é pegar do login
document.getElementById("usuarioLogado").innerText = "Logado como: " + usuarioLogado;

// =======================
// Buscar produto
// =======================
async function buscarProduto() {
    const codigo = document.getElementById("codigoProduto").value;

    if (!codigo) {
        alert("Digite um código de produto!");
        return;
    }

    const resp = await fetch(`${API_PRODUTOS}/codigo/${codigo}`);

    if (!resp.ok) {
        alert("Produto não encontrado!");
        return;
    }

    const produto = await resp.json();

    document.getElementById("nomeProduto").value = produto.nome;
    document.getElementById("precoUnitario").value = produto.precoUnitario;

    calcularValores();
}

// =======================
// Calcular valores
// =======================
function calcularValores() {
    const preco = parseFloat(document.getElementById("precoUnitario").value || 0);
    const quantidade = parseInt(document.getElementById("quantidade").value || 1);
    const recebido = parseFloat(document.getElementById("valorRecebido").value || 0);

    const total = preco * quantidade;
    document.getElementById("valorTotal").value = total.toFixed(2);

    const diferenca = recebido - total;
    document.getElementById("valorDiferenca").value = diferenca.toFixed(2);

    // Ativar motivo se tiver diferença
    document.getElementById("motivoDiferencaBox").style.display =
        diferenca !== 0 ? "block" : "none";
}

// =======================
// Registrar venda
// =======================
async function registrarVenda() {

    const venda = {
        produto: { id: document.getElementById("codigoProduto").value },
        quantidade: document.getElementById("quantidade").value,
        valorRecebido: document.getElementById("valorRecebido").value,
        motivoDiferenca: document.getElementById("motivoDiferenca").value,
        formaPagamento: document.getElementById("formaPagamento").value
    };

    const resp = await fetch(API_VENDAS, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "usuario-login": usuarioLogado
        },
        body: JSON.stringify(venda)
    });

    if (!resp.ok) {
        alert("Erro ao registrar venda!");
        return;
    }

    alert("Venda registrada com sucesso!");
    carregarVendas();
}

// =======================
// Carregar vendas recentes
// =======================
async function carregarVendas() {
    const resp = await fetch(API_VENDAS);
    const lista = await resp.json();

    let html = "";

    lista.forEach(v => {
        html += `
        <tr>
            <td>${v.id}</td>
            <td>${v.produto.nome}</td>
            <td>${v.quantidade}</td>
            <td>R$ ${v.valorOriginal.toFixed(2)}</td>
            <td>${v.criadoPor ? v.criadoPor.nome : "-"}</td>
            <td>${v.dataHora.replace("T", " ")}</td>
        </tr>`;
    });

    document.getElementById("tabelaVendas").innerHTML = html;
}

carregarVendas();
