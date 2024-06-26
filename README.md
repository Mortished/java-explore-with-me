# java-explore-with-me

## Sprint-18

### 1. Подготовлена сборка проекта
1. Собрать **.jar** проекта - `mvn clean package`
2. Запустить приложение в контейнере - `docker compose up`

### 2. Реализован сервис статистики c эндпоинтами:

- `POST /hit`
- `GET /stats`

### 3. Реализован HTTP-клиент для работы с сервисом статистики

### 4. Реализован основной сервер с эндпоинтами:

#### Public: Подборки событий
Публичный API для работы с подборками событий
- `GET /compilations` - Получение подборок событий
- `GET /compilations/{compId}` - Получение подборки событий по его id

#### Admin: Категории
API для работы с категориями
- `POST /admin/categories` - Добавление новой категории
- `DELETE /admin/categories/{catId}` - Удаление категории
- `PATCH /admin/categories/{catId}` - Изменение категории

#### Private: События
Закрытый API для работы с событиями
- `GET /users/{userId}/events` - Получение событий, добавленных текущим пользователем
- `POST /users/{userId}/events` - Добавление нового события
- `GET /users/{userId}/events/{eventId}` - Получение полной информации о событии добавленном текущим пользователем
- `PATCH /users/{userId}/events/{eventId}` - Изменение события добавленного текущим пользователем
- `GET /users/{userId}/events/{eventId}/requests` - Получение информации о запросах на участие в событии текущего пользователя
- `PATCH /users/{userId}/events/{eventId}/requests` - Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя

#### Public: Категории
Публичный API для работы с категориями
- `GET /categories` - Получение категорий
- `GET /categories/{catId}` - Получение информации о категории по её идентификатору

#### Admin: События
API для работы с событиями
- `GET /admin/events` - Поиск событий
- `PATCH /admin/events/{eventId}` - Редактирование данных события и его статуса (отклонение/публикация).

#### Public: События
Публичный API для работы с событиями
- `GET /events` - Получение событий с возможностью фильтрации
- `GET /events/{id}` - Получение подробной информации об опубликованном событии по его идентификатору

#### Private: Запросы на участие
Закрытый API для работы с запросами текущего пользователя на участие в событиях
- `GET /users/{userId}/requests` - Получение информации о заявках текущего пользователя на участие в чужих событиях
- `POST /users/{userId}/requests` - Добавление запроса от текущего пользователя на участие в событии
- `PATCH /users/{userId}/requests/{requestId}/cancel` - Отмена своего запроса на участие в событии

#### Admin: Пользователи
API для работы с пользователями
- `GET /admin/users` - Получение информации о пользователях
- `POST /admin/users` - Добавление нового пользователя
- `DELETE /admin/users/{userId}` - Удаление пользователя

#### Admin: Подборки событий
API для работы с подборками событий
- `POST /admin/compilations` - Добавление новой подборки (подборка может не содержать событий)
- `DELETE /admin/compilations/{compId}` - Удаление подборки
- `PATCH /admin/compilations/{compId}` - Обновить информацию о подборке

### 5. Реализована фича - Комментарии
- `POST /users/{userId}/events/{eventId}/comment` - Добавление комментария
- `PATCH /users/{userId}/events/{eventId}/comment/{commentId}` - Изменение комментария
- `DELETE /admin/events/{eventId}/comment/{commentId}/delete` - Удаление комментария