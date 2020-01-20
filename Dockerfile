FROM airhacks/glassfish
COPY ./target/JobBoerse.war ${DEPLOYMENT_DIR}
