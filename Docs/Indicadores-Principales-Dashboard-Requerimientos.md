# Indicadores Principales
**Módulo:** Dashboard
**Versión:** 1.0
**Fecha:** 13 de septiembre de 2026
**Estado:** Borrador

---

## 1. Introducción

El submódulo de Indicadores Principales constituye la pantalla de entrada del sistema de finanzas personales: el primer lugar que el usuario ve al abrir la aplicación. Su propósito es consolidar, en una sola vista, los datos más relevantes que ya fueron calculados por otros submódulos (Movimientos, Diezmo y Reportes), presentándolos de forma resumida, visual y accionable, sin que el usuario tenga que navegar por distintas secciones para conocer su situación financiera actual.

El contexto operativo es el mismo del sistema general: uso personal, sin autenticación en V1, pensado para consultarse rápidamente tanto en escritorio como en móvil, muchas veces como una revisión de "un vistazo" antes de tomar una decisión de gasto. A diferencia de los demás submódulos, el Dashboard no captura ni modifica información: es exclusivamente una capa de consulta y visualización agregada.

Actualmente, sin un panel consolidado, el usuario tendría que entrar al submódulo de Movimientos para ver su saldo, luego a Diezmo para saber cuánto ha acumulado, y luego a Reportes para saber en qué está gastando más, repitiendo este recorrido cada vez que quiere una visión general. Esto genera fricción y reduce la probabilidad de que el usuario revise su situación financiera con la frecuencia deseada.

El valor que aporta este submódulo es la inmediatez: con una sola pantalla, el usuario conoce su saldo disponible, su diezmo pendiente y acumulado, su categoría de mayor gasto del mes, y la tendencia general de sus finanzas, todo actualizado en tiempo real a medida que registra nuevos movimientos.

Este submódulo se integra directamente con el submódulo de Registro de Ingresos y Egresos (fuente del saldo disponible), con el submódulo de Diezmo (fuente del diezmo pendiente y acumulado), y con el submódulo de Reportes (fuente de la categoría de mayor gasto y la tendencia mensual). El Dashboard no contiene lógica de cálculo propia: reutiliza los servicios ya existentes de esos submódulos.

---

## 2. Alcance

- **Incluido en este módulo:**
  - Visualización del saldo disponible actual.
  - Visualización del diezmo del mes en curso (generado y pendiente de pago).
  - Visualización del diezmo acumulado en el año.
  - Indicador de la categoría con mayor gasto del mes en curso.
  - Resumen rápido de ingresos y egresos totales del mes en curso.
  - Comparación breve del gasto total del mes actual contra el mes anterior (indicador de tendencia: sube/baja).
  - Accesos directos a las funciones más usadas (registrar ingreso, registrar egreso, ver reporte completo).
  - Alerta visual si existe diezmo pendiente de pago.

- **Excluido de este módulo (V1):**
  - Personalización o configuración del layout del dashboard (widgets fijos en V1, no reordenables).
  - Indicadores basados en presupuestos o metas de ahorro (no existe submódulo de presupuestos en V1).
  - Notificaciones push o alertas fuera de la propia pantalla del dashboard.
  - Comparaciones multianuales o proyecciones de gasto futuro.

- **Actores / Roles involucrados:**
  - **Usuario propietario:** único rol existente en V1, consumidor exclusivo del dashboard.

- **Establecimientos aplicables:** No aplica (uso personal).

---

## 3. Justificación

### 3.1 Justificación Operativa
Consolidar los indicadores más relevantes en una sola pantalla reduce el número de pasos que el usuario debe realizar para conocer su situación financiera, fomentando una revisión más frecuente y, por lo tanto, una mejor toma de decisiones de gasto en el día a día.

### 3.2 Justificación Personal / Espiritual
Mostrar de forma destacada el diezmo pendiente y acumulado en la primera pantalla del sistema asegura que esta prioridad esté siempre visible para el usuario, reforzando el compromiso de entregarlo oportunamente y evitando que quede "escondido" dentro de un submódulo secundario.

### 3.3 Justificación Técnica
Diseñar el Dashboard como una capa de solo lectura que reutiliza los servicios ya existentes de Movimientos, Diezmo y Reportes evita la duplicación de lógica de cálculo, reduce el riesgo de inconsistencias entre pantallas y simplifica el mantenimiento futuro, ya que cualquier cambio en las reglas de negocio se refleja automáticamente en el dashboard sin necesidad de modificarlo directamente.

---

## 4. Funciones Principales

### FP-01: Mostrar saldo disponible
- **Descripción:** Presenta de forma destacada el saldo disponible actual, calculado por el submódulo de Movimientos.
- **Actor principal:** Sistema.
- **Resultado esperado:** El usuario conoce de inmediato cuánto dinero tiene disponible.

### FP-02: Mostrar resumen de diezmo
- **Descripción:** Presenta el diezmo generado en el mes, el pendiente de pago y el acumulado anual.
- **Actor principal:** Sistema.
- **Resultado esperado:** El usuario identifica rápidamente su situación respecto al diezmo.

### FP-03: Mostrar categoría de mayor gasto
- **Descripción:** Indica cuál categoría concentra el mayor gasto del mes en curso.
- **Actor principal:** Sistema.
- **Resultado esperado:** El usuario identifica en qué se está gastando más dinero sin entrar al reporte completo.

### FP-04: Mostrar tendencia de gasto mensual
- **Descripción:** Compara el gasto total del mes actual contra el mes anterior y muestra un indicador visual de aumento o disminución.
- **Actor principal:** Sistema.
- **Resultado esperado:** El usuario percibe rápidamente si está gastando más o menos que el periodo anterior.

### FP-05: Proveer accesos directos
- **Descripción:** Ofrece botones de acceso rápido a las acciones más frecuentes: registrar ingreso, registrar egreso y ver el reporte completo.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El usuario reduce la cantidad de clics necesarios para realizar sus tareas más comunes.

### FP-06: Alertar diezmo pendiente
- **Descripción:** Muestra una alerta visual destacada cuando existe diezmo generado y aún no marcado como pagado.
- **Actor principal:** Sistema.
- **Resultado esperado:** El usuario no olvida que tiene un compromiso de diezmo pendiente de entregar.

---

## 5. Requerimientos Funcionales

| ID | Nombre | Descripción | Prioridad | Actor |
|----|--------|-------------|-----------|-------|
| RF-001 | Mostrar saldo disponible actual | El sistema debe mostrar en el dashboard el saldo disponible calculado por el submódulo de Movimientos. | Alta | Sistema |
| RF-002 | Mostrar ingresos totales del mes | El sistema debe mostrar el total de ingresos registrados en el mes en curso. | Alta | Sistema |
| RF-003 | Mostrar egresos totales del mes | El sistema debe mostrar el total de egresos registrados en el mes en curso. | Alta | Sistema |
| RF-004 | Mostrar diezmo generado del mes | El sistema debe mostrar el total de diezmo generado en el mes en curso. | Alta | Sistema |
| RF-005 | Mostrar diezmo pendiente de pago | El sistema debe mostrar el monto de diezmo generado que aún no ha sido marcado como pagado. | Alta | Sistema |
| RF-006 | Mostrar diezmo acumulado anual | El sistema debe mostrar el total de diezmo acumulado en el año en curso. | Media | Sistema |
| RF-007 | Mostrar categoría de mayor gasto | El sistema debe identificar y mostrar la categoría con mayor monto de egresos del mes en curso. | Media | Sistema |
| RF-008 | Mostrar tendencia de gasto vs. mes anterior | El sistema debe calcular y mostrar si el gasto total del mes actual aumentó o disminuyó respecto al mes anterior. | Media | Sistema |
| RF-009 | Mostrar porcentaje de variación de gasto | El sistema debe mostrar el porcentaje exacto de variación entre el mes actual y el anterior. | Baja | Sistema |
| RF-010 | Proveer acceso directo a registrar ingreso | El sistema debe incluir un botón de acceso directo al formulario de registro de ingreso. | Alta | Usuario propietario |
| RF-011 | Proveer acceso directo a registrar egreso | El sistema debe incluir un botón de acceso directo al formulario de registro de egreso. | Alta | Usuario propietario |
| RF-012 | Proveer acceso directo al reporte completo | El sistema debe incluir un enlace directo a la sección de Reportes mensuales. | Media | Usuario propietario |
| RF-013 | Alertar diezmo pendiente visualmente | El sistema debe mostrar una alerta o indicador visual destacado cuando exista diezmo pendiente de pago. | Alta | Sistema |
| RF-014 | Ocultar alerta si no hay diezmo pendiente | El sistema debe ocultar la alerta de diezmo pendiente cuando el usuario haya marcado todo el diezmo del periodo como pagado. | Media | Sistema |
| RF-015 | Actualizar indicadores en tiempo real | El sistema debe reflejar en el dashboard cualquier movimiento nuevo, editado o eliminado sin requerir recarga manual de la página. | Alta | Sistema |
| RF-016 | Mostrar mensaje de bienvenida sin datos | El sistema debe mostrar un mensaje orientativo cuando no existan movimientos registrados aún en el sistema. | Baja | Sistema |
| RF-017 | Mostrar fecha de última actualización | El sistema debe indicar la fecha/hora en que se calcularon por última vez los indicadores mostrados. | Baja | Sistema |
| RF-018 | Adaptar indicadores a pantallas móviles | El sistema debe reorganizar los indicadores en una sola columna cuando se accede desde un dispositivo móvil. | Media | Sistema |

---

## 6. Requerimientos No Funcionales

**Rendimiento**
- RNF-001: El dashboard debe cargar todos sus indicadores en menos de 2 segundos en condiciones normales de uso.
- RNF-002: Las actualizaciones en tiempo real de los indicadores no deben requerir más de 1 segundo adicional tras registrar un movimiento.

**Seguridad**
- RNF-003: El dashboard debe consultar únicamente datos ya validados y calculados por los submódulos correspondientes, sin duplicar reglas de negocio sensibles como el cálculo del diezmo.

**Disponibilidad**
- RNF-004: El dashboard debe poder mostrarse sin conexión a internet, utilizando los datos almacenados localmente.

**Usabilidad**
- RNF-005: Los indicadores más importantes (saldo disponible y diezmo pendiente) deben ubicarse en la parte superior de la pantalla, sin necesidad de desplazamiento (scroll).
- RNF-006: El dashboard debe ser el punto de entrada por defecto al abrir la aplicación.

**Escalabilidad**
- RNF-007: El diseño del dashboard debe permitir agregar nuevos indicadores en el futuro (por ejemplo, metas de ahorro) sin rediseñar la estructura general de la pantalla.

**Interoperabilidad**
- RNF-008: El dashboard debe consumir los mismos servicios de backend utilizados por los submódulos de Movimientos, Diezmo y Reportes, sin crear endpoints redundantes.

**Mantenibilidad**
- RNF-009: La lógica de presentación del dashboard debe mantenerse completamente separada de la lógica de cálculo, que reside en los servicios de los submódulos de origen.

**Trazabilidad**
- RNF-010: El dashboard debe indicar claramente la fecha de corte de los datos mostrados, evitando ambigüedad sobre si la información está actualizada.

---

## 7. Reglas de Negocio

- **RN-001:** El dashboard no realiza cálculos propios; todos los valores mostrados provienen de los servicios ya existentes de Movimientos, Diezmo y Reportes.
- **RN-002:** El saldo disponible mostrado en el dashboard debe coincidir exactamente con el calculado en el submódulo de Movimientos para el mismo momento.
- **RN-003:** El diezmo pendiente mostrado corresponde a la suma de todos los registros de diezmo en estado "pendiente", sin límite de periodo (puede incluir meses anteriores no pagados).
- **RN-004:** La categoría de mayor gasto se determina exclusivamente sobre las categorías de tipo egreso del mes en curso.
- **RN-005:** La tendencia de gasto se calcula comparando el total de egresos del mes actual contra el total de egresos del mes calendario inmediatamente anterior.
- **RN-006:** Si no existe información suficiente para calcular la tendencia (por ejemplo, es el primer mes de uso del sistema), el indicador de tendencia debe mostrarse como "sin datos comparables" en lugar de un valor erróneo.
- **RN-007:** La alerta de diezmo pendiente se muestra siempre que exista al menos un registro de diezmo sin marcar como pagado, independientemente del mes al que pertenezca.
- **RN-008:** Los accesos directos del dashboard deben dirigir exactamente a los mismos formularios y pantallas utilizados por los submódulos correspondientes, sin duplicar su lógica.

---

## 8. Casos de Uso Principales

### CU-01: Consultar el dashboard al abrir la aplicación
- **Actor:** Usuario propietario.
- **Precondiciones:** El sistema debe estar operativo y con acceso a la base de datos local.
- **Flujo Principal:**
  1. El usuario abre la aplicación.
  2. El sistema carga automáticamente el dashboard como pantalla inicial.
  3. El sistema consulta los servicios de Movimientos, Diezmo y Reportes para obtener los valores actuales.
  4. El sistema presenta los indicadores consolidados en pantalla.
- **Flujos Alternativos:**
  - 3a. Si no existen movimientos registrados aún, el sistema muestra un mensaje de bienvenida orientativo en lugar de indicadores vacíos.

### CU-02: Detectar diezmo pendiente desde el dashboard
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un registro de diezmo en estado "pendiente".
- **Flujo Principal:**
  1. El usuario accede al dashboard.
  2. El sistema identifica que existe diezmo pendiente de pago.
  3. El sistema muestra una alerta visual destacada indicando el monto pendiente.
- **Flujos Alternativos:**
  - 2a. Si todo el diezmo generado ya fue marcado como pagado, el sistema no muestra ninguna alerta.

### CU-03: Acceder rápidamente a registrar un movimiento
- **Actor:** Usuario propietario.
- **Precondiciones:** El usuario se encuentra en el dashboard.
- **Flujo Principal:**
  1. El usuario presiona el botón de acceso directo "Registrar ingreso" o "Registrar egreso".
  2. El sistema redirige al formulario correspondiente del submódulo de Movimientos.
  3. El usuario completa el registro normalmente.
  4. Al finalizar, el sistema regresa al dashboard con los indicadores actualizados.
- **Flujos Alternativos:**
  - 3a. Si el usuario cancela el registro, el sistema regresa al dashboard sin modificar los indicadores.

### CU-04: Consultar la categoría de mayor gasto del mes
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un egreso registrado en el mes en curso.
- **Flujo Principal:**
  1. El usuario consulta el dashboard.
  2. El sistema muestra la categoría con mayor monto de egresos del mes.
  3. El usuario puede presionar sobre dicho indicador para navegar directamente al reporte detallado de esa categoría.
- **Flujos Alternativos:**
  - 2a. Si no hay egresos registrados en el mes, el sistema no muestra ninguna categoría destacada.

### CU-05: Consultar la tendencia de gasto mensual
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un movimiento en el mes anterior al actual.
- **Flujo Principal:**
  1. El usuario consulta el dashboard.
  2. El sistema calcula la variación entre el gasto total del mes actual y el del mes anterior.
  3. El sistema muestra un indicador visual (flecha arriba/abajo) junto con el porcentaje de variación.
- **Flujos Alternativos:**
  - 2a. Si el mes anterior no tiene movimientos, el sistema indica que no hay datos comparables disponibles.

---

## 9. Criterios de Aceptación

1. **Given** el usuario abre la aplicación, **When** el sistema carga la pantalla inicial, **Then** se muestra el dashboard con el saldo disponible actualizado.
2. **Given** existe diezmo generado y no pagado, **When** el usuario consulta el dashboard, **Then** se muestra una alerta visual indicando el monto pendiente.
3. **Given** todo el diezmo generado ya fue marcado como pagado, **When** el usuario consulta el dashboard, **Then** no se muestra ninguna alerta de diezmo pendiente.
4. **Given** existen egresos registrados en distintas categorías del mes, **When** se carga el dashboard, **Then** se muestra correctamente la categoría con el mayor monto de gasto.
5. **Given** el usuario presiona el acceso directo "Registrar ingreso" desde el dashboard, **When** completa el formulario, **Then** el sistema regresa al dashboard mostrando el saldo ya actualizado con el nuevo ingreso.
6. **Given** el mes anterior no tiene movimientos, **When** el usuario consulta la tendencia de gasto, **Then** el sistema muestra "sin datos comparables" en lugar de un porcentaje incorrecto.
7. **Given** el usuario aún no ha registrado ningún movimiento en el sistema, **When** abre el dashboard por primera vez, **Then** se muestra un mensaje de bienvenida orientativo en lugar de indicadores vacíos o con errores.
8. **Given** el usuario registra un nuevo egreso mientras el dashboard está abierto, **When** finaliza el registro, **Then** los indicadores del dashboard se actualizan automáticamente sin necesidad de recargar manualmente.
9. **Given** el usuario accede desde un dispositivo móvil, **When** visualiza el dashboard, **Then** los indicadores se reorganizan correctamente en una sola columna sin perder legibilidad.
10. **Given** el diezmo acumulado anual es de $1,200, **When** el usuario consulta el dashboard, **Then** el monto mostrado coincide exactamente con el calculado en el submódulo de Diezmo para el año en curso.

---

## 10. Riesgos Técnicos

| ID | Riesgo | Impacto | Probabilidad | Mitigación |
|----|--------|---------|---------------|------------|
| RT-01 | Inconsistencia entre los valores mostrados en el dashboard y los calculados en los submódulos de origen | Alto | Media | Consumir directamente los mismos servicios/endpoints utilizados por Movimientos, Diezmo y Reportes, sin duplicar lógica de cálculo en el frontend. |
| RT-02 | Lentitud en la carga inicial del dashboard por consultar múltiples servicios de forma secuencial | Medio | Media | Realizar las consultas a los distintos submódulos de forma paralela (asíncrona) en lugar de secuencial. |
| RT-03 | Datos desactualizados en el dashboard tras registrar un movimiento desde otra pantalla | Medio | Media | Forzar la recarga de los indicadores del dashboard cada vez que el usuario navega de regreso a esa pantalla. |
| RT-04 | Error al calcular la tendencia de gasto cuando no existe un mes anterior con datos (usuario nuevo) | Bajo | Alta | Validar explícitamente la existencia de datos del mes anterior antes de calcular el porcentaje de variación. |
| RT-05 | Sobrecarga visual del dashboard al intentar mostrar demasiados indicadores en pantallas pequeñas | Bajo | Media | Priorizar los indicadores esenciales (saldo y diezmo pendiente) y colapsar los secundarios en móvil. |
| RT-06 | Confusión del usuario si el "diezmo pendiente" incluye meses muy antiguos sin pagar, sin contexto claro | Bajo | Media | Indicar junto al monto pendiente desde qué periodo se está acumulando, o desglosar por mes si el monto es significativo. |
| RT-07 | Degradación del rendimiento a medida que crece el historial de movimientos utilizado para calcular los indicadores | Medio | Baja | Reutilizar los totales mensuales precalculados de los submódulos de Diezmo y Reportes, evitando recalcular desde el detalle completo. |
| RT-08 | Falta de sincronización visual entre el indicador de "categoría de mayor gasto" y el reporte detallado si difieren en criterios de cálculo | Medio | Baja | Reutilizar exactamente la misma consulta utilizada en el submódulo de Reportes para determinar la categoría de mayor gasto. |

---

**Fin del documento**
