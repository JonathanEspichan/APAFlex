<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<!-- jQuery library -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!--  jQuery Validation -->
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>

<!-- editar y eliminar  -->
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>

<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/csv-parser/2.4.7/csv-parser.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- Chosen CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/chosen-js@1.8.7/chosen.min.css">

<!-- Chosen JavaScript -->
<script
	src="https://cdn.jsdelivr.net/npm/chosen-js@1.8.7/chosen.jquery.min.js"></script>

<style>
.error-input {
	border: 1px solid red;
}
</style>

<style>
.pdf-title {
	text-align: center;
	margin-top: 10px;
}

.pdf-table {
	border-collapse: collapse;
	width: 100%;
}

.pdf-table th, .pdf-table td {
	border: 1px solid black;
	padding: 8px;
	text-align: center;
}

.pdf-table th {
	background-color: lightgray;
}
</style>


<style>
.btn-square {
	width: 40px;
	height: 40px;
	line-height: 40px;
	border-radius: 0;
	text-align: center;
	padding: 0;
}
</style>



<style>
.pdf-title {
	text-align: center;
	margin-top: 10px;
}

.pdf-table {
	border-collapse: collapse;
	width: 100%;
}

.pdf-table th, .pdf-table td {
	border: 1px solid black;
	padding: 8px;
	text-align: center;
}

.pdf-table th {
	background-color: lightgray;
}
</style>

<style>
.btn-square {
	width: 40px;
	height: 40px;
	line-height: 40px;
	border-radius: 0;
	text-align: center;
	padding: 0;
}
</style>

<style>
.content {
	margin-left: 240px;
	padding: 25px;
}
</style>

<style>
/* Estilos para el modo oscuro */
body.dark h1.crud-title {
	color: #ffffff; /* Cambiar a color blanco (#ffffff) */
}
</style>

<style>
.letra {
	font-size: 80%;
}
</style>


<title>I.E.P.N°20191 Alfonso Ugarte</title>
<link rel="shortcut icon" href="img/logocolegio.png">
<link rel="stylesheet" href="css/style.css" />

</head>
<body>
	<jsp:include page="menu.jsp"></jsp:include>

	<br>
	<br>
	<br>

	<div class="content">
		<div class="container">

			<h1 class="crud-title">CRUD DE PAGO DE APAFA</h1>

			<!-- Card de datos de entrada -->
			<div class="card">
				<div class="card-header">Criterios de busqueda</div>
				<div class="card-body">
					<form method="post" action="PaymentApafaBuscar2">
						<div class="mb-3 row">
							<div class="col-sm-4">
								<input type="text" class="form-control" id="person_id"
									name="person_id" placeholder="personaID">
							</div>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="activity_id"
									name="activity_id" placeholder="ActividadID">
							</div>
							<div class="col-sm-4">
								<select class="form-select" id="tipe_pay">
									<option value="">Todo los Pagos</option>
									<option value="E">Efectivo</option>
									<option value="T">Transferencia</option>
									<option value="Y ">Yape</option>
								</select>
							</div>
							<br> <br>
							<div class="col-sm-2">
								<button type="button" class="btn btn-primary mb-3"
									id="btnBuscar" name="btnBuscar">Buscar Regitros</button>
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-primary mb-3" id="btnNuevo"
									name="btnNuevo">Nuevo Trabajador</button>
							</div>
						</div>
					</form>
					<button onclick="exportToCSV()">Exportar a CSV</button>
					<button onclick="exportToPDF()">Exportar a PDF</button>
					<button onclick="exportToExcel()">Exportar a excel</button>
					<BR>
				</div>
			</div>

			<br />

			<!-- Card de resultados -->
			<div class="letra">
				<div class="card" id="divResultado">
					<div class="card-header">Resultado</div>
					<div class="card-body">
						<table class="table">
							<thead>
								<tr>
									<th>ID</th>
									<th>TIPO DE PAGO</th>
									<th>MONTO</th>
									<th>FECHA</th>
									<th>PERSONA_ID</th>
									<th>ACTIVIDAD_ID</th>
								</tr>
							</thead>
							<tbody id="detalleTabla">
							</tbody>
						</table>
					</div>
				</div>
			</div>


			<!-- Formulario de edición de registro -->
			<div class="card" id="divRegistro" style="display: none; width: 100%">
				<div class="card-header" id="tituloRegistro">{accion} NUEVO
					PAGO DE APAFA</div>
				<div class="card-body">
					<form>
						<input type="hidden" id="accion" name="accion">
						<div class="row mb-3">
							<label for="frmId" class="col-sm-2 col-form-label">ID</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="frmId"
									disabled="disabled" value="0">
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmTipoPago" class="col-sm-2 col-form-label">Tipo
								de Pago</label>
							<div class="col-sm-8">
								<select class="form-select" id="frmTipoPago">
									<option value="">Seleccione una opcion</option>
									<option value="E">Efectivo</option>
									<option value="T">Transferencia</option>
									<option value="Y ">Yape</option>
								</select>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmMonto" class="col-sm-2 col-form-label">Monto
								de pago</label>
							<div class="col-sm-8">
								<input type="number" class="form-control" id="frmMonto"
									oninput="validateNumberInput(this)">
							</div>
						</div>

						<!-- 						<div class="row mb-3 d-none"> -->
						<div class="row mb-3">
							<label for="frmFecha" class="col-sm-2 col-form-label">Fecha</label>
							<div class="col-sm-8">
								<input type="date" class="form-control" id="frmFecha">
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmPersona_ID" class="col-sm-2 col-form-label">Persona_ID</label>
							<select class="form-select chosen-select" id="frmPersona_ID"
								aria-label="Default select example" required>
							</select>

							<div class="invalid-feedback">Por favor, seleccione un
								nombre válido</div>
						</div>

						<div class="row mb-3">
							<label for="frmActividad_ID" class="col-sm-2 col-form-label">Actividad_ID</label>
							<select class="form-select chosen-select" id="frmActividad_ID"
								aria-label="Default select example" required>
							</select>

							<div class="invalid-feedback">Por favor, seleccione un
								nombre válido</div>
						</div>
						<button type="button" class="btn btn-primary" id="btnProcesar">Procesar</button>
						<button type="button" class="btn btn-primary" id="btnLimpiar">Limpiar
							Formulario</button>
						<br> <br>
					</form>
				</div>
			</div>
		</div>
	</div>


	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-zqPhW1E/60EGWjgVjK8bNSVEkDTo1dh1pPSm3pbBny6lWfsF9QR8pSc/qf5gMBvW"
		crossorigin="anonymous"></script>



	<script>
	//Buscar en combo
	$(document).ready(function() {
		$('.chosen-select').chosen({
			width : "67%"
		});
	});
		// Constantes del CRUD
		const ACCION_NUEVO = "NUEVO";
// 		const ACCION_LIMPIAR = "LIMPIAR";
		const ACCION_EDITAR = "EDITAR";
		const ACCION_ELIMINAR = "ELIMINAR";
// 		const ACCION_RESTAURE = "RESTAURE";
// 		const ACCION_ELIMINATE = "ELIMINATE";

		// Arreglo de registros
		let arreglo = [];

		// Acceder a los controles
		let btnBuscar = document.getElementById("btnBuscar");
		let btnNuevo = document.getElementById("btnNuevo");
		let btnLimpiar = document.getElementById("btnLimpiar");
		let btnProcesar = document.getElementById("btnProcesar");

		// Programar los controles
		btnBuscar.addEventListener("click", fnBtnBuscar);
		btnNuevo.addEventListener("click", fnBtnNuevo);
		btnProcesar.addEventListener("click", fnBtnProcesar);
		btnLimpiar.addEventListener("click", fnBtnLimpiar);

		// Funcion Editar
		function fnEditar(id) {
    // Preparando el formulario
    document.getElementById("tituloRegistro").innerHTML = ACCION_EDITAR + " REGISTRO";
    document.getElementById("accion").value = ACCION_EDITAR;
    fnCargarForm(id);  // Llamamos a la función para cargar los datos del registro
    // Mostrar formulario
    document.getElementById("divResultado").style.display = "none";
    document.getElementById("divRegistro").style.display = "block";
}


		// Funcion Eliminar
		function fnEliminar(id) {
			Swal.fire({
				  title: 'Estas Seguro de Eliminar el Registro',
				  text: "No podras volver a editarlo",
				  icon: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'SI CONTINUA'
				}).then((result) => {
				  if (result.isConfirmed) {
				    Swal.fire(
				      'Se elimino',
				      'El proceso fue EXITOSO.',
				      'BUENAAAA'
				    )
					document.getElementById("accion").value = ACCION_ELIMINAR;
					fnCargarForm(id);
					fnBtnProcesar();
					setTimeout(fnBtnBuscar, 1000);
				  }
				})
			}
	
		
		// Funcion fnBtnProcesar
function fnBtnProcesar() {
			// Preparar los datos
			let datos = "accion=" + document.getElementById("accion").value;
			datos += "&id=" + document.getElementById("frmId").value;
			datos += "&tipe_pay="+ document.getElementById("frmTipoPago").value;
			datos += "&amount="+ document.getElementById("frmMonto").value;
			datos += "&dates="+ document.getElementById("frmFecha").value;
			datos += "&person_id=" + document.getElementById("frmPersona_ID").value;
			datos += "&activity_id=" + document.getElementById("frmActividad_ID").value;
			// El envio con AJAX
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "PaymentApafaProcesar", true);
			xhr.setRequestHeader('Content-type',
					'application/x-www-form-urlencoded');
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					// La solicitud se completó correctamente
					console.log(xhr.responseText);
// 					alert(xhr.responseText);
					fnBtnBuscar();
					 
					
					if (xhr.readyState === 4 && xhr.status === 200) {
					    console.log(xhr.responseText);
// 					    alert(xhr.responseText);
						fnBtnBuscar();

					    // Limpiar el formulario
					    if (document.getElementById("accion").value === ACCION_NUEVO) {
					    	fnLimpiarForm();
					        fnBtnBuscar();
					    }
					}

					
				}
			};
			xhr.send(datos);
		}
		
		function fnLimpiarForm() {
		    document.getElementById('frmId').value = '0';
		    document.getElementById('frmTipoPago').value = '1';
		    document.getElementById('frmMonto').value = '';
		    document.getElementById('frmFecha').value = '';
		    document.getElementById('frmPersona_ID').value = '';
		    document.getElementById('frmActividad_ID').value = '';

		}
		
		// Funcion fnBtnNuevo
		function fnBtnNuevo() {
			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_NUEVO
					+ " REGISTRO";
			document.getElementById("accion").value = ACCION_NUEVO;
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
		}
		
		
		fnBtnBuscar();
		
		
		
		// Función fnBtnBuscar
		function fnBtnBuscar() {
			// Datos
			let person_id = document.getElementById("person_id").value;
			let activity_id = document.getElementById("activity_id").value;
			let tipe_pay = document.getElementById("tipe_pay").value;
			// Preparar la URL
			let url = "PaymentApafaBuscar2?person_id=" + person_id +  "&activity_id=" + activity_id  +  "&tipe_pay=" + tipe_pay;
			// La llama AJAX
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", url, true);
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					let respuesta = xhttp.responseText;
					arreglo = JSON.parse(respuesta);
					let detalleTabla = "";
					arreglo.forEach(function(item) {
						detalleTabla += "<tr>";
						detalleTabla += "<td>" + item.id + "</td>";
						detalleTabla += "<td>" + item.tipe_pay + "</td>";
						detalleTabla += "<td>" + item.amount + "</td>";
						detalleTabla += "<td>" + item.dates + "</td>";
						detalleTabla += "<td>" + item.person_id + "</td>";
						detalleTabla += "<td>" + item.activity_id + "</td>";
						detalleTabla += "<td>";
						detalleTabla += "<div class='d-flex gap-2'>"+
						"<button type='button' class='btn btn-light' onclick='fnEditar(" + item.id + ")'>"+
						"<i class='fa-solid fa-pen'></i>"+
					"</button>"+
					"<button type='button' class='btn btn-danger' onclick='fnEliminar(" + item.id +")'>"+
					"<i class='fa-solid fa-trash'></i>"+
				"</button>"+
			"</div>";
						detalleTabla += "</td>";
						detalleTabla += "</tr>";
					});
					document.getElementById("detalleTabla").innerHTML = detalleTabla;
					// Mostrar formulario
					document.getElementById("divResultado").style.display = "block";
					document.getElementById("divRegistro").style.display = "none";
				}
			};
			xhttp.send();
		}
		
		fnBtnBuscar();


cargarPersonID();

function cargarPersonID() {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", "SelectListarTodoPerson", true);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			let respuesta = JSON.parse(xhr.responseText);
			construirComboPersonID(respuesta);
		}
	};
	xhr.send();
}

function construirComboPersonID(persons) {
	let selectPerson = document.getElementById("frmPersona_ID");
	selectPerson.innerHTML = "";

	// Agregar una opción vacía como selección predeterminada
	let opcionVacia = document.createElement("option");
	opcionVacia.value = "";
	opcionVacia.text = "Persona";
	selectPerson.appendChild(opcionVacia);

	persons.forEach(function(persona) {
		let opcion = document.createElement("option");
		opcion.value = persona.id;
		opcion.text = persona.names + ',  ' + persona.last_name;
		selectPerson.appendChild(opcion);
	});

	// Inicializar el select de profesores con Chosen
	$(selectPerson).chosen();
	$(selectPerson).trigger("chosen:updated");
}


cargarActivityID();
function cargarActivityID() {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", "SelectListarTodoActivity", true);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			let respuesta = JSON.parse(xhr.responseText);
			construirComboActivityID(respuesta);
		}
	};
	xhr.send();
}

function construirComboActivityID(activitys) {
	let selectActivity = document.getElementById("frmActividad_ID");
	selectActivity.innerHTML = "";

	// Agregar una opción vacía como selección predeterminada
	let opcionVacia = document.createElement("option");
	opcionVacia.value = "";
	opcionVacia.text = "Actividad";
	selectActivity.appendChild(opcionVacia);

	activitys.forEach(function(Actividad) {
		let opcion = document.createElement("option");
		opcion.value = Actividad.id;
		opcion.text = Actividad.names ;
		selectActivity.appendChild(opcion);
	});

	// Inicializar el select de profesores con Chosen
	$(selectActivity).chosen();
	$(selectActivity).trigger("chosen:updated");
}
		

function fnCargarForm(id) {
    let registro = arreglo.find(item => item.id == id);
    if (registro) {
        document.getElementById('frmId').value = registro.id;
        document.getElementById('frmTipoPago').value = registro.tipe_pay;
        document.getElementById('frmMonto').value = registro.amount;
        document.getElementById('frmFecha').value = registro.dates;
        document.getElementById('frmPersona_ID').value = registro.person_id;
        document.getElementById('frmActividad_ID').value = registro.activity_id;
    }
}


// function fnCargarForm(id) {
// 	arreglo
// 			.forEach(function(item) {
// 				if (item.id == id) {
// 					document.getElementById("frmId").value = item.id;
// 					document.getElementById("frmTipoDocumento").value = item.document_type;
// 					document.getElementById("frmNumeroDocumento").value = item.number_document;
// 					document.getElementById("frmPersona_ID").value = item.person_id;
// 					document.getElementById("frmCargo").value = item.charges;
// 					//break;
// 				}
// 			});
// }
		
		fnBtnBuscar();
		
		// Función generar EXCEL
		function exportToExcel() {
			// Obtener los datos de la tabla
			let rows = document.querySelectorAll("#detalleTabla tr");
			// Crear una matriz de datos con las columnas deseadas
			let data = [];
			// Agregar los encabezados de columna
			data.push([ "ID", "IPO DE PAGO", "MONTO", "FECHA",
					"PERSONA_ID", "ACTIVIDAD_ID" ]);
			rows
					.forEach(function(row) {
						let rowData = [];
						let columns = row
								.querySelectorAll("td:nth-child(1), td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6)"); // Incluir solo las columnas deseadas
						columns.forEach(function(column) {
							rowData.push(column.innerText);
						});
						data.push(rowData);
					});
			// Crear una hoja de cálculo de Excel
			let worksheet = XLSX.utils.aoa_to_sheet(data);
			// Crear un libro de Excel y agregar la hoja de cálculo
			let workbook = XLSX.utils.book_new();
			XLSX.utils.book_append_sheet(workbook, worksheet, "pago_apafa");
			// Guardar el archivo de Excel
			XLSX.writeFile(workbook, "reportePago_apafa.xlsx");
		}
		

		// Función generar CSV
		
		function exportToCSV() {
			  // Obtener los datos de la tabla
			  let tableData = [];
			  let rows = document.querySelectorAll('#detalleTabla tr');

			  for (let row of rows) {
			    let rowData = [];
			    let cells = row.querySelectorAll('td');

			    for (let cell of cells) {
			      rowData.push(cell.textContent.trim());
			    }

			    tableData.push(rowData);
			  }

			  // Crear el contenido del archivo CSV
			  let csvContent = "data:text/csv;charset=utf-8,";

			  tableData.forEach(function(rowArray) {
			    let row = rowArray.join(",");
			    csvContent += row + "\r\n";
			  });

			  // Crear un enlace de descarga
			  let encodedUri = encodeURI(csvContent);
			  let link = document.createElement("a");
			  link.setAttribute("href", encodedUri);
			  link.setAttribute("download", "data.csv");
			  document.body.appendChild(link);

			  // Hacer clic en el enlace para descargar el archivo CSV
			  link.click();
			}

		
		  function validateTextInput(input) {
		      let inputValue = input.value;
		      let sanitizedValue = inputValue.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, '');
		      input.value = sanitizedValue;
		  
		      if (sanitizedValue !== inputValue) {
		        input.classList.add("error-input");
		      } else {
		        input.classList.remove("error-input");
		      }
		    }
	  
		// Función generar PDF
		
	  function exportToPDF() {
		  const excludedColumns = [6]; // Índice de la columna "Acciones"

		  // Copia de la tabla para eliminar las columnas no deseadas
		  const table = document.querySelector("table").cloneNode(true);
		  table.querySelectorAll("th, td").forEach((cell) => {
		    const columnIndex = cell.cellIndex;
		    if (excludedColumns.includes(columnIndex)) {
		      cell.remove();
		    } 
		  });

		  const element = document.createElement("div");

		  // Agregar título a la hoja
		  const title = document.createElement("h1");
		  title.classList.add("pdf-title");
		  title.textContent = "Tabla de pago_apafa";
		  element.appendChild(title);

		  // Agregar líneas divisorias a las columnas
		  const tableWithLines = document.createElement("table");
		  tableWithLines.classList.add("pdf-table");
		  tableWithLines.style.width = "80%"; // Establecer el ancho de la tabla al 50% del contenedor
		  tableWithLines.style.fontSize = "12px"; // Reducir el tamaño de fuente de la tabla
		  const rows = table.rows;
		  const columns = rows[0].cells.length;

		  for (let i = 0; i < rows.length; i++) {
		    const row = tableWithLines.insertRow();
		    for (let j = 0; j < columns; j++) {
		      const cell = row.insertCell();
		      if (i === 0) {
		        cell.classList.add("pdf-table-header");
		      }
		      cell.innerHTML = rows[i].cells[j].innerHTML;
		    }
		  }

		  element.appendChild(tableWithLines);

		  const opt = {
		    margin: [0.5, 0, 1, 0], // Márgenes superior, derecho, inferior, izquierdo
		    filename: "tabla_pago_apafa.pdf",
		    image: { type: "jpeg", quality: 0.98 },
		    html2canvas: { scale: 2 },
		    jsPDF: { unit: "in", format: "letter", orientation: "portrait" },
		  };

		  html2pdf().set(opt).from(element).save();
		}

		//document.getElementById("btnExportarPDF").addEventListener("click", exportToPDF);

		<!--voy a generar mi funcion limpiar formulario -->
		// Funcion fnBtnLimpiar
		function fnBtnLimpiar() {
		    // Limpiar los campos del formulario
			document.getElementById('frmId').value = '0';
		    document.getElementById('frmTipoPago').value = '1';
		    document.getElementById('frmMonto').value = '';
		    document.getElementById('frmFecha').value = '';
		    document.getElementById('frmPersona_ID').value = '';
		    document.getElementById('frmActividad_ID').value = '';

		}

		
	</script>


</body>
</html>