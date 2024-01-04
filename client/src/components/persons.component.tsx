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

    constructor(props: null) {
        super(props);
        this.state = {
            persons: [],
            firstName: "",
            lastName: "",
            status: false
        };
    }

    async componentDidMount() {
        await ApiService.getPersons().then((response: AxiosResponse) => {
            this.setState({...this.state, persons: response.data});
        });
    }

    addRow(): void {

        ApiService.addPerson({
            userId: 0,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            isHim: this.state.status
        }).then((response: AxiosResponse) => {
            // TODO: render this dynamically...
            this.setState({
                persons: this.state.persons.push(response.data),
                firstName: "",
                lastName: "",
                status: false
            });
        });
    }

    render() {
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
                    <tbody>{this.state.persons.map((person: Person) =>
                        <tr key={person.userId}>
                            <td>{person.isHim.toString()}</td>
                            <td>{person.firstName}</td>
                            <td>{person.lastName}</td>
                        </tr>
                    )}</tbody>
                </table>
                <div className={"input-group-lg input-group-text inputs"}>
                    <div className={"input-group-text"}>
                        <label htmlFor={"first-name"} className={"label"}>First Name: </label>
                        <input id={"first-name"} onChange={e =>
                            this.setState({...this.state, firstName: e.target.value})} type={"text"}/>
                    </div>
                    <div className={"input-group-text"}>
                        <label htmlFor={"last-name"} className={"label"}>Last Name:</label>
                        <input id={"last-name"} onChange={e =>
                            this.setState({...this.state, lastName: e.target.value})} type={"text"}/>
                    </div>
                    <div className={"input-group-text"}>
                        <label htmlFor={"is-him"} className={"label"}>Is Him?</label>
                        <input id={"is-him"} onChange={() =>
                            this.setState({...this.state, status: !this.state?.status})} type={"checkbox"}/>
                    </div>
                    <div className={"button button-group"}>
                        <button type={"submit"} onClick={() => this.addRow()} className={"button"}>Submit</button>
                    </div>
                </div>
            </>
        )
    }
}

