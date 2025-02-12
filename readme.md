# Coffee Machine API

## Описание
Coffee Machine API - это RESTful сервис для управления рецептами, ингредиентами и статистикой приготовления напитков.

## Стек технологий
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- OpenAPI (Swagger)
- Docker

## Установка и запуск
### 1. Клонирование репозитория
```sh
git clone https://github.com/xfxom/coffee_machine
cd coffee_machine
```

### 2. Запуск через Docker Compose
Сборка и запуск приложения выполняются следующими командами:
```sh
docker-compose up --build -d
```

## API эндпоинты
### Рецепты (`/api/recipes`)
- `GET /api/recipes` - получить все рецепты
- `GET /api/recipes/{id}` - получить рецепт по ID
- `POST /api/recipes` - создать новый рецепт
- `POST /api/recipes/{id}/make` - приготовить напиток
- `PUT /api/recipes/{id}` - обновить рецепт
- `DELETE /api/recipes/{id}` - удалить рецепт

### Ингредиенты (`/api/ingredients`)
- `GET /api/ingredients` - получить все ингредиенты
- `GET /api/ingredients/{id}` - получить ингредиент по ID
- `POST /api/ingredients` - создать ингредиент
- `PUT /api/ingredients/{id}` - обновить ингредиент
- `DELETE /api/ingredients/{id}` - удалить ингредиент

### Статистика (`/statistic`)
- `GET /statistic/most-popular` - получить самый популярный напиток

## Документация API
Swagger доступен по адресу: `http://localhost:8080/swagger-ui/index.html`

