package Logic;

import java.awt.*;
import java.sql.*;

import DatabaseHandler.Database;
import DatabaseHandler.DatabaseMongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SellerBackend extends DatabaseMongo {

    public static String sellerEmail;
    public static ArrayList<String> productName;
    public static ArrayList<String> help;

    public SellerBackend() throws SQLException {

    }

    public boolean validateSeller(String emailAddress, String password) {

        String validation = "SELECT * FROM SELLER";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Seller");

        Document valid = collection.find(new Document("Email", emailAddress).append("Password", password)).first();

        if (valid != null) {
            sellerEmail = emailAddress;
        }
        return valid != null;
    }

    public boolean signupSeller(String firstName, String lastName, String emailAddress, String phoneNumber, String gender, String address, String password) {

        // Seller_Email, FirstName, LastName, PhoneNumber, Gender, Password, DeliveryAddress
        String sql = "INSERT INTO Seller (Seller_Email, FirstName, LastName, PhoneNumber, Gender, Password, DeliveryAddress) VALUES (?,?,?,?,?,?,?)";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Seller");

        Document document = new Document("Email", emailAddress).append("FirstName", firstName).append("LastName", lastName).append("Gender", gender)
                .append("Password", password)
                .append("DeliveryAddress", address);

        collection.insertOne(document);
        return true;

    }

    public boolean addToCategory(String categoryName) {
        String sql = "SELECT * FROM Categories WHERE CategoryName='" + categoryName + "'";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Categories");

        String cat = collection.find(new Document("CategoryName", categoryName)).first().getString("CategoryName");
        if (cat != null) {
            return false;
        } else {


            MongoCursor<Document> cursor = collection.find().cursor();
            ArrayList<Integer> catID = new ArrayList<>();
            while (cursor.hasNext()) {
                catID.add(cursor.next().getInteger("CategoryID"));
            }

            collection.insertOne(new Document("CategoryID", (catID.size() + 1)).append("CategoryName", categoryName));
            return true;
        }
    }


    public String[] getCategoryName() {
        String validation = "SELECT CategoryName FROM categories";

        MongoCursor<Document> cursor = mongoDatabase.getCollection("Categories").find().cursor();
        ArrayList<String> categories = new ArrayList<>();
        String[] category = new String[0];


        while (cursor.hasNext()) {
            Document rs = cursor.next();
            String categories_ = rs.getString("CategoryName");

            categories.add(categories_);

        }
        category = new String[categories.size()];


        for (int i = 0; i < categories.size(); i++) {
            category[i] = categories.get(i);
        }

        return category;
    }


    public boolean addItem(String productName, int productPrice, String productDesc, String categoryName) {

        String sql = "SELECT CategoryID FROM Categories WHERE CategoryName= '" + categoryName + "'";

        int categoryID = mongoDatabase.getCollection("Categories").find(new Document("CategoryName", categoryName)).first().getInteger("CategoryID");

        MongoCursor<Document> cursor = mongoDatabase.getCollection("Products").find().cursor();
        ArrayList<Integer> catID = new ArrayList<>();
        while (cursor.hasNext()) {
            catID.add(cursor.next().getInteger("CategoryID"));
        }

        mongoDatabase.getCollection("Products").insertOne(new Document()
                .append("ProductID", (catID.size() + 1))
                .append("ProductPrice", productPrice)
                .append("ProductDesc", productDesc)
                .append("Seller_Email", sellerEmail)
                .append("CategoryID", categoryID)
                .append("ProductName", productName));

        return true;
    }

    public ArrayList<String> showCategory() {

        MongoCursor<Document> cursor = mongoDatabase.getCollection("Categories").find().cursor();
        ArrayList<String> categories = new ArrayList<>();

        while (cursor.hasNext()) {
            Document rs = cursor.next();
            String categories_ = rs.getString("CategoryName");

            categories.add(categories_);

        }
        return categories;
    }

    public ArrayList<String> retrieveProducts(String category) {
        String sql = "SELECT * FROM Products NATURAL JOIN Categories Where Seller_Email='" + sellerEmail + "' and CategoryName='" + category + "'";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Categories");

        List<Bson> pipeline = Arrays.asList(new Document("$lookup", new Document()
                        .append("from", "Products")
                        .append("localField", "CategoryID")
                        .append("foreignField", "CategoryID")
                        .append("as", "Product_Details")),
                new Document("$match", new Document("CategoryName", category)));

        MongoCursor<Document> cursor = collection.aggregate(pipeline).cursor();
        ArrayList<String> arr = new ArrayList<>();
        productName = new ArrayList<>();

        while (cursor.hasNext()) {
            Document rs = cursor.next();

            ArrayList<Document> product_details = (ArrayList<Document>) rs.get("Product_Details");

            for (int i = 0; i < product_details.size(); i++) {
                String email = product_details.get(i).getString("Seller_Email");
                if (email.equalsIgnoreCase(sellerEmail)) {
                    String productName = product_details.get(i).getString("ProductName");
                    String productPrice = String.valueOf(product_details.get(i).getInteger("ProductPrice"));
                    String productDesc = product_details.get(i).getString("ProductDesc");
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("PRODUCT NAME: ").append(productName).append(" PRODUCT PRICE: ").append(productPrice).append(" PRODUCT DESCRIPTION: ").append(productDesc);
                    SellerBackend.productName.add(productName);
                    arr.add(stringBuilder.toString());
                }
            }
        }


        return arr;
    }


    public boolean deleteItem(String categoryName, String productName) {
        String sql_ = "SELECT CategoryID FROM Categories Where CategoryName='" + categoryName + "'";

        int categoryID = mongoDatabase.getCollection("Categories").find(new Document("CategoryName", categoryName)).first().getInteger("CategoryID");

        System.out.println(categoryID);

        mongoDatabase.getCollection("Products").deleteOne(new Document("Seller_Email", sellerEmail).append("CategoryID", categoryID).append("ProductName", productName));

//        System.out.println(mongoDatabase.getCollection("Products").find(new Document("CategoryID", categoryID).append("ProductName", productName).append("Seller_Email", sellerEmail)).first().toJson());

        return true;
    }

    public ArrayList<String> showMessages() {

        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("HelpChat");
        MongoCursor<Document> document = mongoCollection.find(new Document("Seller_Email", sellerEmail)).cursor();
        ArrayList<String> messages = new ArrayList<>();
        help = new ArrayList<>();

        while (document.hasNext()) {
            Document rs = document.next();
            String helpDesc = rs.getString("HelpDescription");
            String helpTitle = rs.getString("HelpTitle");
            String buyerEmail = rs.getString("Buyer_Email");
            StringBuilder stringBuilder = new StringBuilder();


            stringBuilder.append("MESSAGE DESCRIPTION: ").append(helpDesc).append("     MESSAGE TITLE: ").append(helpTitle).append("     BUYER EMAIL: ").append(buyerEmail);
            SellerBackend.help.add(helpDesc);
            messages.add(stringBuilder.toString());
        }
        return messages;
    }
}

class Mem {
    public static void main(String[] args) throws SQLException {
        SellerBackend sellerBackend = new SellerBackend();
        sellerBackend.deleteItem("Laptop", "HP Notebook");
    }

}
