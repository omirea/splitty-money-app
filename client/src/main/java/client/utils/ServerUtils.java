/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
	private static final String SERVER = "http://localhost:8080/";

		public String json(String invitationID){
		return ClientBuilder.newClient(new ClientConfig())
			.target(SERVER).path("event/json/" + invitationID)
			.request(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.get()
			.readEntity(String.class);
	}



	/**
	 * method to getAll participants from the database
	 * connects ui to getAll endpoint
	 * @return list of all participants
	 */
	public List<Participant> getAllParticipants(){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("participant")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Participant>>() {});
	}

	/**
	 * method to get a specific participant based on id
	 * @param id id of the participant requested
	 * @return the requested participant
	 */
	public Participant getParticipantById(Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("participant/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Participant>() {});
	}

	/**
	 * method to delete a specific participant from the database
	 * @param id the id of the participant to be deleted
	 * @return the deleted participant
	 */
	public Participant deleteParticipant(Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("participant/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.delete(new GenericType<Participant>() {});
	}

	/**
	 * method to create a new participant in the database
	 * @param participant participant to be added to the database
	 * @return the created participant
	 */
	public Participant createParticipant(Participant participant){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("participant")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(participant , APPLICATION_JSON),
						Participant.class);
	}

	/**
	 * method to update a participant from the database
	 * @param participant participant to be updated in the database
	 * @return the updated participant
	 */
	public Participant updateParticipant(Participant participant, Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("participant/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(participant, APPLICATION_JSON),
					Participant.class);
	}

	public List<Participant> getParticipantsByInvitationId(String invitationId) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("event/" + invitationId + "/participant")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Participant>>() {});
	}

	/**
	 *
	 * @return a list of events
	 */
	public List<Event> getAllEvents(){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("event")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Event>>() {});
	}


	/**
	 * method to get a specific Event based on invitationID
	 * @param invitationID invitationID of the Event requested
	 * @return the requested Event
	 */
	public Event getEventByInvitationId(String invitationID){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("event/" + invitationID)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Event>() {});
	}

	/**
	 * method to delete a specific Event  from the database
	 * @param id the id of the Event  to be deleted
	 * @return the deleted Event
	 */
	public Event deleteEvent (long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("event/delete/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.delete(new GenericType<Event>() {});
	}

	/**
	 * method to create a new Event  in the database
	 * @param event  Event  to be added to the database
	 * @return the created Event
	 */
	public Event createEvent(Event event){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("event")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(event, APPLICATION_JSON),
						Event.class);
	}

	/**
	 * method to update an Event  from the database
	 * @param event Event  to be updated in the database
	 * @return the updated Event
	 */
	public Event updateEvent(Event event, long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("event/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(event, APPLICATION_JSON),
						Event.class);
	}


	/**
	 * addExpense method
	 * @param expense - expense object
	 * @return added Expense
	 */
	public Expense createExpense(Expense expense) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("expense")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(expense, APPLICATION_JSON),
						Expense.class);
	}

	/**
	 * getExpense method
	 * @param id - get expense by id
	 * @return gotten Expense
	 */
	public Expense getExpenseByID(long id) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("expense/" + id)
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
				.target(SERVER).path("expense")
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
				.target(SERVER).path("expense/" + id)
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
				.target(SERVER).path("expense/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(expense, APPLICATION_JSON), Expense.class);
	}

	/**
	 * get all debts from the database
	 * @return list of all debts
	 */
	public List<Debt> getAllDebts(){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("debt")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Debt>>() {});
	}

	/**
	 * get a specific debt based on id
	 * @param id id of the debt requested
	 * @return the requested debt
	 */
	public Debt getDebtById(Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("debt/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Debt>() {});
	}

	/**
	 * method to delete a specific debt from the database
	 * @param id the id of the debt to be deleted
	 * @return the deleted debt
	 */
	public Debt deleteDebt(Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("debt/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.delete(new GenericType<Debt>() {});
	}

	/**
	 * create a new debt in the database
	 * @param debt to be added to the database
	 * @return the created debt
	 */
	public Debt createDebt(Debt debt){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("debt")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.post(Entity.entity(debt, APPLICATION_JSON),
						Debt.class);
	}

	/**
	 * update a debt from the database
	 * @param debt to be updated in the database
	 * @return the updated debt
	 */
	public Debt updateDebt(Debt debt, Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("debt/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(debt, APPLICATION_JSON),
						Debt.class);
	}

	/**
	 * compares the password to the server password
	 * @param password is the password inputted
	 * @return true if equal, false otherwise
	 */
	public boolean checkPassword(String password) {
		return ClientBuilder.newClient(new ClientConfig())
			.target(SERVER).path("admin/" + password)
			.request(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.get(new GenericType<>() {});
	}

	/**
	 * generates a new password to input
	 */
	public void generatePassword() {
		ClientBuilder.newClient(new ClientConfig())
			.target(SERVER).path("admin/")
			.request(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
				.get();
	}
}