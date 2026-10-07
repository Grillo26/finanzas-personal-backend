# Gestión de Categorías
**Módulo:** Gestión de Movimientos
**Versión:** 1.0
**Fecha:** 13 de septiembre de 2026
**Estado:** Borrador

---

## 1. Introducción

El submódulo de Gestión de Categorías provee la estructura de clasificación sobre la cual se organizan todos los movimientos financieros del sistema. Sin categorías bien definidas, el registro de ingresos y egresos carecería de sentido analítico: sería imposible saber en qué se gasta el dinero mes a mes o de dónde provienen los ingresos. Este submódulo es, por tanto, un componente transversal y de soporte para el resto del sistema.

El contexto operativo es el mismo del sistema general: uso personal, sin autenticación en V1, accesible desde escritorio o móvil. Las categorías son mantenidas directamente por el usuario propietario, quien decide qué categorías necesita según su realidad financiera particular (por ejemplo, "Alimentación", "Transporte", "Salario", "Diezmo y Ofrendas").

Actualmente, sin un submódulo de categorías bien definido, el usuario tendería a escribir libremente el concepto de cada gasto en una hoja de cálculo, lo que genera inconsistencias: la misma categoría escrita de formas distintas ("Comida", "comida", "Alimentación") impide agrupar correctamente los datos para generar reportes confiables.

El valor que aporta este submódulo es la estandarización: al obligar a que todo movimiento se asocie a una categoría predefinida y controlada, se garantiza la consistencia necesaria para que los reportes mensuales sean precisos y comparables mes a mes. Además, permite distinguir claramente entre categorías de ingreso y de egreso, evitando clasificaciones incorrectas.

Este submódulo se integra directamente con el submódulo de Registro de Ingresos y Egresos (toda transacción requiere una categoría válida), con el submódulo de Reportes (los reportes mensuales agrupan los egresos precisamente por categoría), y de forma indirecta con el submódulo de Diezmo, ya que las categorías de tipo "ingreso" son la base sobre la cual se calcula el diezmo correspondiente.

---

## 2. Alcance

- **Incluido en este módulo:**
  - Creación de categorías de tipo "ingreso" o "egreso".
  - Edición del nombre, color e ícono de una categoría existente.
  - Eliminación de categorías, siempre que no tengan movimientos asociados.
  - Listado de categorías con filtro por tipo (ingreso/egreso).
  - Validación de nombres únicos por tipo de categoría.
  - Categorías predefinidas del sistema (creadas automáticamente en la instalación inicial).
  - Marcado de categorías como activas/inactivas (sin eliminarlas físicamente).

- **Excluido de este módulo (V1):**
  - Subcategorías o jerarquías de categorías (categorías padre/hijo).
  - Categorías compartidas entre múltiples usuarios.
  - Presupuestos o límites de gasto asociados a categorías (se contempla en un submódulo futuro).
  - Reglas automáticas de categorización basadas en texto o machine learning.

- **Actores / Roles involucrados:**
  - **Usuario propietario:** único rol existente en V1, con control total sobre la creación, edición y eliminación de categorías.

- **Establecimientos aplicables:** No aplica (uso personal).

---

## 3. Justificación

### 3.1 Justificación Operativa
Contar con un catálogo controlado de categorías evita la dispersión y duplicidad de conceptos que ocurre al escribir libremente el motivo de un gasto. Esto permite que los reportes agreguen correctamente la información y que el usuario identifique con precisión en qué está gastando su dinero.

### 3.2 Justificación Personal / Espiritual
Una categoría dedicada y no eliminable para el diezmo y las ofrendas asegura que esta aportación siempre tenga un lugar visible y diferenciado dentro del sistema, reforzando su importancia frente a otros gastos y evitando que se mezcle o se pierda entre categorías genéricas.

### 3.3 Justificación Técnica
Modelar las categorías como una entidad independiente, relacionada con los movimientos mediante una llave foránea, permite mantener la integridad referencial de los datos y facilita futuras mejoras, como agregar subcategorías, presupuestos o iconografía personalizada, sin necesidad de rediseñar el modelo de movimientos.

---

## 4. Funciones Principales

### FP-01: Crear categoría
- **Descripción:** Permite registrar una nueva categoría indicando nombre, tipo (ingreso/egreso) y, opcionalmente, color e ícono.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** La categoría queda disponible para ser seleccionada al registrar movimientos.

### FP-02: Editar categoría
- **Descripción:** Permite modificar el nombre, color o ícono de una categoría existente.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** Los cambios se reflejan en todos los movimientos históricos asociados a esa categoría.

### FP-03: Eliminar categoría
- **Descripción:** Permite eliminar una categoría siempre que no tenga movimientos asociados.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** La categoría deja de estar disponible para nuevos movimientos.

### FP-04: Desactivar categoría
- **Descripción:** Permite marcar una categoría como inactiva sin eliminarla, cuando ya tiene movimientos asociados pero ya no se usará más.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** La categoría deja de aparecer como opción para nuevos movimientos, pero se conserva en el historial.

### FP-05: Listar y filtrar categorías
- **Descripción:** Permite visualizar el catálogo completo de categorías, filtrando por tipo o estado.
- **Actor principal:** Usuario propietario.
- **Resultado esperado:** Se muestra un listado claro de las categorías disponibles, activas e inactivas.

### FP-06: Inicializar categorías predefinidas
- **Descripción:** Al instalar el sistema por primera vez, se crean automáticamente categorías base (ej. "Salario", "Diezmo y Ofrendas", "Alimentación", "Transporte", "Otros").
- **Actor principal:** Sistema.
- **Resultado esperado:** El usuario cuenta con un catálogo mínimo funcional desde el primer uso, sin necesidad de configuración inicial.

---

## 5. Requerimientos Funcionales

| ID | Nombre | Descripción | Prioridad | Actor |
|----|--------|-------------|-----------|-------|
| RF-001 | Crear categoría | El sistema debe permitir crear una categoría indicando nombre y tipo (ingreso/egreso). | Alta | Usuario propietario |
| RF-002 | Validar nombre único por tipo | El sistema debe impedir crear dos categorías con el mismo nombre dentro del mismo tipo. | Alta | Sistema |
| RF-003 | Asignar color a categoría | El sistema debe permitir asociar un color a la categoría para su identificación visual. | Baja | Usuario propietario |
| RF-004 | Asignar ícono a categoría | El sistema debe permitir seleccionar un ícono representativo para la categoría. | Baja | Usuario propietario |
| RF-005 | Editar nombre de categoría | El sistema debe permitir modificar el nombre de una categoría existente. | Media | Usuario propietario |
| RF-006 | Editar color/ícono de categoría | El sistema debe permitir modificar el color o ícono de una categoría existente. | Baja | Usuario propietario |
| RF-007 | Impedir eliminación con movimientos asociados | El sistema debe impedir eliminar una categoría si existen movimientos (activos o inactivos) asociados a ella. | Alta | Sistema |
| RF-008 | Eliminar categoría sin movimientos | El sistema debe permitir eliminar físicamente una categoría que nunca ha sido utilizada en ningún movimiento. | Media | Usuario propietario |
| RF-009 | Desactivar categoría con movimientos | El sistema debe permitir marcar como inactiva una categoría que ya tiene movimientos asociados. | Alta | Usuario propietario |
| RF-010 | Reactivar categoría | El sistema debe permitir reactivar una categoría previamente desactivada. | Media | Usuario propietario |
| RF-011 | Listar categorías activas | El sistema debe mostrar por defecto solo las categorías activas al momento de registrar un movimiento. | Alta | Sistema |
| RF-012 | Listar todas las categorías | El sistema debe permitir consultar el listado completo, incluyendo categorías inactivas, desde una pantalla de administración. | Media | Usuario propietario |
| RF-013 | Filtrar categorías por tipo | El sistema debe permitir filtrar el catálogo de categorías por tipo (ingreso/egreso). | Media | Usuario propietario |
| RF-014 | Crear categorías predefinidas | El sistema debe crear automáticamente un conjunto mínimo de categorías al inicializarse por primera vez. | Alta | Sistema |
| RF-015 | Proteger categoría "Diezmo y Ofrendas" | El sistema debe impedir la eliminación (aunque sí permitir la edición de nombre/color) de la categoría predefinida asociada al diezmo. | Alta | Sistema |
| RF-016 | Proteger categoría "Otros" | El sistema debe mantener disponible una categoría genérica "Otros" que no pueda eliminarse, para movimientos sin clasificación clara. | Media | Sistema |
| RF-017 | Validar longitud del nombre | El sistema debe validar que el nombre de la categoría tenga entre 3 y 40 caracteres. | Media | Sistema |
| RF-018 | Contar movimientos por categoría | El sistema debe mostrar, en el listado de categorías, cuántos movimientos tiene asociados cada una. | Baja | Sistema |
| RF-019 | Registrar fecha de creación y modificación | El sistema debe almacenar automáticamente la fecha de creación y última modificación de cada categoría. | Media | Sistema |
| RF-020 | Ordenar categorías | El sistema debe permitir ordenar el listado de categorías alfabéticamente o por cantidad de uso. | Baja | Usuario propietario |
| RF-021 | Buscar categoría por nombre | El sistema debe permitir buscar una categoría específica por coincidencia parcial de nombre. | Baja | Usuario propietario |
| RF-022 | Exponer catálogo de categorías vía API | El sistema debe exponer un endpoint que devuelva el catálogo de categorías activas para ser consumido por el formulario de movimientos. | Alta | Sistema |

---

## 6. Requerimientos No Funcionales

**Rendimiento**
- RNF-001: La creación o edición de una categoría debe completarse en menos de 1 segundo.
- RNF-002: El listado de categorías debe cargar en menos de 1 segundo, incluso con más de 100 categorías registradas.

**Seguridad**
- RNF-003: Las categorías protegidas del sistema (ej. "Diezmo y Ofrendas", "Otros") no deben poder eliminarse desde ninguna vía, incluyendo llamadas directas a la API.
- RNF-004: Los cambios sobre categorías deben quedar registrados en el historial de auditoría del sistema.

**Disponibilidad**
- RNF-005: El catálogo de categorías debe estar disponible incluso en escenarios de baja conectividad, priorizando su carga desde caché local si aplica.

**Usabilidad**
- RNF-006: El formulario de creación de categoría debe requerir como máximo 2 campos obligatorios (nombre y tipo).
- RNF-007: La selección de categoría en el formulario de movimientos debe permitir búsqueda rápida por texto.

**Escalabilidad**
- RNF-008: El modelo de datos debe permitir en el futuro agregar jerarquías (subcategorías) sin romper la estructura actual.
- RNF-009: El modelo de datos debe permitir asociar en el futuro un presupuesto máximo por categoría.

**Interoperabilidad**
- RNF-010: El catálogo de categorías debe poder exportarse en formato CSV en versiones futuras.

**Mantenibilidad**
- RNF-011: La lógica de validación de categorías protegidas debe estar centralizada en un único servicio, evitando duplicación en distintos controladores.
- RNF-012: El código debe seguir la misma arquitectura en capas utilizada en el resto del sistema (controlador, servicio, repositorio).

**Trazabilidad**
- RNF-013: Toda categoría debe conservar su fecha de creación y última modificación.
- RNF-014: Las categorías desactivadas deben conservar su historial completo de uso para fines de auditoría y reportes históricos.

---

## 7. Reglas de Negocio

- **RN-001:** Toda categoría debe pertenecer exactamente a un tipo: "ingreso" o "egreso"; no se permiten categorías mixtas.
- **RN-002:** No pueden existir dos categorías activas con el mismo nombre dentro del mismo tipo.
- **RN-003:** Una categoría no puede eliminarse físicamente si tiene al menos un movimiento (activo o inactivo) asociado; en ese caso solo puede desactivarse.
- **RN-004:** La categoría "Diezmo y Ofrendas" es una categoría protegida del sistema y no puede eliminarse ni desactivarse bajo ninguna circunstancia.
- **RN-005:** La categoría "Otros" es una categoría protegida del sistema, utilizada como respaldo para movimientos sin clasificación clara, y no puede eliminarse.
- **RN-006:** El nombre de una categoría debe tener entre 3 y 40 caracteres, sin permitir cadenas vacías o compuestas solo de espacios.
- **RN-007:** Al desactivar una categoría, esta deja de estar disponible en el selector de nuevos movimientos, pero los movimientos históricos que la usan conservan la referencia.
- **RN-008:** El sistema debe crear automáticamente un conjunto mínimo de categorías predefinidas la primera vez que se inicializa (instalación limpia).
- **RN-009:** El tipo de una categoría (ingreso/egreso) no puede modificarse una vez que tiene movimientos asociados, para evitar inconsistencias en reportes históricos.
- **RN-010:** Solo se pueden asociar movimientos a categorías que se encuentren en estado activo.
- **RN-011:** El color y el ícono de una categoría son opcionales y no afectan ningún cálculo del sistema; son puramente visuales.
- **RN-012:** La reactivación de una categoría previamente desactivada la vuelve a poner disponible en el selector de nuevos movimientos, sin afectar el histórico ya registrado.
- **RN-013:** El conteo de movimientos asociados a una categoría debe considerar únicamente los movimientos activos (no eliminados lógicamente).

---

## 8. Casos de Uso Principales

### CU-01: Crear una categoría
- **Actor:** Usuario propietario.
- **Precondiciones:** El usuario ha iniciado la funcionalidad de administración de categorías.
- **Flujo Principal:**
  1. El usuario accede a la sección de Categorías y selecciona "Nueva categoría".
  2. El usuario ingresa el nombre y selecciona el tipo (ingreso/egreso).
  3. Opcionalmente, selecciona un color y un ícono.
  4. El sistema valida que el nombre no esté duplicado dentro del mismo tipo.
  5. El sistema almacena la nueva categoría y la muestra en el listado.
- **Flujos Alternativos:**
  - 4a. Si el nombre ya existe dentro del mismo tipo, el sistema muestra un error y no permite guardar.
  - 2a. Si el nombre no cumple la longitud mínima o máxima, el sistema muestra un mensaje de validación.

### CU-02: Editar una categoría existente
- **Actor:** Usuario propietario.
- **Precondiciones:** La categoría a editar debe existir en el sistema.
- **Flujo Principal:**
  1. El usuario selecciona una categoría del listado y elige "Editar".
  2. El usuario modifica el nombre, color o ícono.
  3. El sistema valida que el nuevo nombre no genere duplicados.
  4. El sistema guarda los cambios y actualiza la fecha de modificación.
- **Flujos Alternativos:**
  - 3a. Si la categoría es protegida y se intenta cambiar su tipo, el sistema rechaza el cambio.

### CU-03: Eliminar una categoría
- **Actor:** Usuario propietario.
- **Precondiciones:** La categoría a eliminar no debe estar protegida por el sistema.
- **Flujo Principal:**
  1. El usuario selecciona una categoría y elige "Eliminar".
  2. El sistema verifica si la categoría tiene movimientos asociados.
  3. Si no tiene movimientos, el sistema solicita confirmación y elimina la categoría físicamente.
- **Flujos Alternativos:**
  - 2a. Si la categoría tiene movimientos asociados, el sistema impide la eliminación y sugiere desactivarla en su lugar.
  - 2b. Si la categoría es protegida ("Diezmo y Ofrendas" u "Otros"), el sistema impide la eliminación sin excepción.

### CU-04: Desactivar una categoría con movimientos
- **Actor:** Usuario propietario.
- **Precondiciones:** La categoría debe tener al menos un movimiento asociado y no ser protegida.
- **Flujo Principal:**
  1. El usuario intenta eliminar una categoría con movimientos asociados.
  2. El sistema informa que no puede eliminarse y ofrece la opción de desactivarla.
  3. El usuario confirma la desactivación.
  4. El sistema marca la categoría como inactiva y la retira del selector de nuevos movimientos.
- **Flujos Alternativos:**
  - 3a. El usuario cancela la operación y la categoría permanece activa.

### CU-05: Consultar el catálogo de categorías
- **Actor:** Usuario propietario.
- **Precondiciones:** Debe existir al menos una categoría registrada (mínimo, las predefinidas del sistema).
- **Flujo Principal:**
  1. El usuario accede a la sección de Categorías.
  2. El sistema muestra el listado de categorías activas por defecto, con su tipo y cantidad de movimientos asociados.
  3. El usuario puede aplicar filtros por tipo o buscar por nombre.
  4. El usuario puede alternar la vista para incluir categorías inactivas.
- **Flujos Alternativos:**
  - 2a. Si no hay categorías registradas (escenario anómalo), el sistema sugiere reinicializar las categorías predefinidas.

---

## 9. Criterios de Aceptación

1. **Given** el usuario crea una categoría de tipo "egreso" llamada "Transporte", **When** ya existe una categoría de tipo "egreso" con ese mismo nombre, **Then** el sistema rechaza la creación con un mensaje de duplicado.
2. **Given** una categoría no tiene movimientos asociados, **When** el usuario la elimina, **Then** el sistema la borra físicamente y deja de aparecer en el listado.
3. **Given** una categoría tiene movimientos asociados, **When** el usuario intenta eliminarla, **Then** el sistema impide la eliminación y ofrece desactivarla.
4. **Given** el usuario intenta eliminar la categoría "Diezmo y Ofrendas", **When** confirma la acción, **Then** el sistema rechaza la eliminación indicando que es una categoría protegida.
5. **Given** el usuario registra un nuevo movimiento, **When** abre el selector de categorías, **Then** solo se muestran categorías activas y correspondientes al tipo de movimiento (ingreso/egreso).
6. **Given** una categoría con movimientos es desactivada, **When** el usuario consulta reportes de meses anteriores, **Then** los movimientos históricos siguen mostrándose correctamente agrupados por esa categoría.
7. **Given** el sistema se instala por primera vez, **When** el usuario ingresa por primera vez a Categorías, **Then** ya existe un conjunto mínimo de categorías predefinidas, incluida "Diezmo y Ofrendas" y "Otros".
8. **Given** el usuario intenta crear una categoría con un nombre de 2 caracteres, **When** guarda el formulario, **Then** el sistema muestra un error de longitud mínima.
9. **Given** una categoría tiene movimientos asociados, **When** el usuario intenta cambiar su tipo de "ingreso" a "egreso", **Then** el sistema impide el cambio.
10. **Given** el usuario reactiva una categoría previamente desactivada, **When** registra un nuevo movimiento, **Then** dicha categoría vuelve a aparecer disponible en el selector.

---

## 10. Riesgos Técnicos

| ID | Riesgo | Impacto | Probabilidad | Mitigación |
|----|--------|---------|---------------|------------|
| RT-01 | Eliminación accidental de una categoría protegida por un error de validación en backend | Alto | Baja | Implementar la validación de categorías protegidas a nivel de base de datos (restricción) y de servicio. |
| RT-02 | Duplicidad de categorías por condiciones de carrera al crear dos categorías simultáneamente | Medio | Baja | Aplicar restricción de unicidad (constraint) a nivel de base de datos, no solo a nivel de aplicación. |
| RT-03 | Inconsistencia en reportes históricos al desactivar una categoría con movimientos | Medio | Media | Asegurar que los reportes consulten movimientos por su categoría asociada independientemente del estado activo/inactivo de esta. |
| RT-04 | Pérdida de la categoría "Diezmo y Ofrendas" en una migración o actualización de base de datos | Alto | Baja | Incluir un script de verificación/reinicialización de categorías protegidas en cada despliegue. |
| RT-05 | Crecimiento descontrolado del catálogo de categorías dificultando su selección en el formulario | Bajo | Media | Incorporar búsqueda y ordenamiento eficiente en el selector de categorías. |
| RT-06 | Cambios de nombre de categoría que generan confusión en reportes históricos ya generados | Bajo | Media | Mostrar el nombre vigente en reportes, documentando que el histórico refleja el nombre actual, no el que existía al momento del movimiento. |
| RT-07 | Necesidad futura de subcategorías obligando a un rediseño mayor del modelo de datos | Medio | Media | Diseñar el modelo de categorías contemplando un campo opcional de categoría padre desde el inicio, aunque no se use en V1. |
| RT-08 | Falta de sincronización entre el catálogo de categorías y el caché del frontend tras una edición | Bajo | Media | Invalidar y recargar el caché de categorías en el frontend inmediatamente después de cualquier operación de creación, edición o eliminación. |

---

**Fin del documento**
