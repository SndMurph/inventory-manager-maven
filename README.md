# Inventory Manager (JavaFX + MySQL + Maven)

A simple inventory management application built in Java using JavaFX for the GUI and MySQL for persistent storage. 
This project was developed to practice object-oriented design, JDBC, and MVC-style architecture with a clean separation 
of data access (DAO) and presentation layers.

## Features

- View, add, update, and delete inventory items.
- Stores all item data (name, quantity, price) in a MySQL database.
- JavaFX GUI with TableView for live display of database records.
- DAO (Data Access Object) pattern for clean database interaction.
- Maven project structure for easy dependency management.

## Technologies Used

- Java 21
- JavaFX 21
- MySQL 8+
- JDBC
- Maven

## Setup Instructions

1. Clone this repository:
   ```bash
   git clone https://github.com/<your-username>/inventory-manager-maven.git
   cd inventory-manager-maven
   ```

2. Configure the database:
   ```sql
   CREATE DATABASE inventory_db;
   USE inventory_db;

   CREATE TABLE items (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       quantity INT DEFAULT 0,
       price DOUBLE DEFAULT 0
   );
   ```

3. Edit your database credentials in `DatabaseManager.java`:
   ```java
   String url = "jdbc:mysql://127.0.0.1:3306/inventory_db";
   String user = "root";
   String password = "your_password";
   ```

4. Run the project:
   ```bash
   mvn clean javafx:run
   ```

## Project Structure

```
InventoryManageMvn/
 ├── src/
 │   ├── main/java/
 │   │   ├── inventorymanage/
 │   │   │   ├── DatabaseManager.java
 │   │   │   ├── InventoryItem.java
 │   │   │   └── dao/
 │   │   │       ├── InventoryDao.java
 │   │   │       └── JdbcInventoryDao.java
 │   │   └── ui/
 │   │       ├── InventoryController.java
 │   │       └── MainApp.java
 │   └── main/resources/
 │       └── ui/inventory_view.fxml
 ├── pom.xml
 ├── .gitignore
 └── README.md
```

## Future Improvements

- Add login and user authentication.
- Improve error handling and validation.
- Add export/import of inventory data (CSV).
- Use background threads for DB queries (Task API).

## Author

Ryan Murphey  
LinkedIn: https://www.linkedin.com/in/ryan-murphey-643ab1371/  
GitHub: https://github.com/SndMurph
