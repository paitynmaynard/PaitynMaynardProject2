import models.Attraction;
import org.junit.Test;

/**
 * Initialized by Jean Aldoph II
 * The testing for all functions of the park manager service
 * These tests are to cover 50% of all methods which have been
 * implemented.
 *
 *version 0.0: Jean D Aldoph II
 */


public class FullTests
{

    /**
     * Model AND DAO Tests, which should determine if any of our model classes have improper functionalities.
      */



    /**
     * Testing Attraction Constructors and DAOs
     */
    @Test
    public void test_Constructor_Attraction()
    {
        Attraction atr = new Attraction("name","hhtp://stuff.com","Operational",10,5);
        System.out.println(atr);
    }

    /**
     * Testing Maint_Ticket Constructors and DAOs
     */
    @Test
    public void test_Constructor_Maintenance_Ticket()
    {

    }

    /**
     * Testing Employee Model Constructors and DAOs
     */
    @Test
    public void test_Constructor_Employee()
    {

    }


}
