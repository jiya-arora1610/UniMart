
# UniMart
### The University Commerce Network
One Campus. Endless Possibilities.

## Requirements
- Java JDK
- MySQL Server
- VS Code (or any Java IDE)

## Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Create the database:
   - Open MySQL Workbench.
   - Execute `backend/database/schema.sql`.

3. Configure MySQL credentials in:
   `backend/database/DatabaseConnection.java`

   Update the username and password according to your local MySQL setup.

4. Ensure MySQL Connector/J is available in:
   `backend/lib/`

## Compile

Run from the UniMart root directory:

```bash
mkdir -p out

javac -cp "backend/lib/mysql-connector-j-26.10.0.jar" \
-d out $(find backend -name "*.java")
```

## Run

```bash
java -cp "out:backend/lib/mysql-connector-j-26.10.0.jar" \
backend.main.UniMartApp
```

## Notes
- Execute the schema before running the application.
- Each developer must configure their own MySQL credentials.
- The `out/` directory contains compiled files and should not be committed.
- Sample login credentials are available in `schema.sql`.
