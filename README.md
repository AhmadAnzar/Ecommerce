# Minimal E-Commerce Backend API 🛒

## Overview
This is a **Spring Boot** based backend API for a minimal e-commerce application. It handles the core e-commerce flow: Product listing, Cart management, Order creation, and Payment processing.

> **Note:** This project uses a **Mock Payment Provider**. It does not connect to a real banking system. The payment flow simulates a 3rd-party gateway (like Razorpay) to demonstrate the architecture without financial transactions.

---

## 🛠️ Tech Stack
- **Framework:** Spring Boot 3.x (Web, Data MongoDB)
- **Database:** MongoDB
- **Build Tool:** Maven
- **Utilities:** Lombok (for boilerplate reduction)
- **Architecture:** Layered (Controller -> Service -> Repository)

---

## 🚀 Setup & Execution

### 1. Database Setup
Ensure **MongoDB** is running on your local machine or update the connection string in `src/main/resources/application.properties`.
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce_db
```

### 2. Run the Application
Navigate to the project root and run:
```bash
mvn spring-boot:run
```
The server will start at: `http://localhost:8080`

---

## 🔌 API Endpoints

### Products
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/products` | Retrieve all available products. |
| `POST` | `/api/products` | Create a new product (Admin function). |

### Cart
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/cart/add` | Add an item to a user's cart. |
| `GET` | `/api/cart/{userId}` | View items in a user's cart. |
| `DELETE`| `/api/cart/{userId}/clear` | Manually clear a user's cart. |

### Orders
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/orders` | Create an order from the current cart. |
| `GET` | `/api/orders/{orderId}` | Get details of a specific order. |
| `GET` | `/api/orders/user/{userId}` | View order history for a specific user. |

### Payments (Mock)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/payments/create` | Initiate a payment for an order. Returns `PENDING` status. |
| `POST` | `/api/webhooks/payment` | **Simulate** a success callback from the payment provider. |

---

## 🧪 Testing the Payment Flow (Manual Trigger)
Because we are mocking the external bank, the payment does not auto-complete. You must manually trigger the success webhook.

1. **Create Order:** `POST /api/orders` (Response: returns `orderId`)
2. **Start Payment:** `POST /api/payments/create` with `{ "orderId": "..." }`.
   - *Result:* Payment created with status `PENDING`.
3. **Simulate Bank Success:** `POST /api/webhooks/payment`
   - **Body:**
     ```json
     {
       "orderId": "YOUR_ORDER_ID_HERE",
       "status": "captured",
       "paymentId": "pay_ext_mock123"
     }
     ```
4. **Verify:** `GET /api/orders/{orderId}`.
   - *Result:* Status should be `PAID`.

---

## 📂 Project Structure
- **`config`**: App configuration (RestTemplate).
- **`controller`**: REST endpoints.
- **`service`**: Business logic (Stock checks, calculations).
- **`repository`**: Database interfaces.
- **`model`**: MongoDB documents.
- **`dto`**: Request/Response objects.
- **`client`**: Mock client for external services.
- **`webhook`**: Dedicated handler for async payment callbacks.
