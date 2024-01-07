import { ApiService } from "../service/api.service.ts";
import { ErrorComponent } from "../error.component.tsx";
import * as React from "react";

export class NewPersonComponent extends React.Component<any, any> {
	state: {
		firstName: string;
		lastName: string;
		status: boolean;
	};

	constructor(props: any) {
		super(props);
		this.state = {
			firstName: "",
			lastName: "",
			status: false,
		};
	}

	addRow(): void {
		ApiService.addPerson({
			userId: 0,
			firstName: this.state.firstName,
			lastName: this.state.lastName,
			isHim: this.state.status,
		})
			.then(() =>
				this.setState({
					firstName: "",
					lastName: "",
					status: false,
				}),
			)
			.catch((netErr) => ErrorComponent.addError(netErr));
	}

	render(): React.JSX.Element {
		return (
			<div className={"input-group-lg input-group-text inputs"}>
				<div className={"input-group-text"}>
					<label htmlFor={"first-name"} className={"label"}>
						First Name:
					</label>
					<input
						id={"first-name"}
						value={this.state.firstName}
						onChange={(e) =>
							this.setState((prevState: any) => ({
								...prevState,
								firstName: e.target.value,
							}))
						}
						type={"text"}
					/>
				</div>
				<div className={"input-group-text"}>
					<label htmlFor={"last-name"} className={"label"}>
						Last Name:
					</label>
					<input
						id={"last-name"}
						value={this.state.lastName}
						onChange={(e) =>
							this.setState((prevState: any) => ({
								...prevState,
								lastName: e.target.value,
							}))
						}
						type={"text"}
					/>
				</div>
				<div className={"input-group-text"}>
					<label htmlFor={"is-him"} className={"label"}>
						Is Him?
					</label>
					<input
						id={"is-him"}
						checked={this.state.status}
						onChange={() => {
							this.setState((prevState: any) => ({
								...prevState,
								status: !prevState.status,
							}));
						}}
						type={"checkbox"}
					/>
				</div>
				<div className={"button button-group"}>
					<button
						type={"submit"}
						onClick={() => this.addRow()}
						className={"button"}
					>
						Submit
					</button>
				</div>
			</div>
		);
	}
}
