# Datum

Inteligencia para tus aplicaciones.

### Contribuyentes

```
GET /contribuyentes/:RUC
```

Obtiene información actualizada de la empresa o persona y un listado de
establecimientos.

```json
{  
  "actividad_principal":"Venta al por mayor y menor de alimentos, bebidas y tabaco en supermercados",
  "obligado_contabilidad":true,
  "tipo":"Sociedad",
  "clase":"Especial",
  "estado":"Activo",
  "nombre_comercial":"Mi Comisariato",
  "razon_social":"Corporacion El Rosado S.A.",
  "establecimientos":[  
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Av. 9 De Octubre 729 Y Boyaca - Garcia Aviles",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"001"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Victor Emilio Estrada 410 Y Datiles",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"002"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Roca 207 Y Rocafuerte",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"003"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Eloy Alfaro S/N Y Brasil",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"004"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Bogota 1424 Y Rosendo Aviles",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"005"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Av. 25 De Julio S/N",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Hipermarket Sur",
      "codigo":"006"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Gomez Rendon 2008 Y Abel Castillo",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"007"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Gomez Rendon 1020 Y Quito",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato",
      "codigo":"008"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Av. Teodoro Maldonado S/N Y Av. Guillermo Pareja",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato Alborada",
      "codigo":"009"
    },
    {  
      "estado":"Abierto",
      "direccion":{  
        "calle":"Av. De Las Americas 99 Y J. Coronel",
        "canton":"Guayaquil",
        "provincia":"Guayas"
      },
      "nombre_comercial":"Mi Comisariato Americas",
      "codigo":"010"
    }
  ]
}
```

### Vehículos

```
GET /vehiculos/:placa
```

Información registrada en la Agencia Nacional de Tránsito.
La placa de tener este formato, ejemplo: "GAG0123".

```json
{  
  "clase":"AUTOMOVIL",
  "fecha_matricula":"31-07-2014",
  "color":"PLATEADO",
  "fecha_caducidad":"30-07-2019",
  "anio_matricula":"2014",
  "modelo":"NUEVO JETTA 162VS1 AC 2.5 4P 4X2 TM",
  "servicio":"PARTICULAR",
  "propietario":{  
    "licencia":"LICENCIA TIPO: B   / VALIDEZ: 30-12-2009 - 30-12-2014",
    "cedula":"0924447956",
    "nombre":"PLAZA ARGUELLO JUAN ANTONIO"
  },
  "anio":"2014",
  "marca":"VOLKSWAGEN"
}
```
