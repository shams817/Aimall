# Aimall
# AI Mall - AI-Powered E-Commerce Platform

A modern, intelligent e-commerce application built with Spring Boot and featuring AI-powered product recommendations and smart shopping features.

## Features

### 🛍️ Shopping Experience
- **Product Browsing**: Browse thousands of products across multiple categories
- **Smart Search**: Full-text search functionality to find products quickly
- **Intelligent Filtering**: Filter by categories, ratings, stock availability, and AI recommendations
- **Shopping Cart**: Add, update, and manage items in your cart
- **Checkout**: Smooth checkout process with delivery address management

### 🤖 AI Features
- **AI Product Scoring**: Intelligent scoring system that evaluates products based on:
  - Customer ratings
  - Stock availability
  - Popularity metrics
  - Price-quality ratio
- **AI Recommendations**: Personalized product recommendations based on AI algorithms
- **AI Shopping Insights**: Smart suggestions during checkout and order processing
- **AI-Powered Order Insights**: Get suggestions based on your cart items

### 👤 User Management
- **User Registration**: Simple registration with email and name
- **User Profiles**: Manage profile information
- **Order History**: Track all your orders and their status
- **Address Management**: Save and use delivery addresses

### 📦 Order Management
- **Order Tracking**: Track your orders from PENDING to DELIVERED
- **Order Status**: Real-time order status updates
- **Order History**: View all past and current orders
- **Delivery Notifications**: Get notified about order status changes

### 💳 Product Management (Admin)
- **Product CRUD**: Create, read, update, and delete products
- **Category Management**: Organize products into categories
- **Stock Management**: Track and update product inventory
- **Rating System**: Products can be rated and reviewed

## Architecture

### Backend Stack
- **Framework**: Spring Boot 4.0.1
- **Database**: H2 (In-Memory Database)
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 21 LTS

### Frontend Stack
- **HTML5**: Modern semantic markup
- **CSS3**: Responsive design with gradients and animations
- **Vanilla JavaScript**: Pure JS (no dependencies) for simplicity
- **REST API Integration**: Consumes backend APIs

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?name=query` - Search products
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/top-rated` - Get top-rated products
- `GET /api/products/ai-recommendations` - Get AI recommendations
- `POST /api/products` - Create product (Admin)
- `PUT /api/products/{id}` - Update product (Admin)
- `DELETE /api/products/{id}` - Delete product (Admin)

### Cart
- `GET /api/cart/{userId}` - Get user's cart
- `POST /api/cart/{userId}/add` - Add item to cart
- `PUT /api/cart/update/{cartItemId}` - Update cart item quantity
- `DELETE /api/cart/remove/{cartItemId}` - Remove item from cart
- `DELETE /api/cart/clear/{userId}` - Clear entire cart
- `GET /api/cart/total/{userId}` - Get cart total

### Orders
- `POST /api/orders/{userId}` - Create order from cart
- `GET /api/orders/{orderId}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get user's orders
- `GET /api/orders` - Get all orders (Admin)
- `PUT /api/orders/{orderId}/status` - Update order status
- `DELETE /api/orders/{orderId}` - Delete order

### Users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users` - Get all users (Admin)
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create category (Admin)
- `PUT /api/categories/{id}` - Update category (Admin)
- `DELETE /api/categories/{id}` - Delete category (Admin)

## Getting Started

### Prerequisites
- Java 21 LTS
- Maven 3.6+ (or use mvnw.cmd on Windows)

### Installation

1. **Clone/Extract Project**
   ```bash
   cd c:\Users\Dell\Desktop\java\springboot\aimall\aimall
   ```

2. **Build Project**
   ```bash
   .\mvnw.cmd clean compile
   ```

3. **Run Application**
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

4. **Access Application**
   - Open browser and navigate to: `http://localhost:8080`
   - H2 Database Console: `http://localhost:8080/h2-console`

### Database Access
- **URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

## Sample Data

The application comes pre-loaded with sample data:

### Categories
- Electronics (iPhones, MacBooks, AirPods, etc.)
- Fashion (Nike shoes, jeans, t-shirts)
- Books (Programming books, design patterns)
- Gaming (PS5, Gaming monitors, headsets)

### Products (12 sample products)
Each product includes:
- Name and description
- Price and stock information
- Customer rating (4.4 - 4.9 stars)
- AI score for intelligent ranking

## Usage Guide

### As a Customer

1. **Browse Products**
   - View all products on the main page
   - Use filters to narrow down results
   - Search for specific products

2. **Sign In**
   - Click "Sign In" button
   - Enter your email and name
   - Account is created automatically

3. **Add to Cart**
   - Click "Add to Cart" on any product
   - View cart by clicking cart icon

4. **Checkout**
   - Click cart icon to view cart
   - Enter delivery address
   - Click "Place Order" to complete purchase

5. **View Orders**
   - Click "My Orders" tab to see all orders
   - Check order status and details

### As an Admin (Using Postman/cURL)

1. **Create Category**
   ```bash
   POST /api/categories
   {
     "name": "Electronics",
     "description": "Electronic devices"
   }
   ```

2. **Create Product**
   ```bash
   POST /api/products
   {
     "name": "Wireless Mouse",
     "description": "Ergonomic wireless mouse",
     "price": 49.99,
     "stock": 100,
     "category": { "id": 1 }
   }
   ```

3. **Update Order Status**
   ```bash
   PUT /api/orders/1/status
   {
     "status": "SHIPPED"
   }
   ```

## Project Structure

```
src/main/java/com/aimall/aimall/
├── AimallApplication.java          # Main Spring Boot application
├── config/
│   └── DataInitializer.java        # Sample data initialization
├── model/                          # Entity classes
│   ├── Category.java
│   ├── Product.java
│   ├── User.java
│   ├── CartItem.java
│   ├── Order.java
│   └── OrderItem.java
├── repository/                     # Data access layer
│   ├── ProductRepository.java
│   ├── CategoryRepository.java
│   ├── UserRepository.java
│   ├── CartItemRepository.java
│   ├── OrderRepository.java
│   └── OrderItemRepository.java
├── service/                        # Business logic layer
│   ├── ProductService.java
│   ├── CartService.java
│   ├── OrderService.java
│   └── UserService.java
└── controller/                     # REST API controllers
    ├── ProductController.java
    ├── CartController.java
    ├── OrderController.java
    ├── UserController.java
    └── CategoryController.java

src/main/resources/
├── application.properties          # Configuration
├── static/
│   └── index.html                 # Frontend UI

pom.xml                            # Maven dependencies
```

## Key Technologies

### Spring Boot Ecosystem
- Spring Web MVC - REST API development
- Spring Data JPA - Object-relational mapping
- Spring Boot DevTools - Development utilities

### Database
- H2 Database - Embedded SQL database
- Hibernate - ORM framework

### Libraries
- Lombok - Reduce boilerplate code
- Jakarta Persistence API - Modern JPA

## Performance Features

- **Intelligent Caching**: Products are cached in memory
- **Optimized Queries**: JPA relationships reduce N+1 problems
- **Responsive UI**: CSS Grid for fast rendering
- **AI Scoring**: Fast in-memory AI calculations

## Security Considerations

For production deployment, consider adding:
- Authentication & Authorization (Spring Security)
- HTTPS/SSL encryption
- Input validation and sanitization
- SQL injection prevention (using JPA)
- CSRF protection
- Rate limiting
- API authentication tokens (JWT)

## Future Enhancements

- [ ] Payment gateway integration
- [ ] Email notifications
- [ ] Advanced AI/ML recommendations
- [ ] Wishlist functionality
- [ ] Product reviews and comments
- [ ] Inventory management dashboard
- [ ] Admin panel UI
- [ ] Multi-currency support
- [ ] Mobile app
- [ ] Analytics dashboard

## Troubleshooting

### Port 8080 already in use
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (replace PID with the process ID)
taskkill /PID <PID> /F

# Or change port in application.properties
server.port=8081
```

### H2 Console not accessible
Ensure `spring.h2.console.enabled=true` in application.properties

### Database not initializing
Check that `spring.jpa.hibernate.ddl-auto=create-drop` is set correctly

## License

This project is open source and available under the MIT License.

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review the API documentation
3. Check Spring Boot logs in console
4. Access H2 console for database inspection

---

**Happy Shopping! 🛍️ Enjoy the AI-Powered Mall Experience!**
