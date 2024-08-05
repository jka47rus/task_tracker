# Task tracker

Трекер задач - приложение написанное на Spring с использованием нереляционной базы данных MongoDB в реактивной
парадигме. Имеется две сущности: User и Task.
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
5. Добавление наблюдателя в задачу;
6. Удаление задачи по ID.
