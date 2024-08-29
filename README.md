# Task tracker

Трекер задач - приложение написанное на Spring с использованием Spring Security, не реляционной базы данных MongoDB в
реактивной парадигме. Имеется две сущности: User и Task.
Контроллеры написаны с использованием Mono и Flux.

Посредством Api-контроллера для сущности User, приложение выполняет следующие задачи:

1. Поиск всех пользователей;
2. Поиск пользователя по ID;
3. Создание пользователя;
4. Обновление информации о пользователе;
5. Удаление пользователя по ID.

Посредством Api-контроллера для сущности Task, приложение выполняет следующие задачи:

1. Поиск всех задач (в ответе находятся вложенные сущности, которые описывают автора задачи и исполнителя, а также
   содержат список наблюдающих за задачей);
2. Поиск конкретной задачи по ID (в ответе находятся вложенные сущности, которые описывают автора задачи и исполнителя,
   а также содержат список наблюдающих за задачей);
3. Создание задачи;
4. Обновление задачи;
5. Добавление наблюдателя в задачу (пользователь имеет право добавить сам себя, если он зарегистрирован);
6. Удаление задачи по ID.

Также реализованна basic authentication с помощью spring security:

1. Контроллер, который предоставляет операции взаимодействия с сущностью User:
    - получение (как списком, так и по ID), обновление и удаление профилей пользователей доступны только тем
      клиентам, которые имеют одну из следующих ролей: ROLE_USER, ROLE_MANAGER.
2. Контроллер, отвечающего за работу с задачами:
    - получение списка задач, получение задачи по ID, добавление наблюдателя доступны пользователю с одной из следующих
      ролей: ROLE_USER, ROLE_MANAGER;
    - создание, обновление и удаление задачи доступны пользователю с ролью ROLE_MANAGER.