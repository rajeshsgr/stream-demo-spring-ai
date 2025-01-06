# Streaming Example: Story Generation Endpoint

This Spring Boot application provides two endpoints for generating stories using a chat client (likely Spring AI).

## Prerequisites
Before setting up the project, make sure you have the following:
- **Java 17 or higher**: Spring Boot requires Java 11 or later.
- **Maven**: For building the project and managing dependencies.
- **An AI Service**: This application requires an Open AI client  for generating responses. You will need an API key or endpoint for the `chatClient` to work.
## Setup and Installation

### 1. Clone the Repository

Start by cloning this repository to your local machine.

```bash
git clone https://github.com/your-repository/stream-demo-spring-ai
cd stream-demo-spring-ai 
```


### 2. Configure the AI Client
In the application.properties file, configure the API key


### 3. Build the Project
Use Maven to build the project:

```
mvn clean install
```

### 4. Run the Application
You can run the Spring Boot application with the following command:
```
mvn spring-boot:run
```

### Features

**1. `/storyGen`**

* **Functionality:**
    - Generates a story based on the provided `message` (default: "Tell a story in less than 100 words").
    - Returns the generated story as a stream of text (`Flux<String>`).

**2. `/storyNmetaData`**

* **Functionality:**
    - Generates a story similar to `/storyGen`.
    - Additionally, extracts and logs metadata from the chat client's response, including:
        - Model information
        - Rate limit details (requests, tokens)
        - Usage statistics (prompt tokens, generation tokens)
    - Returns the generated story as a stream of text (`Flux<String>`).

**Usage:**

* **`/storyGen`**
    - Make a GET request to `/storyGen` with an optional `message` query parameter.
    - Receive the generated story as a stream of text.

* **`/storyNmetaData`**
    - Make a GET request to `/storyNmetaData` with an optional `message` query parameter.
    - Receive the generated story as a stream of text.
    - View the logged metadata in the application's console output.

**Note:**

* This README provides a basic overview. Refer to the actual code for detailed implementation.
* Ensure the necessary dependencies (Spring Boot, Spring AI, etc.) are included in the `pom.xml` file.
* Adjust the `chatClient` and metadata extraction logic according to your specific implementation.
