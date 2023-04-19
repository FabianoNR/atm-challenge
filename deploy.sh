set -e

./gradlew clean build

docker build -t my/atm .

docker run -p 8080:8080 -d my/atm
