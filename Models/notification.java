package notifications;


public class Notifications {
private String message;
private String productID;
private String type;

public Notifications(String message,String productID ,String type){
    this.message= message;
    this.productID= productID;
    this.type= type;
}

public String getmessage() {
        return message;
    }

    public String getProductID() {
        return productID;
    }

    public String getType() {
        return type;
    }

   
    public void setmessage(String message) {
        this.message = message;
    }

    public void setProductId(String productID) {
        this.productID = productID;
    }

    public void setType(String type) {
        this.type = type;
    }
}

    
    

