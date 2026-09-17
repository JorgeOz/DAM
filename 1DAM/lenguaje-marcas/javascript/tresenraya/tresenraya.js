console.log("--- TRES EN RAYA ---");
console.log("********************")

let tablero = ["", "", "", "", "", "", "", "", ""];
let jugadorActual = "X";
let partidaFinalizada = false;

const celdas = document.querySelectorAll(".celda");
const mensaje = document.getElementById("mensaje");
const botonReiniciar = document.getElementById("reiniciar");

const combinacionesGanadoras = [
  [0, 1, 2], [3, 4, 5], [6, 7, 8],
  [0, 3, 6], [1, 4, 7], [2, 5, 8],
  [0, 4, 8], [2, 4, 6]
];

function verificarGanador() {
  return combinacionesGanadoras.some(combo => {
    const [a, b, c] = combo;
    return (
      tablero[a] &&
      tablero[a] === tablero[b] &&
      tablero[a] === tablero[c]
    );
  });
}

function manejarClick(e) {
  const index = e.target.dataset.index;

  if (tablero[index] !== "" || partidaFinalizada) return;

  tablero[index] = jugadorActual;
  e.target.textContent = jugadorActual;

  if (verificarGanador()) {
    mensaje.textContent = `¡Ha ganado el jugador ${jugadorActual}!`;
    partidaFinalizada = true;
  } else if (!tablero.includes("")) {
    mensaje.textContent = "¡Empate!";
    partidaFinalizada = true;
  } else {
    jugadorActual = jugadorActual === "X" ? "O" : "X";
    mensaje.textContent = `Turno del jugador ${jugadorActual}`;
  }
}

function reiniciarJuego() {
  tablero = ["", "", "", "", "", "", "", "", ""];
  jugadorActual = "X";
  partidaFinalizada = false;
  mensaje.textContent = "Turno del jugador X";
  celdas.forEach(celda => celda.textContent = "");
}

celdas.forEach(celda => celda.addEventListener("click", manejarClick));
botonReiniciar.addEventListener("click", reiniciarJuego);
