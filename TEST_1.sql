SELECT P.FABRICANTE FROM PRODUCTO P, IMPRESORA I
WHERE P.MODELO=I.PRODUCTO_MODELO AND I.COLOR<>'ROJO';

SELECT PC.PRODUCTO_MODELO, PC.VELOCIDAD FROM PC PC
WHERE PC.SSD>120 AND PC.PRECIO<=(SELECT AVG(PC.PRECIO) FROM PC);

SELECT P.FABRICANTE, AVG(I.PRECIO) FROM PRODUCTO P, IMPRESORA I
WHERE P.MODELO = I.PRODUCTO_MODELO
GROUP BY P.FABRICANTE;

SELECT P.PRODUCTO_MODELO FROM PC P
WHERE PRECIO = (SELECT MAX(PRECIO) FROM PC)
GROUP BY P.PRODUCTO_MODELO;

SELECT P.FABRICANTE FROM PC P, IMPRESORA I
WHERE P.MODELO = I.PRODUCTO_MODELO AND I.PRECIO = (SELECT MIN(PRECIO) FROM IMPRESORA);

SELECT I.MODELO, I.TIPO FROM IMPRESORA I, PRODUCTO P
WHERE I.PRODUCTO_MODELO = P.MODELO AND P.FABRICANTE<>'CANON' AND P.FABRICANTE <>'EPSON'
-- P.FABRICANTE NOT IN ('CANON', 'EPSON')

CREATE VIEW PROMEDIO_MIN AS
SELECT P.FABRICANTE, AVG(PC.PRECIO) AS PROMEDIO_VENTAS FROM PRODUCTO P, PC PC
WHERE P.MODELO = PC.PRODUCTO_MODELO AND PC.VELOCIDAD = (SELECT MAX(VELOCIDAD) FROM PC)
GROUP BY P.FABRICANTE

SELECT FABRICANTE FROM PROMEDIO_MIN
WHERE PROMEDIO_VENTAS = (SELECT MIN(PROMEDIO_VENTAS) FROM PROMEDIO_MIN)

SELECT P.FABRICANTE, COUNT(*), AVG(I.PRECIO) FROM PRODUCTO P, IMPRESORA I
WHERE P.MODELO = I.PRODUCTO_MODELO
GROUP BY P.FABRICANTE
HAVING COUNT(*)>3;

CREATE VIEW FABRICANTES_SIN_PC AS
SELECT P.FABRICANTE, COUNT(*) AS CANTIDAD FROM PRODUCTO P, IMPRESORA I
WHERE P.MODELO = I.PRODUCTO_MODELO AND P.MODELO NOT IN (SELECT MODELO FROM PC)
GROUP BY P.FABRICANTE

SELECT * FROM FABRICANTES_SIN_PC