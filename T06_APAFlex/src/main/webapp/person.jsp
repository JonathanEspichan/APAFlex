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

			<h1 class="crud-title">CRUD DE PERSONA</h1>

			<!-- Card de datos de entrada -->
			<div class="card">
				<div class="card-header">Criterios de busqueda</div>
				<div class="card-body">
					<form method="post" action="PersonBuscar2">
						<div class="mb-3 row">
							<div class="col-sm-4">
								<input type="text" class="form-control" id="last_name"
									name="last_name" placeholder="Apellido">
							</div>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="names" name="names"
									placeholder="Nombre">
							</div>
							<div class="col-sm-4">
								<select class="form-select" id="document_type">
									<option value="">Tipo de Documento</option>
									<option value="DNI">DNI</option>
									<option value="CNE ">CNE</option>
								</select>
							</div>
							<br> <br>
							<div class="col-sm-2">
								<button type="button" class="btn btn-primary mb-3"
									id="btnBuscar" name="btnBuscar">Buscar Regitros</button>
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-primary mb-3" id="btnNuevo"
									name="btnNuevo">Nueva Persona</button>
							</div>
							<div class="col-sm-2">
								<button type="button" onclick="fnBtnBuscarInactivo()"
									class="btn btn-primary mb-3" id="btnListarInactivos"
									name="btnNuevo">Listar Inactivos</button>

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
									<th>NOMBRE</th>
									<th>APELLIDO</th>
									<th>TIPO DE DOCUMENTO</th>
									<th>NÚMERO DE DOCUMENTO</th>
									<th>NÚMERO DE CELULAR</th>
									<th>EMAIL</th>
									<th>SEXO</th>
									<th>ESTADO</th>
									<th>ESTADO CIVIL</th>
								</tr>
							</thead>
							<tbody id="detalleTabla">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<!-- Formulario de edición de registro -->
		<div class="card" id="divRegistro" style="display: none;">
			<div class="card-header" id="tituloRegistro">{accion} NUEVO
				APODERADO</div>
			<div class="card-body">
				<form>
					<div>
						<input type="hidden" id="accion" name="accion">
						<div class="row mb-3">
							<label for="frmId" class="col-sm-2 col-form-label">ID</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="frmId"
									disabled="disabled" value="0">
							</div>
						</div>
						<div class="row mb-3">
							<label for="frmNombre" class="col-sm-2 col-form-label">Nombre</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="frmNombre"
									oninput="validateTextInput(this)"><small>Campo
									Obligatorio.</small>
							</div>
						</div>
						<div class="row mb-3">
							<label for="frmApellido" class="col-sm-2 col-form-label">Apellido</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="frmApellido"
									oninput="validateTextInput(this)"><small>Campo
									Obligatorio.</small>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmTipoDocumento" class="col-sm-2 col-form-label">Tipo
								de Documento</label>
							<div class="col-sm-8">
								<select class="form-select" id="frmTipoDocumento">
									<option value="">Seleccione una opcion</option>
									<option value="DNI">DNI</option>
									<option value="CNE">CNE</option>
								</select>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmNumeroDocumento" class="col-sm-2 col-form-label">Numero
								de Documento</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="frmNumeroDocumento"
									oninput="validateNumberInput(this)"> <small>Campo
									Obligatorio.</small>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmCelular" class="col-sm-2 col-form-label">Celular</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" pattern="[0-9]{9}"
									maxlength="9" id="frmCelular" required> <small>Solo
									se permiten 9 dijitos númericos.</small>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmEmail" class="col-sm-2 col-form-label">Email</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="frmEmail"
									oninput="validateEmail(this)"> <small>Ejemplo:
									jonathan.espichan@vallegrande.edu.pe.</small>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmSexo" class="col-sm-2 col-form-label">Sexo</label>
							<div class="col-sm-8">
								<select class="form-select" id="frmSexo">
									<option value="1">Seleccione una opción</option>
									<option value="M">M</option>
									<option value="F">F</option>
								</select>
							</div>
						</div>


						<div class="row mb-3">
							<label for="frmEstado" class="col-sm-2 col-form-label">Estado</label>
							<div class="col-sm-8">
								<select class="form-select" id="frmEstado">
									<option value="1">Seleccione una opción</option>
									<option value="A">A</option>
									<option value="I">I</option>
								</select>
							</div>
						</div>

						<div class="row mb-3">
							<label for="frmEstadoCivil" class="col-sm-2 col-form-label">Estado
								Civil</label>
							<div class="col-sm-8">
								<select class="form-select" id="frmEstadoCivil">
									<option value="1">Seleccione una opción</option>
									<option value="Soltero">Soltero/a</option>
									<option value="Viudo">Viudo/a</option>
									<option value="Casado">Casado/a</option>
									<option value="Divorciado">Divorciado/a</option>
								</select>
							</div>
						</div>
						<button type="button" class="btn btn-primary" id="btnProcesar">Procesar</button>
						<button type="button" class="btn btn-primary" id="btnLimpiar">Limpiar
							Formulario</button>
						<br> <br>
				</form>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-zqPhW1E/60EGWjgVjK8bNSVEkDTo1dh1pPSm3pbBny6lWfsF9QR8pSc/qf5gMBvW"
		crossorigin="anonymous"></script>

	<!-- Font Awesome JS -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"
		integrity="sha512-Gg3UzYejcZ2iId+zayfqZ4oRAKoMfhVn/BnQOgqG+O2EJhHmyM/ED2B+MPpUXCrkHqduGtsWeS0Q1XtXgn7jBQ=="
		crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<!-- Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script>
		// Constantes del CRUD
		const ACCION_NUEVO = "NUEVO";
// 		const ACCION_LIMPIAR = "LIMPIAR";
		const ACCION_EDITAR = "EDITAR";
		const ACCION_ELIMINAR = "ELIMINAR";
		const ACCION_RESTAURE = "RESTAURE";
		const ACCION_ELIMINATE = "ELIMINATE";

		// Arreglo de registros
		let arreglo = [];

		// Acceder a los controles
		let btnBuscar = document.getElementById("btnBuscar");
		let btnNuevo = document.getElementById("btnNuevo");
// 		let btnLimpiar = document.getElementById("btnLimpiar");
		let btnProcesar = document.getElementById("btnProcesar");

		// Programar los controles
		btnBuscar.addEventListener("click", fnBtnBuscar);
		btnNuevo.addEventListener("click", fnBtnNuevo);
		btnProcesar.addEventListener("click", fnBtnProcesar);
// 		btnLimpiar.addEventListener("click", fnBtnLimpiar);

		// Funcion Editar
		function fnEditar(id) {
			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_EDITAR
					+ " REGISTRO";
			document.getElementById("accion").value = ACCION_EDITAR;
			fnCargarForm(id);
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


		// Funcion RESTAURE
		function fnRestaurate(id) {
			Swal.fire(
					  'Vamos  a RESTAURAR',
					  'Muy bien el proceso de completo',
					  'EXISTOS'
					)
					document.getElementById("accion").value = ACCION_RESTAURE;
					fnCargarForm(id);
					fnBtnProcesar();
					setTimeout(fnBtnBuscar, 1000);
				}
		
		
		

		// Funcion ELIMINATE
		function fnEliminate(id) {
			Swal.fire({
				  title: 'Quieres poner a INACTIVO',
				  text: "Quieres que este registro se ponga en inactivo",
				  icon: 'muy bien',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'si el registro'
				}).then((result) => {
				  if (result.isConfirmed) {
				    Swal.fire(
				      'ELIMINADO!',
				      'Se cumplio ',
				      'EXITOSAMENTE'
				    )
					document.getElementById("accion").value = ACCION_ELIMINATE;
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
			datos += "&names=" + document.getElementById("frmNombre").value;
			datos += "&last_name="+ document.getElementById("frmApellido").value;
			datos += "&document_type="+ document.getElementById("frmTipoDocumento").value;
			datos += "&number_document="	+ document.getElementById("frmNumeroDocumento").value;
			datos += "&cell_phone="+ document.getElementById("frmCelular").value;
			datos += "&email=" + document.getElementById("frmEmail").value;
			datos += "&sex=" + document.getElementById("frmSexo").value;
			datos += "&states=" + document.getElementById("frmEstado").value;
			datos += "&civil_status="	+ document.getElementById("frmEstadoCivil").value;
			// El envio con AJAX
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "PersonProcesar", true);
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
		    document.getElementById('frmNombre').value = '';
		    document.getElementById('frmApellido').value = '';
		    document.getElementById('frmTipoDocumento').value = '1';
		    document.getElementById('frmNumeroDocumento').value = '';
		    document.getElementById('frmCelular').value = '';
		    document.getElementById('frmEmail').value = '';
		    document.getElementById('frmSexo').value = '1';
		    document.getElementById('frmEstado').value = '1'; // Reiniciar al valor predeterminado
		    document.getElementById('frmEstadoCivil').value = '1';
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
		
		//busca de manera automatica al dar click en dni y cne 
		const selectDocumentType = document.getElementById("document_type");
		selectDocumentType.addEventListener("change", (event) => {
			fnBtnBuscar();
			});
		
		// Función fnBtnBuscar
		function fnBtnBuscar() {
			// Datos
			let last_name = document.getElementById("last_name").value;
			let names = document.getElementById("names").value;
			let document_type = document.getElementById("document_type").value;
			// Preparar la URL
			let url = "PersonBuscar2?last_name=" + last_name + "&names="
					+ names + "&document_type=" + document_type;
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
						detalleTabla += "<td>" + item.names + "</td>";
						detalleTabla += "<td>" + item.last_name + "</td>";
						detalleTabla += "<td>" + item.document_type + "</td>";
						detalleTabla += "<td>" + item.number_document + "</td>";
						detalleTabla += "<td>" + item.cell_phone + "</td>";
						detalleTabla += "<td>" + item.email + "</td>";
						detalleTabla += "<td>" + item.sex + "</td>";
						detalleTabla += "<td>" + item.states + "</td>";
						detalleTabla += "<td>" + item.civil_status + "</td>";
						detalleTabla += "<td>";
						detalleTabla += "<div class='d-flex gap-2'>"+
						"<button type='button' class='btn btn-light' onclick='fnEditar(" + item.id + ")'>"+
						"<i class='fa-solid fa-pen'></i>"+
					"</button>"+
					"<button type='button' class='btn btn-danger' onclick='fnEliminate(" + item.id +")'>"+
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
		
		// Función fnBtnBuscarInactivos
		function fnBtnBuscarInactivo() {
			// La llama AJAX
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", "PersonInactivos", true);
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					let respuesta = xhttp.responseText;
					arreglo = JSON.parse(respuesta);
					let detalleTabla = "";
					arreglo.forEach(function(item) {
						detalleTabla += "<tr>";
						detalleTabla += "<td>" + item.id + "</td>";
						detalleTabla += "<td>" + item.names + "</td>";
						detalleTabla += "<td>" + item.last_name + "</td>";
						detalleTabla += "<td>" + item.document_type + "</td>";
						detalleTabla += "<td>" + item.number_document + "</td>";
						detalleTabla += "<td>" + item.cell_phone + "</td>";
						detalleTabla += "<td>" + item.email + "</td>";
						detalleTabla += "<td>" + item.sex + "</td>";
						detalleTabla += "<td>" + item.states + "</td>";
						detalleTabla += "<td>" + item.civil_status + "</td>";
						detalleTabla += "<td>";
						detalleTabla += "<div class='d-flex gap-2'>"+
						"<button type='button' class='btn btn-light' onclick='fnRestaurate(" + item.id + ")'>"+
						"<i class='fa-solid fa-trash-arrow-up'></i>"+
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
		
		function fnCargarForm(id) {
			arreglo
					.forEach(function(item) {
						if (item.id == id) {
							document.getElementById("frmId").value = item.id;
							document.getElementById("frmNombre").value = item.names;
							document.getElementById("frmApellido").value = item.last_name;
							document.getElementById("frmTipoDocumento").value = item.document_type;
							document.getElementById("frmNumeroDocumento").value = item.number_document;
							document.getElementById("frmCelular").value = item.cell_phone;
							document.getElementById("frmEmail").value = item.email;
							document.getElementById("frmSexo").value = item.sex;
							document.getElementById("frmEstado").value = item.states;
							document.getElementById("frmEstadoCivil").value = item.civil_status;
							//break;
						}
					});
		}
		
		fnBtnBuscar();
		
		// Función generar EXCEL
		function exportToExcel() {
			// Obtener los datos de la tabla
			let rows = document.querySelectorAll("#detalleTabla tr");
			// Crear una matriz de datos con las columnas deseadas
			let data = [];
			// Agregar los encabezados de columna
			data.push([ "ID", "NOMBRE", "APELLIDO", "TIPO DOCUMENTO", "NÚMERO DOCUMENTO",
					"CELULAR", "EMAIL", "SEXO","ESTADO", "ESTADO CIVIL" ]);
			rows
					.forEach(function(row) {
						let rowData = [];
						let columns = row
								.querySelectorAll("td:nth-child(1), td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6), td:nth-child(7),td:nth-child(8),td:nth-child(9),td:nth-child(10)"); // Incluir solo las columnas deseadas
						columns.forEach(function(column) {
							rowData.push(column.innerText);
						});
						data.push(rowData);
					});
			// Crear una hoja de cálculo de Excel
			let worksheet = XLSX.utils.aoa_to_sheet(data);
			// Crear un libro de Excel y agregar la hoja de cálculo
			let workbook = XLSX.utils.book_new();
			XLSX.utils.book_append_sheet(workbook, worksheet, "person");
			// Guardar el archivo de Excel
			XLSX.writeFile(workbook, "reportePerson.xlsx");
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
		  const excludedColumns = [10]; // Índice de la columna "Acciones"

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
		  title.textContent = "Tabla de Personas";
		  element.appendChild(title);

		  // Agregar líneas divisorias a las columnas
		  const tableWithLines = document.createElement("table");
		  tableWithLines.classList.add("pdf-table");
		  tableWithLines.style.width = "85%"; // Establecer el ancho de la tabla al 50% del contenedor
		  tableWithLines.style.fontSize = "11.5px"; // Reducir el tamaño de fuente de la tabla
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
		    filename: "tabla_persona.pdf",
		    image: { type: "jpeg", quality: 0.98 },
		    html2canvas: { scale: 2 },
		    jsPDF: { unit: "in", format: "letter", orientation: "portrait" },
		  };

		  html2pdf().set(opt).from(element).save();
		}

		document.getElementById("btnExportarPDF").addEventListener("click", exportToPDF);

		<!--voy a generar mi funcion limpiar formulario -->
		// Funcion fnBtnLimpiar
		function fnBtnLimpiar() {
		    // Limpiar los campos del formulario
		    document.getElementById("frmId").value = "0";
		    document.getElementById("frmNombre").value = "";
		    document.getElementById("frmApellido").value = "";
		    document.getElementById("frmTipoDocumento").value = "1";
		    document.getElementById("frmNumeroDocumento").value = "";
		    document.getElementById("frmCelular").value = "";
		    document.getElementById("frmEmail").value = "";
		    document.getElementById("frmSexo").value = "1";
		    document.getElementById("frmEstado").value = "1";
		    document.getElementById("frmEstadoCivil").value = "1";
		}

		
	</script>




	<!-- VALIDACION CAMPO NOMBRE -->
	<script>
// Obtener el campo de entrada de nombre
var nombreInput = document.getElementById('frmNombre');
// Agregar un event listener para el evento 'input'
nombreInput.addEventListener('input', function(event) {
    // Obtener el valor actual del campo de nombre
    var nombre = event.target.value.trim();
    // Validar el valor ingresado
    if (nombre === '') {
        // El campo está vacío
        nombreInput.classList.remove('is-valid');
        nombreInput.classList.remove('is-invalid');
    } else if (/^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ\s]+$/.test(nombre)) {
        // El valor es válido (solo contiene letras, espacios, y los caracteres ñ, tildes y dieresis)
        nombreInput.classList.remove('is-invalid');
        nombreInput.classList.add('is-valid');
    } else {
        // El valor es inválido
        nombreInput.classList.remove('is-valid');
        nombreInput.classList.add('is-invalid');
    }
});
</script>


	<!-- VALIDACION CAMPO APELLIDO -->
	<script>
// Obtener el campo de entrada de apellido
var apellidoInput = document.getElementById('frmApellido');
// Agregar un event listener para el evento 'input'
apellidoInput.addEventListener('input', function(event) {
    // Obtener el valor actual del campo de apellido
    var apellido = event.target.value.trim();
    // Validar el valor ingresado
    if (apellido === '') {
        // El campo está vacío
        apellidoInput.classList.remove('is-valid');
        apellidoInput.classList.remove('is-invalid');
    } else if (/^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ\s]+$/.test(apellido)) {
        // El valor es válido (solo contiene letras, espacios y los caracteres ñ, tildes y dieresis)
        apellidoInput.classList.remove('is-invalid');
        apellidoInput.classList.add('is-valid');
    } else {
        // El valor es inválido
        apellidoInput.classList.remove('is-valid');
        apellidoInput.classList.add('is-invalid');
    }
});
</script>


	<!-- VALIDACION PARA MI CAMPO TIPO DE DOCUMENTO Y NUMERO DE DOCUMENTO -->
	<script>
// Obtener los campos de selección y entrada
var frmTypeDocumentSelect = document.getElementById('frmTipoDocumento');
var frmNumberDocumentInput = document.getElementById('frmNumeroDocumento');
var numberDocument = '';

// Agregar event listener para el evento 'change' en el campo de tipo de documento
frmTypeDocumentSelect.addEventListener('change', function(event) {
    // Restablecer las clases de validación
    frmNumberDocumentInput.classList.remove('is-valid');
    frmNumberDocumentInput.classList.remove('is-invalid');
    frmTypeDocumentSelect.classList.remove('is-invalid');

    // Obtener el valor actual del campo de tipo de documento seleccionado
    var selectedType = event.target.value;
    numberDocument = frmNumberDocumentInput.value.trim();

    // Validar el valor ingresado solo si se ha seleccionado un tipo de documento y se ha ingresado un número de documento
    if (selectedType !== '' && numberDocument !== '') {
        if (selectedType === 'DNI' && numberDocument.length === 8) {
            frmNumberDocumentInput.classList.remove('is-invalid');
            frmNumberDocumentInput.classList.add('is-valid');
        } else if (selectedType === 'CNE' && numberDocument.length === 9) {
            frmNumberDocumentInput.classList.remove('is-invalid');
            frmNumberDocumentInput.classList.add('is-valid');
        } else {
            frmNumberDocumentInput.classList.remove('is-valid');
            frmNumberDocumentInput.classList.add('is-invalid');
        }
    }
});

// Agregar event listener para el evento 'input' en el campo de número de documento
frmNumberDocumentInput.addEventListener('input', function(event) {
    // Obtener el valor actual del campo de número de documento
    numberDocument = event.target.value.trim();
    var selectedType = frmTypeDocumentSelect.value;

    // Validar el valor ingresado solo si se ha seleccionado un tipo de documento y se ha ingresado un número de documento
    if (selectedType !== '' && numberDocument !== '') {
        if (selectedType === 'DNI' && numberDocument.length === 8) {
            frmNumberDocumentInput.classList.remove('is-invalid');
            frmNumberDocumentInput.classList.add('is-valid');
        } else if (selectedType === 'CNE' && numberDocument.length === 9) {
            frmNumberDocumentInput.classList.remove('is-invalid');
            frmNumberDocumentInput.classList.add('is-valid');
        } else {
            frmNumberDocumentInput.classList.remove('is-valid');
            frmNumberDocumentInput.classList.add('is-invalid');
        }
    } else {
        frmNumberDocumentInput.classList.remove('is-valid');
        frmNumberDocumentInput.classList.remove('is-invalid');
    }
});

// Función para validar la entrada de número de documento (opcional)
function validateNumberInput(input) {
    input.value = input.value.replace(/\D/g, '');
}
</script>




	<!-- VALIDACION CAMPO CELULAR -->
	<script>
// Obtener el campo de entrada de celular
var celularInput = document.getElementById('frmCelular');
// Agregar un event listener para el evento 'input'
celularInput.addEventListener('input', function(event) {
    // Obtener el valor actual del campo de celular
    var celular = event.target.value.trim();
    // Validar el valor ingresado
    if (celular === '') {
        // El campo está vacío
        celularInput.classList.remove('is-valid');
        celularInput.classList.remove('is-invalid');
    } else if (/^9\d{8}$/.test(celular)) {
        // El valor es válido (comienza con el número 9 y contiene exactamente 9 dígitos)
        celularInput.classList.remove('is-invalid');
        celularInput.classList.add('is-valid');
    } else {
        // El valor es inválido
        celularInput.classList.remove('is-valid');
        celularInput.classList.add('is-invalid');
    }
});
</script>


	<!-- VALIDACION CAMPO EMAIL -->
	<script>
// Obtener el campo de entrada de Email
var emailInput = document.getElementById('frmEmail');
// Agregar un event listener para el evento 'input'
emailInput.addEventListener('input', function(event) {
    // Obtener el valor actual del campo de Email
    var email = event.target.value.trim();
    // Validar el valor ingresado
    if (email === '') {
        // El campo está vacío
        emailInput.classList.remove('is-valid');
        emailInput.classList.remove('is-invalid');
    } else if (/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
        // El valor es válido (contiene un solo "@" y termina en ".com")
        emailInput.classList.remove('is-invalid');
        emailInput.classList.add('is-valid');
    } else {
        // El valor es inválido
        emailInput.classList.remove('is-valid');
        emailInput.classList.add('is-invalid');
    }
});

</script>
</body>
</html>