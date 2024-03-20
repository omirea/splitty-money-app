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

import commons.Participant;
import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

	private static final String SERVER = "http://localhost:8080/";

	public List<Participant> getAllParticipants(){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("/participant")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Participant>>() {});
	}

	public Participant getParticipantById(Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("/participant/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<Participant>() {});
	}

	public Participant deleteParticipant(Long id){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path("/participant/" + id)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.delete(new GenericType<Participant>() {});
	}

	public Participant createParticipant(Participant participant){
		return ClientBuilder.newClient(new ClientConfig())
				.target(SERVER).path({" ","/"} + participant)
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.c(new GenericType<Participant>() {});
	}


}