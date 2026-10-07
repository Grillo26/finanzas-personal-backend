-- =====================================================================
-- SISTEMA DE FINANZAS PERSONALES - Esquema PostgreSQL v1.0
-- Basado en los documentos de requerimientos:
--   - Registro de Ingresos y Egresos
--   - Gestión de Categorías
--   - Cálculo y Seguimiento del Diezmo
--   - Reportes Mensuales por Categoría
--   - Indicadores Principales (Dashboard)
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. TIPOS ENUMERADOS
-- ---------------------------------------------------------------------

CREATE TYPE tipo_categoria_enum    AS ENUM ('INGRESO', 'EGRESO');
CREATE TYPE tipo_movimiento_enum   AS ENUM ('INGRESO', 'EGRESO');
CREATE TYPE estado_diezmo_enum     AS ENUM ('PENDIENTE', 'PAGADO');
CREATE TYPE accion_auditoria_enum  AS ENUM (
    'CREAR', 'EDITAR', 'ELIMINAR',
    'DESACTIVAR', 'REACTIVAR',
    'MARCAR_PAGADO', 'REVERTIR_PAGADO',
    'EXCLUIR_DIEZMO', 'REVERTIR_EXCLUSION'
);

-- ---------------------------------------------------------------------
-- 2. TABLA: categorias
-- ---------------------------------------------------------------------

CREATE TABLE categorias (
    id                  BIGSERIAL PRIMARY KEY,
    nombre              VARCHAR(40) NOT NULL,
    tipo                tipo_categoria_enum NOT NULL,
    color               VARCHAR(20),
    icono               VARCHAR(50),
    activa              BOOLEAN NOT NULL DEFAULT TRUE,
    protegida           BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_creacion      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_cat_nombre_longitud
        CHECK (LENGTH(TRIM(nombre)) BETWEEN 3 AND 40),
    CONSTRAINT chk_cat_nombre_no_vacio
        CHECK (TRIM(nombre) <> '')
);

-- RN-002: no puede haber dos categorías ACTIVAS con el mismo nombre
-- dentro del mismo tipo. Si se desactiva, se libera el nombre.
CREATE UNIQUE INDEX uk_categorias_nombre_tipo_activa
    ON categorias (LOWER(nombre), tipo)
    WHERE activa = TRUE;

CREATE INDEX idx_categorias_tipo   ON categorias (tipo);
CREATE INDEX idx_categorias_activa ON categorias (activa);

COMMENT ON TABLE  categorias           IS 'Catálogo de categorías de ingreso/egreso.';
COMMENT ON COLUMN categorias.protegida IS 'TRUE para categorías del sistema no eliminables (Diezmo y Ofrendas, Otros).';


-- ---------------------------------------------------------------------
-- 3. TABLA: movimientos
-- ---------------------------------------------------------------------

CREATE TABLE movimientos (
    id                  BIGSERIAL PRIMARY KEY,
    tipo                tipo_movimiento_enum NOT NULL,
    monto               NUMERIC(12,2) NOT NULL,
    fecha               DATE NOT NULL,
    descripcion         VARCHAR(200),
    categoria_id        BIGINT NOT NULL,
    excluido_diezmo     BOOLEAN NOT NULL DEFAULT FALSE,
    planificado         BOOLEAN NOT NULL DEFAULT FALSE,
    activo              BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_mov_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT chk_mov_monto_positivo
        CHECK (monto > 0),
    -- RN-009: solo ingresos pueden marcarse como "planificado" o excluirse del diezmo.
    CONSTRAINT chk_mov_planificado_solo_ingreso
        CHECK (tipo = 'INGRESO' OR planificado = FALSE),
    CONSTRAINT chk_mov_excluido_solo_ingreso
        CHECK (tipo = 'INGRESO' OR excluido_diezmo = FALSE)
);

CREATE INDEX idx_mov_fecha      ON movimientos (fecha DESC);
CREATE INDEX idx_mov_categoria  ON movimientos (categoria_id);
CREATE INDEX idx_mov_tipo       ON movimientos (tipo);
CREATE INDEX idx_mov_activo     ON movimientos (activo);
CREATE INDEX idx_mov_fecha_categoria ON movimientos (fecha, categoria_id) WHERE activo = TRUE;

COMMENT ON COLUMN movimientos.excluido_diezmo IS 'RN: solo aplica a ingresos. Marca el ingreso como no sujeto a diezmo.';
COMMENT ON COLUMN movimientos.planificado     IS 'RN-009: gastos futuros permitidos solo si se marcan como planificados.';
COMMENT ON COLUMN movimientos.activo          IS 'Soft delete: FALSE = eliminado lógicamente.';


-- ---------------------------------------------------------------------
-- 4. TABLA: configuracion_diezmo
-- Versionada: cada fila es una configuración con vigencia.
-- RN-006: cambios aplican solo a movimientos registrados después.
-- ---------------------------------------------------------------------

CREATE TABLE configuracion_diezmo (
    id                  BIGSERIAL PRIMARY KEY,
    porcentaje          NUMERIC(5,2) NOT NULL DEFAULT 10.00,
    dia_corte           SMALLINT,                 -- NULL = último día del mes
    vigente_desde       DATE NOT NULL DEFAULT CURRENT_DATE,
    fecha_creacion      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_conf_porcentaje
        CHECK (porcentaje > 0 AND porcentaje <= 100),
    CONSTRAINT chk_conf_dia_corte
        CHECK (dia_corte IS NULL OR dia_corte BETWEEN 1 AND 31)
);

COMMENT ON TABLE  configuracion_diezmo              IS 'Histórico versionado de la configuración del diezmo. La fila vigente es la de mayor vigente_desde.';
COMMENT ON COLUMN configuracion_diezmo.dia_corte    IS 'NULL = corte el último día del mes calendario.';


-- ---------------------------------------------------------------------
-- 5. TABLA: diezmos
-- Una fila por cada ingreso no excluido.
-- ---------------------------------------------------------------------

CREATE TABLE diezmos (
    id                  BIGSERIAL PRIMARY KEY,
    movimiento_id       BIGINT NOT NULL,
    monto_ingreso       NUMERIC(12,2) NOT NULL,   -- base sobre la que se calculó
    porcentaje_aplicado NUMERIC(5,2) NOT NULL,    -- congelado al momento del cálculo
    monto_diezmo        NUMERIC(12,2) NOT NULL,
    estado              estado_diezmo_enum NOT NULL DEFAULT 'PENDIENTE',
    fecha_pago          DATE,
    fecha_calculo       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    excluido            BOOLEAN NOT NULL DEFAULT FALSE,
    motivo_exclusion    VARCHAR(200),
    fecha_exclusion     TIMESTAMP,
    activo              BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_diez_movimiento
        FOREIGN KEY (movimiento_id) REFERENCES movimientos(id),
    CONSTRAINT uk_diez_movimiento
        UNIQUE (movimiento_id),
    CONSTRAINT chk_diez_porcentaje
        CHECK (porcentaje_aplicado > 0 AND porcentaje_aplicado <= 100),
    CONSTRAINT chk_diez_monto
        CHECK (monto_diezmo >= 0),
    -- Coherencia estado ↔ fecha de pago
    CONSTRAINT chk_diez_fecha_pago
        CHECK (
            (estado = 'PENDIENTE' AND fecha_pago IS NULL)
            OR (estado = 'PAGADO')
        ),
    -- Coherencia exclusión ↔ motivo y fecha
    CONSTRAINT chk_diez_exclusion
        CHECK (
            (excluido = FALSE AND motivo_exclusion IS NULL AND fecha_exclusion IS NULL)
            OR (excluido = TRUE AND fecha_exclusion IS NOT NULL)
        )
);

CREATE INDEX idx_diez_estado     ON diezmos (estado);
CREATE INDEX idx_diez_fecha_calc ON diezmos (fecha_calculo);
CREATE INDEX idx_diez_activo     ON diezmos (activo);

COMMENT ON COLUMN diezmos.porcentaje_aplicado IS 'RN-013: valor congelado al momento del cálculo. No se altera si cambia la configuración global.';


-- ---------------------------------------------------------------------
-- 6. TABLA: auditoria
-- ---------------------------------------------------------------------

CREATE TABLE auditoria (
    id          BIGSERIAL PRIMARY KEY,
    entidad     VARCHAR(50) NOT NULL,
    entidad_id  BIGINT NOT NULL,
    accion      accion_auditoria_enum NOT NULL,
    detalle     JSONB,
    fecha       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_aud_entidad ON auditoria (entidad, entidad_id);
CREATE INDEX idx_aud_fecha   ON auditoria (fecha DESC);


-- ---------------------------------------------------------------------
-- 7. TRIGGER: actualizar fecha_modificacion automáticamente
-- ---------------------------------------------------------------------

CREATE OR REPLACE FUNCTION fn_actualizar_fecha_modificacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_modificacion := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_categorias_mod BEFORE UPDATE ON categorias
    FOR EACH ROW EXECUTE FUNCTION fn_actualizar_fecha_modificacion();

CREATE TRIGGER trg_movimientos_mod BEFORE UPDATE ON movimientos
    FOR EACH ROW EXECUTE FUNCTION fn_actualizar_fecha_modificacion();

CREATE TRIGGER trg_diezmos_mod BEFORE UPDATE ON diezmos
    FOR EACH ROW EXECUTE FUNCTION fn_actualizar_fecha_modificacion();


-- ---------------------------------------------------------------------
-- 8. VISTAS PARA REPORTES Y DASHBOARD
-- ---------------------------------------------------------------------

-- 8.1 Saldo y resumen por mes (Dashboard + Reportes)
CREATE OR REPLACE VIEW v_resumen_mensual AS
SELECT
    DATE_TRUNC('month', m.fecha)::DATE AS mes,
    SUM(CASE WHEN m.tipo = 'INGRESO' THEN m.monto ELSE 0 END) AS total_ingresos,
    SUM(CASE WHEN m.tipo = 'EGRESO'  THEN m.monto ELSE 0 END) AS total_egresos,
    COALESCE(SUM(d.monto_diezmo) FILTER (WHERE d.activo AND NOT d.excluido), 0) AS total_diezmo
FROM movimientos m
LEFT JOIN diezmos d ON d.movimiento_id = m.id
WHERE m.activo = TRUE
GROUP BY DATE_TRUNC('month', m.fecha);

-- 8.2 Reporte mensual por categoría
CREATE OR REPLACE VIEW v_reporte_categoria AS
SELECT
    DATE_TRUNC('month', m.fecha)::DATE  AS mes,
    c.id                                AS categoria_id,
    c.nombre                            AS categoria,
    c.tipo                              AS tipo_categoria,
    SUM(m.monto)                        AS total,
    COUNT(*)                            AS cantidad_movimientos
FROM movimientos m
INNER JOIN categorias c ON c.id = m.categoria_id
WHERE m.activo = TRUE
GROUP BY DATE_TRUNC('month', m.fecha), c.id, c.nombre, c.tipo;

-- 8.3 Diezmo mensual (detalle consolidado)
CREATE OR REPLACE VIEW v_diezmo_mensual AS
SELECT
    DATE_TRUNC('month', d.fecha_calculo)::DATE AS mes,
    SUM(d.monto_diezmo) FILTER (WHERE NOT d.excluido AND d.activo)                         AS total_generado,
    SUM(d.monto_diezmo) FILTER (WHERE d.estado = 'PENDIENTE' AND NOT d.excluido AND d.activo) AS total_pendiente,
    SUM(d.monto_diezmo) FILTER (WHERE d.estado = 'PAGADO'    AND NOT d.excluido AND d.activo) AS total_pagado
FROM diezmos d
GROUP BY DATE_TRUNC('month', d.fecha_calculo);

-- 8.4 Diezmo pendiente acumulado (Dashboard)
CREATE OR REPLACE VIEW v_diezmo_pendiente_total AS
SELECT
    COALESCE(SUM(monto_diezmo), 0) AS total_pendiente,
    COUNT(*)                        AS cantidad_registros
FROM diezmos
WHERE estado = 'PENDIENTE' AND NOT excluido AND activo;


-- ---------------------------------------------------------------------
-- 9. SEED: datos iniciales
-- ---------------------------------------------------------------------

-- 9.1 Configuración inicial del diezmo (10%, corte último día del mes)
INSERT INTO configuracion_diezmo (porcentaje, dia_corte, vigente_desde)
VALUES (10.00, NULL, CURRENT_DATE);

-- 9.2 Categorías predefinidas (RF-014, RN-004, RN-005)
INSERT INTO categorias (nombre, tipo, color, icono, protegida) VALUES
    ('Salario',            'INGRESO', '#4CAF50', 'payments',   FALSE),
    ('Otros ingresos',     'INGRESO', '#8BC34A', 'savings',    FALSE),
    ('Alimentación',       'EGRESO',  '#FF9800', 'restaurant', FALSE),
    ('Transporte',         'EGRESO',  '#2196F3', 'directions_car', FALSE),
    ('Servicios',          'EGRESO',  '#9C27B0', 'home',       FALSE),
    ('Ocio',               'EGRESO',  '#E91E63', 'movie',      FALSE),
    ('Diezmo y Ofrendas',  'EGRESO',  '#3F51B5', 'church',     TRUE),   -- protegida
    ('Otros',              'EGRESO',  '#607D8B', 'category',   TRUE);   -- protegida

-- =====================================================================
-- FIN DEL SCRIPT
-- =====================================================================