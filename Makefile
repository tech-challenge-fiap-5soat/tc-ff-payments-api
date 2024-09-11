# Variáveis
MAVEN = mvn
JAR_FILE = target/payments-api-0.0.1-SNAPSHOT.jar

# Alvos
.PHONY: all clean build run test

all: clean build run

clean:
	$(MAVEN) clean

build-jar:
	$(MAVEN) package -DskipTests

run:
	$(MAVEN) spring-boot:run

test:
	$(MAVEN) test

start-local-development:
	docker compose -f docker-compose.yaml up

stop-local-development: 
	docker compose -f docker-compose.yaml down

# Alvo para executar o Docker
build-image:
	docker build -t payments-api -f Dockerfile .

docker-run:
	docker run -p 8079:8079 payments-api