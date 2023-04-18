function calculadora(){
    let result = document.querySelector('#rslt')
    let op = document.querySelector('#op')
    let op1 = document.querySelector('#op1')
    let op2 = document.querySelector('#op2')
    
    let btn1 = document.querySelector('#id1')
    let btn2 = document.querySelector('#id2')
    let btn3 = document.querySelector('#id3')
    let btn4 = document.querySelector('#id4')
    let btn5 = document.querySelector('#id5')
    let btn6 = document.querySelector('#id6')
    let btn7 = document.querySelector('#id7')
    let btn8 = document.querySelector('#id8')
    let btn9 = document.querySelector('#id9')
    let btn0 = document.querySelector('#id0')
    
    let btnSum = document.querySelector('#sum')
    let btnRes = document.querySelector('#res')
    let btnMult = document.querySelector('#mult')
    let btnDiv = document.querySelector('#div')
    let btnIgual = document.querySelector('#igual')
    
    // op1.hidden = !op1.hidden
    op2.hidden = !op2.hidden
    op.hidden = !op.hidden
    result.hidden = !result.hidden
    document.querySelector('#span').hidden = !document.querySelector('#span').hidden

    let operador
    let n1 = 0
    let n2 = 0
    let n = 0
    let n1fin = 1
    let n2fin = 1
    let solucion = 0

    op1.textContent = n

    function operacion(){
        op1.textContent = n1
        solucion = eval(solucion + n1 + operador + +n)
        result.hidden = false
        document.querySelector('#span').hidden = false
        result.textContent = solucion
        op.textContent = operador
        op2.textContent = n
        n = 0
        n1fin = 1
    }

    btnIgual.addEventListener('click', ev => {
        operacion()
    })

    btnSum.addEventListener('click', ev => {
        operador = '+'
        op.textContent = operador
        op.hidden = false
        if (!n1fin) {
            operacion()
        }
        else{
            n1 = n
            n1fin = 0
        }
        n = 0
    })
    btnRes.addEventListener('click', ev => {
        operador = '-'
        operacion()
    })
    btnMult.addEventListener('click', ev => {
        operador = '*'
        operacion()
    })
    btnDiv.addEventListener('click', ev => {
        operador = '/'
        operacion()
    })

    btn1.addEventListener('click', ev => {
        n = eval(n + '1')
        if (n1fin) {
            op1.textContent = n
        } 
        else {
            op2.textContent = n
            op2.hidden = false
        }
        
    })
    btn2.addEventListener('click', ev => {
        n = n + 2
        op2.textContent = n
    })
    btn3.addEventListener('click', ev => {
        n = n + 3
        op2.textContent = n
    })
    btn4.addEventListener('click', ev => {
        n = n + 4
        op2.textContent = n
    })
    btn5.addEventListener('click', ev => {
        n = n + 5
        op2.textContent = n
    })
    btn6.addEventListener('click', ev => {
        n = n + 6
        op2.textContent = n
    })
    btn7.addEventListener('click', ev => {
        n = n + 7
        op2.textContent = n
    })
    btn8.addEventListener('click', ev => {
        n = n + 8
        op2.textContent = n
    })
    btn9.addEventListener('click', ev => {
        n = n + 9
        op2.textContent = n
    })
    btn0.addEventListener('click', ev => {
        n = n + 0
        op2.textContent = n
    })

}
