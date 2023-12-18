//METODO PARA BARRA DE BUSQUEDA
document.addEventListener('DOMContentLoaded', function () {
    let url = 'http://localhost:8080/estudiantes';

    // Llamada inicial para cargar los datos al cargar la página
    cargarDatosBusqueda();
    
    let barraBusqueda = document.getElementById('barraBusqueda');
    barraBusqueda.addEventListener('input', function () {
        
        const valorBusqueda = barraBusqueda.value.trim().toLowerCase();
        // Llamada para actualizar los datos y aplicar la búsqueda
        cargarDatosBusqueda(valorBusqueda);
    });

    function cargarDatosBusqueda(busqueda = '') {
        fetch(url)
            .then(response => response.json())
            .then(data => mostrarData(data, busqueda));
    }

    

    function mostrarData(data, busqueda) {
        let tablaDatos = '';
        for (let i = 0; i < data.length; i++) {
            const contenidoFila = `${data[i].nombre} ${data[i].cedula} ${data[i].tipo} ${data[i].titulo} ${data[i].etapa} ${data[i].carrera} ${data[i].semestre} ${data[i].empresa} ${data[i].año} ${data[i].sustentacion}`.toLowerCase();

            if (contenidoFila.includes(busqueda)) {
                tablaDatos += `<tr>
                    <td>${data[i].nombre}</td>
                    <td>${data[i].cedula}</td>
                    <td>${data[i].tipo}</td>
                    <td>${data[i].titulo}</td>
                    <td class="etapa-verde">${data[i].etapa}</td>
                    <td>${data[i].carrera}</td>
                    <td>${data[i].semestre}</td>
                    <td>${data[i].empresa}</td>
                    <td>${data[i].año}</td>
                    <td>${data[i].sustentacion}</td>
                </tr>`;
            }
        }

        let cuerpoTabla = document.getElementById('cuerpoTabla');
        if (cuerpoTabla) {
            cuerpoTabla.innerHTML = tablaDatos;
        } else {
            console.error('El elemento con ID "cuerpoTabla" no fue encontrado.');
        }
    }

    const resultadosPorPagina = 8;
    let paginaActual = 1;
    let datosTotales = [];

    function obtenerDatosDesdeURL(url) {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                datosTotales = data;
                mostrarDatosPaginados(paginaActual);
            })
            .catch(error => console.error('Error al obtener los datos:', error));
    }

    function mostrarDatosEnTabla(datos) {
        let tablaDatos = '';
        for (let i = 0; i < datos.length; i++) {
            tablaDatos += `<tr>
                <td>${datos[i].nombre}</td>
                <td>${datos[i].cedula}</td>
                <td>${datos[i].tipo}</td>
                <td>${datos[i].titulo}</td>
                <td class="etapa-verde">${datos[i].etapa}</td>
                <td>${datos[i].carrera}</td>
                <td>${datos[i].semestre}</td>
                <td>${datos[i].empresa}</td>
                <td>${datos[i].año}</td>
                <td>${datos[i].sustentacion}</td>
            </tr>`;
        }

        let cuerpoTabla = document.getElementById('cuerpoTabla');
        if (cuerpoTabla) {
            cuerpoTabla.innerHTML = tablaDatos;
        } else {
            console.error('El elemento con ID "cuerpoTabla" no fue encontrado.');
        }
    }

    function mostrarDatosPaginados(pagina) {
        let datosPagina = [];
        const indiceInicial = (pagina - 1) * resultadosPorPagina;
        const indiceFinal = pagina * resultadosPorPagina;
    
        if (pagina === 1) {
            datosPagina = datosTotales.slice(0, resultadosPorPagina);
        } else {
            datosPagina = datosTotales.slice(indiceInicial, indiceFinal);
        }
    
        mostrarDatosEnTabla(datosPagina);
    }
    

    obtenerDatosDesdeURL('http://localhost:8080/estudiantes');

    // Funciones para controlar la paginación
    function cargarPaginaSiguiente() {
        if (paginaActual * resultadosPorPagina < datosTotales.length) {
            paginaActual++;
            mostrarDatosPaginados(paginaActual);
        }
    }

    function cargarPaginaAnterior() {
        if (paginaActual > 1) {
            paginaActual--;
            mostrarDatosPaginados(paginaActual);
        }
    }

    // Event listeners para los botones de paginación 
    const botonPaginaSiguiente = document.getElementById('botonSiguiente');
    const botonPaginaAnterior = document.getElementById('botonAnterior');

    if (botonPaginaSiguiente && botonPaginaAnterior) {
        botonPaginaSiguiente.addEventListener('click', cargarPaginaSiguiente);
        botonPaginaAnterior.addEventListener('click', cargarPaginaAnterior);
    }

}); //FIN CONTENTLOADED


//logica de elena
function aplicarFiltros() {
    const seleccionarFiltros = {
        carrera: obtenerValor('listaCarreras'),
        año: obtenerValor('listaAno'),
        etapa: obtenerValor('listaEtapa'),
        semestre: parseInt(obtenerValor('listaSemestre')),
        tipo: obtenerValor('listaTipoTrabajo')
    };

    console.log(seleccionarFiltros);
    enviarFiltros(seleccionarFiltros);
}

function obtenerValor(id) {
    const valorSeleccionado = document.getElementById(id).value;
    return valorSeleccionado === 'Todas' || valorSeleccionado === 'Ambos' ? null : valorSeleccionado;
}

function enviarFiltros(seleccionarFiltros) {
    const url = 'http://localhost:8080/Busqueda';
    console.log(JSON.stringify(seleccionarFiltros));

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(seleccionarFiltros)
    })
    .then(response => response.json())
    .then(data => {
        console.log("Datos filtrados:", data);

        const busqueda = document.getElementById('barraBusqueda').value.toLowerCase();

        mostrarDatosFiltrados(data, busqueda);
    })
    .catch(error => {
        console.error('Error al obtener datos:', error);
    });
}


function mostrarDatosFiltrados(data, busqueda) {
    let tablaDatos = '';
    for (let i = 0; i < data.length; i++) {
        const contenidoFila = `${data[i].nombre} ${data[i].cedula} ${data[i].tipo} ${data[i].titulo} ${data[i].etapa} ${data[i].carrera} ${data[i].semestre} ${data[i].empresa} ${data[i].año} ${data[i].sustentacion}`.toLowerCase();

        if (contenidoFila.includes(busqueda)) {
            tablaDatos += `<tr>
                <td>${data[i].nombre}</td>
                <td>${data[i].cedula}</td>
                <td>${data[i].tipo}</td>
                <td>${data[i].titulo}</td>
                <td class="etapa-verde">${data[i].etapa}</td>
                <td>${data[i].carrera}</td>
                <td>${data[i].semestre}</td>
                <td>${data[i].empresa}</td>
                <td>${data[i].año}</td>
                <td>${data[i].sustentacion}</td>
            </tr>`;
        }
    }

    let cuerpoTabla = document.getElementById('cuerpoTabla');
    if (cuerpoTabla) {
        cuerpoTabla.innerHTML = tablaDatos;
    } else {
        console.error('El elemento con ID "cuerpoTabla" no fue encontrado.');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    // Llamada a la función aplicarFiltros después de que el DOM se haya cargado
    aplicarFiltros();
});
