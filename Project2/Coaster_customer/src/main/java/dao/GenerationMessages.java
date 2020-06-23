package dao;

import models.Customer;
import models.Ticket;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import utils.PostgresConnectionUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class GenerationMessages {

    public static void events(String id, Integer number)
    {
        CustomerDAO og = new CustomerDAO(new PostgresConnectionUtil());
        System.out.println("GOT A MESSAGE");
        Random rand = new Random();
        int randomMonth = rand.nextInt(12);
        if (randomMonth < 6) randomMonth = 6;
        int randomDay = rand.nextInt(30);
        int randomStay = rand.nextInt(21);
        LocalDateTime ldt = LocalDateTime.now();
        ldt.plusMonths(randomMonth);
        ldt.plusDays(randomDay);
        Customer c = og.findById(id);
        TicketDAO tickets = new TicketDAO(new PostgresConnectionUtil());
        for (int i = 0; i < number; i++)
        {
            tickets.save(new Ticket(c.getCustomerID(), rand.nextInt(3), ldt, ldt.plusDays(randomStay)));
        }
        System.out.println(String.format("Ticket Generation Event:\n" +
                "Created: %d tickets \n" +
                "for customer: %s", number, c.getEmail()));


//        Jedis jedis = new Jedis("redis-clusterip", 6379);
//
//        jedis.subscribe(new JedisPubSub() {
//            @Override
//            public void onMessage(String channel, String message)
//            {
//                Random rand = new Random();
//                int randomMonth = rand.nextInt(12);
//                if (randomMonth < 6) randomMonth = 6;
//                int randomDay = rand.nextInt(30);
//                int randomStay = rand.nextInt(21);
//                LocalDateTime ldt = LocalDateTime.now();
//                ldt.plusMonths(randomMonth);
//                ldt.plusDays(randomDay);
//                String[] split = message.split("!");
//                int numberOfTickets = new Integer(split[5]);
//                Customer c = new Customer(new Integer(split[0]),split[1],split[2],split[3],split[4]);
//                TicketDAO tickets = new TicketDAO(new PostgresConnectionUtil());
//
//                for (int i = 0; i < numberOfTickets; i++)
//                {
//                    tickets.save(new Ticket(c.getCustomerID(), rand.nextInt(3), ldt, ldt.plusDays(randomStay)));
//                }
//                System.out.println(String.format("Ticket Generation Event:\n" +
//                        "Created: %d tickets \n" +
//                        "for customer: %s", numberOfTickets, c.getEmail()));
//            }
//        }, "TicketGeneration");

    }
}
