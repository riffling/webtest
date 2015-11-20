mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 \
  -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
  -DgroupId=com.example -DartifactId=jersey-jaxrs -Dpackage=com.example \
  -DarchetypeVersion=2.22.1
  
mvn clean test
mvn exec:java  