package codingdojo;

/**
 * This class contains the information about how the customer would like to
 * have the contents of their shopping cart delivered to them.
 */
public class DeliveryInformation implements ModelObject {
    private String type;
    private String deliveryAddress;
    private Store pickupLocation;
    private long weight;

    public DeliveryInformation(String type, Store pickupLocation,
                               long weight) {
        this.type = type;
        this.pickupLocation = pickupLocation;
        this.weight = weight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setPickupLocation(Store store) {
        this.pickupLocation = store;
    }

    public Store getPickupLocation() {
        return this.pickupLocation;
    }

    public void setTotalWeight(long weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "DeliveryInformation{" + "\n" +
                "type='" + type + '\'' + "\n" +
                "deliveryAddress='" + deliveryAddress + '\'' + "\n" +
                "pickupLocation=" + pickupLocation + "\n" +
                "weight=" + weight + "\n" +
                '}';
    }

    @Override
    public void saveToDatabase() {
        throw new UnsupportedOperationException("missing from this exercise - shouldn't be called from a unit test");
    }

    public void configureDeliveryInformation(Store storeToSwitch, LocationService locationService) {
        Store currentStore = getPickupLocation();
        setPickupLocation(storeToSwitch);

        setDeliveryAddress(deliveryAddress);

        if (storeToSwitch != null) {
            boolean isHome = getType() != null && "HOME_DELIVERY".equals(getType());
            boolean isWithin = false;

            if (getDeliveryAddress() != null) {
                isWithin = locationService.isWithinDeliveryRange(storeToSwitch, getDeliveryAddress());
            }
            boolean canSendWithDrone = weight < 500 && deliveryAddress != null && getDeliveryAddress() != null && isWithin && storeToSwitch.hasDroneDelivery();


            if (canSendWithDrone) {
                setType("DRONE_DELIVERY");
            } else if (isHome && !isWithin) {
                setType("PICK_UP");
                setPickupLocation(currentStore);
            } else if (isWithin) {
                setType("HOME_DELIVERY");
            }


        } else {
            setType("SHIPPING");
        }
    }
}
