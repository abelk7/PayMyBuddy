import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) {
    }

    addConnection(emailSender: string, emailRecipient: string) {

        let body = new URLSearchParams();
        body.set('emailSender', emailSender);
        body.set('emailRecipient', emailRecipient);

        return new Promise((resolve, reject) => {
            this.http.post<any>(`${environment.apiUrl}/addConnection`, body).subscribe({
                next: (response) => {
                    resolve(response);
                },
                error: (error) => {
                    reject(error);
                }
            })
        })
    }


}
