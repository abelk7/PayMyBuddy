import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AlertService} from "../service/alert.service";
import {TransferService} from "../service/transfer.service";
import {first} from "rxjs/operators";
import {Router} from "@angular/router";
import {Alert} from "../model/alert";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {UserService} from "../service/user.service";
import * as stream from "stream";


@Component({
    selector: 'app-transfer',
    templateUrl: './transfer.component.html',
    styleUrls: ['./transfer.component.css']
})

export class TransferComponent implements OnInit {

    // @ts-ignore
    @ViewChild('closeModal') closeModal: ElementRef

    emailAddConnection!: string;
    errorAddConnection?: any;

    constructor(
        private alertService: AlertService,
        private transferService: TransferService,
        private userService: UserService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
    }

    onAddConnection() {
        if (this.emailAddConnection == null) {
            this.errorAddConnection = "L'adresse email doit être défini correctement.";
            return;
        }
        let emailSender  = sessionStorage.getItem("userEmail");

        // @ts-ignore
        this.userService.addConnection(emailSender ,this.emailAddConnection)
            .then((response: any) => {
                this.closeModal.nativeElement.click();
                this.alertService.success("La demande de connection à été envoyé.", {keepAfterRouteChange: true});

            }, (error) => {
                this.errorAddConnection ="L'utilisateur saisie n'a pas été trouvé.";
            });
    }


    //
    //         .subscribe({
    //             next: (data) => {
    //                 this.alertService.success('La demande de connexion à été envoyé avec succès.', {keepAfterRouteChange: true});
    //
    //             },
    //             error: (error) => {
    //                 this.errorAddConnection = error.message;
    //                 //this.alertService.error(error.error.message, {keepAfterRouteChange: true});
    //             }
    //         })
    //
    // }

    removeAlert() {
        this.errorAddConnection = null;
    }


}


