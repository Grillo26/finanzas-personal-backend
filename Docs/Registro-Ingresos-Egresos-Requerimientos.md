# Registro de Ingresos y Egresos
**Módulo:** Gestión de Movimientos
**Versión:** 1.0
**Fecha:** 13 de septiembre de 2026
**Estado:** Borrador

---

## 1. Introducción

El submódulo de Registro de Ingresos y Egresos constituye el núcleo operativo del sistema de finanzas personales. Su propósito es permitir que el usuario registre, de manera rápida y estructurada, cada movimiento de dinero que recibe o gasta, sentando la base de datos sobre la cual se construyen los demás submódulos: cálculo del diezmo, reportes mensuales y clasificación por categorías. Sin un registro confiable y consistente de movimientos, ningún otro cálculo del sistema tiene sentido, por lo que este submódulo debe priorizar la simplicidad de captura y la integridad de los datos.

El contexto operativo es estrictamente personal: se trata de un sistema de un solo usuario, sin mecanismos de autenticación en su primera versión, pensado para ser usado indistintamente desde un navegador de escritorio o desde un dispositivo móvil. No existe la necesidad de gestionar permisos ni roles múltiples, lo que simplifica considerablemente el diseño, pero no exime al sistema de mantener buenas prácticas de organización y trazabilidad de la información.

Actualmente, la gestión financiera personal del usuario se realiza mediante hojas de cálculo dispersas o anotaciones manuales, lo que genera varios problemas recurrentes: pérdida de historial, dificultad para saber cuánto se ha gastado por categoría en un mes determinado, y — de forma particularmente sensible — el olvido de apartar el diezmo correspondiente a los ingresos recibidos. Esta desorganización genera ansiedad financiera y afecta la disciplina de ahorro y de aportación.

El valor que aporta este submódulo es doble: por un lado, ofrece claridad financiera inmediata al centralizar todos los movimientos en un único lugar, permitiendo consultar en cualquier momento cuánto se ha ingresado, gastado y apartado; por otro lado, introduce disciplina automática, ya que cada ingreso registrado dispara de forma transparente el cálculo del diezmo correspondiente, eliminando la posibilidad de olvido humano.

Este submódulo se integra directamente con el submódulo de Categorías (toda transacción debe asociarse a una categoría existente), con el submódulo de Diezmo (cada ingreso alimenta el cálculo del diezmo mensual y acumulado), y con el submódulo de Reportes (los movimientos registrados aquí son la fuente de datos para los reportes mensuales por categoría). Cualquier cambio en el modelo de datos de este submódulo debe evaluarse considerando su impacto en estos tres submódulos dependientes.

---

## 2. Alcance

**Incluido en este módulo:**
- Registro de ingresos (monto, fecha, categoría, descripción, origen).
- Registro de egresos/gastos (monto, fecha, categoría, descripción, medio de pago opcional).
- Edición de movimientos del mes en curso.
- Eliminación (lógica) de movimientos con confirmación.
- Búsqueda y filtrado de movimientos por fecha, categoría y tipo (ingreso/egreso).
- Listado paginado y ordenable de movimientos.
- Cálculo automático del saldo disponible en tiempo real.
- Disparo del cálculo de diezmo asociado a cada ingreso registrado.
- Validaciones de monto, fecha y categoría al momento de registrar.
- Historial de auditoría básico (fecha de creación y última modificación).

**Excluido de este módulo (V1):**
- Autenticación y gestión de usuarios (sistema single-user).
- Soporte multiusuario o multi-perfil.
- Sincronización en la nube o entre dispositivos.
- Conversión o manejo de múltiples monedas.
- Movimientos recurrentes automáticos (ej. gastos fijos programados).
- Adjuntar comprobantes o imágenes de recibos.
- Integración con bancos o lectura automática de extractos.

**Actores / Roles involucrados:**
- **Usuario propietario:** único rol existente en V1. Tiene control total sobre el registro, edición, eliminación y consulta de sus propios movimientos.

**Establecimientos aplicables:** No aplica (sistema de uso personal, no multi-sede).

---

## 3. Justificación

### 3.1 Justificación Operativa
Registrar ingresos y egresos en un formulario estructurado, en lugar de una hoja de cálculo libre, reduce errores de digitación, evita la duplicación de conceptos y permite obtener el saldo disponible de forma inmediata sin cálculos manuales. Esto acorta el tiempo que el usuario dedica a "poner en orden" sus finanzas y aumenta la probabilidad de que el registro se mantenga actualizado día a día.

### 3.2 Justificación Personal / Espiritual
El cálculo automático del diezmo a partir de cada ingreso registrado responde a una necesidad concreta del usuario: evitar el olvido de esta aportación por desorden administrativo. Al automatizar este cálculo desde el momento mismo del registro del ingreso, el sistema refuerza la fidelidad y la constancia en esta práctica, quitando la carga mental de "recordar calcularlo después".

### 3.3 Justificación Técnica
Separar el backend (Spring Boot) del frontend (Angular) permite evolucionar cada capa de forma independiente: se puede mejorar la interfaz sin tocar la lógica de negocio, y viceversa. Esta arquitectura también facilita un eventual crecimiento futuro del sistema (por ejemplo, soporte multiusuario o una app móvil nativa que consuma la misma API), además de ser una arquitectura de referencia ampliamente usada en el mercado, lo que favorece el aprendizaje técnico del desarrollador.

---

## 4. Funciones Principales

### FP-01: Registrar ingreso
- **Descripción:** Permite capturar un nuevo ingreso de dinero indicando monto, fecha, categoría y descripción opcional.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El ingreso queda almacenado, el saldo se actualiza y se dispara el cálculo del diezmo correspondiente a ese ingreso.

### FP-02: Registrar egreso/gasto
- **Descripción:** Permite capturar un nuevo gasto indicando monto, fecha, categoría y descripción opcional.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El egreso queda almacenado y el saldo disponible se reduce en el monto correspondiente.

### FP-03: Editar movimiento existente
- **Descripción:** Permite modificar los datos de un movimiento previamente registrado, siempre que pertenezca al mes actual.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El movimiento se actualiza, se recalcula el saldo y, si aplica, se recalcula el diezmo asociado.

### FP-04: Eliminar movimiento
- **Descripción:** Permite eliminar (de forma lógica) un movimiento registrado por error o duplicado.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El movimiento deja de considerarse en saldos y reportes, pero se conserva en el historial de auditoría.

### FP-05: Consultar y filtrar movimientos
- **Descripción:** Permite visualizar el listado de movimientos aplicando filtros por rango de fechas, categoría y tipo.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** Se muestra un listado paginado y ordenado que coincide con los criterios de filtro seleccionados.

### FP-06: Visualizar saldo disponible en tiempo real
- **Descripción:** Muestra de forma permanente el saldo disponible, calculado como ingresos menos egresos menos diezmo separado.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El usuario conoce en todo momento cuánto dinero tiene disponible después de apartar el diezmo.

---

## 5. Requerimientos Funcionales

| ID | Nombre | Descripción | Prioridad | Actor |
|----|--------|-------------|-----------|-------|
| RF-001 | Registrar ingreso | El sistema debe permitir registrar un ingreso con monto, fecha, categoría y descripción opcional. | Alta | Usuario propietario |
| RF-002 | Registrar egreso | El sistema debe permitir registrar un egreso con monto, fecha, categoría y descripción opcional. | Alta | Usuario propietario |
| RF-003 | Validar monto positivo | El sistema debe rechazar movimientos con monto igual o menor a cero. | Alta | Sistema |
| RF-004 | Validar fecha de movimiento | El sistema debe rechazar fechas futuras para egresos ya realizados. | Alta | Sistema |
| RF-005 | Asociar categoría obligatoria | El sistema debe exigir que todo movimiento tenga una categoría válida asociada. | Alta | Sistema |
| RF-006 | Editar movimiento del mes actual | El sistema debe permitir editar únicamente movimientos cuya fecha pertenezca al mes en curso. | Media | Usuario propietario |
| RF-007 | Bloquear edición de meses cerrados | El sistema debe impedir la edición de movimientos de meses anteriores al actual. | Media | Sistema |
| RF-008 | Eliminar movimiento (borrado lógico) | El sistema debe permitir eliminar un movimiento marcándolo como inactivo, sin borrarlo físicamente. | Alta | Usuario propietario |
| RF-009 | Confirmar antes de eliminar | El sistema debe solicitar confirmación explícita antes de eliminar un movimiento. | Media | Usuario propietario |
| RF-010 | Listar movimientos | El sistema debe mostrar un listado paginado de movimientos ordenado por fecha descendente por defecto. | Alta | Usuario propietario |
| RF-011 | Filtrar por rango de fechas | El sistema debe permitir filtrar movimientos entre una fecha inicial y una fecha final. | Alta | Usuario propietario |
| RF-012 | Filtrar por categoría | El sistema debe permitir filtrar movimientos por una o varias categorías seleccionadas. | Alta | Usuario propietario |
| RF-013 | Filtrar por tipo de movimiento | El sistema debe permitir filtrar entre ingresos, egresos o ambos. | Media | Usuario propietario |
| RF-014 | Calcular saldo disponible | El sistema debe calcular el saldo disponible como ingresos menos egresos menos diezmo apartado del periodo. | Alta | Sistema |
| RF-015 | Calcular diezmo por ingreso | El sistema debe calcular automáticamente el 10% de cada ingreso registrado como diezmo correspondiente. | Alta | Sistema |
| RF-016 | Consultar diezmo mensual acumulado | El sistema debe mostrar el total de diezmo acumulado en el mes en curso. | Alta | Usuario propietario |
| RF-017 | Consultar diezmo anual acumulado | El sistema debe mostrar el total de diezmo acumulado en el año en curso. | Media | Usuario propietario |
| RF-018 | Generar reporte mensual por categoría | El sistema debe agrupar los egresos del mes por categoría y mostrar el total gastado en cada una. | Alta | Usuario propietario |
| RF-019 | Exportar movimientos | El sistema debe permitir exportar el listado de movimientos filtrado a un archivo (CSV/Excel) en versiones futuras. | Baja | Usuario propietario |
| RF-020 | Registrar fecha de creación y modificación | El sistema debe almacenar automáticamente la fecha/hora de creación y última modificación de cada movimiento. | Media | Sistema |
| RF-021 | Impedir eliminación de categorías con movimientos | El sistema debe impedir eliminar una categoría si existen movimientos asociados a ella. | Alta | Sistema |
| RF-022 | Registrar descripción opcional | El sistema debe permitir añadir una descripción libre de hasta 200 caracteres a cada movimiento. | Baja | Usuario propietario |
| RF-023 | Buscar movimiento por texto | El sistema debe permitir buscar movimientos por coincidencia de texto en la descripción. | Baja | Usuario propietario |
| RF-024 | Mostrar totales del periodo filtrado | El sistema debe mostrar el total de ingresos y egresos correspondientes al filtro aplicado. | Media | Sistema |

---

## 6. Requerimientos No Funcionales

**Rendimiento**
- RNF-001: El registro de un movimiento debe completarse en menos de 1 segundo en condiciones normales de uso.
- RNF-002: El cálculo de reportes mensuales no debe tardar más de 3 segundos con hasta 5,000 movimientos registrados.
- RNF-003: El listado de movimientos paginado debe cargar en menos de 2 segundos por página.

**Seguridad**
- RNF-004: Aunque no exista autenticación en V1, los datos deben almacenarse en una base de datos local con acceso restringido al proceso de la aplicación.
- RNF-005: El sistema debe generar respaldos periódicos automáticos de la base de datos.
- RNF-006: Los borrados deben ser lógicos, no físicos, para permitir recuperación ante errores.

**Disponibilidad**
- RNF-007: El sistema debe permitir operar en modo local sin dependencia obligatoria de conexión a internet.
- RNF-008: El sistema debe manejar de forma controlada errores de conexión a la base de datos, mostrando mensajes claros al usuario.

**Usabilidad**
- RNF-009: La interfaz de registro de movimientos debe poder completarse en no más de 4 campos obligatorios.
- RNF-010: El diseño debe ser responsivo y utilizable cómodamente desde un dispositivo móvil.
- RNF-011: Los mensajes de validación deben ser claros y estar en español.

**Escalabilidad**
- RNF-012: El modelo de datos debe permitir agregar en el futuro un identificador de usuario sin rediseñar las tablas principales.
- RNF-013: El modelo de datos debe contemplar la posibilidad futura de manejar múltiples monedas sin romper la estructura actual.

**Interoperabilidad**
- RNF-014: El backend debe exponer una API REST documentada que permita futuras integraciones de exportación a CSV/Excel.

**Mantenibilidad**
- RNF-015: El código debe estar organizado en capas (controlador, servicio, repositorio) claramente separadas.
- RNF-016: Las reglas de cálculo del diezmo y del saldo deben estar centralizadas en un único servicio, evitando duplicación de lógica.

**Trazabilidad**
- RNF-017: Todo movimiento debe conservar su fecha de creación y última modificación de forma inmutable en el historial.
- RNF-018: Los movimientos eliminados deben poder consultarse en un registro de auditoría interno.

---

## 7. Reglas de Negocio

- **RN-001:** El diezmo se calcula como el 10% del monto bruto de cada ingreso registrado, sin descuentos previos.
- **RN-002:** El cálculo del diezmo se realiza de forma individual por cada ingreso, no sobre el total acumulado del mes, aunque el sistema también debe poder mostrar el total acumulado.
- **RN-003:** La frecuencia de consolidación del diezmo es mensual, con corte automático el último día de cada mes.
- **RN-004:** Un ingreso es todo movimiento que representa una entrada de dinero al usuario (salario, ventas, regalos, reembolsos, etc.).
- **RN-005:** Un egreso es todo movimiento que representa una salida de dinero del usuario (compras, servicios, pagos, gastos varios).
- **RN-006:** El saldo disponible se calcula como: Saldo = Total Ingresos − Total Egresos − Total Diezmo Apartado del periodo.
- **RN-007:** Una categoría no puede eliminarse si tiene al menos un movimiento (activo o inactivo) asociado a ella.
- **RN-008:** El monto de un movimiento debe ser mayor a cero y debe almacenarse con exactamente 2 decimales.
- **RN-009:** No se permiten fechas futuras en egresos, salvo que el movimiento se marque explícitamente como "gasto planificado".
- **RN-010:** Solo pueden editarse movimientos cuya fecha corresponda al mes calendario actual; los movimientos de meses cerrados son de solo lectura.
- **RN-011:** El diezmo acumulado en el año se calcula como la suma de los diezmos mensuales consolidados desde enero hasta el mes actual.
- **RN-012:** Los reportes mensuales consideran el periodo desde el día 1 hasta el último día del mes calendario correspondiente.
- **RN-013:** Un movimiento eliminado no se contempla en el cálculo de saldo, diezmo ni reportes, pero permanece visible en el historial de auditoría.
- **RN-014:** Si se edita el monto de un ingreso ya registrado, el diezmo asociado a ese ingreso debe recalcularse automáticamente.

---

## 8. Casos de Uso Principales

### CU-01: Registrar un ingreso
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos una categoría de tipo "ingreso" creada en el sistema.
- **Flujo Principal:**
  1. El usuario accede al formulario de nuevo movimiento y selecciona el tipo "Ingreso".
  2. El usuario ingresa monto, fecha, categoría y (opcionalmente) descripción.
  3. El sistema valida los datos ingresados.
  4. El sistema almacena el ingreso.
  5. El sistema calcula automáticamente el diezmo correspondiente (10% del monto).
  6. El sistema actualiza el saldo disponible y muestra confirmación.
- **Flujos Alternativos:**
  - 3a. Si el monto es menor o igual a cero, el sistema muestra un error y no guarda el movimiento.
  - 3b. Si no se selecciona categoría, el sistema solicita seleccionar una antes de continuar.

### CU-02: Registrar un gasto/egreso
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos una categoría de tipo "egreso" creada en el sistema.
- **Flujo Principal:**
  1. El usuario accede al formulario de nuevo movimiento y selecciona el tipo "Egreso".
  2. El usuario ingresa monto, fecha, categoría y (opcionalmente) descripción.
  3. El sistema valida que la fecha no sea futura (salvo que se marque como planificado).
  4. El sistema almacena el egreso.
  5. El sistema actualiza el saldo disponible y muestra confirmación.
- **Flujos Alternativos:**
  - 3a. Si la fecha es futura y no está marcada como planificado, el sistema rechaza el registro.
  - 4a. Si ocurre un error de conexión con la base de datos, el sistema informa al usuario y no pierde los datos ingresados en el formulario.

### CU-03: Calcular y visualizar el diezmo mensual
- **Actor:** Usuario propietario.
- **Precondiciones:** Deben existir ingresos registrados en el mes en curso.
- **Flujo Principal:**
  1. El usuario accede a la sección de Diezmo.
  2. El sistema recupera todos los ingresos del mes en curso.
  3. El sistema suma el 10% de cada ingreso individual.
  4. El sistema muestra el total de diezmo del mes y el detalle por ingreso.
- **Flujos Alternativos:**
  - 2a. Si no hay ingresos registrados en el mes, el sistema muestra el diezmo en cero con un mensaje informativo.

### CU-04: Generar reporte mensual por categoría
- **Actor:** Usuario propietario.
- **Precondiciones:** Deben existir movimientos registrados en el mes consultado.
- **Flujo Principal:**
  1. El usuario selecciona el mes a consultar en la sección de Reportes.
  2. El sistema agrupa los egresos de ese mes por categoría.
  3. El sistema calcula el total gastado por cada categoría y el porcentaje respecto al total.
  4. El sistema muestra el reporte en forma de tabla/gráfico.
- **Flujos Alternativos:**
  - 2a. Si no existen movimientos en el mes seleccionado, el sistema muestra un mensaje indicando que no hay datos para ese periodo.

### CU-05: Consultar saldo actual y diezmo acumulado
- **Actor:** Usuario propietario.
- **Precondiciones:** El sistema debe tener al menos un movimiento registrado.
- **Flujo Principal:**
  1. El usuario accede al panel principal (dashboard).
  2. El sistema calcula el saldo disponible (ingresos − egresos − diezmo apartado).
  3. El sistema calcula el diezmo acumulado del mes y del año en curso.
  4. El sistema muestra ambos valores de forma destacada.
- **Flujos Alternativos:**
  - 2a. Si no existen movimientos registrados, el sistema muestra saldo y diezmo en cero con un mensaje de bienvenida/orientación.

---

## 9. Criterios de Aceptación

1. **Given** el usuario está en el formulario de nuevo movimiento, **When** ingresa un monto de $0, **Then** el sistema muestra un error y no permite guardar.
2. **Given** el usuario registra un ingreso de $1,000, **When** el sistema calcula el diezmo, **Then** el diezmo mostrado es de $100.
3. **Given** el usuario registra un egreso con fecha futura sin marcarlo como planificado, **When** intenta guardar, **Then** el sistema rechaza el registro con un mensaje explicativo.
4. **Given** un movimiento pertenece a un mes anterior al actual, **When** el usuario intenta editarlo, **Then** el sistema bloquea la edición.
5. **Given** una categoría tiene movimientos asociados, **When** el usuario intenta eliminarla, **Then** el sistema impide la eliminación y muestra el motivo.
6. **Given** el usuario ha registrado varios ingresos en el mes, **When** consulta el diezmo mensual, **Then** el sistema muestra la suma correcta del 10% de cada ingreso.
7. **Given** existen movimientos activos e inactivos (eliminados), **When** se calcula el saldo, **Then** solo se consideran los movimientos activos.
8. **Given** el usuario filtra movimientos por una categoría específica, **When** aplica el filtro, **Then** el listado muestra únicamente movimientos de esa categoría.
9. **Given** el usuario consulta el reporte mensual, **When** selecciona un mes sin movimientos, **Then** el sistema muestra un mensaje indicando ausencia de datos, sin errores.
10. **Given** el usuario edita el monto de un ingreso ya registrado, **When** guarda los cambios, **Then** el diezmo asociado a ese ingreso se recalcula automáticamente.
11. **Given** el usuario consulta el diezmo acumulado anual, **When** han transcurrido varios meses con ingresos, **Then** el sistema muestra la suma de los diezmos mensuales consolidados del año.

---

## 10. Riesgos Técnicos

| ID | Riesgo | Impacto | Probabilidad | Mitigación |
|----|--------|---------|---------------|------------|
| RT-01 | Cálculo incorrecto del diezmo por error en la lógica de redondeo | Alto | Media | Centralizar el cálculo en un único servicio con pruebas unitarias dedicadas. |
| RT-02 | Pérdida de precisión decimal al almacenar montos como tipos flotantes | Alto | Media | Usar tipos de dato decimales (BigDecimal) en backend y base de datos. |
| RT-03 | Pérdida de datos por ausencia de respaldo automático | Alto | Media | Implementar backups periódicos automáticos de la base de datos. |
| RT-04 | Registro inconsistente por olvido del usuario de ingresar movimientos | Medio | Alta | Agregar recordatorios/notificaciones opcionales en versiones futuras. |
| RT-05 | Eliminación accidental de una categoría con movimientos asociados | Medio | Baja | Validar en backend la existencia de movimientos antes de permitir el borrado. |
| RT-06 | Inconsistencia entre saldo mostrado y movimientos reales por fallos de sincronización de caché en frontend | Medio | Media | Recalcular saldo desde el backend en cada consulta, evitando cálculos solo en cliente. |
| RT-07 | Dificultad de escalar el modelo de datos a multiusuario en el futuro | Medio | Media | Diseñar el modelo de datos considerando un campo de usuario/propietario desde el inicio, aunque no se use en V1. |
| RT-08 | Errores de validación de fechas por diferencias de zona horaria entre frontend y backend | Medio | Media | Estandarizar el manejo de fechas en UTC y normalizar en la capa de presentación. |
| RT-09 | Crecimiento del volumen de movimientos degradando el rendimiento de reportes | Medio | Baja | Indexar adecuadamente las columnas de fecha y categoría en la base de datos. |

---

**Fin del documento**
