/**
 * JS / TS interfaces are a lot more useful than their Java alternative.
 * Basic object definitions like this give us a straightforward data
 * format expectation with the ability to mutate as we please. We can
 * make any of the fields optional, or even use them to store functions.
 */
export interface Person {
	userId: number;
	isHim: boolean;
	firstName: string;
	lastName: string;
	// EXAMPLE - DON'T USE THIS FIELD!
	optionalField?: Function;
}

// EXAMPLE CODE
/**
 * Just like nearly every other language we can implement our
 * interfaces/structures. TS/JS does this similarly to Java with classes
 */
export class PersonImpl implements Person {
	// Class constructors can be implicit! No need for a typical
	// constructor when we define *all* our structure values!
	// (this is actually how we can declare anonymous instances of our interface)
	//
	// Obviously using a constructor would allow us to generate new instances
	// of this class implementation that have different internal values...
	userId = 0;
	isHim = false;
	firstName = "";
	lastName = "";

	// constructor(...) {...}

	optionalField = () => {
		console.log("Hello hominiv!");
	};

	// Implement independent functions just like Java classes
	equals(p: Person): boolean {
		return (
			this.userId === p.userId &&
			this.isHim === p.isHim &&
			this.firstName === p.firstName &&
			this.lastName === p.lastName
		);
	}
}
