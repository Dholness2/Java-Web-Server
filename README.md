#Simple Java Web Server

### Start Server
 1. cd my-app

 2. mvn package

 3. `java -jar Server.jar -p 5000 -d public`

### Requried Flags
 `-p {PORT}`
 `-d {DIRECTORY PATH}`

### Example
 `java -jar Server.jar -p 5000 -d public`

### Resources
  Default directory resources are available in my-app/public

### To Run Test
  1. cd my-app

  2. mvn clean test
