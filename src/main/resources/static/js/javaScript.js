 //const btnModificar = document.getElementById("btnModificar");
 const formulario = document.querySelector("form");
const btnModificar = document.getElementById("btnModificar");
 console.log("funka");

 btnModificar.addEventListener("click",() => {

    formulario.action = `/profesional/cambiar-turno`;

 });