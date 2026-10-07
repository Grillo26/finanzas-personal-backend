# Cálculo y Seguimiento del Diezmo
**Módulo:** Gestión del Diezmo
**Versión:** 1.0
**Fecha:** 13 de septiembre de 2026
**Estado:** Borrador

---

## 1. Introducción

El submódulo de Cálculo y Seguimiento del Diezmo es el componente encargado de garantizar que, de manera automática y confiable, se calcule, consolide y muestre el diezmo correspondiente a cada ingreso registrado por el usuario. A diferencia de otros submódulos que son de uso puramente administrativo, este tiene una motivación personal y espiritual concreta: asegurar que la aportación del diezmo nunca se pierda por desorden, olvido o falta de cálculo manual.

El contexto operativo es el mismo del sistema general: se trata de una aplicación de uso personal, sin autenticación en V1, utilizable desde escritorio o móvil. El cálculo del diezmo depende directamente de los ingresos registrados en el submódulo de Registro de Ingresos y Egresos, por lo que este submódulo actúa principalmente como un consumidor y consolidador de esa información, más que como un punto de captura independiente.

Actualmente, sin un mecanismo automatizado, el usuario debe recordar manualmente calcular el 10% de cada ingreso recibido, sumar estos montos a lo largo del mes y llevar un control aparte de cuánto ya ha entregado como diezmo. Este proceso manual es propenso a errores de cálculo, omisiones de ingresos (especialmente ingresos no salariales, como ventas ocasionales o regalos) y pérdida de visibilidad sobre el diezmo acumulado en el año.

El valor que aporta este submódulo es la certeza y la disciplina: al calcularse automáticamente en el momento mismo del registro del ingreso, el usuario nunca tiene que preguntarse "¿ya calculé el diezmo de este ingreso?". Además, la posibilidad de consultar el diezmo acumulado mensual y anual da al usuario una visión clara de su fidelidad en esta práctica a lo largo del tiempo.

Este submódulo se integra directamente con el submódulo de Registro de Ingresos y Egresos (fuente de los ingresos sobre los que se calcula el diezmo), con el submódulo de Categorías (permite excluir ciertas categorías de ingreso del cálculo, si el usuario así lo configura), y con el submódulo de Reportes (el diezmo mensual y anual se presenta como parte de los reportes financieros generales).

---

## 2. Alcance

- **Incluido en este módulo:**
  - Cálculo automático del diezmo (10%) por cada ingreso registrado.
  - Consulta del diezmo acumulado del mes en curso.
  - Consulta del diezmo acumulado del año en curso, desglosado por mes.
  - Historial detallado de diezmo por ingreso (trazabilidad de cómo se compone el total).
  - Marcado de un ingreso como "excluido del cálculo de diezmo" (por ejemplo, un reembolso).
  - Registro del momento en que el diezmo fue efectivamente entregado (marcar como "pagado" o "pendiente").
  - Recalculo automático del diezmo cuando se edita o elimina un ingreso.
  - Configuración del porcentaje de diezmo (por defecto 10%, editable si el usuario lo desea).

- **Excluido de este módulo (V1):**
  - Gestión de otras ofrendas distintas al diezmo (ofrendas especiales, misiones) con reglas propias.
  - Recordatorios o notificaciones automáticas de pago pendiente (se contempla para versiones futuras).
  - Integración con pasarelas de pago o transferencias bancarias para el pago del diezmo.
  - Cálculo de diezmo sobre bases distintas al ingreso bruto (ej. neto de impuestos), salvo configuración manual futura.

- **Actores / Roles involucrados:**
  - **Usuario propietario:** único rol existente en V1, con control total sobre la configuración y consulta del diezmo.

- **Establecimientos aplicables:** No aplica (uso personal).

---

## 3. Justificación

### 3.1 Justificación Operativa
Automatizar el cálculo del diezmo elimina una tarea manual repetitiva y propensa a errores, liberando al usuario de tener que recordar hacer este cálculo cada vez que recibe un ingreso, y asegurando que el monto sea siempre exacto y consistente.

### 3.2 Justificación Personal / Espiritual
El diezmo representa una práctica de fidelidad y orden en la vida financiera del usuario. Automatizar su cálculo desde el momento del registro del ingreso refuerza el compromiso con esta práctica, evita el olvido involuntario y permite al usuario tener plena claridad de cuánto ha aportado a lo largo del tiempo, fortaleciendo su tranquilidad y disciplina espiritual.

### 3.3 Justificación Técnica
Aislar la lógica de cálculo del diezmo en un servicio dedicado permite modificar en el futuro las reglas del cálculo (por ejemplo, cambiar el porcentaje o excluir ciertos tipos de ingreso) sin afectar el submódulo de registro de movimientos, respetando el principio de responsabilidad única y facilitando las pruebas unitarias de esta lógica sensible.

---

## 4. Funciones Principales

### FP-01: Calcular diezmo por ingreso
- **Descripción:** Al registrarse un ingreso, el sistema calcula automáticamente el diezmo correspondiente (por defecto, 10% del monto).
- **Actor principal:** Sistema.
- **Resultado esperado:** Cada ingreso queda asociado a un monto de diezmo calculado y visible.

### FP-02: Consultar diezmo mensual
- **Descripción:** Permite ver el total de diezmo generado por los ingresos del mes en curso.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** Se muestra el monto total y el detalle de los ingresos que lo componen.

### FP-03: Consultar diezmo anual acumulado
- **Descripción:** Permite ver el total de diezmo acumulado en el año, desglosado mes a mes.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** Se presenta una vista consolidada del comportamiento del diezmo a lo largo del año.

### FP-04: Marcar diezmo como pagado
- **Descripción:** Permite indicar que el diezmo correspondiente a un ingreso (o a un periodo) ya fue entregado.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El sistema distingue entre diezmo pendiente de entregar y diezmo ya pagado.

### FP-05: Excluir ingreso del cálculo de diezmo
- **Descripción:** Permite marcar un ingreso específico (ej. un reembolso o préstamo recibido) como no sujeto a diezmo.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El ingreso no se contabiliza dentro del total de diezmo generado.

### FP-06: Configurar porcentaje de diezmo
- **Descripción:** Permite al usuario ajustar el porcentaje de cálculo del diezmo (por defecto 10%), aplicable a futuros ingresos.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El sistema utiliza el nuevo porcentaje configurado para todos los cálculos posteriores, sin alterar los ya generados.

---

## 5. Requerimientos Funcionales

| ID | Nombre | Descripción | Prioridad | Actor |
|----|--------|-------------|-----------|-------|
| RF-001 | Calcular diezmo automáticamente | El sistema debe calcular el diezmo de cada ingreso aplicando el porcentaje configurado (10% por defecto) al monto bruto. | Alta | Sistema |
| RF-002 | Recalcular diezmo al editar ingreso | El sistema debe recalcular el diezmo asociado cuando el monto de un ingreso es modificado. | Alta | Sistema |
| RF-003 | Anular diezmo al eliminar ingreso | El sistema debe descartar el diezmo asociado cuando el ingreso correspondiente es eliminado. | Alta | Sistema |
| RF-004 | Consultar diezmo del mes actual | El sistema debe mostrar el total de diezmo generado por los ingresos del mes en curso. | Alta | Usuario propietario |
| RF-005 | Ver detalle de diezmo por ingreso | El sistema debe permitir consultar, para cada ingreso, cuánto diezmo generó individualmente. | Alta | Usuario propietario |
| RF-006 | Consultar diezmo acumulado anual | El sistema debe mostrar el total de diezmo acumulado durante el año en curso. | Alta | Usuario propietario |
| RF-007 | Desglosar diezmo por mes | El sistema debe presentar el diezmo anual desglosado en montos mensuales. | Media | Usuario propietario |
| RF-008 | Marcar diezmo como pagado | El sistema debe permitir marcar el diezmo de un periodo (o de un ingreso puntual) como efectivamente pagado. | Media | Usuario propietario |
| RF-009 | Marcar diezmo como pendiente | El sistema debe permitir revertir el estado de un diezmo de "pagado" a "pendiente" en caso de error. | Baja | Usuario propietario |
| RF-010 | Consultar diezmo pendiente | El sistema debe mostrar el total de diezmo generado que aún no ha sido marcado como pagado. | Alta | Usuario propietario |
| RF-011 | Excluir ingreso del cálculo | El sistema debe permitir marcar un ingreso específico como "no sujeto a diezmo". | Media | Usuario propietario |
| RF-012 | Recalcular al cambiar exclusión | El sistema debe actualizar el total de diezmo del periodo cuando se marca o desmarca un ingreso como excluido. | Media | Sistema |
| RF-013 | Configurar porcentaje de diezmo | El sistema debe permitir configurar el porcentaje aplicado al cálculo del diezmo, con 10% como valor por defecto. | Media | Usuario propietario |
| RF-014 | Aplicar nuevo porcentaje solo a futuro | El sistema debe aplicar cambios de porcentaje únicamente a los ingresos registrados después del cambio, sin alterar el histórico. | Alta | Sistema |
| RF-015 | Mostrar corte mensual configurable | El sistema debe permitir definir el día de corte del mes para efectos de consolidación del diezmo (por defecto, el último día calendario). | Baja | Usuario propietario |
| RF-016 | Consultar histórico de diezmo por año | El sistema debe permitir consultar el diezmo acumulado de años anteriores, no solo del año actual. | Media | Usuario propietario |
| RF-017 | Alertar ingresos sin diezmo calculado | El sistema debe identificar y señalar ingresos que, por alguna inconsistencia, no tengan un diezmo calculado asociado. | Media | Sistema |
| RF-018 | Exportar resumen de diezmo | El sistema debe permitir exportar el resumen de diezmo mensual/anual en versiones futuras (CSV/Excel). | Baja | Usuario propietario |
| RF-019 | Mostrar porcentaje aplicado por ingreso | El sistema debe registrar y mostrar qué porcentaje de diezmo se aplicó a cada ingreso, incluso si luego cambia la configuración general. | Media | Sistema |
| RF-020 | Registrar fecha de pago del diezmo | El sistema debe permitir registrar la fecha en la que el diezmo fue efectivamente entregado. | Baja | Usuario propietario |
| RF-021 | Mostrar resumen en el panel principal | El sistema debe mostrar en el dashboard un resumen visible del diezmo del mes y del acumulado anual. | Alta | Sistema |

---

## 6. Requerimientos No Funcionales

**Rendimiento**
- RNF-001: El cálculo del diezmo de un ingreso debe ejecutarse en menos de 500 milisegundos.
- RNF-002: La consulta del diezmo acumulado anual debe responder en menos de 2 segundos, incluso con varios años de historial.

**Seguridad**
- RNF-003: El histórico de cálculos de diezmo no debe poder alterarse retroactivamente al cambiar la configuración del porcentaje.
- RNF-004: Los cambios de estado (pagado/pendiente) deben quedar registrados en el historial de auditoría.

**Disponibilidad**
- RNF-005: La consulta del diezmo debe estar disponible sin conexión a internet, al operar sobre datos almacenados localmente.

**Usabilidad**
- RNF-006: El resumen de diezmo mensual debe ser visible desde el panel principal sin necesidad de navegación adicional.
- RNF-007: El estado de "pendiente" o "pagado" debe representarse visualmente de forma clara (por ejemplo, mediante colores o etiquetas).

**Escalabilidad**
- RNF-008: El modelo de datos debe permitir en el futuro manejar distintos tipos de ofrendas (diezmo, ofrenda especial, misiones) sin rediseño mayor.
- RNF-009: El cálculo debe soportar eficientemente el crecimiento del historial a varios años sin degradar el rendimiento de las consultas.

**Interoperabilidad**
- RNF-010: El resumen de diezmo debe poder integrarse en el submódulo de Reportes sin duplicar lógica de cálculo.

**Mantenibilidad**
- RNF-011: La lógica de cálculo del diezmo debe estar centralizada en un único servicio, reutilizado tanto por el registro de ingresos como por los reportes.
- RNF-012: Los cambios de porcentaje deben implementarse de forma versionada (histórico de configuración), evitando sobrescribir configuraciones anteriores.

**Trazabilidad**
- RNF-013: Cada registro de diezmo debe conservar el porcentaje aplicado, la fecha de cálculo y su estado (pagado/pendiente).
- RNF-014: Debe poder auditarse en cualquier momento qué ingresos fueron excluidos del cálculo de diezmo y por qué.

---

## 7. Reglas de Negocio

- **RN-001:** El diezmo se calcula por defecto como el 10% del monto bruto de cada ingreso, sin aplicar descuentos previos de ningún tipo.
- **RN-002:** El cálculo del diezmo ocurre en el momento del registro del ingreso y se recalcula automáticamente ante cualquier edición del monto.
- **RN-003:** Si un ingreso es eliminado, su diezmo asociado se anula y deja de contarse en los totales mensuales y anuales.
- **RN-004:** Un ingreso puede marcarse explícitamente como "excluido del cálculo de diezmo"; en ese caso, su monto no genera ningún valor de diezmo.
- **RN-005:** El porcentaje de diezmo por defecto es 10%, pero puede configurarse a un valor distinto por decisión del usuario.
- **RN-006:** Un cambio en el porcentaje de diezmo aplica únicamente a los ingresos registrados después del cambio; los cálculos previos conservan el porcentaje vigente al momento de su generación.
- **RN-007:** El diezmo mensual se consolida entre el primer y el último día calendario del mes, salvo que el usuario configure un día de corte distinto.
- **RN-008:** El diezmo acumulado anual es la suma de los diezmos mensuales consolidados desde enero hasta el mes actual del año correspondiente.
- **RN-009:** El estado de un diezmo puede ser únicamente "pendiente" o "pagado"; no existen estados intermedios en V1.
- **RN-010:** Marcar un diezmo como "pagado" no impide que, si el ingreso asociado se edita, el sistema recalcule y notifique la diferencia resultante.
- **RN-011:** El monto de diezmo se almacena y presenta con exactamente 2 decimales, utilizando redondeo estándar (half-up) en caso de fracciones.
- **RN-012:** Todo ingreso registrado, salvo que se marque explícitamente como excluido, genera diezmo de forma obligatoria; no existe una opción para omitirlo de forma implícita.
- **RN-013:** El historial de diezmo por ingreso es inmutable en cuanto al porcentaje aplicado en su momento, incluso si la configuración general cambia después.

---

## 8. Casos de Uso Principales

### CU-01: Calcular el diezmo al registrar un ingreso
- **Actor:** Sistema (disparado por el registro de un ingreso realizado por el Usuario propietario).
- **Precondiciones:** Debe existir un porcentaje de diezmo configurado (10% por defecto).
- **Flujo Principal:**
  1. El usuario registra un nuevo ingreso desde el submódulo de Movimientos.
  2. El sistema identifica que el ingreso no está marcado como excluido.
  3. El sistema calcula el diezmo aplicando el porcentaje vigente sobre el monto del ingreso.
  4. El sistema almacena el registro de diezmo asociado, en estado "pendiente".
- **Flujos Alternativos:**
  - 2a. Si el ingreso está marcado como excluido, el sistema omite el cálculo y registra diezmo en cero para ese ingreso.

### CU-02: Consultar el diezmo mensual
- **Actor:** Usuario propietario.
- **Precondiciones:** Deben existir ingresos registrados en el mes en curso.
- **Flujo Principal:**
  1. El usuario accede a la sección de Diezmo.
  2. El sistema recupera todos los registros de diezmo generados en el mes en curso.
  3. El sistema suma los montos y presenta el total, junto con el detalle por ingreso.
- **Flujos Alternativos:**
  - 2a. Si no hay ingresos registrados en el mes, el sistema muestra el total en cero con un mensaje informativo.

### CU-03: Marcar el diezmo como pagado
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un registro de diezmo en estado "pendiente".
- **Flujo Principal:**
  1. El usuario accede al detalle de diezmo del mes.
  2. El usuario selecciona el diezmo pendiente y elige "Marcar como pagado".
  3. El usuario indica opcionalmente la fecha de pago.
  4. El sistema actualiza el estado a "pagado".
- **Flujos Alternativos:**
  - 4a. El usuario puede revertir el estado a "pendiente" si el pago se marcó por error.

### CU-04: Excluir un ingreso del cálculo del diezmo
- **Actor:** Usuario propietario.
- **Precondiciones:** El ingreso a excluir debe existir y estar activo.
- **Flujo Principal:**
  1. El usuario accede al detalle de un ingreso específico.
  2. El usuario activa la opción "Excluir del cálculo de diezmo".
  3. El sistema anula el diezmo previamente calculado para ese ingreso y actualiza los totales del periodo.
- **Flujos Alternativos:**
  - 3a. Si el usuario desmarca la exclusión posteriormente, el sistema vuelve a calcular el diezmo correspondiente.

### CU-05: Consultar el diezmo acumulado anual
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un mes con diezmo generado en el año consultado.
- **Flujo Principal:**
  1. El usuario accede a la sección de Diezmo y selecciona la vista anual.
  2. El sistema recupera los totales mensuales de diezmo del año seleccionado.
  3. El sistema presenta el desglose mes a mes junto con el total acumulado.
- **Flujos Alternativos:**
  - 2a. Si el usuario selecciona un año sin datos, el sistema muestra un mensaje indicando ausencia de información para ese periodo.

---

## 9. Criterios de Aceptación

1. **Given** el usuario registra un ingreso de $2,000, **When** el sistema calcula el diezmo, **Then** el monto de diezmo generado es de $200 (10%).
2. **Given** un ingreso está marcado como excluido, **When** se registra, **Then** el sistema no genera ningún monto de diezmo para ese ingreso.
3. **Given** un ingreso con diezmo ya calculado es editado, **When** el usuario cambia el monto, **Then** el sistema recalcula el diezmo correspondiente automáticamente.
4. **Given** un ingreso con diezmo asociado es eliminado, **When** se confirma la eliminación, **Then** el diezmo asociado deja de contarse en los totales mensuales.
5. **Given** el usuario configura el porcentaje de diezmo al 12%, **When** registra un nuevo ingreso, **Then** el cálculo utiliza el 12%, mientras que los ingresos anteriores conservan el 10% original.
6. **Given** existen varios ingresos en el mes, **When** el usuario consulta el diezmo mensual, **Then** el total mostrado corresponde a la suma exacta del diezmo individual de cada ingreso no excluido.
7. **Given** el usuario marca un diezmo como "pagado", **When** consulta el detalle, **Then** el sistema refleja el nuevo estado y la fecha de pago registrada.
8. **Given** el usuario consulta el diezmo acumulado anual, **When** han transcurrido varios meses, **Then** el sistema muestra el desglose correcto mes a mes y el total sumado.
9. **Given** no existen ingresos registrados en un mes determinado, **When** el usuario consulta el diezmo de ese mes, **Then** el sistema muestra el total en cero sin generar errores.
10. **Given** un ingreso fue excluido y luego se revierte esa exclusión, **When** el usuario consulta el diezmo del periodo, **Then** el monto correspondiente vuelve a incluirse en el total.

---

## 10. Riesgos Técnicos

| ID | Riesgo | Impacto | Probabilidad | Mitigación |
|----|--------|---------|---------------|------------|
| RT-01 | Error de redondeo en el cálculo del 10% generando descuadres mínimos entre el detalle y el total | Alto | Media | Usar tipos de dato decimales exactos (BigDecimal) y una única función de redondeo estandarizada en todo el sistema. |
| RT-02 | Cambios en el porcentaje de diezmo alterando incorrectamente cálculos históricos ya generados | Alto | Media | Almacenar el porcentaje aplicado en cada registro de diezmo individual, en lugar de recalcular con la configuración vigente. |
| RT-03 | Inconsistencia entre el diezmo calculado y el ingreso real tras una edición no propagada correctamente | Alto | Media | Implementar el recálculo del diezmo como parte de la misma transacción que la edición del ingreso. |
| RT-04 | Pérdida de trazabilidad de ingresos excluidos del cálculo | Medio | Baja | Registrar de forma explícita y auditable el motivo y la fecha de exclusión de cada ingreso. |
| RT-05 | Confusión del usuario entre diezmo "pendiente" y diezmo "apartado" pero no reflejado en el saldo disponible | Medio | Media | Documentar claramente en la interfaz que el diezmo pendiente ya se descuenta del saldo disponible, aunque no se haya entregado físicamente. |
| RT-06 | Desfase entre el corte mensual configurado y el calendario natural, generando ambigüedad en qué mes pertenece un ingreso | Medio | Baja | Validar y documentar claramente las reglas de corte configurado, con pruebas específicas para fechas límite. |
| RT-07 | Degradación del rendimiento al consultar diezmo acumulado en cuentas con varios años de historial | Medio | Baja | Precalcular y almacenar totales mensuales consolidados en lugar de recalcular desde el detalle en cada consulta. |
| RT-08 | Errores de sincronización entre el submódulo de Diezmo y el submódulo de Reportes al mostrar cifras distintas para el mismo periodo | Alto | Media | Centralizar el cálculo del diezmo en un único servicio reutilizado por ambos submódulos, evitando lógica duplicada. |

---

**Fin del documento**
