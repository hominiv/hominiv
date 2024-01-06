import * as React from "react";
import {Person} from "../models/person.models.ts";
import {ApiService} from "../service/api.service.ts";
import {AxiosResponse} from "axios";
import "./persons.component.css";

export class PersonsComponent extends React.Component<any, any>{

    state: {
        persons: Person[],
        firstName: string,
        lastName: string,
        status: boolean };

    constructor(props: any) {
        super(props);
        this.state = {
            persons: [],
            firstName: "",
            lastName: "",
            status: false
        };
    }

    async componentDidMount(): Promise<void> {
        await this.getRows();
    }
    async componentDidUpdate(): Promise<void> {
        await this.getRows();
    }

    async getRows() {
        await ApiService.getPersons().then((response: AxiosResponse): void => {
            this.setState((prevState: any) => ({...prevState, persons: response.data}));
        });
    }

    mountRows(): React.JSX.Element[] {
        // For some reason React doesn't initialize the persons value
        // as an array. Wait for the function to be called again after
        // proper initialization has occurred...
        if (Array.isArray(this.state.persons)) {
            return (
                this.state.persons.map(
                    (person: Person) =>
                        <tr key={person.userId}>
                            <td>{person.isHim.toString()}</td>
                            <td>{person.firstName}</td>
                            <td>{person.lastName}</td>
                            <td>
                                <button type={"submit"} onClick={(): void => {
                                    this.delete(person.userId)
                                }}></button>
                            </td>
                        </tr>
                )
            )
        } else {
            return [];
        }
    }

    addRow(): void {
        let person: Person = {
            userId: 0,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            isHim: this.state.status
        }
        ApiService.addPerson(person).then((response: AxiosResponse): void => {
            this.setState((prevState: any) => ({
                persons: prevState.persons.push(response.data),
                firstName: "",
                lastName: "",
                status: false
            }));
        });
    }

    delete(userId: number): void {
        ApiService.removePerson(userId).then((response: AxiosResponse): void => {
            let people: Person[] = this.state.persons;
            this.setState((prevState: any) => ({
                ...prevState,
                persons: people.filter((p: Person) => p.userId !== response.data)
            }));
        });
    }

    render(): React.JSX.Element {
        return (
            <>
                <h1>Who is him?</h1>
                <table className="table">
                    <thead>
                    <tr className="table-header, bold">
                        <td>Is Him?</td>
                        <td>First Name</td>
                        <td>Last Name</td>
                    </tr>
                    </thead>
                    <tbody>{this.mountRows()}</tbody>
                </table>
                <div className={"input-group-lg input-group-text inputs"}>
                    <div className={"input-group-text"}>
                        <label htmlFor={"first-name"} className={"label"}>First Name:</label>
                        <input id={"first-name"} value={this.state.firstName} onChange={e =>
                            this.setState((prevState: any) => ({...prevState, firstName: e.target.value}))} type={"text"}/>
                    </div>
                    <div className={"input-group-text"}>
                        <label htmlFor={"last-name"} className={"label"}>Last Name:</label>
                        <input id={"last-name"} value={this.state.lastName} onChange={e =>
                            this.setState((prevState: any) => ({...prevState, lastName: e.target.value}))} type={"text"}/>
                    </div>
                    <div className={"input-group-text"}>
                        <label htmlFor={"is-him"} className={"label"}>Is Him?</label>
                        <input id={"is-him"} onChange={() =>
                            this.setState((prevState: any) => ({...prevState, status: !prevState.status}))} type={"checkbox"}/>
                    </div>
                    <div className={"button button-group"}>
                        <button type={"submit"} onClick={() => this.addRow()} className={"button"}>Submit</button>
                    </div>
                </div>
            </>
        )
    }
}

