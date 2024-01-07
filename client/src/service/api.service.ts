import axios, { AxiosResponse } from "axios";
import { Person } from "../models/person.models.ts";

// TODO: environment needs to be set up, because obviously these values won't work in a production setting...
const LOCAL_URL: string = "http://localhost:8080";
const PERSONS_URL: string = "/him";

export const ApiService = {
	/**
	 * These HTTP calls are pretty straight forward. We just need to define an HTTP path/URL,
	 * HTTP method, and request body values if applicable.
	 *
	 *     @Request-Parameters: append to the URL with "?paramName1=paramValue1&paramName2=paramValue2&..."
	 *     @Request-Body: AXIOS handles this for us, but generally HTTP will expect JSON formatted values.
	 *                    Server endpoints can be setup to handle whatever object type you want them to.
	 *     @Path-Variables: Typically URLs are static, but request variables allow us to decode them into
	 *                      different parts and reference different values based on those.
	 */
	getPersons(): Promise<AxiosResponse> {
		// GET request with no other options
		return axios.get(LOCAL_URL + PERSONS_URL);
	},

	addPerson(person: Person): Promise<AxiosResponse> {
		// PUT request with Person as the request body
		return axios.put(LOCAL_URL + PERSONS_URL, person);
	},

	removePerson(userId: number): Promise<AxiosResponse> {
		// DELETE request with a path variable of the user ID
		return axios.delete(LOCAL_URL + PERSONS_URL + "/" + userId);
	},
};
