package Logic;

import DatabaseHandler.Database;
import DatabaseHandler.DatabaseMongo;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuyerBackend extends DatabaseMongo {
    public static String buyerEmail;
    public static String buyerFullName;
    public static ArrayList<String> buyerItemName = new ArrayList<>();
    public static ArrayList<Integer> buyerItemPrice = new ArrayList<>();

    public BuyerBackend() throws SQLException {

    }

    public boolean validateBuyer(String emailAddress, String password) {

        MongoCollection<Document> collection = mongoDatabase.getCollection("Buyer");

        Document valid = collection.find(new Document("Email", emailAddress).append("Password", password)).first();
        System.out.println(valid);

        if (valid != null) {
            buyerEmail = emailAddress;
        }
        return valid != null;
    }


    //
    public ArrayList<String> getCategoryName() {
        String validation = "SELECT CategoryName FROM categories";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Categories");

        MongoCursor<Document> cursor = collection.find().iterator();
        ArrayList<String> categories = new ArrayList<>();

        while (cursor.hasNext()) {
            Document rs = cursor.next();
            String categories_ = rs.getString("CategoryName");
            categories.add(categories_);

        }

        return categories;
    }

    //
    public String getDeliveryAddress() {
        return mongoDatabase.getCollection("Buyer").find(new Document("Email", buyerEmail)).first().getString("DeliveryAddress");

    }

    //
    public ArrayList<String> categoryItems(String item) {

        String validation = "SELECT * FROM categories NATURAL JOIN products WHERE CategoryName= '" + item + "'";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Categories");

        List<Bson> pipeline = Arrays.asList(new Document("$lookup", new Document()
                        .append("from", "Products")
                        .append("localField", "CategoryID")
                        .append("foreignField", "CategoryID")
                        .append("as", "Category_Products")),
                new Document("$match", new Document("CategoryName", item)));


        ArrayList<Document> documentArrayList = (ArrayList<Document>) collection.aggregate(pipeline).first().get("Category_Products");
        ArrayList<String> arr = new ArrayList<>();

        if (documentArrayList.isEmpty()) return new ArrayList<>();

        for (int i = 0; i < documentArrayList.size(); i++) {
            StringBuilder desc = new StringBuilder();
            String name = documentArrayList.get(i).getString("ProductName");
            String price = String.valueOf(documentArrayList.get(i).getInteger("ProductPrice"));
            String description = documentArrayList.get(i).getString("ProductDesc");
            desc.append("PRODUCT NAME: ").append(name).append("     PRICE: ").append(price).append("     PRODUCT DESCRIPTION: ").append(description);
            arr.add(desc.toString());
        }

        return arr;
    }

    public boolean addToCart(String description, int qty) {
        String sql_1 = "SELECT DISTINCT ProductID,ProductDesc FROM products NATURAL JOIN buyer WHERE ProductDesc='" + description + "'";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Products");
        Document document = collection.find(new Document("ProductDesc", description)).first();
        if (document == null) return false;

        int productID = document.getInteger("ProductID");

        collection = mongoDatabase.getCollection("Cart");
        document = collection.find(new Document("ProductID", productID)).first();
        int qty_ = 0;
        if (document != null) {
            qty_ = document.getInteger("Quantity");
        }
        if (qty_ > 0) {
            Document query = new Document("ProductID", productID);
            UpdateOptions options = new UpdateOptions().upsert(true);
            Bson updates = Updates.combine(
                    Updates.set("Quantity", (qty_ + qty))
            );

            return collection.updateOne(query, updates, options).getModifiedCount() > 0;
        } else {
            ArrayList<Integer> integerArrayList = new ArrayList<>();
            MongoCursor<Document> cardID = collection.find().iterator();
            while (cardID.hasNext()) {
                Document rs = cardID.next();
                integerArrayList.add(rs.getInteger("CartID"));
            }
            collection.insertOne(new Document("CartID", (integerArrayList.size() + 1)).append("ProductID", productID).append("Quantity", qty).append("Buyer_Email", buyerEmail)).getInsertedId();
            return true;
        }
    }


    public String getProductDescription(String description) {


        MongoCursor<Document> cursor = mongoDatabase.getCollection("Products").find().cursor();

        while (cursor.hasNext()) {
            Document rs = cursor.next();
            String desc = rs.getString("ProductDesc");

            if (description.endsWith(desc)) {
                System.out.println(desc);
                return desc;
            }
        }
        return null;
    }


    public ArrayList<String> getCart() {
        String sql = "SELECT DISTINCT * FROM Cart NATURAL JOIN Products WHERE Buyer_Email='" + buyerEmail + "'";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Cart");

        List<Bson> pipeline = Arrays.asList(new Document("$lookup", new Document()
                        .append("from", "Products")
                        .append("localField", "ProductID")
                        .append("foreignField", "ProductID")
                        .append("as", "Product_Details")),
                new Document("$match", new Document("Buyer_Email", buyerEmail)));

        AggregateIterable<Document> aggregateIterable = collection.aggregate(pipeline);
        aggregateIterable.forEach(document -> System.out.println(document.toJson()));
        MongoCursor<Document> cursor = aggregateIterable.cursor();

        ArrayList<String> arr = new ArrayList<>();
        buyerItemName = new ArrayList<>();
        buyerItemPrice = new ArrayList<>();

        while (cursor.hasNext()) {
            StringBuilder desc = new StringBuilder();
            Document rs = cursor.next();

            ArrayList<Document> productDetails = (ArrayList<Document>) rs.get("Product_Details");
            String productName = null;
            Integer productPrice = null;
            String productDesc = "";
            for (int i = 0; i < productDetails.size(); i++) {
                productName = productDetails.get(i).getString("ProductName");
                productPrice = productDetails.get(i).getInteger("ProductPrice");
                productDesc = productDetails.get(i).getString("ProductDesc");
            }
            int productQty = rs.getInteger("Quantity");

            buyerItemName.add(productName);
            System.out.println("in get cart " + productName);
            buyerItemPrice.add(productPrice);

            desc.append("PRODUCT NAME: ").append(productName).append(" ITEM PRICE: ").append(productPrice).append(" PRODUCT DESCRIPTION: ").append(productDesc).append(" PRODUCT QTY: ").append(productQty);

            arr.add(desc.toString());

        }


        return arr;
    }


    public int getProductID(String desc) {
        String sql = "SELECT * FROM Cart NATURAL JOIN Products";

        MongoCursor<Document> cursor = mongoDatabase.getCollection("Products").find().cursor();

        while (cursor.hasNext()) {
            Document rs = cursor.next();
            String productName = rs.getString("ProductName");
            int productID = rs.getInteger("ProductID");
            if (desc.startsWith("PRODUCT NAME: " + productName)) {

                return productID;
            }

        }
        return 0;

    }

    public boolean removeFromSpecificCart(int productID) {
        String sql = "DELETE FROM Cart WHERE ProductID =" + productID;

        Document document = new Document("ProductID", productID);

        return mongoDatabase.getCollection("Cart").deleteOne(document).getDeletedCount() > 0;

    }


    public int getTotalPrice() {
        String sql = "SELECT *,(ProductPrice*Quantity) AS \"Total Price\" FROM Cart NATURAL JOIN Products WHERE Buyer_Email='" + buyerEmail + "'";

        MongoCollection<Document> collection = mongoDatabase.getCollection("Cart");


        Document d = mongoDatabase.getCollection("Buyer").find(new Document("Email", buyerEmail)).first();

        if (d != null) {
            buyerFullName = d.getString("FirstName") + " " + d.getString("LastName");
            System.out.println(buyerFullName);
        }

        List<Bson> pipeline = Arrays.asList(new Document("$lookup", new Document()
                        .append("from", "Products")
                        .append("localField", "ProductID")
                        .append("foreignField", "ProductID")
                        .append("as", "Product_Details")),
                new Document("$match", new Document("Buyer_Email", buyerEmail)));

        AggregateIterable<Document> aggregateIterable = collection.aggregate(pipeline);
        aggregateIterable.forEach(document -> System.out.println(document.toJson()));
        MongoCursor<Document> cursor = aggregateIterable.cursor();
        int totalPrice = 0;

        while (cursor.hasNext()) {
            Document rs = cursor.next();
            ArrayList<Document> product_details = (ArrayList<Document>) rs.get("Product_Details");
            int qty = rs.getInteger("Quantity");

            for (int i = 0; i < product_details.size(); i++) {
                int productPrize = product_details.get(i).getInteger("ProductPrice");
                System.out.println("prize" + productPrize);
                totalPrice += (productPrize * qty);
            }

        }
        return totalPrice;
    }

    public boolean truncateCart() {
        String sql = "DELETE FROM Cart Where Buyer_Email='" + buyerEmail + "'";

        MongoCursor<Document> collection = mongoDatabase.getCollection("Cart").find(new Document("Buyer_Email", buyerEmail)).cursor();

        while (collection.hasNext()) {
            Document rs = collection.next();
            mongoDatabase.getCollection("Cart").deleteOne(rs);
        }
        return true;
    }


    public boolean signupBuyer(String firstName, String lastName, String emailAddress, String phoneNumber, String gender, String address, String password) {

        // Email, FirstName, LastName, PhoneNumber, Gender, Password, DeliveryAddress
        MongoCollection<Document> collection = mongoDatabase.getCollection("Buyer");

        Document document = new Document("Email", emailAddress).append("FirstName", firstName).append("LastName", lastName).append("Gender", gender)
                .append("Password", password)
                .append("DeliveryAddress", address);

        collection.insertOne(document);
        return true;

    }

    public boolean addMessage(String HelpTitle, String HelpDescription, String Seller_Email) {
        mongoDatabase.getCollection("HelpChat").insertOne(new Document("HelpTitle", HelpTitle).append("Seller_Email", Seller_Email).append("HelpDescription", HelpDescription).append("Buyer_Email", buyerEmail));

        return true;
    }
}


class M {
    public static void main(String[] args) throws SQLException {
        BuyerBackend buyerBackend = new BuyerBackend();
        buyerBackend.getCart();

    }
}

