package client.utils;

import commons.Expense;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ExpenseServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * addExpense method
     * @param expense - expense object
     * @return added Expense
     */
    public Expense addExpense(Expense expense) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    /**
     * getExpense method
     * @param id - get expense by id
     * @return gotten Expense
     */
    public Expense getExpenseByID(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Expense>() {});
    }

    /**
     * get all expenses
     * @return all expenses
     */
    public Expense getAllExpenses() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Expense>() {});
    }

    /**
     * deleteExpense method
     * @return deleted Expense
     */
    public Expense deleteExpense(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(new GenericType<Expense>() {});
    }

    /**
     * updateExpense method
     * @param expense - expense object
     * @param id - expense id
     * @return updated Expense
     */
    public Expense updateExpense(Expense expense, long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/expense/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }


}
