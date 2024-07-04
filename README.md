# Bus Ticket Booking Management System

## Introduction

This project is a Bus Ticket Booking Management System. It lets users register, log in, search for buses, book tickets, and get booking confirmation PDFs. The system supports both regular users and admins. Admins can manage buses by adding, updating, and deleting bus details. You can find the frontend part, documentation, and screenshots in the provided Drive link. Please read the mandatory information.

## Table of Contents

- Introduction
- Features
- Technologies Used
- Project Structure
  - DTO
  - Entities
  - Repositories
  - Services
  - Controllers
- Running the Application
  - Prerequisites
  - Steps
- Usage
  - User Registration and Login
  - Admin Functionalities
  - Booking Tickets
- Mandatory Information
- FrontEnd

## Features

- User registration and login
- Different access controls for Admin and User roles
- Search for buses by origin, destination, and date
- Book bus tickets and provide passenger details
- View individual booking tickets
- Generate booking confirmation PDFs
- Admin functions to add, update, and delete bus details

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Thymeleaf
- iTextPDF for PDF generation
- Lombok
- Jakarta Servlet API

## Project Structure

### DTO

Data Transfer Objects used to transfer data between different parts of the application.

- **UserDto.java**: DTO for user registration and login.

### Entities

Database entities representing the main parts of the system.

- **Passengers.java**: Represents passengers.
- **Bus.java**: Represents buses.
- **Booking.java**: Represents bookings.
- **PassengerDetail.java**: Represents details of passengers in a booking.

### Repositories

Interfaces for accessing data.

- **PassengerRepository.java**: Repository for the `Passengers` entity.
- **BusRepository.java**: Repository for the `Bus` entity.
- **BookingRepository.java**: Repository for the `Booking` entity.

### Services

Contains the business logic of the application.

- **PassengerService.java**: Implements `UserService` and handles passenger-related operations.
- **GeneratePdfService.java**: Service for generating PDFs.
- **CustomUserDetailService.java**: Service for loading user details for Spring Security.
- **CustomHandlerUsers.java**: Custom authentication success handler.
- **BusService.java**: Handles bus-related operations.
- **BookingService.java**: Handles booking-related operations.

### Controllers

Handles HTTP requests.

- **PassengerController.java**: Handles passenger-related endpoints.
- **BusController.java**: Handles admin and bus-related endpoints.
- **BookingController.java**: Handles booking-related endpoints.

## Running the Application

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- A relational database (e.g., MySQL, PostgreSQL)

1. Build the project:
    
    mvn clean install

2. Run the application:

    mvn spring-boot:run


3. Access the application:
    - http://localhost:8080

## Usage

### User Registration and Login

- Register a new user at `/registration`.
- Log in with registered credentials at `/login`.

### Admin Functionalities

- Access the admin page at `/admin` after logging in as an admin.
- Add a new bus at `/addBus`.
- View all buses at `/viewAllBuses`.
- Find a bus by ID at `/findBusById`.
- Update bus details at `/updateByBus`.
- Delete a bus at `/delete/{serialNo}`.

### Booking Tickets

- Search for buses from the user home page.
- Book a bus at `/bookBus/{busId}`.
- View bookings at `/bookings`.
- Generate a PDF of a booking at `/generatePdf/{bookingId}`.
