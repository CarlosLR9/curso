'use strict'
console.log('Soy el fichero en node')
x = 'En demo'
var a = '5'
let b = 2
var c = a = b = 3
console.log(`-------> ${a*b+a}`)

function kk() {
    let a = '7'
    if(true){
        let x = 'bloque'
    }
    var d = a + x;
    console.log(`-------> funcion ${d}`)
}
kk();
var x = 33
kk();
function usaKk() {
kk();
}
// console.log(`-------> fuera ${d}`)

// if(a=1){
//     console.log(`Cierto`)
// }
// funcion de orden superior

// let tab = new Array();
// tab = [10, 20]
// tab[4] = undefined
// tab[7] = 'A'
// console.log(tab)