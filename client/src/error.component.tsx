import React from "react";

export class ErrorComponent extends React.Component<any, any> {
	public static addError(err: any, context?: {}) {
		console.log(err, context);
	}
}
