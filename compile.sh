mkdir -p CompiledApps
mvn clean compile assembly:single -Pmanager
mv target/enchere-projet-1.0-SNAPSHOT-jar-with-dependencies.jar CompiledApps/Manager.jar
chmod +x CompiledApps/Manager.jar
mvn clean compile assembly:single -Pseller
mv target/enchere-projet-1.0-SNAPSHOT-jar-with-dependencies.jar CompiledApps/Seller.jar
chmod +x CompiledApps/Seller.jar
mvn clean compile assembly:single -Pbidder
mv target/enchere-projet-1.0-SNAPSHOT-jar-with-dependencies.jar CompiledApps/Bidder.jar
chmod +x CompiledApps/Bidder.jar

