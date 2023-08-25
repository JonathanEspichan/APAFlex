// Activar las validaciones de Bootstrap en el formulario
(function () {
  'use strict';

  // Obtener el formulario a validar
  var form = document.querySelector('.card-body form');

  // Obtener los campos del formulario
  var namesInput = form.querySelector('#frmNombre');
  var last_nameInput = form.querySelector('#frmApellido');
  var document_typeInput = form.querySelector('#frmdocument_type');
  var document_numberInput = form.querySelector('#frmNumeroDocumento');
  var cellphoneInput = form.querySelector('#frmCelular');

  // Expresión regular para validar letras
  var regexLetters = /^[A-Za-z\u00C0-\u017F]+$/;
  // Expresión regular para validar números de 8 dígitos
  var regexNumbers8Digits = /^\d{8}$/;
  // Expresión regular para validar números de 9 dígitos
  var regexNumbers9Digits = /^\d{9}$/;
  // Expresión regular para validar números de celular de 9 dígitos que empiecen con 9
  var regexCellphone = /^9\d{8}$/;

  // Función de validación personalizada para letras
  function validateLetters(input) {
    if (!regexLetters.test(input.value)) {
      input.setCustomValidity('Por favor, ingrese solo letras.');
    } else {
      input.setCustomValidity('');
    }
  }

  // Función de validación personalizada para números de 8 o 9 dígitos según la opción seleccionada
  function validateNumbersLength(input, length) {
    if (input.value.length !== length) {
      input.setCustomValidity(`Por favor, ingrese un número de ${length} dígitos.`);
    } else {
      input.setCustomValidity('');
    }
  }

  // Función de validación personalizada para números de celular de 9 dígitos que empiecen con 9
  function validateCellphone(input) {
    if (!regexCellphone.test(input.value)) {
      input.setCustomValidity('Por favor, ingrese un número de celular válido de 9 dígitos empezando con 9.');
    } else {
      input.setCustomValidity('');
    }
  }

  // Validar el campo de nombre al cambiar su valor
  namesInput.addEventListener('input', function () {
    validateLetters(namesInput);
  });

  // Validar el campo de apellido al cambiar su valor
  last_nameInput.addEventListener('input', function () {
    validateLetters(last_nameInput);
  });

  // Validar el campo de número de documento al cambiar su valor
  document_numberInput.addEventListener('input', function () {
    var selectedDocumentType = document_typeInput.value;
    if (selectedDocumentType === 'DNI') {
      validateNumbersLength(document_numberInput, 8);
    } else if (selectedDocumentType === 'CNE') {
      validateNumbersLength(document_numberInput, 9);
    } else {
      document_numberInput.setCustomValidity('');
    }
  });

  // Validar el campo de número de celular al cambiar su valor
  cellphoneInput.addEventListener('input', function () {
    validateCellphone(cellphoneInput);
  });

  // Validar el formulario al intentar enviarlo
  form.addEventListener('submit', function (event) {
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
   }

    form.classList.add('was-validated');
  }, false);
})();