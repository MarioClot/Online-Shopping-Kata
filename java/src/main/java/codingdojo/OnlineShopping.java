package codingdojo;

import java.util.ArrayList;

/**
 * The online shopping company owns a chain of Stores selling
 * makeup and beauty products.
 * <p>
 * Customers using the online shopping website can choose a Store then
 * can put Items available at that store into their Cart.
 * <p>
 * If no store is selected, then items are shipped from
 * a central warehouse.
 */
public class OnlineShopping {

    private Session session;

    public OnlineShopping(Session session) {
        this.session = session;
    }

    /**
     * This method is called when the user changes the
     * store they are shopping at in the online shopping
     * website.
     */
    public void switchStore(Store storeToSwitchTo) {
        Cart cart = (Cart) session.get("CART");
        DeliveryInformation deliveryInformation = (DeliveryInformation) session.get("DELIVERY_INFO");

        if (cart != null) {
            cart.updateUnavailableItems(storeToSwitchTo);

            LocationService locationService= ((LocationService) session.get("LOCATION_SERVICE"));

            if (deliveryInformation != null) {
                deliveryInformation.configureDeliveryInformation( storeToSwitchTo, locationService );
                deliveryInformation.setTotalWeight( cart.getWeight() );
            }
        }
        session.put("STORE", storeToSwitchTo);
        session.saveAll();
    }

    @Override
    public String toString() {
        return "OnlineShopping{\n"
                + "session=" + session + "\n}";
    }
}
