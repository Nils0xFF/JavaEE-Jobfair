# Build
mvn clean package && docker build -t de.hsos.kbse/JobBoerse .

# RUN

docker rm -f JobBoerse || true && docker run -d -p 8080:8080 -p 4848:4848 --name JobBoerse de.hsos.kbse/JobBoerse 