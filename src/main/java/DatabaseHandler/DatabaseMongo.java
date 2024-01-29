package DatabaseHandler;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.SQLException;

public class DatabaseMongo {
    public MongoClient mongoClient;
    public MongoDatabase mongoDatabase;


    public DatabaseMongo() {

        mongoClient = MongoClients.create("mongodb://localhost:27017/");
        mongoDatabase = mongoClient.getDatabase("Online_Shopping_System");

    }

}

class M {
    public static void main(String[] args) {
        DatabaseMongo databaseMongo = new DatabaseMongo();

    }
}
