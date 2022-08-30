function share() {
    let titleA = document.getElementById("titleA").innerText;
    let titleB = document.getElementById("titleB").innerText;

    navigator.clipboard.writeText("http://localhost:8080/zadaca3/VoteServlet?titleA=" + titleA + "&titleB=" + titleB);
}