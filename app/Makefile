.PHONY: run-dist run build clean test test-src sonar

FIXTURES := src/test/resources/fixtures
FILES ?=
FORMAT ?=

run-dist:
	./build/install/app/bin/app

run:
	./gradlew run
build:
	./gradlew clean build installDist
clean:
	./gradlew clean
test:
	./gradlew test
test-src:
	./gradlew checkstyleMain checkstyleTest

sonar:
	./gradlew sonarqube --info

all: build test-src test sonar
