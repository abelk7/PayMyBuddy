export class Alert {
    id?: string;
    type?: AlertType;
    message?: string;
    autoClose?: boolean;
    keepAfterRouteChange?: boolean;
    fade?: boolean;
    keeptime : number  = 5000;

    constructor(init?: Partial<Alert>) {
        Object.assign(this, init);
    }
}

export enum AlertType {
    Success,
    Error,
    Info,
    Warning
}
