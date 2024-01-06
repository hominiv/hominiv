import axios, {AxiosResponse} from "axios";
import {Person} from "../models/person.models.ts";

const LOCAL_URL: string = 'http://localhost:8080';
const PERSONS_URL: string = '/him';

export const ApiService = {

    getPersons(): Promise<AxiosResponse> {
        return axios.get(LOCAL_URL + PERSONS_URL);
    },

    addPerson(person: Person): Promise<AxiosResponse> {
        return axios.put(LOCAL_URL + PERSONS_URL, person);
    },

    removePerson(userId: number): Promise<AxiosResponse> {
        return axios.delete(LOCAL_URL + PERSONS_URL + "/" + userId);
    }
}