# Variables
MVN = mvn
MAIN_CLASS = com.example.api.App

# Compile the project
all:
	$(MVN) compile

# Run the application
run: all
	$(MVN) exec:java -Dexec.mainClass="$(MAIN_CLASS)"

# Run tests
test:
	$(MVN) test

# Clean compiled files
clean:
	$(MVN) clean

.PHONY: all run test clean