package pl.norbit.pluginutils.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.Document;

@Getter
public class MongoDB {

    private MongoClient mongoClient;
    private String
    password, user, host, port;
    private boolean useSSL;
    private MongoDB (){
        password = "";
        user = "";
        host = "";
        port = ":27017";
    }

    @Builder
    private static MongoDB of(String password, String user, String host, int port, boolean useSSL){
        MongoDB mongoDB = new MongoDB();
        if(password != null) {
            if(!password.isEmpty()) {
                mongoDB.password = ":" + password;
            }
        }

        if(user != null) {
            mongoDB.user = user;
        }

        if(host != null) {
            if(user == null){
                mongoDB.host = host;
            }else {
                if(!user.isEmpty()) {
                    mongoDB.host = "@" + host;
                }else{
                    mongoDB.host = host;
                }
            }
        }else{
            return null;
        }

        if(port != 0) {
            mongoDB.port = ":" + port;
        }

        mongoDB.useSSL = useSSL;

        return mongoDB;
    }

    public void openConnection(){
        String URI = "mongodb://" + user + password + host + port;

        if(useSSL){
           URI =  URI + "&tls=true";
        }

        mongoClient = MongoClients.create(URI);
    }

    @AllArgsConstructor
    public static class Database {
       private MongoDatabase mongoDatabase;

       public MongoCollection<Document> getCollection(String collectionName){
           return mongoDatabase.getCollection(collectionName);
       }
    }

    public Database getDatabase(String databaseName){
        return new Database(mongoClient.getDatabase(databaseName));
    }

    public void closeConnection(){
        mongoClient.close();
    }
}
