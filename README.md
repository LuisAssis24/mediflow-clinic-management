# MediFlow

Desktop application developed as part of the Applied Application Development module in the Information Technologies programme at the University of Aveiro.
The system centralises scheduling, clinical records, and credential management for a medical clinic, through a Java Swing interface backed by a MySQL database.

## Overview

MediFlow was designed to support different roles:
- Managers create and maintain staff credentials, assigning specialties, rooms, and schedules.
- Secretaries schedule, look up, and cancel appointments, manage patient data, and verify doctors availability.
- Doctors review their daily agenda, update clinical records, and add clinical entries and prescriptions.

All information is persisted through MySQL stored procedures, and passwords are encrypted before reaching the database.

## Key Features

- Authentication backed by the `CALL VerificarLogin` procedure and DES encryption (`sql/server/CifrarPasswords.java`).
- Manager console: user search, credential CRUD, room and schedule assignment (`inter/face/VistaGestor.java`).
- Secretary console: booking/cancelation of appointments, patient creation, doctor agenda queries (`inter/face/VistaSecretaria.java`).
- Doctor console: dynamic appointment list, direct access to clinical records, prescription issuing (`inter/face/VistaMedico.java`, `inter/face/RegistoClinicoPanel.java`).
- Detailed clinical records with history, allergies, surgeries, and doctor entries (`medi/flow/RegistoClinico.java`, `sql/server/SqlMedico.java`).
- Utilities for text/date formatting (`medi/flow/Text.java`) and a segmented data-access layer (`SqlGeral`, `SqlGestor`, `SqlSecretaria`, `SqlMedico`).

## Architecture

```
src/
  main/
    java/
      medi/flow/        # Domain and business logic
      inter/face/       # Swing interfaces generated with NetBeans
      sql/server/       # MySQL integration via stored procedures
    resources/images/   # UI icons
  test/java/            # JUnit 5 and JUnit 4 tests
pom.xml                  # Maven configuration (Java 21)
```

## Requirements

- JDK 21
- Maven 3.9+
- MySQL Server 8.x with stored procedures:
  - `VerificarLogin`, `SelecionarConsultas`
  - `ObterTodosUtilizadores`, `ObterMedicoPorId`, `CriarUtilizador`, `InserirMedico`, `ObterTodasSalas`, `ObterSalaMedico`, `EliminarMedico`, `EliminarUtilizador`
  - `ObterTodosMedicos`, `ObterHorariosMedico`, `ObterTodosPacientes`, `MarcarConsulta`, `DesmarcarConsulta`, `CriarPaciente`, `CriarNovoRegisto`
  - `ObterRegistosClinicos`, `ObterEntradasDeRC`, `CriarEntradaRegistoClinico`, `AlterarRegistoClinico`
- Maven dependencies: `mysql-connector-java`, `org.netbeans.external:AbsoluteLayout`, JUnit 5/4 (tests)
- IDE with Swing form designer support (NetBeans recommended) to edit `.form` files

## Environment Setup

1. Clone the repository.
2. Update the database credentials (`URL`, `USER`, `PASSWORD`) in `src/main/java/sql/server/SqlGeral.java`.
3. Ensure the database contains the expected tables and stored procedures (not included in this repo).
4. Optional: adjust or externalise the DES key in `sql/server/CifrarPasswords.java`.

## Build and Run

```powershell
# Build artifact
mvn clean package

# Copy runtime dependencies
mvn dependency:copy-dependencies -DincludeScope=runtime

# Launch (Windows)
java -cp "target/MediFlow-1.0-SNAPSHOT.jar;target/dependency/*" medi.flow.Main

# Launch (macOS/Linux)
java -cp "target/MediFlow-1.0-SNAPSHOT.jar:target/dependency/*" medi.flow.Main
```

You can also run `medi.flow.Main` directly from your IDE.

## Tests

Tests live in `src/test/java` and cover connection, encryption, and domain logic. Run them with:

```powershell
mvn test
```

Note: Some tests hit the live database, so matching data must be available.

## Usage Flow

1. On startup (`medi.flow.Main`) the application connects to MySQL and opens the login screen.
2. The user type returned by `VerificarLogin` routes to the appropriate console (Manager, Doctor, Secretary).
3. Actions in each console update the in-memory `medi.flow.Clinica` instance and synchronise via dedicated stored procedures.

## Important Notes

- Database credentials are hardcoded; move them to environment variables or config files before distributing.
- DES encryption with a fixed key in `CifrarPasswords` is for academic purposes only. Replace with stronger algorithms/key management in production.
- The `target/` directory can be cleaned with `mvn clean` if you need a fresh workspace.


