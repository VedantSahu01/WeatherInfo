# Weather Information API

## Introduction

This project is a Java Spring application that provides a REST API to fetch weather information for a given pincode and date. It utilizes the OpenWeather API to fetch weather data and stores it in a MySQL database for future references. Users can query the API to retrieve weather information for a specific pincode and date.

## Project Setup

### Prerequisites
- Java 17 or higher
- Docker

### Setup Steps
1. Clone the project repository.
2. Navigate to the project directory.
3. Update the OpenWeather API key in the `application.yml` file.
4. Run the following command to start the MySQL database using Docker Compose:
    ```
    docker-compose up -d
    ```
5. After the database is up and running, build and run the Spring application using Maven:
    ```
    mvn spring-boot:run
    ```

## API Contract

### Get Weather Information by Pincode and Date

#### Endpoint

GET /weather/{pincode}

#### Query Parameters
- `for_date` (optional): The date for which weather information is requested. Format: YYYY-MM-DD. If not provided, current date will be used.

#### Response
- Success: HTTP 200 OK
    ```
    {
        
    }
    ```
- Not Found: HTTP 404 Not Found
    ```
    "Weather information not found"
    ```

#### Example
GET /weather/411014

```
{
  "weather": [
    {
      "main": "Clouds",
      "description": "broken clouds",
      "icon": "04d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 307.5,
    "feels_like": 307.1,
    "temp_min": 307.5,
    "temp_max": 307.5,
    "pressure": 1004,
    "humidity": 31,
    "sea_level": 1004,
    "grnd_level": 934
  },
  "visibility": 10000,
  "wind": {
    "speed": 3.47,
    "deg": 303,
    "gust": 2.37
  },
  "clouds": {
    "value": null
  },
  "rain": null,
  "dt": 1713955288
}
```

## Note
- Historical weather data requires a paid API token from OpenWeather. If historical data is not available in the database, it may be necessary to subscribe to OpenWeather's paid services to access historical weather information.
