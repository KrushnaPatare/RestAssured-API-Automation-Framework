# 🚀 REST Assured API Testing Framework for PetStore Swagger API

### *by Krushna-QA*

---

## 📘 About the Project

### Overview
This project provides a **REST API testing framework** designed to automate the testing of the **PetStore Swagger API**. The framework ensures the API's quality and reliability by covering **CRUD operations** (Create, Read, Update, Delete) for the **Pet**, **Store**, and **User** models.

### Features
- 🚦 **Comprehensive API testing** for `GET`, `POST`, `PUT`, and `DELETE` requests.
- ✨ **Dynamic Data Generation** using **POJOs** and the **Faker** library for generating random test data.
- 📊 **Detailed Test Reporting** via **Extent Reports** for clean, customizable, and easy-to-read HTML reports.
- 📝 **Logging** using **Log4j2** for traceable logs and easier debugging.
- ✅ **JSON Schema Validation** to ensure API responses conform to predefined structures.
- 🔁 **Parameterized Tests** for reusability and thorough coverage across multiple data sets.
- 🛠️ **Utility Methods** for simplifying request setup, authentication handling (if required), and managing logging.

---

## 🛠️ Technologies Used
- **IDE**: Eclipse
- **Programming Language**: Java
- **Test Framework**: TestNG
- **API Testing Library**: REST Assured
- **Build Tool**: Maven
- **Reporting**: Extent Reports
- **Logging**: Log4j2
- **Data Generation**: Faker (for dynamic test data)

---

## 📂 Framework Structure

The project is structured into four main packages:

- **`api.endpoints`**
  - Each model (Pet, Store, User) has its own **Endpoint** class responsible for performing CRUD operations using REST Assured.

- **`api.path`**
  - Contains the **Routes** class to manage base URLs and endpoint paths.

- **`api.payload`**
  - Houses **POJO classes** representing Pet, Store, and User data models for requests and responses.

- **`api.payloadManager`**
  - Houses **Inner POJO classes** of **POJO classes** representing Pet, Store, and User data models for requests and responses.

- **`api.test`**
  - Includes **Test classes** for each endpoint, containing test methods for executing API requests.
  - Uses **setup methods** to generate dynamic data with **POJOs** and **Faker**.

- **`api.utilities`**
  - Contains utility classes such as:
    - **ExtentReportManager** for generating HTML reports using **Extent Reports**.
    - **DataProvider** for performing Data Driven Testing.
    - **PojoSetter** class for accepting arguments from dataprovider and returing **POJO classes** that are used as payload.


---

## 🚀 Setup and Usage

### Prerequisites
Make sure the following tools are installed:
- **Java 11+**
- **Maven 3.6+**
# PetStore RestAssured Automation Framework

This repository contains an automation framework built using RestAssured for testing Pet Store APIs. The framework is designed to validate status codes, response bodies, headers, and more, ensuring thorough API testing.

## 📦 Installation

To set up the project locally, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/MUSTAPHA-QA/PetStore-RestAssured-Framework.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd PetStore-RestAssured-Framework
    ```

3. **Install project dependencies**:
    ```bash
    mvn clean install
    ```

## 🚀 Running the Tests

You can run the tests using Maven commands:

- **To execute all tests**:
    ```bash
    mvn test
    ```

- **To run specific test classes or suites**:  
   Configure your `TestNG` XML or run the tests directly from the IDE.

## 📊 Generating Test Reports

The framework generates **Extent Reports** to visualize test execution results. Follow these steps to view the reports:

1. Execute the tests:
    ```bash
    mvn test
    ```

2. After test execution, open the report:
    - Navigate to the `reports` folder.
    - Open the **Extent Report HTML** file in your browser to view detailed results.

## 📁 Project Structure

The project is structured using standard **Maven** conventions:

    PetStore-RestAssured-Framework
    ├── src
    │   └── test
    │       ├── java
    │       │   ├── api.endpoints           # Contains API endpoints logic                         
    │       │   ├── api.path                # Contain urls
    │       │   ├── api.payload             # Contains payload creation logic
    │       │   ├── api.test                # Test classes for API testing
    │       │   └── api.utilities           # Utility classes (e.g., data provider, extent report generator , helper methods)
    │       └── resources
    │           └── log4j2.xml              # Logging configuration
    ├── logs                                # Contains generated logs
    ├── reports                             # Contains generated extent reports
    ├── screenshots                         # Folder containing screenshots of extent report
    ├── testData
    │   ├── excelDataFiles                  # Contains Excel data fies for data driven testing
    │   ├── images                          # Contains images that are used to upload 
    │   ├── jsonDataFiles                   # Contains JsonData files used as input payload
    │   └── jsonSchemaFiles                 # Contains Schema files for schema validation
    ├── pom.xml                             # Maven configuration
    └── testng.xml                          # Test configuration



To ensure a clear and organized project structure, we've adopted the following conventions:

- **Source Code:**  
  All project packages are located within the `src/test/java` folder, adhering to Maven's standard test code directory structure.

- **External Resources:**  
  The `testData` folder serves as a repository for external files essential for testing, such as:
  - **Schema Files:** Used for JSON schema validation
  - **Excel Files:** Used for Data Driven Testing
  - **Json Files:** Used as Payload of request


---

## **Key Achievements**

#### ✅ **Status Code Validation**
- Verified all HTTP status codes against the expected results from the Swagger API documentation.

#### ✅ **Response Body Validation**
- Ensured the response body accurately reflects the Swagger API specification, ensuring consistency and correctness.

#### ✅ **Header Validation**
- Selected HTTP headers were validated for correctness, further ensuring that the API adheres to expected standards.

#### ✅ **Robust Error Handling**
- Meaningful error messages were captured, and tests validated correct status codes for various failure scenarios.

#### ✅ **JSON Schema Validation**
- Ensured API responses comply with the predefined JSON schema, guaranteeing the integrity of the response format.

---

## **Screenshots of Extent Report**

The framework generates a comprehensive **Extent Report** that visually presents the test results. Below is a sample screenshot from the report:

![Extent Report Screenshot](https://github.com/KrushnaPatare/RestAssured-API-Automation-Framework/blob/main/screenshots/Screenshot%202024-10-23%20190452.png)

![Extent Report Screenshot](https://github.com/KrushnaPatare/RestAssured-API-Automation-Framework/blob/main/screenshots/Screenshot%202024-10-23%20190024.png)

For additional reports and logs, please check the `screenshots` folder.

---

This framework ensures that the PetStore API meets its expected behavior through thorough validation and automation, delivering reliable and detailed results.


