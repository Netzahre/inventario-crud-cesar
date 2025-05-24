# Inventory Management System

## Description
A desktop application for managing a product inventory. It allows users to perform CRUD (Create, Read, Update, Delete) operations.

## Features
- Product Management: Add, view, edit, and delete products.
- Category Management: Add, view, edit, and delete categories.
- Search and Filtering: Search and filter products by various criteria.

## Technologies Used
- Java
- Maven
- Hibernate
- JavaFX
- MySQL (or other SQL database)

## Setup and Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/your-repository-name.git
    cd your-repository-name
    ```
2.  **Set up the database:**
    - Ensure you have MySQL installed and running.
    - Create a database named `inventario` (or as specified in `hibernate.cfg.xml`).
    - Import the schema and initial data from `inventario.sql`:
      ```bash
      mysql -u your-username -p inventario < inventario.sql
      ```
    - Configure the database connection in `src/main/resources/hibernate.cfg.xml` if necessary.

3.  **Build the project:**
    ```bash
    mvn clean install
    ```

4.  **Run the application:**
    - **Option 1: Using Maven JavaFX Plugin** (Recommended for development)
      ```bash
      mvn javafx:run
      ```
      This command will compile and run the application. The main class `org.example.dad_aed_proyectocrudeas.ejecutarApp` will be executed.

    - **Option 2: Running the packaged JAR**
      After building the project with `mvn clean install` (or `mvn package`), a JAR file will be created in the `target` directory (e.g., `target/your-project-name-1.0-SNAPSHOT.jar`).
      You can run this JAR file using:
      ```bash
      java -jar target/your-project-name-1.0-SNAPSHOT.jar 
      ```
      (Replace `your-project-name-1.0-SNAPSHOT.jar` with the actual name of the generated JAR file.)

## Usage
1. After successfully building and running the application, the main window will appear.
2. Navigate through the different sections using the menu or toolbar.
3. Use the provided buttons and forms to add, edit, view, or delete records.
4. Utilize search and filter functionalities to find specific items.
