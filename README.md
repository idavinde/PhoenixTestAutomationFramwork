# Phoenix API Test Automation Framework

This is a Java-based API test automation framework.

The framework uses different libraries and tools to support API testing, data-driven testing, database validation, logging, reporting, environment-based execution, and continuous integration using GitHub Actions.

## 🚀 About Me

Hi, my name is **Davinder Raju**, and I have more than 6 years of experience in automation testing using technologies such as Selenium WebDriver, Rest Assured, Playwright, TestNG, JavaScript and Java.

My major expertise is in the Java programming language and developing scalable test automation frameworks.

## Author

GitHub: [@idavinde](https://github.com/idavinde)

Email Address: [davinderraju@gmail.com](mailto:davinderraju@gmail.com)

## 🔗 Links

Portfolio: Add your portfolio link here

LinkedIn: Add your LinkedIn profile link here

## Prerequisites

Before running this framework, ensure the following software is installed on your system:

* **Java 16** – Make sure Java is installed and the `JAVA_HOME` environment variable is configured.
* **Maven** – Make sure Maven is installed and added to the system path.
* **Git** – Required to clone the repository.
* **Allure Commandline** – Optional, required to view Allure reports locally.
* **MySQL Database Access** – Required for database validation tests.

Maven download link:

https://maven.apache.org/download.cgi

Verify the installation:

```bash
java -version
mvn -version
git --version
```

## Features

* **API Testing:** Automates REST APIs using Rest Assured.
* **Service Layer:** API endpoints are organized into reusable service classes.
* **Environment-Based Execution:** Supports QA, DEV, and UAT environments.
* **Data-Driven Testing:** Supports CSV, JSON, Excel, Faker, and database test data.
* **Database Validation:** Validates API request data against the MySQL database.
* **Connection Pooling:** Uses HikariCP for database connection management.
* **JSON Schema Validation:** Validates API response structures using JSON schemas.
* **Authentication Management:** Generates and reuses authentication tokens based on user roles.
* **Sensitive Data Protection:** Redacts passwords, authorization headers, and tokens from logs.
* **Logging:** Uses Log4j 2 for detailed test execution logs.
* **Reporting:** Generates detailed Allure reports.
* **Retry Mechanism:** Automatically retries selected failed tests.
* **Parallel Execution:** TestNG data providers support parallel execution.
* **Continuous Integration:** Integrated with GitHub Actions.
* **Scheduled Execution:** Test suites are automatically executed using GitHub Actions cron schedules.

## Technologies Used

* Java 16
* Maven
* Rest Assured
* TestNG
* Jackson
* JSON Schema Validator
* OpenCSV
* Apache POI
* Poiji
* Java Faker
* MySQL
* HikariCP
* dotenv-java
* HashiCorp Vault
* Log4j 2
* Allure Reports
* AspectJ
* GitHub Actions

## Project Structure

```text
src/test/java
│
├── com/api/constants
├── com/api/filters
├── com/api/request/model
├── com/api/response/model
├── com/api/retry
├── com/api/services
├── com/api/tests
├── com/api/tests/datadriven
├── com/api/utils
├── com/database
├── com/database/dao
├── com/database/model
├── com/dataproviders
└── com/listener

src/test/resources
│
├── Config
├── response-schema
└── testData
```

## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/idavinde/PhoenixTestAutomationFramwork.git
```

Open the project directory:

```bash
cd PhoenixTestAutomationFramwork
```

Install the Maven dependencies:

```bash
mvn clean install -DskipTests
```

## Environment Configuration

The framework supports the following environments:

* QA
* DEV
* UAT

Environment configuration files are located inside:

```text
src/test/resources/Config
```

The environment can be selected using the `env` system property.

Example:

```bash
-Denv=qa
```

When no environment is provided, the framework uses the QA environment by default.

## Database Configuration

Create a `.env` file in the project root.

Copy the existing example file:

```bash
cp .env.example .env
```

Add the database connection information:

```env
DB_URL=jdbc:mysql://database-host:3306/database-name
DB_USER=your-database-username
DB_PASSWORD=your-database-password
```

Do not commit the `.env` file to GitHub.

The framework first attempts to retrieve database credentials from HashiCorp Vault. If Vault is unavailable, it reads the credentials from the local `.env` file.

## Running Tests

### Run the Main API Test Suite

```bash
mvn clean test -DsuiteXmlFile=testng.xml -Denv=qa
```

The main API suite covers:

* Login API
* User Details API
* Dashboard Count API
* Dashboard Details API
* Master Data API
* Create Job API

### Run the Data-Driven Test Suite

```bash
mvn clean test -DsuiteXmlFile=testng-datadriven.xml -Denv=qa
```

The data-driven suite executes tests using:

* CSV files
* JSON files
* Excel files
* Faker-generated data
* Database test data
* API-to-database validation

### Run Tests in the DEV Environment

```bash
mvn clean test -DsuiteXmlFile=testng.xml -Denv=dev
```

### Run Tests in the UAT Environment

```bash
mvn clean test -DsuiteXmlFile=testng.xml -Denv=uat
```

### Run Tests with Faker Data

By default, the framework generates five Faker test records.

Use the `fakerCount` property to control the number of generated records:

```bash
mvn clean test -DsuiteXmlFile=testng-datadriven.xml -Denv=qa -DfakerCount=10
```

## TestNG Suite Files

The framework currently contains the following TestNG suite files:

```text
testng.xml
testng-datadriven.xml
```

### `testng.xml`

This file executes the main Phoenix API workflow tests.

### `testng-datadriven.xml`

This file executes tests using CSV, JSON, Excel, Faker, and database test data.

## Data-Driven Testing

The framework supports multiple test-data sources.

### CSV Test Data

CSV test data is read using OpenCSV.

Example files:

```text
src/test/resources/testData/LoginCreds.csv
src/test/resources/testData/CreateJobData.csv
```

### JSON Test Data

JSON test data is read and mapped to Java objects using Jackson.

Example files:

```text
src/test/resources/testData/loginAPITestData.json
src/test/resources/testData/CreateJobAPIData.json
```

### Excel Test Data

Excel test data is read using Apache POI and Poiji.

Example workbook:

```text
src/test/resources/testData/PhoenixTestData.xlsx
```

### Faker Test Data

Java Faker is used to generate dynamic customer and job test data.

### Database Test Data

The framework can retrieve test data directly from the database and provide it to TestNG test methods.

## API Test Coverage

The framework currently covers the following APIs and workflows:

* User login
* Authentication-token generation
* User details
* Dashboard count
* Dashboard details
* Master data
* Job search
* In-warranty job creation
* Response body validation
* Response status-code validation
* Response-time validation
* Content-type validation
* JSON schema validation
* API-to-database validation

## Database Validation

The framework validates whether data submitted through the API is stored correctly in the MySQL database.

The Create Job API database validation covers:

* Customer information
* Customer address
* Customer product
* Job information
* Job problem mapping

The validation flow is:

```text
Create API request
        ↓
Send the API request
        ↓
Validate the API response
        ↓
Extract record IDs from the response
        ↓
Retrieve records from the database
        ↓
Compare API data with database data
```

## Reports and Logs

### Allure Reports

After test execution, Allure result files are generated inside:

```text
target/allure-results
```

Generate the Allure HTML report:

```bash
mvn allure:report
```

View the Allure report locally:

```bash
mvn allure:serve
```

The generated static report is stored inside:

```text
target/site/allure-maven-plugin
```

The report contains:

* Test cases executed
* Passed tests
* Failed tests
* Skipped tests
* Test descriptions
* Test steps
* Severity levels
* Features and stories
* API request and response information
* Selected environment
* Java version
* Operating system
* API base URI

### Logs

Logs are created during test execution and stored inside:

```text
./logs/
```

The logs contain:

* Test start and completion details
* API request information
* API response information
* HTTP status
* Response time
* Test execution duration
* Database connection activity
* Retry attempts
* Passed, failed, and skipped test information

Passwords, authentication tokens, and authorization headers are redacted from the logs.

## GitHub Actions Integration

This automation framework is integrated with GitHub Actions.

The workflow file is located at:

```text
.github/workflows/ci.yml
```

The tests are executed automatically:

* When code is pushed to the configured branches
* When a pull request is created against the `master` branch
* Through scheduled cron executions
* Manually using GitHub Actions workflow dispatch

The workflow allows the user to select an environment:

* QA
* UAT
* DEV

The currently available TestNG suites are:

* `testng.xml`
* `testng-datadriven.xml`

The GitHub Actions workflow performs the following steps:

1. Checks out the repository.
2. Sets up Java.
3. Caches Maven dependencies.
4. Creates the `.env` file using GitHub Secrets.
5. Executes the selected TestNG suite.
6. Generates the Allure report.
7. Uploads the Surefire test reports.
8. Deploys the Allure report to the `gh-pages` branch.

Required GitHub Secrets:

```text
DB_URL
DB_USER
DB_PASSWORD
```

The published Allure report can be viewed at:

```text
https://idavinde.github.io/PhoenixTestAutomationFramwork/
```

GitHub Pages must be configured to deploy from the `gh-pages` branch.

## Security

* Never commit the `.env` file.
* Never hardcode database passwords or authentication tokens.
* Store CI credentials in GitHub Secrets.
* Store local credentials in the `.env` file.
* Store shared environment credentials in Vault.
* Do not use real customer information as test data.
* Redact passwords and tokens from logs and reports.
