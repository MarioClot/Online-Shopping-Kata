package codingdojo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CartTest {
    Cart cart;
    Item item = new Item("Simple item", "EVENT", 0);
    Item item2 = new Item("Simple item", "Item", 30);

    @Mock
    Store store;

    @Before
    public void setUp() throws Exception {
        initMocks( this );
        cart = new Cart();
    }

    @Test
    public void updateUnavailableItems() {
        cart.addItem( item );

        cart.updateUnavailableItems();

        assertEquals( cart.getUnavailableItems().size(), 1 );
    }

    @Test
    public void updateUnavailableItemsOnlyEvent() {
        cart.addItem( item );
        cart.addItem( item2 );

        cart.updateUnavailableItems();

        assertEquals( cart.getUnavailableItems().size(), 1 );
    }

    @Test
    public void storeToSwitchDontHaveItemEvent() {
        when( store.hasItem( item ) ).thenReturn( true );

        cart.addItem( item );
        cart.updateUnavailableItems( store );

        verify( store ).hasItem( item );
        assertEquals( cart.getUnavailableItems().size(), 1 );
        assertEquals( cart.getItems().size(), 2 );

    }

    @Test
    public void storeToSwitchDontHaveItem() {
        when( store.hasItem( item2 ) ).thenReturn( false );

        cart.addItem( item2 );
        cart.updateUnavailableItems( store );

        verify( store ).hasItem( item2 );
        assertEquals( 1, cart.getUnavailableItems().size() );
        assertEquals( 1, cart.getItems().size() );

    }

    @Test
    public void checkItemsWeight() {
        when( store.hasItem( item2 ) ).thenReturn( true );

        cart.addItem( item2 );
        cart.updateUnavailableItems( store );
        long totalWeight = cart.getWeight();
        assertEquals( item2.getWeight(), totalWeight );
    }

    @Test
    public void storeIsNull() {
        cart.addItem( item );

        cart.updateUnavailableItems(null);

        assertEquals( cart.getUnavailableItems().size(), 1 );
    }
}