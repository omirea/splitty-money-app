package client.utils;

import commons.Expense;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ExpenseServerUtils {

    private static final String SERVER = "http://localhost:8080/";


    public Expense addExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }


    public Expense getExpense() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Expense>() {});
    }

    public Expense deleteExpense() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(new GenericType<Expense>() {});
    }

    public Expense updateExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }


}
