function calculadora(){
    let result = document.querySelector('#rslt')
    let op = document.querySelector('#op')
    let btnRe = document.querySelector('#re')
    let btnBorrar = document.querySelector('#borrar')
    let btnDec = document.querySelector('#dec')
    let btnNums = document.querySelectorAll('#num')
    let btnCalcs = document.querySelectorAll('#calc')

    let operador = '+'
    let n1 = '0'
    let n = '0'
    let nfin = true
    let opFin = true
    let solucion = 0
    let opEntera = false
    result.textContent = n
    op.textContent = ''

    function operacion(value){

        switch (operador) {
			case '+':
				solucion += +n;
				break;
			case '-':
				solucion -= +n;
				break;
			case '*':
				solucion *= +n;
				break;
			case '/':
				solucion /= +n;
				break;
		}

        op.textContent = value == '=' ? '' : (`${solucion} ${value}`);
        n = parseFloat(solucion.toPrecision(15)).toString();
        result.textContent = n
        nfin = false
        operador = value
        opEntera = false
    }

    function reiniciar()
    {
        operador = '+'
        n1 = '0'
        n = '0'
        nfin = true
        opFin = true
        solucion = 0
        opEntera = false
        result.textContent = n
        op.textContent = ''
    }

    function borrar(){
        if (!nfin || n.length == 1 || (n.length == 2 && n.startsWith('-'))) {
			n = '0';
			nfin = true;
		} else
			n = n.slice(0, -1);
        result.textContent = n
    }

    function decimal() {
		if (!nfin) {
			n = '0.';
			nfin = true;
		} else if (n.indexOf('.') === -1)
			n += '.';
        result.textContent = n
	};

    btnCalcs.forEach(btn => {
        btn.addEventListener('click', ev => {
            operacion(ev.target.value)
    })})
    
    btnNums.forEach(btn => {
        btn.addEventListener('click', ev => {
            if(nfin && n !=0 )
                n += ev.target.value
            else
            {
                n = ev.target.value
                nfin = true
            }
            result.textContent = n
    })})

    btnRe.addEventListener('click', reiniciar)
    btnBorrar.addEventListener('click', borrar)
    btnDec.addEventListener('click', decimal)
}
