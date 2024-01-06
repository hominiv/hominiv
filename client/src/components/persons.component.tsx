import * as React from "react";
import {Person} from "../models/person.models.ts";
import {ApiService} from "../service/api.service.ts";
import {AxiosResponse} from "axios";
import "./persons.component.css";

export class PersonsComponent extends React.Component<any, any>{

    // React's state management is a bit weird. There may be
    // a much better way to do this.
    state: {
        persons: Person[],
        firstName: string,
        lastName: string,
        status: boolean };

    // Constructor elements here are mandated by React. I'm not
    // entirely sure why, but you will need to do this for every
    // new component.
    constructor(props: any) {
        super(props);
        this.state = {
            persons: [],
            firstName: "",
            lastName: "",
            status: false
        };
    }

    // These two functions are built into React, commonly known as
    // "lifecycle hooks". They are called behind the scenes after
    // certain events occur, thus allowing you some control over
    // when and where things load / happen.
    // TODO: these could probably be replaced with "useEffect()"
    async componentDidMount(): Promise<void> {
        await this.getRows();
    }
    async componentDidUpdate(): Promise<void> {
        await this.getRows();
    }

    // Asynchronous functions allow us to wait for actions to take effect
    // before proceeding. This is particularly useful when we don't know
    // how long things will take to happen.
    async getRows(): Promise<void> {
        await ApiService.getPersons().then((response: AxiosResponse): void => {
            this.setState((prevState: any) => ({...prevState, persons: response.data}));
        });
    }

    // We can dynamically mount HTML directly into our template through
    // functions in React. Ignoring the nasty "isArray" check, here we
    // pull the persons list out of our state, and map each person to
    // a JSX table row - returning an array of JSX <tr> elements.
    mountRows(): React.JSX.Element[] {
        // For some reason React doesn't initialize the persons value
        // as an array...
        //
        // Wait for the function to be called again after proper
        // initialization has occurred. (it's called *numerous* times
        // because front end frameworks are very messy behind the scenes
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

    // These function don't need to be async because none of our page
    // is reliant on the data they return. Using the promise's "then"
    // function, we're just telling the app what to do when it hears a
    // response back. In place of "then", "catch" can be used for error
    // cases, and "finally" will run last regardless of state (valid or error)
    addRow(): void {
        ApiService.addPerson({
            userId: 0,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            isHim: this.state.status
        }).then((response: AxiosResponse): void => {
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

    // render() is a built in react function that generates our JSX (HTML) page for us.
    // We can reference our imported CSS file from it, and put anonymous functions
    // directly into the elements.
    //
    // Note: anonymous functions occur with any lambda ( => ) operator. They are essentially
    // a value set being 'directed' by the arrow into a nameless function.
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

