document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    const mensagemErro = document.getElementById("mensagemErro");
    mensagemErro.textContent = "";

    try {
        const resposta = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ login, senha })
        });

        const texto = await resposta.text();

        if (!resposta.ok) {
            mensagemErro.textContent = texto;
            return;
        }

        // Armazena login do usuário para auditoria das vendas
       sessionStorage.setItem("usuarioLogado", login);


        // Redireciona para vendas ou dashboard
        window.location.href = "index.html";

    } catch (error) {
        mensagemErro.textContent = "Erro ao conectar ao servidor.";
    }
});
