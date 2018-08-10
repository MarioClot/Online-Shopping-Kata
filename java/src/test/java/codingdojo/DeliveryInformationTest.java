package codingdojo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeliveryInformationTest {

    @Mock
    Store store;
    @Mock
    Store storeToSwitch;
    @Mock
    LocationService locationService;

    @Before
    public void setUp() throws Exception {
        initMocks( this );

    }

    @Test
    public void storeToSwitchIsNull() {
        when( locationService.isWithinDeliveryRange( storeToSwitch, "NEARBY")).thenReturn( true );
        DeliveryInformation deliveryInformation = new DeliveryInformation("HOME_DELIVERY", store, 30);
        deliveryInformation.setDeliveryAddress( "NEARBY" );
        deliveryInformation.configureDeliveryInformation( null, locationService );

        assertEquals( "SHIPPING", deliveryInformation.getType() );
    }

    @Test
    public void addressIsWithin() {
        when( locationService.isWithinDeliveryRange( storeToSwitch, "NEARBY")).thenReturn( true );
        DeliveryInformation deliveryInformation = new DeliveryInformation("PICK_UP", store, 500);
        deliveryInformation.setDeliveryAddress( "NEARBY" );
        deliveryInformation.configureDeliveryInformation( storeToSwitch, locationService);

        assertEquals( "HOME_DELIVERY", deliveryInformation.getType() );
    }

    @Test
    public  void deliveryIsHome() {
        when( locationService.isWithinDeliveryRange( storeToSwitch, "NOT_NEARBY")).thenReturn( false );
        DeliveryInformation deliveryInformation = new DeliveryInformation("HOME_DELIVERY", store, 30);
        deliveryInformation.setDeliveryAddress( "NEARBY" );
        deliveryInformation.configureDeliveryInformation( storeToSwitch, locationService);

        assertEquals( "PICK_UP", deliveryInformation.getType() );
        assertEquals( store, deliveryInformation.getPickupLocation() );
    }

    @Test
    public void isNotWithin() {
        DeliveryInformation deliveryInformation = new DeliveryInformation("HOME_DELIVERY", store, 30);
        deliveryInformation.setDeliveryAddress( null );
        deliveryInformation.configureDeliveryInformation( storeToSwitch, locationService);

        assertEquals( "PICK_UP", deliveryInformation.getType() );
        assertEquals( store, deliveryInformation.getPickupLocation() );
    }

    @Test
    public void droneDelivery() {
        when( locationService.isWithinDeliveryRange( storeToSwitch, "NEARBY")).thenReturn( true );
        when( storeToSwitch.hasDroneDelivery()).thenReturn( true );
        DeliveryInformation deliveryInformation = new DeliveryInformation("HOME_DELIVERY", store, 30);
        deliveryInformation.setDeliveryAddress( "NEARBY" );
        deliveryInformation.configureDeliveryInformation( storeToSwitch, locationService);

        assertEquals( "DRONE_DELIVERY", deliveryInformation.getType() );
        assertEquals( storeToSwitch, deliveryInformation.getPickupLocation() );
    }

    @Test
    public void droneDeliveryNoDrone() {
        when( locationService.isWithinDeliveryRange( storeToSwitch, "NEARBY")).thenReturn( true );
        when( storeToSwitch.hasDroneDelivery()).thenReturn( false );
        DeliveryInformation deliveryInformation = new DeliveryInformation("HOME_DELIVERY", store, 30);
        deliveryInformation.setDeliveryAddress( "NEARBY" );
        deliveryInformation.configureDeliveryInformation( storeToSwitch, locationService);

        assertEquals( "HOME_DELIVERY", deliveryInformation.getType() );
        assertEquals( storeToSwitch, deliveryInformation.getPickupLocation() );
    }
}