const API_URL = 'http://localhost:8080/produtos';

let modoEdicao = false;
let produtoAtualId = null;

// Carregar produtos ao iniciar
document.addEventListener('DOMContentLoaded', () => {
    carregarProdutos();
});

// Função para mostrar erro
function mostrarErro(mensagem) {
    const erroDiv = document.getElementById('erro');
    erroDiv.textContent = mensagem;
    erroDiv.classList.remove('hidden');
    
    setTimeout(() => {
        erroDiv.classList.add('hidden');
    }, 5000);
}

// Função para carregar todos os produtos
async function carregarProdutos() {
    try {
        const response = await fetch(API_URL);
        const produtos = await response.json();
        renderizarTabela(produtos);
    } catch (error) {
        mostrarErro('Erro ao carregar produtos. Verifique se a API está rodando.' + error);
    }
}

// Função para renderizar a tabela
function renderizarTabela(produtos) {
    const tbody = document.getElementById('tabelaProdutos');
    
    if (produtos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum produto cadastrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = produtos.map(produto => `
        <tr>
            <td>${produto.id}</td>
            <td>${produto.codigoProduto}</td>
            <td><strong>${produto.nome}</strong></td>
            <td>R$ ${produto.precoUnitario.toFixed(2)}</td>
             <td>${produto.descricao}</td>
              <td>${produto.quantidade}</td>
            <td>
                <div class="actions">
                    <button class="btn-icon btn-edit" onclick="abrirModalEdicao(${produto.id})" title="Editar">
                        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                        </svg>
                    </button>
                    <button class="btn-icon btn-delete" onclick="deletarProduto(${produto.id})" title="Deletar">
                        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <polyline points="3 6 5 6 21 6"></polyline>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                        </svg>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

// Função para abrir modal de novo produto
function abrirModalNovo() {
    modoEdicao = false;
    produtoAtualId = null;
    document.getElementById('modalTitulo').textContent = 'Novo Produto';
    document.getElementById('codigoProduto').value = '';
    document.getElementById('nome').value = '';
    document.getElementById('preco').value = '';
    document.getElementById('descricao').value = '';
    document.getElementById('quantidade').value = '';
    document.getElementById('modal').classList.remove('hidden');
}

// Função para abrir modal de edição
async function abrirModalEdicao(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const produto = await response.json();
        
        modoEdicao = true;
        produtoAtualId = id;
        document.getElementById('modalTitulo').textContent = 'Editar Produto';
        document.getElementById('codigoProduto').value = produto.codigoProduto;
        document.getElementById('nome').value = produto.nome;
        document.getElementById('preco').value = produto.precoUnitario;
        document.getElementById('descricao').value = produto.descricao;
        document.getElementById('quantidade').value = produto.quantidade;
        document.getElementById('modal').classList.remove('hidden');
    } catch (error) {
        mostrarErro('Erro ao carregar produto para edição');
    }
}

// Função para fechar modal
function fecharModal() {
    document.getElementById('modal').classList.add('hidden');
}

// Função para salvar produto
async function salvarProduto() {
    const codigoProduto = document.getElementById('codigoProduto').value;
    const nome = document.getElementById('nome').value;
    const preco = document.getElementById('preco').value;
     const descricao = document.getElementById('descricao').value;
      const quantidade = document.getElementById('quantidade').value;
    
    if (!codigoProduto || !nome || !preco || !descricao || !quantidade ) {
        mostrarErro('Preencha todos os campos');
        return;
    }
    
    const produto = {
        codigoProduto: Number(codigoProduto),
        nome: nome,
        precoUnitario: Number(preco),
        descricao: descricao,
        quantidade: Number(quantidade)
    };
    
    try {
        const url = modoEdicao ? `${API_URL}/${produtoAtualId}` : API_URL;
        const method = modoEdicao ? 'PUT' : 'POST';
        
        if (modoEdicao) {
            produto.id = produtoAtualId;
        }
        
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(produto)
        });
        
        if (response.ok) {
            fecharModal();
            carregarProdutos();
        } else {
            mostrarErro('Erro ao salvar produto');
        }
    } catch (error) {
        mostrarErro('Erro ao salvar produto');
    }
}

// Função para deletar produto
async function deletarProduto(id) {
    if (!confirm('Deseja realmente excluir este produto?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            carregarProdutos();
        } else {
            mostrarErro('Erro ao deletar produto');
        }
    } catch (error) {
        mostrarErro('Erro ao deletar produto');
    }
}

// Função para buscar produto
async function buscarProduto() {
    const busca = document.getElementById('busca').value;
    const tipoBusca = document.getElementById('tipoBusca').value;
    
    if (!busca) {
        mostrarErro('Digite um valor para buscar');
        return;
    }
    
    try {
        const endpoint = tipoBusca === 'codigo' 
            ? `${API_URL}/codigo/${busca}`
            : `${API_URL}/${busca}`;
        
        const response = await fetch(endpoint);
        
        if (response.ok) {
            const produto = await response.json();
            renderizarTabela([produto]);
        } else {
            mostrarErro('Produto não encontrado');
        }
    } catch (error) {
        mostrarErro('Erro ao buscar produto');
    }
}

// Função para calcular preço total
async function calcularPrecoTotal(tipo, valor = '') {
    try {
        let endpoint = `${API_URL}/totalPrecoProdutos`;
        
        if (tipo === 'codigo') {
            endpoint = `${API_URL}/totalPrecoProdutos/codigo/${valor}`;
        } else if (tipo === 'nome') {
            endpoint = `${API_URL}/totalPrecoProdutos/nome/${valor}`;
        }
        
        const response = await fetch(endpoint);
        const total = await response.json();
        
        document.getElementById('precoValor').textContent = `R$ ${total.toFixed(2)}`;
        document.getElementById('precoTotal').classList.remove('hidden');
    } catch (error) {
        mostrarErro('Erro ao calcular preço total');
    }
}

// Função para calcular total por código
function calcularTotalPorCodigo() {
    const codigo = prompt('Digite o código do produto:');
    if (codigo) {
        calcularPrecoTotal('codigo', codigo);
    }
}

// Função para calcular total por nome
function calcularTotalPorNome() {
    const nome = prompt('Digite o nome do produto:');
    if (nome) {
        calcularPrecoTotal('nome', nome);
    }
}

// Event listener para tecla Enter nos inputs
document.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        if (document.getElementById('modal').classList.contains('hidden')) {
            buscarProduto();
        } else {
            salvarProduto();
        }
    }
});

// Fechar modal ao clicar fora dele
document.getElementById('modal').addEventListener('click', (e) => {
    if (e.target.id === 'modal') {
        fecharModal();
    }
});