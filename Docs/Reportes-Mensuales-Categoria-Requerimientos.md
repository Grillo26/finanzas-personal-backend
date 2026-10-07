# Reportes Mensuales por Categoría
**Módulo:** Gestión de Reportes
**Versión:** 1.0
**Fecha:** 13 de septiembre de 2026
**Estado:** Borrador

---

## 1. Introducción

El submódulo de Reportes Mensuales por Categoría es el componente analítico del sistema, encargado de transformar los movimientos individuales registrados en información agregada y comprensible para el usuario. Mientras que el submódulo de Registro de Ingresos y Egresos se enfoca en la captura de datos, este submódulo se enfoca en su interpretación: mostrar de forma clara en qué se está gastando el dinero mes a mes y cómo se comparan los ingresos frente a los egresos.

El contexto operativo es el mismo del sistema general: uso personal, sin autenticación en V1, accesible desde escritorio o móvil. Este submódulo no captura información nueva, sino que consulta y procesa los datos ya existentes en los submódulos de Movimientos, Categorías y Diezmo, presentándolos en un formato agregado, visual y fácil de interpretar.

Actualmente, sin un submódulo de reportes, el usuario tendría que revisar manualmente el listado completo de movimientos y sumar por su cuenta cuánto gastó en cada categoría, un proceso tedioso, lento y propenso a errores de cálculo, especialmente a medida que el volumen de movimientos crece con el tiempo.

El valor que aporta este submódulo es la visibilidad financiera inmediata: el usuario puede, en segundos, identificar en qué categorías está concentrando su gasto, comparar un mes contra otro, y verificar que el diezmo y el ahorro estén siendo tratados con la prioridad adecuada dentro de su presupuesto personal. Esta claridad es clave para tomar decisiones informadas y ajustar hábitos de consumo.

Este submódulo se integra directamente con el submódulo de Registro de Ingresos y Egresos (fuente principal de datos), con el submódulo de Categorías (criterio de agrupación de los reportes), y con el submódulo de Diezmo (los reportes deben reflejar el diezmo generado como parte del resumen financiero mensual).

---

## 2. Alcance

- **Incluido en este módulo:**
  - Generación de reportes mensuales de egresos agrupados por categoría.
  - Cálculo del porcentaje que representa cada categoría sobre el total de egresos del mes.
  - Comparación de un mes contra el mes anterior (variación de gasto por categoría).
  - Resumen de ingresos totales, egresos totales, diezmo generado y saldo disponible del mes.
  - Visualización de reportes en formato tabla y en formato gráfico (barras/circular).
  - Selección de un mes o rango de meses específico para generar el reporte.
  - Identificación de la categoría con mayor gasto del mes.

- **Excluido de este módulo (V1):**
  - Reportes multiusuario o comparativos entre distintos usuarios.
  - Proyecciones o predicciones de gasto futuro basadas en tendencias históricas.
  - Exportación a formatos distintos de CSV/Excel (por ejemplo, PDF con diseño personalizado) — se contempla en una versión futura.
  - Reportes por presupuesto o metas de ahorro (dependen de un submódulo de presupuestos no incluido en V1).

- **Actores / Roles involucrados:**
  - **Usuario propietario:** único rol existente en V1, consumidor exclusivo de los reportes generados.

- **Establecimientos aplicables:** No aplica (uso personal).

---

## 3. Justificación

### 3.1 Justificación Operativa
Contar con reportes automáticos elimina la necesidad de que el usuario sume manualmente sus gastos por categoría, reduciendo el tiempo dedicado al análisis financiero y minimizando errores de cálculo, especialmente cuando el volumen de movimientos mensuales es alto.

### 3.2 Justificación Personal / Espiritual
Al incluir el diezmo como parte del resumen financiero mensual, el reporte refuerza la visibilidad de esta aportación dentro del panorama financiero completo del usuario, permitiéndole verificar de un vistazo que esta prioridad se está cumpliendo de forma constante mes a mes.

### 3.3 Justificación Técnica
Separar la lógica de generación de reportes en un servicio propio, que consulta pero no modifica los datos de movimientos, categorías y diezmo, respeta el principio de responsabilidad única y permite optimizar independientemente el rendimiento de las consultas agregadas sin afectar la lógica transaccional del resto del sistema.

---

## 4. Funciones Principales

### FP-01: Generar reporte mensual por categoría
- **Descripción:** Agrupa los egresos de un mes seleccionado por categoría y calcula el total gastado en cada una.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** Se presenta una tabla o gráfico con el total y porcentaje de gasto por categoría.

### FP-02: Comparar mes actual contra mes anterior
- **Descripción:** Calcula la variación de gasto por categoría entre el mes seleccionado y el mes inmediatamente anterior.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El usuario identifica en qué categorías aumentó o disminuyó su gasto respecto al periodo previo.

### FP-03: Mostrar resumen financiero mensual
- **Descripción:** Presenta un resumen consolidado del mes: total de ingresos, total de egresos, diezmo generado y saldo disponible.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El usuario obtiene una visión general de su situación financiera del mes en un solo vistazo.

### FP-04: Identificar categoría de mayor gasto
- **Descripción:** Determina automáticamente cuál fue la categoría con el mayor monto de egresos en el mes.
- **Actor principal:** Sistema.
- **Resultado esperado:** Se destaca visualmente la categoría de mayor gasto en el reporte.

### FP-05: Visualizar reporte en formato gráfico
- **Descripción:** Presenta los datos del reporte mensual mediante gráficos (circular o de barras) además de la tabla numérica.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El usuario interpreta visualmente la distribución de su gasto de forma más intuitiva.

### FP-06: Seleccionar periodo del reporte
- **Descripción:** Permite al usuario elegir el mes (o rango de meses) sobre el cual desea generar el reporte.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** El sistema recalcula y muestra el reporte correspondiente al periodo seleccionado.

---

## 5. Requerimientos Funcionales

| ID | Nombre | Descripción | Prioridad | Actor |
|----|--------|-------------|-----------|-------|
| RF-001 | Generar reporte por categoría | El sistema debe agrupar los egresos del mes seleccionado por categoría y calcular el total de cada una. | Alta | Sistema |
| RF-002 | Calcular porcentaje por categoría | El sistema debe calcular el porcentaje que representa cada categoría respecto al total de egresos del mes. | Alta | Sistema |
| RF-003 | Seleccionar mes del reporte | El sistema debe permitir al usuario seleccionar el mes específico a consultar. | Alta | Usuario propietario |
| RF-004 | Seleccionar rango de meses | El sistema debe permitir seleccionar un rango de varios meses para un reporte consolidado. | Media | Usuario propietario |
| RF-005 | Mostrar resumen de ingresos totales | El sistema debe mostrar el total de ingresos del periodo seleccionado. | Alta | Sistema |
| RF-006 | Mostrar resumen de egresos totales | El sistema debe mostrar el total de egresos del periodo seleccionado. | Alta | Sistema |
| RF-007 | Mostrar diezmo generado en el resumen | El sistema debe incluir el total de diezmo generado en el periodo dentro del resumen financiero mensual. | Alta | Sistema |
| RF-008 | Mostrar saldo disponible del periodo | El sistema debe calcular y mostrar el saldo disponible correspondiente al periodo consultado. | Alta | Sistema |
| RF-009 | Comparar contra mes anterior | El sistema debe calcular la variación porcentual de gasto por categoría entre el mes actual y el mes anterior. | Media | Sistema |
| RF-010 | Identificar categoría de mayor gasto | El sistema debe destacar automáticamente la categoría con mayor monto de egresos en el periodo. | Media | Sistema |
| RF-011 | Visualizar reporte en tabla | El sistema debe presentar el reporte en formato de tabla con categoría, monto y porcentaje. | Alta | Sistema |
| RF-012 | Visualizar reporte en gráfico circular | El sistema debe presentar el reporte también como gráfico circular (pie chart) por categoría. | Media | Sistema |
| RF-013 | Visualizar reporte en gráfico de barras | El sistema debe permitir alternar la visualización a un gráfico de barras comparativo. | Baja | Usuario propietario |
| RF-014 | Excluir movimientos eliminados del reporte | El sistema debe excluir del cálculo cualquier movimiento eliminado (borrado lógico). | Alta | Sistema |
| RF-015 | Filtrar reporte por tipo de categoría | El sistema debe permitir generar reportes exclusivamente de categorías de tipo egreso o de tipo ingreso. | Media | Usuario propietario |
| RF-016 | Exportar reporte | El sistema debe permitir exportar el reporte generado a un archivo (CSV/Excel) en versiones futuras. | Baja | Usuario propietario |
| RF-017 | Mostrar cantidad de movimientos por categoría | El sistema debe mostrar cuántos movimientos individuales componen el total de cada categoría en el reporte. | Baja | Sistema |
| RF-018 | Manejar meses sin datos | El sistema debe mostrar un mensaje claro cuando no existan movimientos en el periodo seleccionado. | Media | Sistema |
| RF-019 | Consultar histórico de reportes anteriores | El sistema debe permitir navegar a reportes de meses anteriores, no solo el mes en curso. | Media | Usuario propietario |
| RF-020 | Actualizar reporte en tiempo real | El sistema debe reflejar en el reporte cualquier movimiento nuevo, editado o eliminado del periodo consultado, sin requerir procesos manuales. | Alta | Sistema |

---

## 6. Requerimientos No Funcionales

**Rendimiento**
- RNF-001: El reporte mensual por categoría debe generarse en menos de 3 segundos con hasta 5,000 movimientos históricos.
- RNF-002: La comparación entre mes actual y mes anterior debe calcularse en menos de 2 segundos adicionales sobre el tiempo de generación del reporte base.

**Seguridad**
- RNF-003: Los reportes deben calcularse exclusivamente a partir de movimientos activos, sin exponer información de movimientos eliminados salvo en vistas de auditoría explícitas.

**Disponibilidad**
- RNF-004: Los reportes deben poder generarse sin conexión a internet, al depender únicamente de datos almacenados localmente.

**Usabilidad**
- RNF-005: El reporte debe mostrar por defecto el mes en curso al acceder al submódulo, sin requerir selección manual inicial.
- RNF-006: Los gráficos deben ser legibles y adaptarse correctamente a pantallas móviles pequeñas.

**Escalabilidad**
- RNF-007: El cálculo de reportes debe soportar eficientemente el crecimiento del historial a varios años de movimientos sin degradar el rendimiento de forma significativa.
- RNF-008: El modelo de reportes debe permitir en el futuro incorporar nuevas dimensiones de análisis (por ejemplo, por medio de pago) sin rediseño mayor.

**Interoperabilidad**
- RNF-009: El submódulo de reportes debe reutilizar el mismo servicio de cálculo de diezmo utilizado en el submódulo correspondiente, evitando discrepancias entre cifras.

**Mantenibilidad**
- RNF-010: La lógica de agregación de datos para reportes debe estar separada de la lógica transaccional de registro de movimientos, en un servicio de solo lectura dedicado.
- RNF-011: Las consultas de reportes deben optimizarse mediante índices adecuados en las columnas de fecha y categoría.

**Trazabilidad**
- RNF-012: Cada reporte generado debe poder identificarse con el periodo exacto (fecha de inicio y fin) que representa, evitando ambigüedades de corte.

---

## 7. Reglas de Negocio

- **RN-001:** Los reportes mensuales consideran el periodo desde el día 1 hasta el último día calendario del mes correspondiente, salvo que exista un día de corte configurado distinto en el submódulo de Diezmo.
- **RN-002:** Solo los movimientos activos (no eliminados lógicamente) se incluyen en el cálculo de los reportes.
- **RN-003:** El porcentaje de participación de cada categoría se calcula sobre el total de egresos del periodo, no sobre el total de ingresos.
- **RN-004:** El diezmo mostrado en el resumen del reporte debe coincidir exactamente con el calculado por el submódulo de Diezmo para el mismo periodo.
- **RN-005:** El saldo disponible mostrado en el reporte se calcula como ingresos totales menos egresos totales menos diezmo generado del periodo.
- **RN-006:** La comparación contra el mes anterior solo es posible si dicho mes anterior tiene al menos un movimiento registrado; en caso contrario, la variación se muestra como "sin datos comparables".
- **RN-007:** La categoría de mayor gasto se determina exclusivamente entre categorías de tipo egreso, excluyendo categorías de ingreso del cálculo.
- **RN-008:** Si una categoría fue desactivada después de haber tenido movimientos en el periodo consultado, dichos movimientos deben seguir apareciendo en el reporte bajo el nombre vigente de la categoría.
- **RN-009:** Un movimiento excluido del cálculo de diezmo sigue formando parte normal del reporte de ingresos/egresos; la exclusión solo afecta el cálculo del diezmo, no el reporte general.
- **RN-010:** Los reportes de rango de meses consolidan los totales sumando los periodos mensuales individuales incluidos en el rango seleccionado.
- **RN-011:** Los montos presentados en los reportes deben mostrarse siempre con 2 decimales, consistente con el resto del sistema.
- **RN-012:** No se pueden generar reportes de periodos futuros; el sistema debe limitar la selección hasta el mes en curso.

---

## 8. Casos de Uso Principales

### CU-01: Generar el reporte del mes en curso
- **Actor:** Usuario propietario.
- **Precondiciones:** Deben existir movimientos registrados en el mes actual.
- **Flujo Principal:**
  1. El usuario accede a la sección de Reportes.
  2. El sistema selecciona por defecto el mes en curso.
  3. El sistema agrupa los egresos por categoría y calcula montos y porcentajes.
  4. El sistema muestra la tabla y el gráfico correspondiente, junto con el resumen financiero del mes.
- **Flujos Alternativos:**
  - 3a. Si no existen movimientos en el mes actual, el sistema muestra un mensaje indicando ausencia de datos.

### CU-02: Consultar el reporte de un mes anterior
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un movimiento en el mes que se desea consultar.
- **Flujo Principal:**
  1. El usuario accede a la sección de Reportes y selecciona un mes distinto al actual.
  2. El sistema recupera los movimientos de ese periodo.
  3. El sistema genera el reporte por categoría correspondiente a ese mes.
- **Flujos Alternativos:**
  - 2a. Si el mes seleccionado no tiene movimientos, el sistema informa la ausencia de datos para ese periodo.

### CU-03: Comparar el mes actual contra el mes anterior
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un movimiento en el mes anterior al consultado.
- **Flujo Principal:**
  1. El usuario activa la opción de comparación en el reporte del mes actual.
  2. El sistema recupera los totales por categoría del mes anterior.
  3. El sistema calcula la variación porcentual por categoría y la presenta junto al reporte actual.
- **Flujos Alternativos:**
  - 2a. Si el mes anterior no tiene movimientos, el sistema indica que no hay datos comparables para esa categoría.

### CU-04: Identificar la categoría de mayor gasto del mes
- **Actor:** Sistema (como parte de la generación del reporte, visible al Usuario propietario).
- **Precondiciones:** Debe existir al menos un egreso registrado en el periodo.
- **Flujo Principal:**
  1. El sistema genera el reporte mensual por categoría.
  2. El sistema identifica la categoría con el monto total más alto entre las de tipo egreso.
  3. El sistema resalta visualmente dicha categoría en el reporte.
- **Flujos Alternativos:**
  - 1a. Si existe un empate entre dos categorías, el sistema muestra ambas como destacadas.

### CU-05: Generar un reporte de rango de meses
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos un movimiento dentro del rango de fechas seleccionado.
- **Flujo Principal:**
  1. El usuario selecciona un rango de fechas que abarca varios meses.
  2. El sistema recupera los movimientos correspondientes a todo el rango.
  3. El sistema consolida los totales por categoría para todo el periodo seleccionado.
  4. El sistema presenta el reporte consolidado junto con el resumen financiero del rango.
- **Flujos Alternativos:**
  - 2a. Si el rango seleccionado no contiene movimientos, el sistema informa la ausencia de datos para ese rango.

---

## 9. Criterios de Aceptación

1. **Given** existen egresos registrados en el mes actual, **When** el usuario accede a Reportes, **Then** el sistema muestra automáticamente el reporte del mes en curso agrupado por categoría.
2. **Given** el total de egresos del mes es $1,000 y la categoría "Alimentación" tiene $300, **When** se genera el reporte, **Then** el sistema muestra que "Alimentación" representa el 30% del total.
3. **Given** un movimiento fue eliminado (borrado lógico), **When** se genera el reporte del mes correspondiente, **Then** dicho movimiento no se incluye en los totales por categoría.
4. **Given** el diezmo generado en el mes es de $150, **When** el usuario consulta el resumen del reporte, **Then** el monto de diezmo mostrado coincide exactamente con el calculado en el submódulo de Diezmo.
5. **Given** el mes anterior no tiene movimientos registrados, **When** el usuario activa la comparación mensual, **Then** el sistema indica que no hay datos comparables, sin generar errores.
6. **Given** existen varias categorías de egreso con montos distintos, **When** se genera el reporte, **Then** el sistema identifica correctamente la categoría con el monto más alto como la de mayor gasto.
7. **Given** el usuario selecciona un mes sin ningún movimiento registrado, **When** consulta el reporte, **Then** el sistema muestra un mensaje claro de ausencia de datos, sin fallar.
8. **Given** el usuario selecciona un rango de tres meses con movimientos en todos ellos, **When** genera el reporte consolidado, **Then** el sistema suma correctamente los montos por categoría a través de los tres meses.
9. **Given** una categoría fue desactivada después de tener movimientos en un mes pasado, **When** el usuario consulta el reporte de ese mes, **Then** los movimientos de esa categoría siguen apareciendo correctamente agrupados.
10. **Given** el usuario intenta seleccionar un mes futuro, **When** intenta generar el reporte, **Then** el sistema impide la selección y muestra un mensaje indicando que no se pueden consultar periodos futuros.

---

## 10. Riesgos Técnicos

| ID | Riesgo | Impacto | Probabilidad | Mitigación |
|----|--------|---------|---------------|------------|
| RT-01 | Discrepancia entre el diezmo mostrado en el reporte y el calculado en el submódulo de Diezmo por lógica duplicada | Alto | Media | Reutilizar el mismo servicio de cálculo de diezmo en ambos submódulos, sin duplicar la lógica de agregación. |
| RT-02 | Degradación del rendimiento al generar reportes de rangos amplios de meses con alto volumen de movimientos | Medio | Media | Implementar consultas agregadas optimizadas a nivel de base de datos en lugar de procesar los datos en memoria. |
| RT-03 | Inclusión accidental de movimientos eliminados en los cálculos del reporte | Alto | Baja | Aplicar el filtro de estado activo de forma centralizada en la capa de acceso a datos, no en cada consulta individual. |
| RT-04 | Errores de redondeo al calcular porcentajes de participación por categoría | Medio | Media | Definir una única función de cálculo de porcentajes con redondeo estandarizado, reutilizada en toda la interfaz. |
| RT-05 | Confusión visual al mostrar categorías desactivadas junto a las activas en reportes históricos | Bajo | Media | Diferenciar visualmente las categorías inactivas en los reportes con una etiqueta o estilo distintivo. |
| RT-06 | Inconsistencia en la comparación mensual si el mes anterior tiene un periodo de corte distinto al configurado actualmente | Medio | Baja | Documentar y aplicar de forma consistente las reglas de corte configuradas para todos los periodos, incluidos los históricos. |
| RT-07 | Sobrecarga de la interfaz al graficar un número elevado de categorías en el gráfico circular | Bajo | Media | Agrupar automáticamente las categorías con menor participación en una sección "Otras" dentro del gráfico. |
| RT-08 | Falta de actualización del reporte al registrar un nuevo movimiento mientras el usuario ya tiene el reporte abierto | Medio | Media | Recalcular el reporte en cada acceso a la vista, evitando el uso de datos cacheados obsoletos. |

---

**Fin del documento**
