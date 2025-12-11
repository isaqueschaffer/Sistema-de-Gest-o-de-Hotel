let usuarioLogado = sessionStorage.getItem("usuarioLogado");


if (!usuarioLogado) {
    window.location.href = "login.html"; // impede acesso sem login
}