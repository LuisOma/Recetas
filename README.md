# Recetas
Proyecto de demostración, basado en una arquitectura CLEAN con MVVM.

## Funciones de la aplicación

- Los usuarios pueden ver la lista de recetas.
- Los usuarios pueden hacer clic en cualquier receta para ver los detalles de la misma.
- Los usuarios pueden visualizar, en mapa, el lugar de creación de la receta.

## Arquitectura de la aplicación
Basado en la arquitectura Clean y el patrón MVVM.

## La aplicación incluye los siguientes componentes principales:
- Un servicio de API web.
- Un datasource que trabaja con el servicio api, proporcionando una interfaz de datos unificada.
- Un ViewModel que proporciona datos específicos para la interfaz de usuario desde el dataSource.
- La interfaz de usuario, que muestra una representación visual de los datos en ViewModel.

## Paquetes de aplicaciones
- *app:*
- base
- ui

- *Módulo data:*
- dataSource.
- domain.
- extensions.
- local.
- network.

## Especificaciones de la aplicación
- SDK mínimo 26
- Java (en la rama maestra) y Kotlin (en la rama kotlin_support)
- Arquitectura MVVM
- Componentes de la arquitectura de Android (LiveData, ViewModel, Navigation, Material Design)
- **Retrofit 2** para integración API.
- **Gson** para serialización.
- **Picasso** para cargar imágenes.
- **ViewModel** para pasar datos del modelo a las vistas
- **LiveData**
- **Corutinas**
